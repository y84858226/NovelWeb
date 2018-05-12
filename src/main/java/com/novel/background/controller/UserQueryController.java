package com.novel.background.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.novel.background.pojo.User;
import com.novel.background.service.UserService;



//证明是controller层并且返回json，不能返回html页面 @controller有视图解析器，可以返回html页面
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.novel.background.service"})//添加的注解
public class UserQueryController {
    //依赖注入
    @Autowired
    UserService userService;

    /**
     * @RestController代表这个类是用Restful风格来访问的，如果是普通的WEB页面访问跳转时，我们通常会使用@Controller
        value = "/users/{username}" 代表访问的URL是"http://host:PORT/users/实际的用户名"
            method = RequestMethod.GET 代表这个HTTP请求必须是以GET方式访问
        consumes="application/json" 代表数据传输格式是json
        @PathVariable将某个动态参数放到URL请求路径中
        @RequestParam指定了请求参数名称
     */
    @RequestMapping(value = "queryU/{username}",method = RequestMethod.GET)
    public  List<User> queryProduct(@PathVariable String username,HttpServletResponse httpServletResponse) {
        System.out.println(username);
        List<User> ulist = userService.queryUserByUserName(username);
        return ulist;
    }
    
    @ResponseBody
	@RequestMapping("hello")
	public String hello() {
		return "hello";
	}
}