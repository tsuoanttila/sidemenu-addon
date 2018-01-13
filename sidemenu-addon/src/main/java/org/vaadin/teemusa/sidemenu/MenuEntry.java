package org.vaadin.teemusa.sidemenu;

import com.vaadin.server.Resource;
import org.vaadin.teemusa.sidemenu.SideMenu.MenuClickHandler;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data object containing the displayed information of a menu item.
 * 
 * @author Teemu Suo-Anttila
 */
public class MenuEntry implements Serializable {
    private static final long serialVersionUID = 1;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntry menuEntry = (MenuEntry) o;
        return Objects.equals(menuText, menuEntry.menuText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuText);
    }
}
