package com.zhan.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        //通过类加载器读取对应资源
        InputStream is =BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();

        try {
            properties.load(is);//将流加入到properties对象中
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");


    }


    //获取数据库连接

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
       return connection;
    }

    //编写查询公共类



    public static ResultSet execute(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] param) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i <param.length ; i++) {
            preparedStatement.setObject(i+1,param[i]);
        }

        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写增删改的公共方法
    public static int execute(Connection connection,PreparedStatement preparedStatement,String sql,Object[] param) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);//预编译

        for (int i = 0; i <param.length ; i++) {
            preparedStatement.setObject(i+1,param[i]);
        }

        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    //关闭连接,释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){

        boolean flag=true;

        if(resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }

        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }

        if(connection!=null){
            try {
                connection.close();
                //GC回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }

        return  flag;

    }



}
