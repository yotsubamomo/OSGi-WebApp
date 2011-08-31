package com.gfactor.web.wicket.osgie;

import org.apache.wicket.injection.ConfigurableInjector;
import org.apache.wicket.injection.IFieldValueFactory;
import org.osgi.framework.BundleContext;

public class OsgiInjector extends ConfigurableInjector {
	IFieldValueFactory factory;
//	private BundleContext context;
	public OsgiInjector() {
		this(true);
	}

	public OsgiInjector(boolean wrapInProxies) {
		initFactory(wrapInProxies);
	}
	
//	public OsgiInjector(boolean wrapInProxies,BundleContext ctx) {
//		this.context = ctx;
//		initFactory(wrapInProxies,this.context);
//	}
	
	private void initFactory(boolean wrapInProxies) {
		factory = new OsgiFieldValueFactory(wrapInProxies);
	}
	
//	private void initFactory(boolean wrapInProxies,BundleContext ctx) {				
//		factory = new OsgiFieldValueFactory(wrapInProxies,ctx);
//	}
	
	@Override
	protected IFieldValueFactory getFieldValueFactory() {
		return factory;
	}
}
