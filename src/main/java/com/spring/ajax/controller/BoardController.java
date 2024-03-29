package com.spring.ajax.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.ajax.dto.BoardDTO;
import com.spring.ajax.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService service;
	
	@RequestMapping(value="/boardList")
	public String boardList() {
		return "boardList";
	}
	
	@RequestMapping(value="/listCall")
	@ResponseBody
	public HashMap<String, Object> listCall(HttpSession session) {
		
		HashMap<String, Object>result = new HashMap<String, Object>();
		
		if(session.getAttribute("loginId") == null) {
			result.put("login", false);
		}else {
			result.put("login", true);
			ArrayList<BoardDTO> list = service.list();
			result.put("list", list);
		}
				
		return result;
	}
	
	// 배열로 파라케터가 들어올 경우 처리(배열임을 명시 해 줘야 한다.)
	@RequestMapping(value="/delete")
	@ResponseBody
	public HashMap<String, Object> delete(
			HttpSession session,
			@RequestParam(value="delList[]") ArrayList<String> delList){
		
		logger.info("delList : "+delList);
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(session.getAttribute("loginId")==null) {
			result.put("login", false);
		}else {
			result.put("login", true);
			int cnt = service.delete(delList);
			result.put("del_cnt", cnt);
		}
			
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
