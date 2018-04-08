package com.adv.library.repo;

import java.util.List;

import com.adv.library.exception.RecordNotFoundException;
import com.adv.library.exception.SampleDataAccessException;
import com.adv.library.model.Book;

public interface BookRepository {
	public List<Book> findAll();
	public Book findBook(Long bookId) throws RecordNotFoundException;
	public List<Book> findBooks(String bookName);
	public boolean saveBook(Book book) throws SampleDataAccessException;
	public boolean saveBooks(List<Book> bookList);

}
