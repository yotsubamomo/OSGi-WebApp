package com.gfactor.auth.xml;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gfactor.auth.xml.internal.XmlAcctReader;
import com.gfactor.export.classes.UserInfoObject;
import com.gfactor.export.iface.AbstractUserDetailService;

/**
 * 
 */

/**
 * @author momo
 *
 */
public class XmlUserDetailService implements AbstractUserDetailService {
	
	private XmlAcctReader xmlReader;
	
	
	public void setXmlReader(XmlAcctReader xmlReader) {
		this.xmlReader = xmlReader;
	}


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		UserInfoObject userDetil = xmlReader.getUserDetailFromXML(username);
		System.out.println("isAccountNonLocked = "+userDetil.isAccountNonLocked());
		System.out.println("isAccountNonExpired = "+userDetil.isAccountNonExpired());
		System.out.println("isCredentialsNonExpired = "+userDetil.isCredentialsNonExpired());
		System.out.println("isEnabled = "+userDetil.isEnabled());
		 
		return userDetil;
	}

}
