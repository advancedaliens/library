package com.adv.library.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SubscriptionDetailMapper implements RowMapper<SubscriptionDetail> {

	public SubscriptionDetail mapRow(ResultSet resultSet, int i) throws SQLException {

		SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
		subscriptionDetail.setFirstName(resultSet.getString("first_name"));
		subscriptionDetail.setLastName(resultSet.getString("last_name"));
		subscriptionDetail.setBookName(resultSet.getString("book_name"));
		subscriptionDetail.setBorrowedDate(resultSet.getTimestamp("borrowed_date"));
		subscriptionDetail.setDueDate(resultSet.getTimestamp("due_date"));
		subscriptionDetail.setReturnedDate(resultSet.getTimestamp("returned_date"));
		subscriptionDetail.setPenaltyPaid(resultSet.getDouble("penalty_paid"));
		
		return subscriptionDetail;
	}
}