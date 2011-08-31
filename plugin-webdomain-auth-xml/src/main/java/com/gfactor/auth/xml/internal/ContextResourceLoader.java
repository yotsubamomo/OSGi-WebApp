package com.gfactor.auth.xml.internal;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


public class ContextResourceLoader implements ApplicationContextAware {
	private ApplicationContext applicationctx;
	private File returnFile = null;
	  
	
	 
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {		
		this.applicationctx = applicationContext;
//		Resource rs = applicationctx.getResource("file:META-INF/UserXml/UserDetail.xml");

	}
	
	public File getResourceFile(String classPathStr) throws IOException{		

		Resource rs = applicationctx.getResource(classPathStr);
		if(rs != null){
			returnFile =  rs.getFile(); 
		}		
		return returnFile;		
		
	}

}
