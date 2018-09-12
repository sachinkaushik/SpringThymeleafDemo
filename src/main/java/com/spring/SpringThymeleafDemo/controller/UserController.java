package com.spring.SpringThymeleafDemo.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.SpringThymeleafDemo.entity.User;
import com.spring.SpringThymeleafDemo.repository.UserRespository;

@Controller
public class UserController {
	
	@Autowired
	private UserRespository userRepository;

	@GetMapping("/")
	public String homePage(@ModelAttribute User user) {
		return "index";
	}
	
	@GetMapping("/index")
	public String index(@ModelAttribute User user) {
		return "index";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute("user") User user, ModelMap map) {
		User usr = userRepository.save(user);
		if(Objects.nonNull(usr)) {
			map.put("msg", "User Created succussfully..");
			map.put("user", usr);
		}else {
			map.put("msg", "User not Created");
		}
		return "user";
	}
	
	@GetMapping("/allUser")
	public String allUser(ModelMap map, @RequestParam(required=false) String msg) {
		map.put("users", userRepository.findAll());
		map.put("msg", msg);
		return "allUser";
	}
	
	@GetMapping("/editUser/{id}")
	public String editUser(@ModelAttribute User user, @PathVariable(required=true) Integer id, ModelMap map) {
		map.put("user", userRepository.findById(id).get());
		return "editUser";
	}
	
	@PutMapping("/updateUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable(required=true) Integer id, ModelMap map) {
		String msg ;
		Optional<User> usr = userRepository.findById(id);
		if(usr.isPresent()) {
			BeanUtils.copyProperties(user, usr.get());
			userRepository.save(usr.get());
			msg = "User updated successfully";
		}else {
			msg = "User doesn't exist";
		}
		
		return "redirect:/allUser?msg="+msg;
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@ModelAttribute User user, @PathVariable(required=true) Integer id, ModelMap map) {
		String msg ;
		Optional<User> usr = userRepository.findById(id);
		if(usr.isPresent()) {
			userRepository.deleteById(id);
			msg = "User deleted succussfully..";
		}else
			msg ="User doesn't exist";
		return "redirect:/allUser?msg="+msg;
	}
}
