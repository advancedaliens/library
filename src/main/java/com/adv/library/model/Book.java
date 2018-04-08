package com.adv.library.model;

import java.util.Date;

public class Book {
	
	private Long bookId;
	private String bookName;
	private String authorName;
	private String category;
	private Double price;
	private Long quantity;
	private Date createdDate;
	private Date updatedDate;
	
	public Book(Long bookId, String bookName, String authorName, String category, Double price, Long quantity, Date createdDate, Date updatedDate) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.authorName = authorName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	public Book(Long bookId, String bookName, String authorName, String category, Double price, Long quantity) {
		this(bookId, bookName, authorName, category, price, quantity, new Date(), new Date());
	}	
	
	public Book (String bookName, String authorName, String category, Double price, Long quantity) {
		this(null, bookName, authorName, category, price, quantity, new Date(), new Date());
	}	

	public Book() {
	}

	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
