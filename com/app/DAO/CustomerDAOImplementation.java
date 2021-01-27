package com.app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.app.utils.DBUtils.fetchDBConnection;
import com.app.my_pojos.Customer;

public class CustomerDAOImplementation implements ICustomerDAO{

	private Connection cn;
	private PreparedStatement pst1, pst2,pst3, pst4;
	private Statement stmt;
	
	public CustomerDAOImplementation() throws ClassNotFoundException, SQLException{
		
		String sql = "select * from my_customers where email=? and password=?";
		String sqlInsert = "insert into my_customers values (?,?,?,?,?,?,?)";
		String sqlUpdatePassword = "update my_customers set password=? where email=? and password=?";
		String sqlDeleteRecord = "delete from my_customers where id = ?";
		
		cn = fetchDBConnection();
		
		pst1 = cn.prepareStatement(sql);
		pst2 = cn.prepareStatement(sqlInsert);
		pst3 = cn.prepareStatement(sqlUpdatePassword);
		pst4 = cn.prepareStatement(sqlDeleteRecord);
		
		System.out.println("emp DAO created successfully!");
		
	}
	
	
	
	@Override
	public Customer login(String email, String password) throws SQLException {
		
		Customer cust = null;
		
		pst1.setString(1, email);
		pst1.setString(2, password);
		
		try(ResultSet rst = pst1.executeQuery()){
			
			if(rst.next()) {
				
				cust = new Customer(rst.getInt(1), rst.getDouble(2), rst.getString(3), rst.getString(4), 
						rst.getString(5),rst.getDate(6), rst.getString(7));
				
			}
			
		}
		
		return cust;
	}

	@Override
	public int register(Customer customer) throws SQLException {
		
		pst2.setInt(1, customer.getId());
		pst2.setDouble(2, customer.getDepositAmount());
		pst2.setString(3, customer.getEmail());
		pst2.setString(4, customer.getName());
		pst2.setString(5, customer.getPassword());
		pst2.setDate(6, customer.getRegistrationDate());
		pst2.setString(7, customer.getRole());
		
		return pst2.executeUpdate();
	}

	@Override
	public int changePassword(String email, String oldPassword, String newPassword) throws SQLException {
		
		pst3.setString(1, newPassword);
		pst3.setString(2, email);
		pst3.setString(3, oldPassword);
		
		return pst3.executeUpdate();
	}

	@Override
	public int deleteUser(int id) throws SQLException {
		
		pst4.setInt(1, id);
		
		return pst4.executeUpdate();
	}

	@Override
	public List<Customer> getDetails() throws SQLException {
		
		String sqlDisplay = "select * from my_customers";
		stmt = cn.createStatement();
		ResultSet rst = stmt.executeQuery(sqlDisplay);
		ArrayList<Customer> customers = new ArrayList<>();
		
		while(rst.next()) {
			customers.add(new Customer(rst.getInt(1), rst.getDouble(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getDate(6), rst.getString(7)));
		}
		
		return customers;
	}
	
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (cn != null)
			cn.close();
		System.out.println("Customer DAO cleaned!");

	}

}
