package lt.nsg.jdbcglass.integration

import lt.nsg.jdbcglass.connection.ConnectionProxy
import lt.nsg.jdbcglass.integration.entities.ChildEntity
import lt.nsg.jdbcglass.integration.entities.ParentEntity
import org.h2.jdbc.JdbcConnection
import org.hibernate.ConnectionReleaseMode
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.Environment
import org.hibernate.dialect.H2Dialect
import org.hibernate.service.jdbc.connections.internal.UserSuppliedConnectionProviderImpl
import org.hibernate.tool.hbm2ddl.SchemaExport
import spock.lang.Shared
import spock.lang.Specification

class HibernateSpecification extends Specification {
    @Shared
    private SessionFactory sessionFactory
    @Shared
    private Configuration configuration

    @SuppressWarnings("GrDeprecatedAPIUsage")
    def setupSpec() {
        configuration = new Configuration()
        configuration.setProperty(Environment.DIALECT, H2Dialect.class.name)
        configuration.setProperty(Environment.CONNECTION_PROVIDER, UserSuppliedConnectionProviderImpl.class.name)
        configuration.addAnnotatedClass(ParentEntity)
        configuration.addAnnotatedClass(ChildEntity)
        sessionFactory = configuration.buildSessionFactory()
    }

    def cleanupSpec() {
        sessionFactory.close()
    }

    def "hibernate test"() {
        given:
        def entity = new ParentEntity("john")
        def firstChild = new ChildEntity("bill")
        def secondChild = new ChildEntity("sally")
        entity.addChild(firstChild)
        entity.addChild(secondChild)
        when:
        def session = getSession()
        def tx = session.beginTransaction()
        session.save(entity)
        tx.commit()

        tx = session.beginTransaction()
        def parent = session.createQuery("FROM ParentEntity").uniqueResult() as ParentEntity
        parent.children.each { println it }
        tx.commit()
        session.close()

        then:
        noExceptionThrown()
    }

    private Session getSession() {
        def connection = new ConnectionProxy(new JdbcConnection("jdbc:h2:mem:test", new Properties()))
        new SchemaExport(configuration, connection).execute(false, true, false, true)
        sessionFactory.withOptions()
                      .connection(connection)
                      .connectionReleaseMode(ConnectionReleaseMode.ON_CLOSE)
                      .openSession()
    }

}

