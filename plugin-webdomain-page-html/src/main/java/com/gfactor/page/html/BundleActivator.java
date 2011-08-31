package com.gfactor.page.html;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

import com.gfactor.page.html.internal.NonePage;
import com.gfactor.pageinfo.jpa.Bndpageinfo;
import com.gfactor.pageinfo.service.IRegisterWicketPageBndIdentify;

public class BundleActivator implements BundleContextAware {
	
	private BundleContext bundleCtx;
	private Bundle bundle;
	private Bndpageinfo bndobj;
	 
public IRegisterWicketPageBndIdentify getRegisterWicketPageBndIdentify() {
		return registerWicketPageBndIdentify;
	}

	public void setRegisterWicketPageBndIdentify(IRegisterWicketPageBndIdentify registerWicketPageBndIdentify) {
		this.registerWicketPageBndIdentify = registerWicketPageBndIdentify;
	}

	//	@Autowired
	private IRegisterWicketPageBndIdentify registerWicketPageBndIdentify;
	
	public void start(){		
		this.bndobj.setClass_name(NonePage.class.getName());		
		this.bndobj.setEntry_point("imagePage1");
		System.out.println("bndobj init finished  ,register = " + this.bndobj);
		
		registerWicketPageBndIdentify.registerPageInfo(this.bndobj); 

	} 
	
	public void stop(){
		System.out.println("unregister = " + this.bndobj);
		registerWicketPageBndIdentify.unregisterPageInfo(this.bndobj);
		
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleCtx = bundleContext;
		this.bundle = this.bundleCtx.getBundle();
		setBndObject();
		registerWicketPageBndIdentify.unregisterPageInfo(this.bndobj);
	}
	
	public void setBndObject(){		
		bndobj = new Bndpageinfo();
		this.bndobj.setBundle_name(bundle.getSymbolicName());		
		this.bndobj.setBundle_version(bundle.getVersion().toString());
		this.bndobj.setId(bundle.getBundleId());
	}
}
