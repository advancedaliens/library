package com.adv.library.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SubscriptionMapper implements RowMapper<Subscription> {

	public Subscription mapRow(ResultSet resultSet, int i) throws SQLException {

		Subscription subscription = new Subscription();
		subscription.setUserId(resultSet.getLong("user_id"));
		subscription.setBookId(resultSet.getLong("book_id"));
		subscription.setBorrowedDate(resultSet.getTimestamp("borrowed_date"));
		subscription.setDueDate(resultSet.getTimestamp("due_date"));
		subscription.setReturnedDate(resultSet.getTimestamp("returned_date"));
		subscription.setPenaltyPaid(resultSet.getDouble("penalty_paid"));
		
		return subscription;
	}
}