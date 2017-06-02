package org.vaadin.teemusa.sidemenu.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.teemusa.sidemenu.SideMenu;
import org.vaadin.teemusa.sidemenu.SideMenu.MenuRegistration;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
@Viewport("user-scalable=no,initial-scale=1.0")
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

	private SideMenu sideMenu = new SideMenu();
	private boolean logoVisible = true;
	private ThemeResource logo = new ThemeResource("images/linux-penguin.png");
	private String menuCaption = "SideMenu Add-on";

	private MenuRegistration menuToRemove;

	@Override
	protected void init(VaadinRequest request) {
		setContent(sideMenu);
		Navigator navigator = new Navigator(this, sideMenu);
		setNavigator(navigator);

		// NOTE: Navigation and custom code menus should not be mixed.
		// See issue #8

		navigator.addView("", new FooView("Initial view"));
		navigator.addView("Foo", new FooView("Foo!"));

		// Since we're mixing both navigator and non-navigator menus the
		// navigator state needs to be manually triggered.
		navigator.navigateTo("");

		sideMenu.setMenuCaption(menuCaption, logo);

		// Navigation examples
		sideMenu.addNavigation("Initial View", "");
		sideMenu.addNavigation("Secondary View", VaadinIcons.AMBULANCE, "Foo");

		// Arbitrary method execution
		sideMenu.addMenuItem("My Menu Entry", () -> {
			VerticalLayout content = new VerticalLayout();
			content.addComponent(new Label("A layout"));
			sideMenu.setContent(content);
		});
		sideMenu.addMenuItem("Entry With Icon", VaadinIcons.ACCESSIBILITY, () -> {
			VerticalLayout content = new VerticalLayout();
			content.addComponent(new Label("Another layout"));
			sideMenu.setContent(content);
		})
				// Navigator has done its own setup, any menu can be selected.
				.select();

		// User menu controls
		sideMenu.addMenuItem("Show/Hide user menu", VaadinIcons.USER, () -> {
			sideMenu.setUserMenuVisible(!sideMenu.isUserMenuVisible());
		});

		menuToRemove = sideMenu.addMenuItem("Remove this menu item", () -> {
			if (menuToRemove != null) {
				menuToRemove.remove();
				menuToRemove = null;
			}
		});

		setUser("Guest", VaadinIcons.MALE);
	}

	private void setUser(String name, Resource icon) {
		sideMenu.setUserName(name);
		sideMenu.setUserIcon(icon);

		sideMenu.clearUserMenu();
		sideMenu.addUserMenuItem("Settings", VaadinIcons.WRENCH, () -> {
			Notification.show("Showing settings", Type.TRAY_NOTIFICATION);
		});
		sideMenu.addUserMenuItem("Sign out", () -> {
			Notification.show("Logging out..", Type.TRAY_NOTIFICATION);
		});

		sideMenu.addUserMenuItem("Hide logo", () -> {
			if (!logoVisible) {
				sideMenu.setMenuCaption(menuCaption, logo);
			} else {
				sideMenu.setMenuCaption(menuCaption);
			}
			logoVisible = !logoVisible;
		});
	}
}
