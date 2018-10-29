package org.vaadin.teemusa.sidemenu;

import org.vaadin.teemusa.sidemenu.SideMenu.MenuClickHandler;

import com.vaadin.server.Resource;

/**
 * Data object containing the displayed information of a menu item.
 * 
 * @author Teemu Suo-Anttila
 */
public class MenuEntry {

    private String menuText;
    private Resource menuIcon;
    private MenuClickHandler clickHandler;

    public MenuEntry(String menuText, Resource menuIcon,
            MenuClickHandler handler) {
        this.menuText = menuText;
        this.menuIcon = menuIcon;
        this.setClickHandler(handler);
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public Resource getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Resource menuIcon) {
        this.menuIcon = menuIcon;
    }

    public MenuClickHandler getClickHandler() {
        return clickHandler;
    }

    public void setClickHandler(MenuClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }
}
