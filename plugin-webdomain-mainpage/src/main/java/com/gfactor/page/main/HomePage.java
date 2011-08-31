/**
 * 
 */
package com.gfactor.page.main;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.PageLink;
import org.odlabs.wiquery.core.events.Event;
import org.odlabs.wiquery.core.events.MouseEvent;
import org.odlabs.wiquery.core.events.WiQueryEventBehavior;
import org.odlabs.wiquery.core.javascript.JsScope;
import org.odlabs.wiquery.ui.dialog.Dialog;

import com.gfactor.locate.IPageClassLocateService;


/**
 * @author momo
 *
 */ 
public class HomePage extends WebPage {
	
	@Inject
	private IPageClassLocateService pageClassLocateService;
	


	public HomePage() {		
		super();
//		this.getApplication().addComponentInstantiationListener
		
		System.out.println("start HomePage(),....");
	
		System.out.println("classloader = "+ this.getClass().getClassLoader());
		System.out.println("service ... = " +pageClassLocateService);
		Class<?> pageClass = pageClassLocateService.getPageClass("plugin-webdomain-page-html", "1.0.0", "imagePage1");
		System.out.println("HomePage pageClass = "+ pageClass);
        final Dialog dialog = new Dialog("dialog");
        add(dialog); 
        
        Button button = new Button("open-dialog");
        button.add(new WiQueryEventBehavior(new Event(MouseEvent.DBLCLICK) {
                
                        @Override
                        public JsScope callback() {
                                return JsScope.quickScope(dialog.open().render());
                        }
                
                }));
        add(button);
        add(new PageLink("pageLink", pageClass));
    }


}
