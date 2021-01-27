package com.app.testers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import com.app.DAO.CustomerDAOImplementation;
import com.app.my_pojos.Customer;

public class CustomerTester {

	public static int id;
	
	static {
		id = 6;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		try(Scanner sc = new Scanner(System.in)){
			
			boolean exit = false;
			
			CustomerDAOImplementation cDaoImplementation = new CustomerDAOImplementation();
			
			while(!exit) {
				
				System.out.println("1. Login");
				System.out.println("2. New User, Register");
				System.out.println("3. Change Password");
				System.out.println("4. Delete a user");
				System.out.println("5. Display all users");
				System.out.println("6. Exit");
				
				System.out.println("enter your choice");
				
				switch (sc.nextInt()) {
				case 1:
					
					System.out.println("Enter email");
					String email = sc.next();
					System.out.println("Enter password");
					String password = sc.next();
					
					Customer cust = cDaoImplementation.login(email, password);
					
					if(cust == null) {
						System.out.println("User doesnt exist!");
					}
					else {
						System.out.println("User exists!");
						System.out.println("User details are \n" + cust);
					}
					
					break;

				case 2: 
					
					System.out.println("Enter deposit amount, email, name, password, registration data and role");
					
					int success = cDaoImplementation.register(new Customer(id++, sc.nextDouble(), sc.next(), sc.next(), sc.next(), Date.valueOf(sc.next()), sc.next()));
					
					if(success == 0) {
						System.out.println("Registration failed!!");
					}
					else {
						System.out.println("Successfully registered!!");
					}
					
					break;
					
				case 3:
					
					System.out.println("Enter email, old password and new password");
					
					int result = cDaoImplementation.changePassword(sc.next(), sc.next(), sc.next());
					
					if(result == 0) {
						System.out.println("Couldnt change password!!");
					}
					else {
						System.out.println("Changed password successfully!!");
					}
					
					break;
					
				case 4:
					cDaoImplementation.getDetails().forEach(System.out::println);
	
					System.out.println("\nEnter the id of the record you want to delete");
					
					int res = cDaoImplementation.deleteUser(sc.nextInt());
					
					if(res == 0) {
						System.out.println("Couldn't delete record");
					}
					else {
						System.out.println("Deleted record successfully!");
					}
					
					break;
					
				case 5:
					
					cDaoImplementation.getDetails().forEach(System.out::println);
					
					break;
					
				case 6:

					System.out.println("BYE!");
					System.exit(0);
					exit = true;
					
					break;
					
				default:
					
					System.out.println("Select a correct option!!");
					
					break;
				}
				
			}
			
			cDaoImplementation.cleanUp();
		}
		
	}
	
}
