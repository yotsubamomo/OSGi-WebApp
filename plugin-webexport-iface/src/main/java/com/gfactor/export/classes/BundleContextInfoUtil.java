package com.gfactor.export.classes;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class BundleContextInfoUtil {
	private BundleContext bundleCtx;
	private Bundle bundle;
	
	public BundleContextInfoUtil(BundleContext bndCtx){
		this.bundleCtx = bndCtx;
		bundle = this.bundleCtx.getBundle();
		this.bundle.getBundleId();
		this.bundle.getClass();
	}
	
	public String getSymbolicName(){
		return this.bundle.getSymbolicName();
	}
	
	public String getBundleVersionString(){
		return this.bundle.getVersion().toString();
	}
	
	public long getBundleId(){
		return this.bundle.getBundleId();
	}
	
	
}
