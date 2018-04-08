package com.adv.library.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adv.library.model.User;
import com.adv.library.model.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final String SQL_FIND_USER_BY_ID= "SELECT user_id, first_name, last_name, address1, address2, address3,"
			+ " city, state, postalcode, country, created_date, updated_date FROM user where user_id= ?";
	
	private final String SQL_FIND_USERS_BY_NAME= "SELECT user_id, first_name, last_name, address1, address2, address3,"
			+ " city, state, postalcode, country, created_date, updated_date FROM user where lower(first_name) like ? and lower(last_name) like ?";
	
	private final String SQL_SAVE_USER = "insert into user (user_id, first_name, last_name, address1, address2, address3,"
			+ " city, state, postalcode,country) values (?,?,?,?,?,?,?,?,?,?)";
	
	private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);  

	@Override
	public User findUser(Long userId) {
		log.debug("findUser called with userId:{}", userId);
		return jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, new Object[] { userId }, new UserMapper());
	}

	@Override
	public List<User> findUsers(String firstName, String lastName) {
		
		log.debug("findUsers called with firstName:{}, lastName:{}", firstName, lastName);
		
		firstName = "%" + firstName + "%";
		lastName = "%" + lastName + "%";
	
		return jdbcTemplate.query(SQL_FIND_USERS_BY_NAME, new Object[] {firstName, lastName}, new UserMapper());
	}

	@Override
	public boolean saveUser(User user) {
		log.debug("saveUser called");
		Object[] inputs = new Object[] {user.getUserId(), user.getFirstName(), user.getLastName(),
				user.getAddress1(), user.getAddress2(), user.getAddress3(),
				user.getCity(), user.getState(), user.getPostalCode(), user.getCountry()};
        int result = jdbcTemplate.update(SQL_SAVE_USER, inputs);
		
        if (result != 1 ) {
        	log.info("User save failed");
        	return false;
        } 
        
        log.info("User successfully saved");
        
		return true;
	}

}
