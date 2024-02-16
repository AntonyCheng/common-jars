package top.sharehome.Dao;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.sharehome.Utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 执行增删改的sql语句
     *
     * @param sql
     * @param params
     * @return
     */
    public int Update(String sql, Object... params) {
        Connection conn = JDBCUtils.getConnection();
        try {
            //执行增删改的sql语句，返回受到影响的行数
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
		//如果获取连接使用了JDBCUtils中的Connection getConnectionByOneThread()方法，那么finally代码需要删去
		//目的是防止线程中唯一的一个连接在使用中被归还
		//一旦这里被删除，就需要在某个地方将该关闭的连接关闭，这个地方就应该在Servlet中的ModelBaseServlet类中做处理
		//即在ModelBaseServlet类中加上下面这些代码
		finally {
            JDBCUtils.releaseConnection(conn);
        }
    }

    /**
     * 执行查询一行数据的sql语句，将结果集封装到JavaBean对象中
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public T getBean(Class<T> clazz, String sql, Object... params) {
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
		//如果获取连接使用了JDBCUtils中的Connection getConnectionByOneThread()方法，那么finally代码需要删去
		//目的是防止线程中唯一的一个连接在使用中被归还
		//一旦这里被删除，就需要在某个地方将该关闭的连接关闭，这个地方就应该在Servlet中的ModelBaseServlet类中做处理
		//即在ModelBaseServlet类中加上下面这些代码
		finally {
            JDBCUtils.releaseConnection(conn);
        }
    }

    /**
     * 执行查询多行数据的sql语句，并且将结果集封装到List<JavaBean>
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public List<T> getBeanList(Class<T> clazz, String sql, Object... params) {
		Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
		//如果获取连接使用了JDBCUtils中的Connection getConnectionByOneThread()方法，那么finally代码需要删去
		//目的是防止线程中唯一的一个连接在使用中被归还
		//一旦这里被删除，就需要在某个地方将该关闭的连接关闭，这个地方就应该在Servlet中的ModelBaseServlet类中做处理
		//即在ModelBaseServlet类中加上下面这些代码
		finally {
            JDBCUtils.releaseConnection(conn);
        }
    }

    /**
     * 批处理方法
     *
     * @param sql
     * @param paramArr
     * @return
     */
    public int[] batchUpdate(String sql, Object[][] paramArr) {
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.batch(conn, sql, paramArr);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
		//如果获取连接使用了JDBCUtils中的Connection getConnectionByOneThread()方法，那么finally代码需要删去
		//目的是防止线程中唯一的一个连接在使用中被归还
		//一旦这里被删除，就需要在某个地方将该关闭的连接关闭，这个地方就应该在Servlet中的ModelBaseServlet类中做处理
		//即在ModelBaseServlet类中加上下面这些代码
		finally {
            JDBCUtils.releaseConnection(conn);
        }
    }
}