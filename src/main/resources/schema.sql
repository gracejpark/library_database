
create table books (
  id       LONG NOT NULL Primary Key AUTO_INCREMENT,
  title    VARCHAR(128) NOT NULL,
  author   VARCHAR(128) NOT NULL,
  image	   VARCHAR(238) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  year		INT(5) NOT NULL,
  types		VARCHAR(128) NOT NULL,
  pages 	INT(5) NOT NULL
);

create table reviews (
  id       LONG NOT NULL Primary Key AUTO_INCREMENT,
  bookId   LONG NOT NULL,	
  text     VARCHAR(1024) NOT NULL UNIQUE
);


alter table reviews
  add constraint book_review_fk foreign key (bookId)
  references books (id);

insert into books (title, author, image, description, year, types, pages)
values ('The 7 Habits of Highly Effective People', 'Stephen R. Covey', '/img/book1.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque interdum nisi in imperdiet tincidunt. Praesent erat lorem, congue sit amet enim nec, semper fermentum nulla. Mauris quis maximus dolor. Donec massa justo, dictum ut massa non, sodales posuere leo. Praesent vel purus ac odio varius tempus in nec odio.', 2020, 'Book', 214);
 
insert into books (title, author, image, description, year, types, pages)
values ('The Prince', 'Niccolo Machiavelli', '/img/book2.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque interdum nisi in imperdiet tincidunt. Praesent erat lorem, congue sit amet enim nec, semper fermentum nulla. Mauris quis maximus dolor. Donec massa justo, dictum ut massa non, sodales posuere leo. Praesent vel purus ac odio varius tempus in nec odio.', 2019, 'Book', 189); 
 
insert into reviews (text, bookId)
values ('An older book, but still a very good read for priniciple-centered leadership.', 1);
 
insert into reviews (text, bookId)
values ('A very old book that expounds on gaining and keeping power; at any and all costs. It was banned by the Pope in 1559.', 2);
