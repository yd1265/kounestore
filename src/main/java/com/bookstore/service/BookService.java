package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;

public interface BookService {
	List<Book> findAllBooks();

	Book addBook(Book book);

	void deleteBook(Long id);

	Book findOne(Long id);
}
