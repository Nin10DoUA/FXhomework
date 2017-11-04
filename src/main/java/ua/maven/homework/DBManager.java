package ua.maven.homework;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBManager {
	private static DBManager db;
	private String driver;
	private String url;
	private Connection conn = null;
	private PreparedStatement pst = null;
	private byte authenticated;
	private byte unique;
	private boolean success;

	public boolean isSuccess() {
		return this.success;
	}

	private DBManager(String driver, String url) {
		this.driver = driver;
		this.url = url;
		try {
			Class.forName(driver).newInstance();
			System.out.println("Driver loaded successfully ...");
		} catch (Exception e) {
			System.out.println("Can't load drivers ...");
		}
		try {
			System.out.println("Obtaining connection ...");
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			System.out.println("Connection successful!!!");

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static DBManager newInstance() {
		if (db == null) {
			db = new DBManager(MainApp.driver, MainApp.url);
			System.out.println("DataBaseManagerObj was created and returned");
			return db;
		} else {
			System.out.println("DataBaseManagerObj was returned");
			return db;
		}
	}

	public void closeConnection() {
		if (conn != null) {
			System.out.println("Connection is not null, closing ...");
			try {
				conn.close();
				System.out.println("Connection is closed now...");
			} catch (SQLException e) {
				System.out.println("Unable to close connection");
				e.printStackTrace();
			}
		} else {
			System.out.println("Ñonnection is null, nothing to close");
		}
	}

	public boolean authCheck(String login, String password) {
		final String queryAuth = "SELECT COUNT(*) FROM accounts WHERE username='" + login + "' AND password='"
				+ password + "'";
		try {
			pst = conn.prepareStatement(queryAuth);
			System.out.println("Obtained statement");
			try {
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					authenticated = (byte) rs.getInt("COUNT(*)");
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("Query Select Error");
				e.printStackTrace();
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			System.out.println("Statement error!");
		}
		if (authenticated == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean uniqueCheck(String login, String email) {
		final String queryUnique = "SELECT COUNT(*) FROM accounts WHERE username='" + login + "' AND email='" + email
				+ "'";
		try {
			pst = conn.prepareStatement(queryUnique);
			System.out.println("Obtained statement");
			try {
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					unique = (byte) rs.getInt("COUNT(*)");
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("Query Select Error");
				e.printStackTrace();
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			System.out.println("Statement error!");
		}
		if (unique == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void newUserCreation(String user, String pass, String e) {
		success = false;
		final String queryNewUser = "INSERT INTO accounts (username, password,email) values ('" + user + "','" + pass
				+ "','" + e + "')";
		try {
			pst = conn.prepareStatement(queryNewUser);
			System.out.println("Obtained statement");
			try {
				pst.execute();
				System.out.println("One row has been added!");
				conn.commit();
				success = true;
			} catch (SQLException ex) {
				System.out.println("Query Insert Error");
				ex.printStackTrace();
			} finally {
				pst.close();
			}
		} catch (SQLException ex) {
			System.out.println("Statement error");
		}

	}
	
}
