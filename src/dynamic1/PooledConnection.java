package dynamic1;

import java.sql.Connection;

public interface PooledConnection extends AutoCloseable {
	Connection getConnection();
}
