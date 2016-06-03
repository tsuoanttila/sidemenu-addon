package org.vaadin.teemusa.sidemenu;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("valo")
public abstract class SideMenuUI extends UI {

	public interface MenuClickHandler {
		void click();
	}

	private static final String STYLE_VISIBLE = "valo-menu-visible";

	private final ComponentContainer contentArea = new VerticalLayout();
	private final CssLayout menuArea = new CssLayout();
	private final CssLayout menuItemsLayout = new CssLayout();
	private final HorizontalLayout mainLayout = new HorizontalLayout();

	private Label logo;
	private final MenuBar userMenu = new MenuBar();
	private final ViewChangeListener listener = new ViewChangeListener() {

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menuArea.removeStyleName(STYLE_VISIBLE);
		}

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return true;
		}

	};

	public SideMenuUI() {
		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		super.setContent(mainLayout);
		mainLayout.setSizeFull();
		setSizeFull();

		menuArea.setPrimaryStyleName("valo-menu");
		menuArea.addStyleName("sidebar");
		menuArea.addStyleName(ValoTheme.MENU_PART);
		menuArea.addStyleName("no-vertical-drag-hints");
		menuArea.addStyleName("no-horizontal-drag-hints");
		menuArea.setWidth(null);
		menuArea.setHeight("100%");

		logo = new Label("Menu", ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		menuArea.addComponent(logoWrapper);

		userMenu.addStyleName("user-menu");
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
		valoMenuToggleButton.setIcon(FontAwesome.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		menuArea.addComponent(valoMenuToggleButton);

		menuItemsLayout.addStyleName("valo-menuitems");
		menuArea.addComponent(menuItemsLayout);

		contentArea.setPrimaryStyleName("valo-content");
		contentArea.addStyleName("v-scrollable");
		contentArea.setSizeFull();

		mainLayout.addComponents(menuArea, contentArea);
		mainLayout.setExpandRatio(contentArea, 1);
	}

	public void addMenu(String text, MenuClickHandler handler) {
		addMenu(text, null, handler);
	}

	public void addMenu(String text, Resource icon, MenuClickHandler handler) {
		Button button = new Button(text, e -> { 
			handler.click();
			menuArea.removeStyleName(STYLE_VISIBLE);
		});
		button.setIcon(icon);
		button.setPrimaryStyleName("valo-menu-item");
		menuItemsLayout.addComponent(button);
	}

	@Override
	public void setNavigator(Navigator navigator) {
		if (getNavigator() != null) {
			getNavigator().removeViewChangeListener(listener);
		}
		super.setNavigator(navigator);
		if (navigator != null) {
			navigator.addViewChangeListener(listener);
		}
	}

	public void setMenuVisible(boolean visible) {
		menuArea.setVisible(visible);
	}

	public MenuBar getUserMenu() {
		return userMenu;
	}

	@Override
	public void setContent(Component content) {
		if (contentArea != null) {
			contentArea.removeAllComponents();
			contentArea.addComponent(content);
		} else {
			super.setContent(content);
		}
	}

	public void setMenuCaption(String caption) {
		logo.setValue(caption);
	}
}
