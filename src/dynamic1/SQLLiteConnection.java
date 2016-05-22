package dynamic1;

import java.sql.Connection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SQLLiteConnection  implements PooledConnection {

	private volatile boolean used;

	private final Connection connection;

	public Connection getConnection() {
		return connection;
	}

	static private Semaphore available = new Semaphore(1, true);

	private SQLLiteConnection(Connection c) {
		connection = c;
	}

	@Override
	public void close() throws Exception {
		if (used) {
			used = false;
			available.release();
		}

	}

	@Override
	public void finalize() {
		try {
			close();
		} catch (Exception e){}
	}

	private static SQLLiteConnection singleton;

	/**
	 * @return
	 * @throws InterruptedException
	 */
	public static PooledConnection aquire() throws InterruptedException {
		if (!available.tryAcquire(1, TimeUnit.SECONDS)) {
			throw new RuntimeException("Database connection is locked by another thread.");
		}
		if (singleton == null) {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource ds = (DataSource)
				  envCtx.lookup("jdbc/kopiuj_db");
				Connection connection = ds.getConnection();
				singleton = new SQLLiteConnection(connection);
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				throw new RuntimeException(e);
			}
		}
		singleton.used = true;
		return singleton;
	}
}
