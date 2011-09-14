
package com.gfactor.web.wicket.service;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.util.lang.PackageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.gfactor.osgi.api.export.iface.IWicketPageService;
import com.gfactor.osgi.api.export.util.BundleContextInfoUtil;

/**
 * Default implementation of the {@link IWicketPageService} interface.
 * 
 * 
 * 
 */
public class WicketPageServiceImpl implements IWicketPageService {
    private static final Logger logger = LoggerFactory.getLogger(BundleContextInfoUtil.class);

	private WebApplication application;

	public WicketPageServiceImpl(WebApplication application) {
		this.application = application;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#addIgnoreMountPath(java.lang.String)
	 */
	public void addIgnoreMountPath(String path) {
		this.application.addIgnoreMountPath(path);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#mount(org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy)
	 */
	public void mount(IRequestTargetUrlCodingStrategy encoder) {
		this.application.mount(encoder);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#mount(java.lang.String,
	 *      org.apache.wicket.util.lang.PackageName)
	 */
	public void mount(String path, PackageName packageName) {
		this.application.mount(path, packageName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#mountBookmarkablePage(java.lang.String,
	 *      java.lang.Class)
	 */
	public <T extends Page> void mountBookmarkablePage(String path,
			Class<T> bookmarkablePageClass) {
		logger.debug("mountBookmarkablePage 77");
		this.application.mountBookmarkablePage(path, bookmarkablePageClass);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#mountBookmarkablePage(java.lang.String,
	 *      java.lang.String, java.lang.Class)
	 */
	public <T extends Page> void mountBookmarkablePage(String path,
			String pageMapName, Class<T> bookmarkablePageClass) {
		logger.debug("mountBookmarkablePage 90");
		logger.debug("Path = "+path);
		logger.debug("pageMapName = "+pageMapName);
		logger.debug("bookmarkablePageClass = "+bookmarkablePageClass);
		this.application.mountBookmarkablePage(path, pageMapName,
				bookmarkablePageClass); 

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#mountSharedResource(java.lang.String,
	 *      java.lang.String)
	 */
	public void mountSharedResource(String path, String resourceKey) {
		this.application.mountSharedResource(path, resourceKey);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketPageService#unmount(java.lang.String)
	 */
	public void unmount(String path) {
		this.application.unmount(path);

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}

}
