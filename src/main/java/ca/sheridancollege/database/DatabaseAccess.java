package ca.sheridancollege.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Book;
import ca.sheridancollege.beans.Review;

/**
 * This is the DatabaseAccess
 * It is used to access the database
 * @author Haydyn & Grace
 *
 */
@Repository
public class DatabaseAccess {

@Autowired
private NamedParameterJdbcTemplate jdbc;
	
	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	/**
	 * Retrieves all books from the database;
	 * @return books
	 */
	public List<Book> getBook() {
		String query = "SELECT * FROM books";
		
		BeanPropertyRowMapper<Book> bookMapper = 
				new BeanPropertyRowMapper<>(Book.class);
		
		List<Book> books =jdbc.query(query, bookMapper);
		
		return books;
	}
	
	/**
	 * Retrieves book from the database using it's Id
	 * @param id the id of the book to get
	 * @return book
	 */
	public Book getBook(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books WHERE id = :id";
		
		namedParameters.addValue("id", id);
		
		BeanPropertyRowMapper<Book> bookMapper = 
				new BeanPropertyRowMapper<>(Book.class);
		
		List<Book> books =jdbc.query(query, namedParameters, bookMapper);
		
		if(books.isEmpty()) {
			return null;
		}else {
			return books.get(0);
		}	
	}
	
	/**
	 * Retrieves reviews of a book from the database using it's bookId
	 * @param bookId the bookid of the review to get
	 * @return reviews
	 */
	public List<Review> viewBook(Long bookId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM reviews WHERE bookId = :bookId";
		
		namedParameters.addValue("bookId", bookId);
		
		BeanPropertyRowMapper<Review> reviewMapper = 
				new BeanPropertyRowMapper<>(Review.class);
		
		List<Review> reviews =jdbc.query(query, namedParameters, reviewMapper);
		
		return reviews;
		
	}
	
	/**
	 * Adds a new review into the database
	 * @param bookId the bookid of the review to add
	 * @param text the review to add 
	 * @return the number of rows affected; 1 - successful, 0 - not.
	 */
	public int addReview(Long bookId, String text) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO reviews (bookId, text) VALUES (:bookId,:text)";
		
		namedParameters.addValue("bookId", bookId)
		.addValue("text", text);
		
		int returnValue = jdbc.update(query, namedParameters);
		
		return returnValue;
	}
	
	/**
	 * Adds a new book into the database
	 * @param title the title of the new book to add
	 * @param author the author of the new book to add
	 * @return the number of rows affected; 1 - successful, 0 - not.
	 */
	public int addBook(String title, String author, String image, String description, int year, String types, int pages) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO books (title, author, image, description, year, types, pages) VALUES (:title, :author, :image, :description, :year, :types, :pages)";
		
		namedParameters.addValue("title", title)
		.addValue("author", author)
		.addValue("image", image)
		.addValue("description", description)
		.addValue("year", year)
		.addValue("types", types)
		.addValue("pages", pages);
		
		int returnValue = jdbc.update(query, namedParameters);
		
		return returnValue;
	}
	
	/**
	 * Retrieves authorities from the database
	 * @return authorities
	 */
	public List<String> getAuthorities() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT DISTINCT authority FROM authorities";
		List<String> authorities = jdbc.queryForList(query, namedParameters, String.class);

		return authorities;
	}
	
	/**
	 * Retrieves authorities from the database where authority is role_user
	 * @return authorities
	 */
	public String getUserAuthority() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT DISTINCT authority FROM authorities WHERE AUTHORITY='ROLE_USER'";
		List<String> authorities = jdbc.queryForList(query, namedParameters, String.class);

		if(authorities.isEmpty()) {
			return null;
		}else {
			return authorities.get(0);
		}	
	}
}
