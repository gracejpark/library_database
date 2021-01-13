package ca.sheridancollege.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Book;
import ca.sheridancollege.beans.Review;
import ca.sheridancollege.database.DatabaseAccess;

/**
 * This is the Spring HomeController,
 * it is used to map all the controls.
 * 
 * @author Haydyn & Grace
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	//we will be calling on this class to access the database
	private DatabaseAccess database;

	/**
	 * Our one arg constructor for dependency injection
	 * @param database will be injected by Spring at runtime
	 */
	public HomeController(DatabaseAccess database) {
		this.database = database;
	}

	/**
	 * This method will be used by the framework whenever there is a request
	 * for the root of the website.
	 * The Getmapping provides the mapping to the root.
	 * @param model An instance of Model that can be used to send data
	 * @return "index" A html file marked up with Thymeleaf and displays the main page
	 */
	@GetMapping("/")
		public String goHome(Model model) {
			List<Book> books = database.getBook();
			model.addAttribute("bookList", books);
		return "index";
	}
	
	@RequestMapping("/books")
	public String books(Model model) {
		List<Book> books = database.getBook();
		model.addAttribute("bookList", books);
		return "books";
	}
	
	/**
	 * Method to login.
	 * @return "/login"
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * Method to display permission denied.
	 * @return "/error/permission-denied"
	 */
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "error/permission-denied";
	}

	/**
	 * Method to view the book reviews.
	 * @param id the id of the book to be used to view the reviews
	 * @param model An instance of Model that can be used to send data
	 * @return "view-book"
	 */
	@GetMapping("/viewBook/{id}")
	public String viewBook(@PathVariable Long id, Model model) {
		List<Review> review = database.viewBook(id);
		Book book = database.getBook(id);
		model.addAttribute("reviews", review);
		model.addAttribute("id", book.getId());
		model.addAttribute("title", book.getTitle());
		model.addAttribute("author", book.getAuthor());
		model.addAttribute("image", book.getImage());
		model.addAttribute("description",book.getDescription());
		model.addAttribute("year",book.getYear());
		model.addAttribute("types",book.getTypes());
		model.addAttribute("pages",book.getPages());
		return "view-book";
	}
	
	/**
	 * Method to register a new user.
	 * @param model An instance of Model that can be used to send data
	 * @return "register"
	 */
	@GetMapping("/register")
	public String register(Model model) {
		String authority = database.getUserAuthority();
		model.addAttribute("authority", authority);
		return "register";
	}
	
	/**
	 * Method to add a new book.
	 * @return "/admin/add-book"
	 */
	@GetMapping("/admin/new-book")
	public String newBook() {
		return "admin/add-book";
	}
	
	/**
	 * Method to create a new book.
	 * @param title The value that we got from the RequestParam
	 * @param author The value that we got from the RequestParam
	 * @return "redirect:/" 
	 */
	@PostMapping("/admin/add-book")
	public String addBook(@RequestParam String title, @RequestParam String author) {
		int book = database.addBook(title, author);
		return "redirect:/";
	}
	
	/**
	 * Method to add a review.
	 * @param id the id of the book to be used to add a review
	 * @param model An instance of Model that can be used to send data
	 * @return "/user/add-review"
	 */
	@GetMapping("/new-review/{id}")
	public String newReview(@PathVariable Long id, Model model) {
		Book book = database.getBook(id);
		model.addAttribute("title", book.getTitle());
		return "user/add-review";
	}
	
	/**
	 * Method to create the review for a book.
	 * @param textArea The value that we got from the RequestParam
	 * @param id the id of the book to be used to create a new review
	 * @return "redirect:/viewBook/{id}"
	 */
	@PostMapping("/user/add-review/{id}")
	public String addReview(@RequestParam String textArea, @PathVariable Long id) {
		Book book = database.getBook(id);
		int review = database.addReview(book.getId(), textArea);
		return "redirect:/viewBook/{id}";
	}
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	/**
	 * Method to add a new user
	 * @param userName The value that we got from the RequestParam
	 * @param password The value that we got from the RequestParam
	 * @param authority The value that we got from the RequestParam
	 * @param model An instance of Model that can be used to send data
	 * @return "index" if add user to database was successful else returns "register"
	 */
	@PostMapping("/addUser")
	public String newUser(@RequestParam String userName, @RequestParam String password, @RequestParam String authority, Model model) {
		
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(authority));

		String encodedPassword = passwordEncoder.encode(password);
		try {
			User user = new User(userName, encodedPassword, authorityList);
			jdbcUserDetailsManager.createUser(user);
			model.addAttribute("message","Thanks for registering!");
		}catch(Exception e) {
			String auth = database.getUserAuthority();
			model.addAttribute("authority", auth);
			model.addAttribute("message", "Username already in use!");	
			return "register";
		}
		List<Book> books = database.getBook();
		model.addAttribute("bookList", books);
		return "register";
	}
	
}
