package com.spring.ajax.dao;

import java.util.ArrayList;

import com.spring.ajax.dto.BoardDTO;

public interface BoardDAO {

	ArrayList<BoardDTO> list();

	ArrayList<String> getPhoto(String idx);

	int delete(String idx);

}
