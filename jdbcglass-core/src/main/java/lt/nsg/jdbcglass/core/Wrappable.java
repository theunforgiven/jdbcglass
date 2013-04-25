package lt.nsg.jdbcglass.core;

import java.sql.SQLException;
import java.sql.Wrapper;

public class Wrappable implements Wrapper {
    private final Wrapper wrapped;

    public Wrappable(Wrapper wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return wrapped.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return wrapped.isWrapperFor(iface);
    }
}
