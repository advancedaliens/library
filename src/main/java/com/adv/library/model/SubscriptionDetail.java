package com.adv.library.model;

import java.util.Date;

public class SubscriptionDetail {
	
	private String firstName;
	private String lastName;
	private String bookName;
	private Date borrowedDate;
	private Date dueDate;
	private Date returnedDate;
	private Double penaltyPaid;
	
	public SubscriptionDetail(String firstName, String lastName, String bookName, Date borrowedDate, Date dueDate, 
			Date returnedDate, Double penaltyPaid) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.bookName = bookName;
		this.borrowedDate = borrowedDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.penaltyPaid = penaltyPaid;
	}
	
	public SubscriptionDetail() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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
