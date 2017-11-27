package org.vaadin.teemusa.sidemenu;

import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SideMenuTest {
    @Mock
    private SideMenu.MenuClickHandler clickHandler;

    @Test
    public void addTreeItemRootNotUserOriginated() {
        SideMenu sideMenu = new SideMenu();
        SideMenu.MenuRegistration item = sideMenu.addTreeItem("item", clickHandler);
        item.select();
        verifyZeroInteractions(clickHandler);
    }

    @Test
    public void addTreeItemSubNotUserOriginated() {
        SideMenu sideMenu = new SideMenu();
        sideMenu.addTreeItem("parent", null);
        SideMenu.MenuRegistration item = sideMenu.addTreeItem("parent", "item", clickHandler);
        item.select();
        verifyZeroInteractions(clickHandler);
    }

    @Test
    public void addTreeItemRootRepeat() {
        SideMenu sideMenu = new SideMenu();
        sideMenu.addTreeItem("item", clickHandler);
        sideMenu.addTreeItem("item", clickHandler);
        // expect no exception
    }

    @Test
    public void addTreeItemSubRepeat() {
        SideMenu sideMenu = new SideMenu();
        sideMenu.addTreeItem("parent1", clickHandler);
        sideMenu.addTreeItem("parent1", "item", clickHandler);
        sideMenu.addTreeItem("parent2", clickHandler);
        sideMenu.addTreeItem("parent2", "item", clickHandler);
        // expect no exception
    }
}
