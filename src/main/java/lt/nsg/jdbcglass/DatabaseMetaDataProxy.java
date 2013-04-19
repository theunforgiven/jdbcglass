package lt.nsg.jdbcglass;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DatabaseMetaDataProxy extends AbstractDatabaseMetaDataProxy {
    private final Connection connection;

    public DatabaseMetaDataProxy(DatabaseMetaData databaseMetaData, Connection connection) {
        super(databaseMetaData);
        this.connection = connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connection;
    }
}
