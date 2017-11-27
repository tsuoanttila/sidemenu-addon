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
    public void addTreeItemSubnotUserOriginated() {
        SideMenu sideMenu = new SideMenu();
        sideMenu.addTreeItem("parent", null);
        SideMenu.MenuRegistration item = sideMenu.addTreeItem("parent", "item", clickHandler);
        item.select();
        verifyZeroInteractions(clickHandler);
    }
}
