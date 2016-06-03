# SideMenu UI Add-on for Vaadin

This is a simple extension on top of default Vaadin UI
which adds a Valo themed menu to the left side of the page.

Usage:
```
public class MyUI extends SideMenuUI {

  @Override
  public void init(VaadinRequest request) {
    addMenu("My Menu Entry", () -> {
      Notification.show("Here is my custom action for this menu item.");
    });
  }
}
```

