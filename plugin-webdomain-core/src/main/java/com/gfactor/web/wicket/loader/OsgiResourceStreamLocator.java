
package com.gfactor.web.wicket.loader;

import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
 

public class OsgiResourceStreamLocator extends ResourceStreamLocator
{
	/**
	 * Construct.
	 */
	public OsgiResourceStreamLocator()
	{
	}

	/**
	 * Construct.
	 * 
	 * @param finder
	 */
	public OsgiResourceStreamLocator(final IResourceFinder finder)
	{
		super(finder);
	}

	/**
	 * 
	 * @see org.apache.wicket.util.resource.locator.ResourceStreamLocator#locate(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public IResourceStream locate(final Class<?> clazz, final String path)
	{
		System.out.println("IResourceStream = "+clazz+", path-"+path);
		return super.locate(clazz, "/" + path);
	}
}
