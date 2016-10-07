package org.vaadin.teemusa.sidemenu.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.teemusa.sidemenu.SideMenu;
import org.vaadin.teemusa.sidemenu.SideMenuUI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("SideMenu Add-on Demo")
@SideMenuUI
public class DemoUI extends UI {

	private final class FooView extends VerticalLayout implements View {

		public FooView(String text) {
			addComponent(new Label(text));
		}

		@Override
		public void enter(ViewChangeEvent event) {
		}
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
	public static class Servlet extends VaadinServlet {
	}

	SideMenu sideMenu = new SideMenu();

	@Override
	protected void init(VaadinRequest request) {
		setContent(sideMenu);
		Navigator navigator = new Navigator(this, sideMenu);
		setNavigator(navigator);
		navigator.addView("", new FooView("Initial view"));
		navigator.addView("Foo", new FooView("Foo!"));

		sideMenu.setMenuCaption(null);
		sideMenu.setMenuImage(null,  new ThemeResource("images/linux-penguin.png"));

		// Navigation examples
		sideMenu.addNavigation("Initial View", "");
		sideMenu.addNavigation("Foo View", FontAwesome.AMBULANCE, "Foo");

		// Arbitrary method execution
		sideMenu.addMenuItem("My Menu Entry", () -> {
			VerticalLayout content = new VerticalLayout();
			content.addComponent(new Label("A layout"));
			sideMenu.setContent(content);
		});
		sideMenu.addMenuItem("Entry With Icon", FontAwesome.ANDROID, () -> {
			VerticalLayout content = new VerticalLayout();
			content.addComponent(new Label("Another layout"));
			sideMenu.setContent(content);
		});

		// User menu controls
		sideMenu.addMenuItem("Show/Hide user menu", FontAwesome.USER, () -> {
			sideMenu.setUserMenuVisible(!sideMenu.isUserMenuVisible());
		});

		setUser("Guest", FontAwesome.MALE);
	}

	private void setUser(String name, Resource icon) {
		sideMenu.setUserName(name);
		sideMenu.setUserIcon(icon);

		sideMenu.clearUserMenu();
		sideMenu.addUserMenuItem("Settings", FontAwesome.WRENCH, () -> {
			Notification.show("Showing settings", Type.TRAY_NOTIFICATION);
		});
		sideMenu.addUserMenuItem("Sign out", () -> {
			Notification.show("Logging out..", Type.TRAY_NOTIFICATION);
		});
	}
}
