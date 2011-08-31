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

import com.gfactor.export.classes.Extension;
import com.gfactor.export.iface.IWicketExtensionService;



public class OsgiExtensionPointResolver implements IComponentResolver {

	private static final long serialVersionUID = 1L;
	private BundleContext bundleCtx;
	private static final String TAG_NAME = "extension-point";
	static {
		// register "wicket:extension-point"
		WicketTagIdentifier.registerWellKnownTagName(TAG_NAME);
	}
	
	public OsgiExtensionPointResolver(BundleContext btx){
		System.out.println("constructor for OsgiExtensionPointResolver");
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
			System.out.println("wtag.getName() ="+wtag.getName());
			
			if (TAG_NAME.equalsIgnoreCase(wtag.getName())) {
				String extensionPointId = wtag.getAttributes().getString("id");
				if ((extensionPointId == null) || (extensionPointId.trim().length() == 0)) {
					throw new MarkupException(
							"Wrong format of <wicket:extension-point id='xxx'>: attribute 'id' is missing");
				}
				System.out.println("extensionPointId = "+ extensionPointId);
								
				IWicketExtensionService s = (IWicketExtensionService)this.bundleCtx.getService(this.bundleCtx.getServiceReference(IWicketExtensionService.class.getName()));
				System.out.println("IWicketExtensionService s = "+ s);
				
				//extensions contain all extension-point id by class name,
				//such as extensions =[class com.osgiweb.apps2.OSGiWebApp2.HomePages.SimpleMenuContribution] 
				Collection<Class<? extends Extension>> extensions = s.findExtensions(extensionPointId);
				System.out.println("extensions ="+extensions);
				
				if (extensions != null && !extensions.isEmpty()) {
					for (Class<? extends Extension> ext : extensions) {
						try {
							final String compId = "_extension_" + extensionPointId + "_"
									+ container.getPage().getAutoIndex();
							System.out.println("compId ="+compId);
							
							Extension comp = ext.getConstructor(String.class).newInstance(compId);
							System.out.println("comp ="+comp);
							
							
							comp.setRenderBodyOnly(container.getApplication().getMarkupSettings().getStripWicketTags());
							System.out.println("container ->"+container.getApplication().getMarkupSettings().getStripWicketTags());
							
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
