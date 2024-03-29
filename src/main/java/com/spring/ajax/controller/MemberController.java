package com.spring.ajax.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.ajax.service.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberService service;
	
	@RequestMapping(value= {"/","/index"})
	public String home() {
		return "index";
	}
	
	@RequestMapping(value= {"/joinForm"})
	public String joinForm() {
		return "joinForm";
	}
	
	//ajax 통신의 규칙
	// 1. Response 형태로 반환해야 한다.(ajax 는 요청하는 곳과 데이터 받는것이 같아야 한다.) -> 페이지 이동 안됨
	// 2. json 과 가장 비슷한 형태로 반환해야 한다.({key:value}, {key=value})
	// 3. json 형태로 바꿔줄 라이브러리가 필요하다.
	@RequestMapping(value="/overlay")
	@ResponseBody // ajax 에서 반환하는 값을 response 에 그려주는 역활을 한다.
	public HashMap<String, Object> overlay(@RequestParam String id) {
		boolean use = service.overlay(id);
		logger.info("사용 가능 여부 : "+use);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("use", use);		
		// No converter found for return value of type: class java.util.HashMap
		// response 는 출력(페이지 그리기)이 되는 객체 이므로 기존 페이지위에 값을 출력해 버린다.
		return map;
	}
	
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> join(@RequestParam HashMap<String, String> params){
		logger.info("params : "+params);
		HashMap<String, Object> result = new HashMap<String, Object>();	
		int row = service.join(params);		
		result.put("success", row);		
		return result;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> login(@RequestParam HashMap<String, String> params,
			HttpSession session){
		logger.info("params : "+params);
		HashMap<String, Object> result = service.login(params);
		logger.info("result : "+result);		
		if(result == null) {
			result = new HashMap<String, Object>();
			result.put("msg", "아이디 또는 비밀번호를 확인 하세요");
		}else {
			result.put("msg", "로그인에 성공 하였습니다.");
			session.setAttribute("loginId", result.get("id"));
			session.setAttribute("name", result.get("name"));
			session.setAttribute("perm", result.get("perm"));
		}
		
		return result;
	}
	
	@RequestMapping(value="/memberList")
	public String memberList() {
		return "memberList";
	}
	
	@RequestMapping(value="/getMemberList")
	@ResponseBody
	public HashMap<String, Object> getMemberList(HttpSession session) {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		//로그인이 되어있고, 권한도 있는 상태
		if(session.getAttribute("loginId") != null && session.getAttribute("perm") != null) {
			result.put("success", 1);
			ArrayList<HashMap<String, Object>> list = service.memberList();
			result.put("list", list);
			result.put("size", list.size());
		}else { // 로그인이 안되어 있거나, 권한이 없는 상태
			result.put("success", -1);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
