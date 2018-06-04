package com.revature.jdbcbankproj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Accounts {

	ArrayList<Integer> accountid;
	ArrayList<Double> accountbalance;
	ArrayList<String> accountcreateddate;
	ArrayList<Integer> accountstatusid;
	
	public void CreateAccount(int userid) {
		
		MainDriver.log.info("In CreateAccount()");
		
		Scanner scanCA = new Scanner(System.in);
		
		PreparedStatement stmt;
		
		Connection conn = MainDriver.cf.getConnection();
		
		try {
			String sqlString = "INSERT INTO BANKACCOUNT VALUES (SEQ_CREATE_BANK_ACCOUNT_ID.NEXTVAL, ?, ?, SYSDATE, ?)";
			//System.out.println(sqlString);		
			stmt = conn.prepareStatement(sqlString);
			
			
			stmt.setInt(1, userid);
			stmt.setDouble(2, 0.00);
			stmt.setInt(3, 1);
			
			int cu = stmt.executeUpdate();
			
			if(cu > 0) {
				System.out.println("Account has been created.");
			}
			else {
				System.out.println("Failed to create account.");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void SetAccount(int userid) {
		MainDriver.log.info("In SetAccount(): ");
		System.out.println("userid: " + userid);
		Connection conn = MainDriver.cf.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sqlString = "SELECT * FROM BANKACCOUNT WHERE USER_ID = " + userid + " ORDER BY BANK_ACCOUNT_ID";
			//System.out.println(sqlString);
			ResultSet rs = stmt.executeQuery(sqlString);
			
			this.accountid = new ArrayList<Integer>();
			this.accountbalance = new ArrayList<Double>();
			this.accountcreateddate = new ArrayList<String>();
			this.accountstatusid = new ArrayList<Integer>();
			
			while(rs.next()) {
				// If bank account status is active
//				if(rs.getInt("BANK_ACCOUNT_STATUS_ID") == 1) {
//					System.out.print("Account ID: " + rs.getInt("BANK_ACCOUNT_ID"));
//					System.out.print(String.format("     Balance: %.2f%n", rs.getDouble("BANK_ACCOUNT_BALANCE")));
//					//System.out.println(rs.getString("BANK_ACCOUNT_CREATED_DATE"));
//					//System.out.println(rs.getInt("BANK_ACCOUNT_STATUS_ID"));
//				}
				
				this.accountid.add(rs.getInt("BANK_ACCOUNT_ID"));
				this.accountbalance.add(rs.getDouble("BANK_ACCOUNT_BALANCE"));
				this.accountcreateddate.add(rs.getString("BANK_ACCOUNT_CREATED_DATE"));
				this.accountstatusid.add(rs.getInt("BANK_ACCOUNT_STATUS_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void ViewAccount() {
		MainDriver.log.info("In ViewAccount(): ");
		System.out.println("ACCOUNT INFORMATION: ");
		
		for(int i = 0; i < this.accountid.size(); i++ ) {
			if(!this.accountstatusid.get(i).equals(2)) {
				System.out.print("Account ID: " + this.accountid.get(i));
				System.out.println("     Balance: " + this.accountbalance.get(i));
			}
			
			
		}
	}
	
	public void DeleteAccount(int accountNum, int userid) {
		MainDriver.log.info("In DeleteAccount(): ");
		PreparedStatement stmt;
		Connection conn = MainDriver.cf.getConnection();
		String sqlString = "UPDATE BANKACCOUNT SET BANK_ACCOUNT_STATUS_ID = ? WHERE BANK_ACCOUNT_ID = ? AND USER_ID = ?";
		try {
			stmt = conn.prepareStatement(sqlString);
			stmt.setInt(1,  2);
			stmt.setInt(2, accountNum);
			stmt.setInt(3, userid);
			
			int cu = stmt.executeUpdate();
			
			if(cu > 0) {
				System.out.println("Account has been deleted.");
			}
			else {
				System.out.println("Failed to delete account.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		

	}
}
