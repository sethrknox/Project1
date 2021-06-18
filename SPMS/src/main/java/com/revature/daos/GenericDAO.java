package com.revature.daos;

public interface GenericDAO<T> {

	public void add(T t);
	
	public T getById(Integer id);
	
	public void update(T t);
}
