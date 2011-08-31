package com.gfactor.web.wicket.loader;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag;
 
public class ExtensionPointContainer extends MarkupContainer {

	private static final long serialVersionUID = 1L;

	public ExtensionPointContainer(String id) {			
		super(id);
		System.out.println("ExtensionPointContainer start");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.wicket.MarkupContainer#isTransparentResolver()
	 */
	@Override
	public boolean isTransparentResolver() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.wicket.Component#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag) {
		// Convert <wicket:extension-point /> into
		// <wicket:extension-point>...</wicket:extension-point>
		if (tag.isOpenClose()) {
			tag.setType(XmlTag.OPEN);
		}
		super.onComponentTag(tag);
	}

}