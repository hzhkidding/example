package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.LoginService;
import com.heeexy.example.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")

public class LoginController {

	@Autowired
	private LoginService loginService;


	/**
	 * 登录
	 */

	@PostMapping("/auth")
	public JSONObject authLogin(@RequestBody JSONObject requestJson) {
		System.out.println("auth");
		CommonUtil.hasAllRequired(requestJson, "username,password");
		System.out.println(requestJson.toJSONString());
		return loginService.authLogin(requestJson);
	}

	/**
	 * 查询当前登录用户的信息
	 */
	@GetMapping("/user")

	public JSONObject getInfo() {


		return loginService.getInfo();
	}

	/**
	 * 登出
	 */
	@PostMapping("/logout")
	public JSONObject logout() {
		return loginService.logout();
	}
}
