package main.java.org.polytech.dao;

import main.java.org.polytech.achraf.entities.User;

public interface IConnectionDao {
	public User login(String username, String password);
}
