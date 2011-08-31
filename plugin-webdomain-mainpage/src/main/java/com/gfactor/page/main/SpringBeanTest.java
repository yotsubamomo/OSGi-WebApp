package com.gfactor.page.main;

import org.springframework.beans.factory.annotation.Autowired;

import com.gfactor.locate.IPageClassLocateService;

public class SpringBeanTest {
	@Autowired
	private IPageClassLocateService pageClassLocateService;
	
	public void check(){
		System.out.println("pageClassLocateService = "+ pageClassLocateService);
		System.out.println("SpringBeanTest");
	}
}
