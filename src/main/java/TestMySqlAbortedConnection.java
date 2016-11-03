import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * This code generate an 'Aborted connection' message in the
 * mysql server log every time it's run.
 *
 * Tested against mysql oracle version 5.7.13
 *
 * To try it, set the following system properties: dburl, username, password
 */
public class TestMySqlAbortedConnection {
    interface Mapper {
        @Select("SELECT 1")
        int pingDb();
    }

    public static void main(String[] args) throws IOException {
        if (System.getProperty("dburl") == null ||
                System.getProperty("username") == null ||
                System.getProperty("password") == null) {
            System.err.println("Please set the following system properties: 'dburl', 'username', 'password'");
            System.exit(1);
        }

        TestMySqlAbortedConnection test = new TestMySqlAbortedConnection();
        test.pingDb();
    }

    private void pingDb() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Mapper mapper = session.getMapper(TestMySqlAbortedConnection.Mapper.class);
            mapper.pingDb();
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream, System.getProperties());

        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.addMapper(Mapper.class);
        return sqlSessionFactory;
    }
}
