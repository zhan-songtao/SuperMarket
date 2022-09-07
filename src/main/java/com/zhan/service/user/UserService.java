package com.zhan.service.user;

import com.zhan.pojo.Role;
import com.zhan.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    //用戶登錄
    public User login(String usercode, String password) throws SQLException, ClassNotFoundException;

    //根据用户ID修改密码
    public boolean updatePwd(int id,String password) throws SQLException, ClassNotFoundException;

    //查询记录数
    public int getUserCount(String username,int userRole) throws SQLException, ClassNotFoundException;

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);



}
