package com.xiao.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiao.web.model.User;
import com.xiao.web.security.PermissionSign;
import com.xiao.web.security.RoleSign;
import com.xiao.web.service.UserService;

/**
 * 用户控制器
 * 
 * @author StarZou
 * @since 2014年5月28日 下午3:54:00
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * 
     * @param user
     * @param result
     * @return
     */
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @ResponseBody
//    public String register(HttpServletRequest req, HttpServletResponse response) {
//    	int m = 0;
//    	String username = req.getParameter("userName");
//        String password = req.getParameter("password");
//        String phone = req.getParameter("phone");
//        Date date = new java.sql.Date(new Date().getTime());  
//        User user = new User();
//        user.setPassword(password);
//        user.setUsername(username);
//        user.setPhone(phone);
//        user.setId(new Long(phone));
//        user.setCreateTime(date);
//        try{
//        	m = userService.insert(user);
//        } catch(Exception e){
//        	System.out.println(e);
//        }
//        String message = m==0 ? "insert is error" : "insert is succes";
//        return message;
//    }
    
    /**
     * 用户重名检查
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/checkeUser")
    @ResponseBody
    public String checkeUser(HttpServletRequest req, HttpServletResponse response) {
        String username = req.getParameter("username");
        User user = null;
        try{
        	user = userService.selectByUsername(username);
        } catch(Exception e){
        	System.out.println(e);
        }
        String message = null == user ? "error" : "succes";
        return message;
    }
    
    /**
     * 用户登录
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,HttpServletResponse response) {
    	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
        	String message = "username or password error!";
            return message;
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
        	String message = "username or password error!";
        	return message;
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
        	String message = "times error!";
            return message;
        }
        User user = userService.selectByUsername(username);
        subject.getSession().setAttribute("user", user);
        String message = "{\"info\":\"login success!\"}";
        return message;
    }

    /**
     * 用户登出
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 基于角色 标识的权限控制案例
     */
    @RequestMapping(value = "/admin")
    @ResponseBody
    @RequiresRoles(value = RoleSign.ADMIN)
    public String admin() {
        return "拥有admin角色,能访问";
    }

    /**
     * 基于权限标识的权限控制案例
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    @RequiresPermissions(value = PermissionSign.USER_CREATE)
    public String create() {
        return "拥有user:create权限,能访问";
    }
}
