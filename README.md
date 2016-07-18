# SideMenu Add-on for Vaadin

This is a simple Vaadin component that allows the user to make
side menus like in http://demo.vaadin.com/dashboard easily.

Usage:
```
public class MyUI extends UI {

  @Override
  public void init(VaadinRequest request) {
    SideMenu menu = new SideMenu();
    menu.setMenuCaption("MyUI Menu");
    menu.addMenuItem("My Menu Entry", () -> {
      Notification.show("Here is my custom action for this menu item.");
    });
    setContent(menu);
  }
}
```

