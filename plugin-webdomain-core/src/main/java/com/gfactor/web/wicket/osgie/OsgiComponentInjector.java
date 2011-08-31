package com.gfactor.web.wicket.osgie;

import org.apache.wicket.injection.ComponentInjector;
import org.apache.wicket.injection.web.InjectorHolder;
import org.osgi.framework.BundleContext;

public class OsgiComponentInjector extends ComponentInjector {

    public OsgiComponentInjector() {
        this(true);
    }

    public OsgiComponentInjector(boolean wrapInProxies) {
        InjectorHolder.setInjector(new OsgiInjector(wrapInProxies));
    }
    
//    public OsgiComponentInjector(boolean wrapInProxies,BundleContext ctx) {
//        InjectorHolder.setInjector(new OsgiInjector(wrapInProxies,ctx));
//    }
}

