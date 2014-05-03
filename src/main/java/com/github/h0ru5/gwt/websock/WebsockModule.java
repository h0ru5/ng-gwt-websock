package com.github.h0ru5.gwt.websock;

import com.google.gwt.angular.client.AngularModule;
import com.google.gwt.angular.client.NgDepends;
import com.google.gwt.angular.client.NgName;

/**
 * This is your Module, injectable via the given mnemonic
 * add all exposed Components (Services and Directives) to the Depends-Annotation
 *
 */
@NgName("websock")
@NgDepends({WebsockService.class, WebsockDirective.class})
public class WebsockModule implements AngularModule {

}