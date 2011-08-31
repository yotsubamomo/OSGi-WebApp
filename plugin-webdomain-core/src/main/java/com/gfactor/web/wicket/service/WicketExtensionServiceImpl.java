package com.gfactor.web.wicket.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.gfactor.export.classes.Extension;
import com.gfactor.export.iface.IWicketExtensionService;



public class WicketExtensionServiceImpl implements IWicketExtensionService {

	// Thread
	private ConcurrentHashMap<String, Set<Class<? extends Extension>>> extensionsRegistry = new ConcurrentHashMap<String, Set<Class<? extends Extension>>>();
 
	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketExtensionService#registerExtension(java.lang.String,
	 *      java.lang.Class)
	 */
	public void registerExtension(String extensionPointId,
			Class<? extends Extension> extension) {

		if (!this.extensionsRegistry.containsKey(extensionPointId)) {
			this.extensionsRegistry.put(extensionPointId,
					new HashSet<Class<? extends Extension>>());
		}

		Set<Class<? extends Extension>> extensions = this.extensionsRegistry
				.get(extensionPointId);
		extensions.add(extension);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketExtensionService#findExtensions(java.lang.String)
	 */
	public Collection<Class<? extends Extension>> findExtensions(
			String extensionPointId) {
		return this.extensionsRegistry.get(extensionPointId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see net.javaforge.wicket.osgi.service.IWicketExtensionService#unregisterExtension(java.lang.Class)
	 */
	public void unregisterExtension(Class<? extends Extension> extension) {

		Collection<Set<Class<? extends Extension>>> values = this.extensionsRegistry
				.values();
		for (Set<Class<? extends Extension>> subset : values) {
			Iterator<Class<? extends Extension>> it = subset.iterator();
			while (it.hasNext()) {
				Class<? extends Extension> ext = it.next();
				if (ext == extension)
					it.remove();
			}

		}

	}

}
