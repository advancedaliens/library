package com.adv.library.repo;

import java.util.List;

import com.adv.library.model.User;

public interface UserRepository {
	
	public User findUser(Long userId);
	public List<User> findUsers(String firstName, String lastName);
	public boolean saveUser(User user);
}
