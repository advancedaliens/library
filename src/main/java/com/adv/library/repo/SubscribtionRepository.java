package com.adv.library.repo;

import java.util.List;

import com.adv.library.model.SubscriptionDetail;

public interface SubscribtionRepository {
	
	public boolean borrowBook(Long bookId, Long userId);
	public boolean returnBook(Long bookId, Long userId);
	public List<SubscriptionDetail> getAllSubscriptionsByUserId(Long userId);
	public List<SubscriptionDetail> getAllSubscriptionsByUserName(String firstName, String lastName);
	public List<SubscriptionDetail> getAllSubscriptionsByBookId(Long bookId);
	public List<SubscriptionDetail> getAllSubscriptionsByBookName(String bookName);
}
