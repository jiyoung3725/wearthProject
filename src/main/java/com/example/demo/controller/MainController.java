package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.MainService;

import lombok.Setter;

@Controller
@Setter
public class MainController {

	@Autowired
	private MainService mainService;
	
	@GetMapping("/")
	public String test(Model model) {
		model.addAttribute("edu_list", mainService.getPopularEducation());
		model.addAttribute("goods_list", mainService.getPopularGoods());
		return "main/main";
	}
	@GetMapping("/shop")
	public String test2() {
		return "shop/shopMain";
	}
	@GetMapping("/detail")
	public String test3() {
		return "shop/detail";
	}
	@GetMapping("/cart")
	public String test4() {
		return "shop/cart";
	}
	@GetMapping("/order")
	public String test5() {
		return "shop/order";
	}


}
