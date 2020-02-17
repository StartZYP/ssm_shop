package cn.edu.nuc.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.edu.nuc.shop.entiry.User;
import cn.edu.nuc.shop.service.interfaces.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String tologin(HttpSession session){
		session.removeAttribute("msg");
		return "redirect:/login.jsp";
	}

	@RequestMapping(value="/reg",method=RequestMethod.GET)
	public String toreg(HttpSession session){
		session.removeAttribute("msg");
		return "redirect:/reg.jsp";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(User user,HttpSession session){
		try {
			User exituser = userService.login(user.getUsername(), user.getPassword());

			session.setAttribute("frontuser", exituser.getUsername());

			session.removeAttribute("msg");

			return "redirect:/product/frontlist";

		} catch (Exception e) {

			session.setAttribute("msg", "用户或密码错误");
		}
		return "forward:/login.jsp";
	}

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(User user,HttpSession session){
		try {
			int checkusername = userService.checkusername(user.getUsername());
			if (checkusername>=1){
				session.setAttribute("msg", "此用户名已注册");
			}else {
				userService.regist(user);
				session.setAttribute("msg", "注册成功");
			}
		} catch (Exception e) {
			session.setAttribute("msg", "此用户名已注册");
		}
		return "forward:/reg.jsp";
	}


	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(User user,HttpSession session){

		session.removeAttribute("frontuser");

		return "redirect:/login.jsp";
	}
}