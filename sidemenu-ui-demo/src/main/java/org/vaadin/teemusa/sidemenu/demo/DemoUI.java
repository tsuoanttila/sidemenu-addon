package org.vaadin.teemusa.sidemenu.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.teemusa.sidemenu.SideMenuUI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;

@Theme("demo")
@Title("SideMenuUI Add-on Demo")
@SuppressWarnings("serial")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DemoUI extends SideMenuUI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		addMenu("My Menu Entry", () -> {
			Notification.show("Here is my custom action for this menu item.");
		});
	}
}
