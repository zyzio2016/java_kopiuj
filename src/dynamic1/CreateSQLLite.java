package dynamic1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSQLLite {

	private final Connection connection;

	private CreateSQLLite(String dir) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + dir);
	}

	private void create() throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE if not exists CLIPBOARD (cl_user TEXT PRIMARY KEY NOT NULL,"
				+ " cl_data TEXT NOT NULL, cl_timeout INTEGER NOT NULL)");
		stmt.close();
	}

	private void close() throws SQLException {
		connection.close();
	}

	public static void main(String args[]) {
		try {
			String dbFile = args.length == 1 ? args[0] : "kopiuj.db";
			CreateSQLLite c = new CreateSQLLite(dbFile);
			System.out.println("Opened database " + dbFile + " successfully.");
			c.create();
			System.out.println("Tables created successfully.");
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}
