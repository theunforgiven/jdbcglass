package lt.nsg.jdbcglass.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseMetaDataProxy extends AbstractDatabaseMetaDataProxy {
    public DatabaseMetaDataProxy(DatabaseMetaData databaseMetaData, Connection connection) {
        super(databaseMetaData, connection);
    }
}
