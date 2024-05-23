package com.example.demo.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	@Qualifier("userServiceImpl")  // layer architecture������ ���� ����ϴ� ����� ����.
	private UserService userService;
	
	@GetMapping("home")
	public String home(Model model) throws Exception{
		
		List<User> userList =  userService.getUserList();
		
		model.addAttribute("userList", userList);
		
		return "forward:/index.jsp";
	}
	
	@PostMapping("addUser")
	public String addUser(@ModelAttribute User user) throws Exception {
		
		int result = userService.addUser(user);
		
		if(result != 1)
			System.out.println("user ���� ����...");
		
		return "redirect:/user/home";
	}
	
	@PostMapping("updateUser")
	public String updateUser(@ModelAttribute User user) throws Exception {
		
		int result = userService.updateUser(user);
		
		if(result != 1)
			System.out.println("user ���� ����...");
		
		return "redirect:/user/home";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute User user, HttpSession session) throws Exception {
		
		User result = userService.getUser(user);
		
		// �α��� ���
		if(result != null) {
			System.out.println("�α��� ����");
			session.setAttribute("user", result);
		} else {
			System.out.println("�α��� ����");
		}
		
		return "redirect:/user/home";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) throws Exception {
		
		session.invalidate();
		
		return "redirect:/user/home";
	}
}
