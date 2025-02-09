package ca.sheridancollege.beans;

import lombok.Data;

/**
 * The Book pojo
 * @author Haydyn & Grace
 *
 */
@Data
public class Book {

	private Long id;
	private String title;
	private String author;
	private String image;
	private String description;
	private int year;
	private String types;
	private int pages;
	
}
