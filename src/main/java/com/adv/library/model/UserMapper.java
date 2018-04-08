package com.adv.library.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet resultSet, int i) throws SQLException {

		User user = new User();
		user.setUserId(resultSet.getLong("user_id"));
		user.setFirstName(resultSet.getString("first_name"));
		user.setLastName(resultSet.getString("last_name"));
		user.setAddress1(resultSet.getString("address1"));
		user.setAddress2(resultSet.getString("address2"));
		user.setAddress3(resultSet.getString("address3"));
		user.setCity(resultSet.getString("city"));
		user.setState(resultSet.getString("state"));
		user.setPostalCode(resultSet.getString("postalcode"));
		user.setCountry(resultSet.getString("country"));
		user.setCreatedDate(resultSet.getDate("created_date"));
		user.setUpdatedDate(resultSet.getDate("updated_date"));
		
		return user;
	}
}