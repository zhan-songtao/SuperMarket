package com.zhan.service.user;

import com.zhan.dao.BaseDao;
import com.zhan.dao.user.UserDao;
import com.zhan.dao.user.UserDaoImpl;
import com.zhan.pojo.Role;
import com.zhan.pojo.User;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    //业务层都会调用dao层，所以我们要引入dao层
    private UserDao userDao;

    public UserServiceImpl(){
        userDao=new UserDaoImpl();
    }
    @Override
    public User login(String usercode, String password) throws SQLException, ClassNotFoundException {

        Connection connection=null;
        User user=null;

        connection=BaseDao.getConnection();//获取数据库

        //通过业务层调用对应的具体的数据库操作
        user=userDao.getLoginUser(connection,usercode);

        BaseDao.closeResource(connection,null,null);

        return user;
    }

    @Override
    public boolean updatePwd(int id, String password) throws SQLException, ClassNotFoundException {

           Connection connection=null;
            boolean flag=false;
         connection = BaseDao.getConnection();
         if(userDao.updatePwd(connection,id,password)>0){
             flag=true;
         }

         BaseDao.closeResource(connection,null,null);

        return flag;

    }

    @Override
    public int getUserCount(String username, int userRole) throws SQLException, ClassNotFoundException {

        Connection connection;
        int count=0;
        connection = BaseDao.getConnection();
        userDao.getUserCount(connection,username,userRole);
        BaseDao.closeResource(connection,null,null);
        return count;
    }

    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }


    @Test
    public void test() throws SQLException, ClassNotFoundException {

        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "12345678");
        System.out.println(admin.getUserPassword());

    }

}
