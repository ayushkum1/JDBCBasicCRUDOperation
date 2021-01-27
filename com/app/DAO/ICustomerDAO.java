package com.app.DAO;

import java.sql.SQLException;
import java.util.List;

import com.app.my_pojos.Customer;

public interface ICustomerDAO {

	Customer login(String name, String password) throws SQLException;
	
	int register(Customer customer) throws SQLException;
	
	int changePassword(String email, String oldPassword, String newPassword) throws SQLException;
	
	int deleteUser(int id) throws SQLException;
	
	List<Customer> getDetails() throws SQLException;
}
