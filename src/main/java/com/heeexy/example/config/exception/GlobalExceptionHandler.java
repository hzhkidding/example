package com.heeexy.example.config.exception;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: hxy
 * @description: 统一异常拦截
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ExceptionHandler(value = Exception.class)
	public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
		String errorPosition = "";
		//如果错误堆栈信息存在
		if (e.getStackTrace().length > 0) {
			StackTraceElement element = e.getStackTrace()[0];
			String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
			int lineNumber = element.getLineNumber();
			errorPosition = fileName + ":" + lineNumber;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ErrorEnum.E_400.getErrorCode());
		jsonObject.put("msg", ErrorEnum.E_400.getErrorMsg());
		JSONObject errorObject = new JSONObject();
		errorObject.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
		jsonObject.put("info", errorObject);
		logger.error("异常", e);
		return jsonObject;
	}


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public JSONObject httpRequestMethodHandler() {
		return CommonUtil.errorJson(ErrorEnum.E_500);
	}


	@ExceptionHandler(CommonJsonException.class)
	public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
		return commonJsonException.getResultJson();
	}


	@ExceptionHandler(UnauthorizedException.class)
	public JSONObject unauthorizedExceptionHandler() {
		return CommonUtil.errorJson(ErrorEnum.E_502);
	}


	@ExceptionHandler(UnauthenticatedException.class)
	public JSONObject unauthenticatedException() {
		return CommonUtil.errorJson(ErrorEnum.E_20011);
	}
}
