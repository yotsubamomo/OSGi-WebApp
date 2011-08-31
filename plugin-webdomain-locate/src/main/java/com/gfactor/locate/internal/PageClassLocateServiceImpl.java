/**
 * 
 */
package com.gfactor.locate.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.osgi.context.BundleContextAware;

import com.gfactor.locate.IPageClassLocateService;
import com.gfactor.pageinfo.jpa.Bndpageinfo;
import com.gfactor.pageinfo.service.IGetPageObjectInfoService;

/**
 * @author momo
 *
 */
public class PageClassLocateServiceImpl implements IPageClassLocateService ,BundleContextAware{
	
	private BundleContext bundleCtx;
	
	@Autowired
	private IGetPageObjectInfoService getPageObjectInfoService;
	

	public Class<?> getPageClass(Bndpageinfo bndobj) {
		Bundle bundle = bundleCtx.getBundle(bndobj.getId());		
		System.out.println("to get bundle by id = " +bndobj.getId() +" , bundle ="+bundle);
		
		Class<?> clazz = null;
		try {
			 System.out.println("loading class name = " + bndobj.getClass_name());
			 clazz = bundle.loadClass(bndobj.getClass_name());
			 
			 System.out.println("locate bundleclass on " +bundle.getSymbolicName()+ " = "+ clazz);
			 
		} catch (ClassNotFoundException e) {
			System.out.println("no class found..." + e);
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return clazz;
	}
	
	public Class<?> getPageClass(String bundleName, String bundleVer,String entryPoint) {
		
		Bndpageinfo bndobj = getPageObjectInfoService.getBndPageInfo(bundleName, bundleVer, entryPoint);		
		Class<?> clazz = getPageClass(bndobj);
		return clazz;
	}
	

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleCtx = bundleContext;
	}


	

}
