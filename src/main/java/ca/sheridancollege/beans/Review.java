package ca.sheridancollege.beans;

import lombok.Data;

/**
 * The Review pojo
 * @author Haydyn & Grace
 *
 */
@Data
public class Review {

	private Long id;
	private Long bookid;
	private String text;
}
