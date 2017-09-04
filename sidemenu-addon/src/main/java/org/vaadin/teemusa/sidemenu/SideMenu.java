package org.vaadin.teemusa.sidemenu;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.SerializableConsumer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

/**
 * A helper component to make it easy to create menus like the one in the
 * quicktickets demo. The SideMenu should be the content of the UI, and all
 * other components should be handled through it. The UI should be annotated
 * with {@code @Viewport("user-scalable=no,initial-scale=1.0")} to provide
 * responsiveness on mobile devices.
 * <p>
 * This component has modification to allow it to be used easily with
 * {@link Navigator}. Pass it as a parameter to the constructor like
 * {@code new Navigator(myUI, sideMenu)}.
 *
 * @author Teemu Suo-Anttila
 */
@SuppressWarnings("serial")
public class SideMenu extends HorizontalLayout {

	/**
	 * A simple lambda compatible handler class for executing code when a menu
	 * entry is clicked.
	 */
	public interface MenuClickHandler extends Serializable {

		/**
		 * This method is called when associated menu entry is clicked.
		 */
		void click();
	}

	/**
	 * Interface to provide operations to existing menu items.
	 *
	 * @since 2.0
	 */
	public interface MenuRegistration extends Serializable {

		/**
		 * Select the menu object associated with this registration.
		 */
		void select();

		/**
		 * Removes the menu object associated with this registration.
		 */
		void remove();
	}

	private final class MenuRegistrationImpl<T> implements MenuRegistration {

		private SerializableConsumer<T> selectMethod;
		private SerializableConsumer<T> removeMethod;
		private T menuItem;

		public MenuRegistrationImpl(T menuItem, SerializableConsumer<T> selectMethod,
				SerializableConsumer<T> removeMethod) {
			this.menuItem = menuItem;
			this.selectMethod = selectMethod;
			this.removeMethod = removeMethod;
		}

		@Override
		public void select() {
			selectMethod.accept(menuItem);
		}

		@Override
		public void remove() {
			removeMethod.accept(menuItem);
		}
	}

	/* Class name for hiding the menu when screen is too small */
	private static final String STYLE_VISIBLE = "valo-menu-visible";

	/* Components to handle content and menus */
	private final VerticalLayout contentArea = new VerticalLayout();
	private final CssLayout menuArea = new CssLayout();
	private final CssLayout menuItemsLayout = new CssLayout();
	private final MenuBar userMenu = new MenuBar();

	/* Quick access to user drop down menu */
	private MenuItem userItem;

	/* Caption component for the whole menu */
	private HorizontalLayout logoWrapper;
	private Image menuImage;

