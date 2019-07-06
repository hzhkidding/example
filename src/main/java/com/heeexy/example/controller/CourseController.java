package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CourseService;
import com.heeexy.example.util.CommonUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/article")

public class CourseController {
	private  final Logger logger = LoggerFactory.getLogger(CourseController.class);
	@Autowired
	private CourseService articleService;



	@RequiresPermissions("article:list")
	@GetMapping("/courses")
	public JSONObject listArticle(HttpServletRequest request) {
		return articleService.listArticle(CommonUtil.request2Json(request));
	}

	@RequiresPermissions("course:add")
	@PostMapping("/course")
	public JSONObject addArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "content");

		return articleService.addArticle(requestJson);
	}


	@RequiresPermissions("course:update")
	@PutMapping("/course")
	public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "id,content");
		return articleService.updateArticle(requestJson);
	}
}
