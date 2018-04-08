package com.adv.library.model;

import java.util.Date;

public class Subscription {
	
	private Long userId;
	private Long bookId;
	private Date borrowedDate;
	private Date dueDate;
	private Date returnedDate;
	private Double penaltyPaid;
	
	public Subscription(Long userId, Long bookId, Date borrowedDate, Date dueDate, Date returnedDate, Double penaltyPaid) {
		this.userId = userId;
		this.bookId = bookId;
		this.borrowedDate = borrowedDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.penaltyPaid = penaltyPaid;
	}
	
	public Subscription(Long userId, Long bookId, Date borrowedDate, Date dueDate) {
		this(userId, bookId, borrowedDate, dueDate, null, null);
	}	

	public Subscription() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}

	public Double getPenaltyPaid() {
		return penaltyPaid;
	}

	public void setPenaltyPaid(Double penaltyPaid) {
		this.penaltyPaid = penaltyPaid;
	}
}
