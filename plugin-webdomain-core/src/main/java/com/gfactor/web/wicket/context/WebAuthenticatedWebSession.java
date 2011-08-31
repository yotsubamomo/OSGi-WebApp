package com.gfactor.web.wicket.context;

import javax.inject.Inject;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebAuthenticatedWebSession extends AuthenticatedWebSession{
	
	//jaasAuthenticationProvider
	@Inject
    private AuthenticationManager authenticationManager;
	
	public WebAuthenticatedWebSession(Request request) {		
		super(request);		
		injectDependencies();		
		ensureDependenciesNotNull();		
	}
	  
	
	private void ensureDependenciesNotNull() {
		if (authenticationManager == null) {
			throw new IllegalStateException(
					"AdminSession requires an authenticationManager.");
		}
	}

	private void injectDependencies() {
		InjectorHolder.getInjector().inject(this);
	}
	 
	
	public static WebAuthenticatedWebSession getSpringWicketWebSession() {
		return (WebAuthenticatedWebSession) Session.get();
	}
	 
	@Override
    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        System.out.println("start authenticate");
        System.out.println("username="+username);
        
        try {
        	System.out.println("authenticationManager = "+authenticationManager);
        	
        	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
            System.out.println("authenticationManager instanceof = "+(authenticationManager instanceof org.springframework.security.authentication.AuthenticationManager));
            
            
            Authentication authentication = authenticationManager.authenticate(auth);
            System.out.println("authentication = "+ authentication);
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authenticated = authentication.isAuthenticated();
            System.out.println("authenticated = "+ authenticated);
        } catch (AuthenticationException e) {
        	e.printStackTrace();
        	System.out.println("Exception for auth - "+e);
            authenticated = false;
        }
        return authenticated;
    }
	
	

	@Override
    public Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }

    private void getRolesIfSignedIn(Roles roles) {
        if (isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            addRolesFromAuthentication(roles, authentication);
        }
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

}
