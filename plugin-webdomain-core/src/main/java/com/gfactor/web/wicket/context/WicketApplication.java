package com.gfactor.web.wicket.context;

import org.apache.wicket.Page;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.odlabs.wiquery.core.commons.IWiQuerySettings;
import org.odlabs.wiquery.core.commons.WiQuerySettings;
import org.osgi.framework.BundleContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.stereotype.Component;

import com.gfactor.export.iface.IWicketExtensionService;
import com.gfactor.export.iface.IWicketPageService;
import com.gfactor.web.wicket.loader.OsgiExtensionPointResolver;
import com.gfactor.web.wicket.loader.OsgiResourceStreamLocator;
import com.gfactor.web.wicket.osgie.OsgiComponentInjector;
import com.gfactor.web.wicket.page.LoginPage;
import com.gfactor.web.wicket.service.WicketExtensionServiceImpl;
import com.gfactor.web.wicket.service.WicketPageServiceImpl;

@Component
public class WicketApplication extends AuthenticatedWebApplication  implements IWiQuerySettings,ApplicationContextAware,BundleContextAware {
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	@Autowired
    private ApplicationContext applicationContext;
	private BundleContext bundleCtx;
	
	static ApplicationContext appCtx;
	
	@Autowired
	private IClassResolver osgiClassResolver;
	
	@Override
    public Class<? extends Page> getHomePage() {
//        return home.class;
		return LoginPage.class;
    }
	protected void init() {
		   System.out.println("WicketApplication init........");
		   System.out.println("osgiClassResolver = "+ osgiClassResolver);
		   this.getApplicationSettings().setClassResolver(osgiClassResolver);
		   this.getResourceSettings().setResourceStreamLocator(new OsgiResourceStreamLocator());
		   this.getPageSettings().addComponentResolver(new OsgiExtensionPointResolver(this.bundleCtx));
		   super.init(); 
		   OsgiComponentInjector compInj = new OsgiComponentInjector(true);

		   
//		   addComponentInstantiationListener(new SpringComponentInjector(this,applicationContext,true));
		   addComponentInstantiationListener(compInj);
		   getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);	
		   registerPageService(this);
	}
	
	
	public void registerPageService(WebApplication app){
		System.out.println("register pageService");
		System.out.println("class = "+ IWicketPageService.class.getName());
		this.bundleCtx.registerService(IWicketPageService.class.getName(), new WicketPageServiceImpl(this), null);
		System.out.println("register pageService finished");
		
		this.bundleCtx.registerService(IWicketExtensionService.class.getName(),new WicketExtensionServiceImpl(), null);
	}
	
	@Override
	public WiQuerySettings getWiQuerySettings() {
		 WiQuerySettings settings = new WiQuerySettings();
         settings.setEnableResourcesMerging(true);         
         return settings;

	}
	
	
	
	
//	@Override
//    public String getConfigurationType() {
//        return WebApplication.DEVELOPMENT;
//    }
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		 this.applicationContext = applicationContext;
		 appCtx =applicationContext;
	}
	
	public static ApplicationContext getApplicationCtx(){
		return appCtx;
	}
	// public static WicketApplication get() {
	// return (WicketApplication) WebApplication.get();
	// }
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	} 

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return WebAuthenticatedWebSession.class;
	}
	
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleCtx = bundleContext;
		System.out.println("bundle ctx = "+bundleCtx );
	}

	
	
}	
