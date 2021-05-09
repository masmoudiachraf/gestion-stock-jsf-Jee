package main.java.org.polytech.dao;

import java.util.List;

public interface IDao <T>{
	public void insert(T t);
	public T findById(Integer id);
	public List<T> findAll();
	public void delete(T t);
	public void update(T t);
}
