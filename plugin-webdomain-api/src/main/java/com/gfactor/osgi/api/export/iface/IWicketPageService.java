/* 
  * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gfactor.osgi.api.export.iface;

import org.apache.wicket.Page;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.util.lang.PackageName;

/**
 * @author mkalina 
 * 
 */ 
public interface IWicketPageService {

	/**
	 * Mounts an encoder at the given path.
	 * 
	 * @param encoder
	 *            the encoder that will be used for this mount
	 */
	void mount(IRequestTargetUrlCodingStrategy encoder);

	/**
	 * Mounts all bookmarkable pages at the given path.
	 * 
	 * @param path
	 *            the path to mount the bookmarkable page class on
	 * @param packageName
	 *            the name of the package for which all bookmarkable pages or
	 *            sharedresources should be mounted
	 */
	void mount(final String path, final PackageName packageName);

	/**
	 * Mounts a bookmarkable page class to the given path.
	 * 
	 * @param <T>
	 *            type of page
	 * 
	 * @param path
	 *            the path to mount the bookmarkable page class on
	 * @param bookmarkablePageClass
	 *            the bookmarkable page class to mount
	 */
	<T extends Page> void mountBookmarkablePage(final String path,
			final Class<T> bookmarkablePageClass);

	/**
	 * Mounts a bookmarkable page class to the given pagemap and path.
	 * 
	 * @param <T>
	 *            type of page
	 * 
	 * @param path
	 *            the path to mount the bookmarkable page class on
	 * @param pageMapName
	 *            name of the pagemap this mount is for
	 * @param bookmarkablePageClass
	 *            the bookmarkable page class to mount
	 */
	<T extends Page> void mountBookmarkablePage(final String path,
			final String pageMapName, final Class<T> bookmarkablePageClass);

	/**
	 * Mounts a shared resource class to the given path.
	 * 
	 * @param path
	 *            the path to mount the resource class on
	 * @param resourceKey
	 *            the shared key of the resource being mounted
	 */
	void mountSharedResource(final String path, final String resourceKey);

	/**
	 * Partly unmounts/ignores a path that normally would map to another mount
	 * path. Like mount("/mypage", MyPage.class); and then "/mypage/arealdir"
	 * should be ignored. This can be done by calling
	 * unMount("/mypage/arealdir");
	 * 
	 * @param path
	 *            the path that should be ignored.
	 * 
	 */
	void addIgnoreMountPath(String path);

	/**
	 * Unmounts whatever encoder is mounted at a given path.
	 * 
	 * @param path
	 *            the path of the encoder to unmount
	 */
	void unmount(String path);
}