	/**
	 * Constructor for creating a SideMenu component. This method sets up all
	 * the components and styles needed for the side menu.
	 */
	public SideMenu() {
		super();
		setSpacing(false);
		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		setSizeFull();

		menuArea.setPrimaryStyleName("valo-menu");
		menuArea.addStyleName("sidebar");
		menuArea.addStyleName(ValoTheme.MENU_PART);
		menuArea.addStyleName("no-vertical-drag-hints");
		menuArea.addStyleName("no-horizontal-drag-hints");
		menuArea.setWidth(null);
		menuArea.setHeight("100%");

		logoWrapper = new HorizontalLayout();
		logoWrapper.addStyleName("valo-menu-title");
		menuArea.addComponent(logoWrapper);

		userMenu.addStyleName("user-menu");
		userItem = userMenu.addItem("", null);

		menuArea.addComponent(userMenu);

		Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (menuArea.getStyleName().contains(STYLE_VISIBLE)) {
					menuArea.removeStyleName(STYLE_VISIBLE);
				} else {
					menuArea.addStyleName(STYLE_VISIBLE);
				}
			}
		});
		valoMenuToggleButton.setIcon(VaadinIcons.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		menuArea.addComponent(valoMenuToggleButton);

		menuItemsLayout.addStyleName("valo-menuitems");
		menuArea.addComponent(menuItemsLayout);

		contentArea.setPrimaryStyleName("valo-content");
		contentArea.addStyleName("v-scrollable");
		contentArea.setSizeFull();

		// Remove default margins and spacings
		contentArea.setMargin(false);
		contentArea.setSpacing(false);

		super.addComponent(menuArea);
		super.addComponent(contentArea);
		setExpandRatio(contentArea, 1);
	}

	/**
	 * Adds a menu entry. The given handler is called when the user clicks the
	 * entry.
	 *
	 * @param text
	 *            menu text
	 * @param handler
	 *            menu click handler
	 *
	 * @return menu registration
	 */
	public MenuRegistration addMenuItem(String text, MenuClickHandler handler) {
		return addMenuItem(text, null, handler);
	}

	/**
	 * Adds a menu entry with given icon. The given handler is called when the
	 * user clicks the entry.
	 *
	 * @param text
	 *            menu text
	 * @param icon
	 *            menu icon
	 * @param handler
	 *            menu click handler
	 *
	 * @return menu registration
	 */
	public MenuRegistration addMenuItem(String text, Resource icon, final MenuClickHandler handler) {
		Button button = new Button(text, new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				handler.click();
				menuArea.removeStyleName(STYLE_VISIBLE);
			}
		});
		button.setIcon(icon);
		button.setPrimaryStyleName("valo-menu-item");
		menuItemsLayout.addComponent(button);
		return new MenuRegistrationImpl<>(button, b -> b.click(), menuItemsLayout::removeComponent);
	}

	/**
	 * Adds a menu entry to the user drop down menu. The given handler is called
	 * when the user clicks the entry.
	 *
	 * @param text
	 *            menu text
	 * @param handler
	 *            menu click handler
	 *
	 * @return menu registration
	 */
	public MenuRegistration addUserMenuItem(String text, MenuClickHandler handler) {
		return addUserMenuItem(text, null, handler);
	}

	/**
	 * Adds a menu entry to the user drop down menu with given icon. The given
	 * handler is called when the user clicks the entry.
	 *
	 * @param text
	 *            menu text
	 * @param icon
	 *            menu icon
	 * @param handler
	 *            menu click handler
	 *
	 * @return menu registration
	 */
	public MenuRegistration addUserMenuItem(String text, Resource icon, final MenuClickHandler handler) {
		Command menuCommand = selectedItem -> handler.click();
		MenuItem menuItem = userItem.addItem(text, icon, menuCommand);
		return new MenuRegistrationImpl<>(menuItem, menuCommand::menuSelected, userItem::removeChild);
	}

	/**
	 * Sets the user name to be displayed in the menu.
	 *
	 * @param userName
	 *            user name
	 */
	public void setUserName(String userName) {
		userItem.setText(userName);
	}

	/**
	 * Sets the portrait of the user to be displayed in the menu.
	 *
	 * @param icon
	 *            portrait of the user
	 */
	public void setUserIcon(Resource icon) {
		userItem.setIcon(icon);
	}

	/**
	 * Sets the visibility of the whole user menu. This includes portrait, user
	 * name and the drop down menu.
	 *
	 * @param visible
	 *            user menu visibility
	 */
	public void setUserMenuVisible(boolean visible) {
		userMenu.setVisible(visible);
	}

	/**
	 * Gets the visibility of the user menu.
	 *
	 * @return {@code true} if visible; {@code false} if hidden
	 */
	public boolean isUserMenuVisible() {
		return userMenu.isVisible();
	}

	/**
	 * Sets the title text for the menu
	 *
	 * @param caption
	 *            menu title
	 */
	public void setMenuCaption(String caption) {
		setMenuCaption(caption, null);
	}

	/**
	 * Sets the title caption and logo for the menu
	 *
	 * @param caption
	 *            menu caption
	 * @param logo
	 *            menu logo
	 */
	public void setMenuCaption(String caption, Resource logo) {
		if (menuImage != null) {
			logoWrapper.removeComponent(menuImage);
		}
		menuImage = new Image(caption, logo);
		menuImage.setWidth("100%");
		logoWrapper.addComponent(menuImage);
	}

	/**
	 * Removes all content from the user drop down menu.
	 */
	public void clearUserMenu() {
		userItem.removeChildren();
	}
	
	/**
	 * Removes all content from the navigation menu.
	 */
	public void clearMenu() {
		menuItemsLayout.removeAllComponents();
	}

	/**
	 * Adds a menu entry to navigate to given navigation state.
	 *
	 * @param text
	 *            text to display in menu
	 * @param navigationState
	 *            state to navigate to
	 *
	 * @return menu registration
	 */
	public MenuRegistration addNavigation(String text, String navigationState) {
		return addNavigation(text, null, navigationState);
	}

	/**
	 * Adds a menu entry with given icon to navigate to given navigation state.
	 *
	 * @param text
	 *            text to display in menu
	 * @param icon
	 *            icon to display in menu
	 * @param navigationState
	 *            state to navigate to
	 *
	 * @return menu registration
	 */
	public MenuRegistration addNavigation(String text, Resource icon, final String navigationState) {
		return addMenuItem(text, icon, () -> getUI().getNavigator().navigateTo(navigationState));
	}

	/**
	 * Removes all components from the content area.
	 */
	@Override
	public void removeAllComponents() {
		contentArea.removeAllComponents();
	}

	/**
	 * Adds a component to the content area.
	 */
	@Override
	public void addComponent(Component c) {
		contentArea.addComponent(c);
	}

	/**
	 * Removes all content from the content area and replaces everything with
	 * given component.
	 *
	 * @param content
	 *            new content to display
	 */
	public void setContent(Component content) {
		contentArea.removeAllComponents();
		contentArea.addComponent(content);
	}
}
