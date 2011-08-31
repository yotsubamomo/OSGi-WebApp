package com.gfactor.locate;

import com.gfactor.pageinfo.jpa.Bndpageinfo;

public interface IPageClassLocateService {
	public Class<?> getPageClass(Bndpageinfo bndobj);
	public Class<?> getPageClass(String bundleName,String bundleVer,String entryPoint);
}
