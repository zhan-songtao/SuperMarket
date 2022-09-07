package com.zhan.servlet.user;

import com.zhan.pojo.User;
import com.zhan.service.user.UserService;
import com.zhan.service.user.UserServiceImpl;
import com.zhan.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

//Servlet：控制层，调用业务层(Service)代码

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start......");
        //获取用户从前端带来的参数（账号和密码）
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库中的密码进行对比，调用业务层(Service):
        UserService userService = new UserServiceImpl();

        try {
            User user = userService.login(userCode, userPassword);
            if(user!=null){//查有此人，可以登录

                //将用户的信息放到Session中
                req.getSession().setAttribute(Constant.USER_SESSION,user);
                //登录成功后跳转到内部主页(重定向）
                resp.sendRedirect("jsp/frame.jsp");

            }else{
                //查无此人，无法登录
                //跳转回登录页面
                req.setAttribute("error","用户名或者密码不正确");
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req,resp);
    }
}
