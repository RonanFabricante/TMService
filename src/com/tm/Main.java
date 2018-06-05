package com.tm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{

	private static String JDBC_CONNECTION_URL = 
			"jdbc:oracle:thin:@localhost:1521:SID";
	
	private static class LoadCSV implements Runnable
	{
	  @Override
	  public void run()
	  {
		  	CSVLoader loader = new CSVLoader(getCon());
		  	
			try {
				loader.loadCSV("/Users/ronanrayfabricante/Desktop/csv_files/team.csv", "TEAM", true);
				loader.loadCSV("/Users/ronanrayfabricante/Desktop/csv_files/team_skill.csv", "TEAM_SKILL", true);
				loader.loadCSV("/Users/ronanrayfabricante/Desktop/csv_files/task.csv", "TASK", true);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	  }
	}

	
	public static void main(String[] args) {
		try {
			
			 ExecutorService executor = Executors.newCachedThreadPool();
			 for (int i = 0; i < 3; i++) executor.execute(new LoadCSV());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Connection getCon() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}