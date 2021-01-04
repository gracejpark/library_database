package ca.sheridancollege;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.sheridancollege.database.DatabaseAccess;

/**
 * 
 * @author Haydyn & Grace
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDatabase {

	@Autowired
	private DatabaseAccess da;
	
	@Test
	public void testAddReview() {
		da.addReview((long) 2, "This is a great book");
	}
	
	@Test
	public void testAddBook() {
		int origSize = da.getBook().size();
		da.addBook("Gone with the Wind", "Margaret Mirchell");
		int newSize = da.getBook().size();
		assertThat(newSize).isEqualTo(origSize + 1);
	}
}
