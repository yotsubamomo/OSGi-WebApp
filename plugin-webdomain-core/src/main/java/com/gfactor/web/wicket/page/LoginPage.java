package com.gfactor.web.wicket.page;
import javax.inject.Inject;

import org.apache.wicket.PageParameters;
import org.apache.wicket.Response;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.gfactor.export.iface.IGetMainPageClassService;
import com.gfactor.web.wicket.context.WebAuthenticatedWebSession;

/**
 *
 * @author mbenrhouma
 */
public final class LoginPage extends WebPage {
	
//	@SpringBean 
//	private IUpdate updatePatch;
	
	@Inject
	private IGetMainPageClassService mainPageService;
	 

    public LoginPage() {
        this(null);
    }
    
    	    
	@SuppressWarnings("deprecation")
	public LoginPage(final PageParameters parameters) {    	
        add(new LoginForm("loginform"));        
//        add(new PageLink("pageLink", new IPageLink() {
//        		public Page getPage() {
//        			System.out.println("get page chk updatePatch bean status -> "+updatePatch);
//        			updatePatch.updateFileFromServer();
//        			return new LoginPage();
//        		}
//        		public Class getPageIdentity() {
//        			return LoginPage.class;
//        		}
//        		}));
    }

    class LoginForm extends Form {

        private String username;
        private String password;

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new RequiredTextField("username"));
            add(new PasswordTextField("password"));
            add(new FeedbackPanel("feedback"));
        }

        @Override
        protected void onSubmit() {
        	System.out.println("mainPageService = "+mainPageService);
        	WebAuthenticatedWebSession session = WebAuthenticatedWebSession.getSpringWicketWebSession();
        	System.out.println("userName="+username);
        	System.out.println("password="+password);
        	System.out.println("WebAuthenticatedWebSession = "+ session);
			if (session.signIn(username, password)) {
				System.out.println("session signIn....");
				// redirect here
				// info(getString("login.success"));
				if (!continueToOriginalDestination()) {
//					System.out.println("HomePage.class = " + HomePage.class);
//					System.out.println("osgi get classes " + mainPageService.getPageClazz());
//					setResponsePage(HomePage.class);
					
					Class clazz = mainPageService.getPageClazz();
					
					System.out.println("response page classloader = " + clazz.getClassLoader());
//					
//					Class clazz2 = testPageService.getPageClass();
					setResponsePage(clazz);
//					setr
//					setResponsePage(SuccessPage.class);
//					setResponsePage(home.class);
				}
			} else {
				session.error("Login Fail!");
			}
		}

    }
}
