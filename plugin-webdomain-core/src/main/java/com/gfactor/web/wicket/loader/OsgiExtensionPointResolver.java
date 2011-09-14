package com.gfactor.web.wicket.loader;

import java.util.Collection;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.parser.filter.WicketTagIdentifier;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gfactor.osgi.api.export.classes.Extension;
import com.gfactor.osgi.api.export.iface.IWicketExtensionService;
import com.gfactor.osgi.api.export.util.BundleContextInfoUtil;



public class OsgiExtensionPointResolver implements IComponentResolver {
    private static final Logger logger = LoggerFactory.getLogger(BundleContextInfoUtil.class);

	private static final long serialVersionUID = 1L;
	private BundleContext bundleCtx;
	private static final String TAG_NAME = "extension-point";
	static {
		// register "wicket:extension-point"
		WicketTagIdentifier.registerWellKnownTagName(TAG_NAME);
	}
	
	public OsgiExtensionPointResolver(BundleContext btx){
		logger.debug("constructor for OsgiExtensionPointResolver");
		this.bundleCtx = btx;
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.wicket.markup.resolver.IComponentResolver#resolve(org.apache.wicket.MarkupContainer,
	 *      org.apache.wicket.markup.MarkupStream,
	 *      org.apache.wicket.markup.ComponentTag)
	 */
	public boolean resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
		
		if (tag instanceof WicketTag) {
			WicketTag wtag = (WicketTag) tag;
			logger.debug("wtag.getName() ="+wtag.getName());
			
			if (TAG_NAME.equalsIgnoreCase(wtag.getName())) {
				String extensionPointId = wtag.getAttributes().getString("id");
				if ((extensionPointId == null) || (extensionPointId.trim().length() == 0)) {
					throw new MarkupException(
							"Wrong format of <wicket:extension-point id='xxx'>: attribute 'id' is missing");
				}
				logger.debug("extensionPointId = "+ extensionPointId);
								
				IWicketExtensionService s = (IWicketExtensionService)this.bundleCtx.getService(this.bundleCtx.getServiceReference(IWicketExtensionService.class.getName()));
				logger.debug("IWicketExtensionService s = "+ s);
				
				//extensions contain all extension-point id by class name,
				//such as extensions =[class com.osgiweb.apps2.OSGiWebApp2.HomePages.SimpleMenuContribution] 
				Collection<Class<? extends Extension>> extensions = s.findExtensions(extensionPointId);
				logger.debug("extensions ="+extensions);
				
				if (extensions != null && !extensions.isEmpty()) {
					for (Class<? extends Extension> ext : extensions) {
						try {
							final String compId = "_extension_" + extensionPointId + "_"
									+ container.getPage().getAutoIndex();
							logger.debug("compId ="+compId);
							
							Extension comp = ext.getConstructor(String.class).newInstance(compId);
							logger.debug("comp ="+comp);
							
							
							comp.setRenderBodyOnly(container.getApplication().getMarkupSettings().getStripWicketTags());
							logger.debug("container ->"+container.getApplication().getMarkupSettings().getStripWicketTags());
							
							container.autoAdd(comp, markupStream);
							markupStream.setCurrentIndex(markupStream.getCurrentIndex() - 1);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					markupStream.setCurrentIndex(markupStream.getCurrentIndex() + 1);
				} else {
					ExtensionPointContainer comp = new ExtensionPointContainer(extensionPointId
							+ "_" + container.getPage().getAutoIndex());
					comp.setRenderBodyOnly(container.getApplication().getMarkupSettings()
							.getStripWicketTags());
					container.autoAdd(comp, markupStream);
				}

				return true;

			}
		}

		// We were not able to handle the tag
		return false;
		
	}

	
	
	

}
