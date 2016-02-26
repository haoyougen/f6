package com.f6.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.f6.exceptions.AuthenticationException;
import com.f6.exceptions.BadParameterException;

@Controller
public class CommonController extends BaseController {

	@Override
	public void authenticate(HttpServletRequest requset, HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataValidate(HttpServletRequest requset, HttpServletResponse response) throws BadParameterException {
		// TODO Auto-generated method stub

	}

	@Override
	public void postProcess(HttpServletRequest requset, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	 
}
