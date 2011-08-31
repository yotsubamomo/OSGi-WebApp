package com.gfactor.auth.xml.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.gfactor.export.classes.UserInfoObject;

public class XmlAcctReader {
	private ContextResourceLoader ctxRsLoader;
	public void setCtxRsLoader(ContextResourceLoader ctxRsLoader) throws IOException {
		this.ctxRsLoader = ctxRsLoader;		
	}
	
	
	public UserInfoObject getUserDetailFromXML(String username){
		UserInfoObject userDetail = null;
		try{
			
			File xmlFile = ctxRsLoader.getResourceFile("/META-INF/UserXml/UserDetail.xml");
//			File xmlFile = new File("D:/EclipseJava-Project/plugin-webdomain-auth-xml/src/main/resources/META-INF/UserXml/UserDetail.xml");
			Document docJDOM=null;
			SAXBuilder bSAX = new SAXBuilder(false);
			docJDOM = bSAX.build(xmlFile);
			Element elmtRoot = docJDOM.getRootElement();
			List<Element> allChildren = elmtRoot.getChildren();			
			System.out.println("allChildren size = " +allChildren.size());
			for (int i = 0; i < allChildren.size(); i++) {
//				List<Element> children = allChildren.get(i).getChildren();
//				System.out.println(children.get(0).getText());
				if(username.equals(allChildren.get(i).getChild("Username").getText())){
					List<Element> role = allChildren.get(i).getChild("GrantedAuthorities").getChildren();
					List<GrantedAuthority> grant = new ArrayList<GrantedAuthority>();					
					for (int j = 0; j < role.size(); j++) {
						if(role.get(j).getText() != null && role.get(j).getText().length() >0){
							grant.add(new GrantedAuthorityImpl(role.get(j).getText()));
						}						
					}
					final String passwd = allChildren.get(i).getChild("Password").getText();
					final boolean enabled = getBooleanValue(allChildren.get(i).getChild("Enable").getText());
					final boolean accountNonExpired = getBooleanValue(allChildren.get(i).getChild("AccountNonExpired").getText());
					final boolean credentialsNonExpired = getBooleanValue(allChildren.get(i).getChild("CredentialsNonExpired").getText());
					final boolean accountNonLocked = getBooleanValue(allChildren.get(i).getChild("AccountNonLocked").getText());
					final String mail = allChildren.get(i).getChild("Mail").getText();
					
					userDetail = new UserInfoObject(username,passwd , enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grant);					
					userDetail.setMail(mail);
					
				}else{
					//do something...
				}
				
				
			}
			System.out.println(userDetail);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return userDetail;
	}
	
	private boolean getBooleanValue(String value){
		return value.equals("true") ? true : false;
	}
	
	public static void main(String[] args) {
		XmlAcctReader reader = new XmlAcctReader();
		reader.getUserDetailFromXML("yotsuba");
	}
	
}
