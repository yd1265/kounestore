package com.bookstore.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	
	@Override

			
		
		
		
		public List<Book> findAllBooks() {
			List<Book> bookList = (List<Book>) bookRepository.findAll();
			List<Book> activeBookList = new ArrayList<>();
			
			for (Book book: bookList) {
				if(book.isActive()) {
					activeBookList.add(book);
				}
			}
			
			return activeBookList;	
		
	
		
		
		
		
		
		
		
	}

	@Override
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long id) {
      bookRepository.deleteById(id);		
	}

	@Override
	public Book findOne(Long id) {
		
		return bookRepository.findById(id).get();
	}

	

	public List<Book> findByCategory(String category){
		List<Book> bookList = bookRepository.findByCategory(category);
		
		List<Book> activeBookList = new ArrayList<>();
		
		for (Book book: bookList) {
			if(book.isActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}

	
	
	
	
	public List<Book> blurrySearch(String title) {
		List<Book> bookList = bookRepository.findByTitleContaining(title);
List<Book> activeBookList = new ArrayList<>();
		
		for (Book book: bookList) {
			if(book.isActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}
}

	
	
	
	
	
	
	
	


