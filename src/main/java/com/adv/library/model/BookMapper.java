package com.adv.library.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BookMapper implements RowMapper<Book> {

	public Book mapRow(ResultSet resultSet, int i) throws SQLException {

		Book book = new Book();
		book.setBookId(resultSet.getLong("book_id"));
		book.setBookName(resultSet.getString("book_name"));
		book.setAuthorName(resultSet.getString("author_name"));
		book.setCategory(resultSet.getString("category"));
		book.setPrice(resultSet.getDouble("price"));
		book.setQuantity(resultSet.getLong("quantity"));
		book.setCreatedDate(resultSet.getDate("created_date"));
		book.setUpdatedDate(resultSet.getDate("updated_date"));
		
		return book;
	}
}