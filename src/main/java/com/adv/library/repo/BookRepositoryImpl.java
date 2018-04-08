package com.adv.library.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adv.library.exception.RecordNotFoundException;
import com.adv.library.exception.SampleDataAccessException;
import com.adv.library.model.Book;
import com.adv.library.model.BookMapper;

@Repository
public class BookRepositoryImpl implements BookRepository {
	
	private static final Logger log = LoggerFactory.getLogger(BookRepositoryImpl.class);
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final String SQL_FIND_BOOK_BY_ID = "SELECT book_id, book_name, author_name, category, price, quantity, created_date, "
			+ "updated_date FROM sampledb.book where book_id= ?" ;
	
	private final String SQL_GET_FIRST_1000_BOOKS = "SELECT book_id, book_name, author_name, category, price, quantity, created_date, updated_date "
			+ "FROM sampledb.book limit 2";
	
	private final String SQL_SAVE_BOOK = "insert into sampledb.book (book_id, book_name, author_name, category, price, quantity) values (?,?,?,?,?,?)";
	
	
    public List<Book> findAll() {
    	
    	log.debug("In findAll method");

        List<Book> result = jdbcTemplate.query(
        		SQL_GET_FIRST_1000_BOOKS,
                (rs, rowNum) -> new Book(rs.getLong("book_id"),  rs.getString("book_name"),  rs.getString("author_name"),
                        rs.getString("category"), rs.getDouble("price"), rs.getLong("quantity"), rs.getDate("created_date"), rs.getDate("updated_date"))
        		);

        return result;
    }

	@Override
	public Book findBook(Long bookId) throws RecordNotFoundException {
		
		log.debug("In findBook, with bookId:{}", bookId);
		Book book = null;
		
		try {
			book = jdbcTemplate.queryForObject(SQL_FIND_BOOK_BY_ID, new Object[] { bookId }, new BookMapper());
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("Book with bookId:" + bookId + " not found");
		}
		
		return book;
	}

	@Override
	public List<Book> findBooks(String bookName) {
		
		log.debug("In findBooks, with bookName:{}", bookName);
		 List<Book> result = jdbcTemplate.query(
	                "SELECT book_id, book_name, author_name, category, price, quantity, created_date, updated_date FROM sampledb.book where lower(book_name) like '%" 
		 + bookName.toLowerCase() + "%'",
	                (rs, rowNum) -> new Book(rs.getLong("book_id"),  rs.getString("book_name"),  rs.getString("author_name"),
	                        rs.getString("category"), rs.getDouble("price"), rs.getLong("quantity"), rs.getDate("created_date"), rs.getDate("updated_date"))
	        		);

	        return result;
	}

	@Override
	public boolean saveBook(Book book) throws SampleDataAccessException {
		
		int result = -1;
		
		log.debug("In saveBook, with bookId:{}, bookName:{}, catrgory:{}, authorName:{}, price:{}, quantity:{}, createdDate:{}, updatedDate:{}",
				book.getBookId(), book.getBookName(), book.getCategory(), 
				book.getAuthorName(), book.getPrice(), book.getQuantity(), book.getCreatedDate(), book.getUpdatedDate());
 
        Object[] inputs = new Object[] {book.getBookId(), book.getBookName(), book.getAuthorName(), book.getCategory(), book.getPrice(), book.getQuantity()};
        
        try {
        	result = jdbcTemplate.update(SQL_SAVE_BOOK, inputs);
        } catch (DataAccessException e) {
        	throw new SampleDataAccessException("");
        }        
		
        if (result != 1 ) {
        	log.info("Book:{} added successfully", book.getBookName());
        	return false;
        } else {
        	log.info("Book:{} addition failed", book.getBookName());
        }
        
		return true;
	}

	@Override
	public boolean saveBooks(List<Book> bookList) {
		// TODO Auto-generated method stub
		return false;
	}
}
