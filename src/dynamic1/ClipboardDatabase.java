package dynamic1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClipboardDatabase {

	private static class ClipboardDatabaseHolder {
		private static final ClipboardDatabase instance = new ClipboardDatabase();
	}

	static ClipboardDatabase getInstance() {
		return ClipboardDatabaseHolder.instance;
	}

	synchronized boolean storeData(ClipboardData data) {
		try (PooledConnection pconnection = SQLLiteConnection.aquire()) {
			Connection connection = pconnection.getConnection();
			try (Statement stmt = connection.createStatement()) {
				boolean update;
				try (ResultSet rs = stmt
						.executeQuery("SELECT cl_data FROM CLIPBOARD where cl_user=\"" + data.getUser() + "\"")) {
					update = rs.next();
				}
				String query;
				if (update) {
					query = "UPDATE CLIPBOARD set cl_data=?,cl_timeout=? where cl_user=?";
				} else {
					query = "INSERT INTO CLIPBOARD (cl_data,cl_timeout,cl_user) VALUES(?,?,?)";

				}
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, data.getText());
					pstmt.setLong(2, data.getTimeout());
					pstmt.setString(3, data.getUser());
					pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return true;
	}

	ClipboardData retrieveData(String user) {
		try (PooledConnection pconnection = SQLLiteConnection.aquire()) {
			Connection connection = pconnection.getConnection();
			try (Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT cl_data FROM CLIPBOARD where cl_user=\"" + user + "\"")) {
				if (rs.next()) {
					return new ClipboardData(rs.getString("cl_data"), user, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return new ClipboardData();
	}

	synchronized void removeOlderThan(long timeout) {
		StringBuilder query= new StringBuilder ();
		try (PooledConnection pconnection = SQLLiteConnection.aquire()) {
			Connection connection = pconnection.getConnection();
			try (Statement stmt = connection.createStatement()) {
				query.append ("DELETE from CLIPBOARD where cl_timeout<");
				query.append(timeout);
				stmt.executeUpdate(query.toString());
			}
		} catch (Exception e) {
			System.err.println (query.toString());
			e.printStackTrace(System.err);
		}
	}
}
