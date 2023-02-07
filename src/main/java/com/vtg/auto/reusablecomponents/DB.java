package com.vtg.auto.reusablecomponents;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB extends GlobalVariables {

	private Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs = null;
	public boolean isAlreadyConnectedToDB = false;
	String methodName;

	/***
	 * This Method returns a Result Set by executing the SQL query using Windows
	 * Authentication
	 * 
	 * @param sqlQuery SQL Query to be provided as parameter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public ResultSet executeSQLQuery(String sqlQuery) throws SQLException, IOException {
		String strMethodName = "executeSQLQuery";
		try {
			connectToDB_WindowsAuth();
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlQuery);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());
		}
		return rs;
	}

	/***
	 * This Method returns a Result Set by executing the SQL query using username
	 * and password provided from Config Data "dbUsername" and "dbPassword"
	 * 
	 * @param sqlQuery SQL Query to be provided as parameter
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public ResultSet executeSQLQueryAuth(String sqlQuery) throws SQLException, IOException {
		String strMethodName = "executeSQLQuery";
		try {
			connectToDB_SQLAuth();
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlQuery);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());
		}
		return rs;
	}

	/***
	 * This Method returns the size/count of records by executing the SQL query
	 * using username and password provided from Config Data "dbUsername" and
	 * "dbPassword"
	 * 
	 * @param sqlQuery
	 * @return int
	 * @throws SQLException
	 * @throws IOException
	 */
	public int getcountAuth(String sqlQuery) throws SQLException, IOException {

		String strMethodName = "getcountAuth";
		int size = 0;
		try {
			connectToDB_WindowsAuth();
			// statement = connection.createStatement();

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(sqlQuery);
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
			closeDB();

		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());
		}
		return size;
	}

	/***
	 * This Method returns the size/count of records by executing the SQL query
	 * using Windows Authentication
	 * 
	 * @param sqlQuery
	 * @return int
	 * @throws SQLException
	 * @throws IOException
	 */
	public int getcount(String sqlQuery) throws SQLException, IOException {

		String strMethodName = "getcount";
		int size = 0;
		try {
			connectToDB_WindowsAuth();
			// statement = connection.createStatement();

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(sqlQuery);
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
			closeDB();

		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());
		}
		return size;
	}

	/***
	 * This Method is to close the DB instance opened
	 * 
	 * @throws IOException
	 */
	public void closeDB() throws IOException {

		String strMethodName = "closeDB";
		try {
			System.out.println("DB Cleanup is taken care!");
		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					/* ignored */}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					/* ignored */}
			}
		}

	}

	/***
	 * This Method connectToDB_WindowsAuth reads the jdbc connection of SQL server
	 * using Windows Authentication
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void connectToDB_WindowsAuth() throws IOException {
		String strMethodName = "connectToDB_WindowsAuth";
		try {
			if (connection != null) {
				isAlreadyConnectedToDB = true;
				return;
			}
			String dburl = GlobalVariables.configData.get("dburl");
			String dbName = GlobalVariables.configData.get("dbName");
			String dbPort = GlobalVariables.configData.get("dbPort");
			String databaseURL = "jdbc:sqlserver://" + dburl + ":" + dbPort + ";databasename=" + dbName
					+ ";integratedsecurity=true";
			connection = DriverManager.getConnection(databaseURL);
		} catch (Exception e) {
			e.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", e.getMessage());
		}
	}

	/***
	 * This Method connectToDB_SQLAuth reads the jdbc connection of SQL server using
	 * username and password provided from Config Data "dbUsername" and "dbPassword"
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void connectToDB_SQLAuth() throws SQLException, IOException, ClassNotFoundException {
		String strMethodName = "connectToDB_SQLAuth";
		try {
			String dburl = GlobalVariables.configData.get("dburl");
			String dbName = GlobalVariables.configData.get("dbName");
			String dbPort = GlobalVariables.configData.get("dbPort");
			String databaseURL = "jdbc:sqlserver://" + dburl + ";port=" + dbPort + ";database=" + dbName + ";";
			String user = GlobalVariables.configData.get("dbUsername");
			String password = GlobalVariables.configData.get("dbPassword");

			connection = DriverManager.getConnection(databaseURL, user, password);

		} catch (Exception e) {
			e.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", e.getMessage());
		}
	}

	public void connectToOraDB_SQLAuth() throws SQLException, IOException, ClassNotFoundException {
		String strMethodName = "connectToOraDB_SQLAuth";
		try {
			String dburl = GlobalVariables.configData.get("dburl");
			String dbName = GlobalVariables.configData.get("dbName");
			String dbPort = GlobalVariables.configData.get("dbPort");
			String databaseURL = "jdbc:oracle:thin:@"  + dburl + ":" + dbPort + "/" + dbName ;
			System.out.println("URL:" + databaseURL);

			String user = GlobalVariables.configData.get("dbUsername");
			String password = GlobalVariables.configData.get("dbPassword");

			Class.forName("oracle.jdbc.driver.OracleDriver");

			connection = DriverManager.getConnection(databaseURL, user, password);

		} catch (Exception e) {
			e.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", e.getMessage());
		}
	}

	public ResultSet executeOraSQLQueryAuth(String sqlQuery) throws SQLException, IOException {
		String strMethodName = "executeSQLQueryAuth";
		try {
			connectToOraDB_SQLAuth();
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlQuery);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportUtils.reportLogStatus(strMethodName, false, "not applicable", ex.getMessage());
		}

		return rs;
	}
}
