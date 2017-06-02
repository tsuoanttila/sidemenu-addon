package org.vaadin.teemusa.sidemenu;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.vaadin.annotations.Viewport;

/**
 * This annotation is for the UI to activate the responsiveness.
 */
@Inherited
@Viewport("user-scalable=no,initial-scale=1.0")
@Retention(RetentionPolicy.RUNTIME)
public @interface SideMenuUI {
}
