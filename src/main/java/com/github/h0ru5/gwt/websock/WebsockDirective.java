package com.github.h0ru5.gwt.websock;

import com.google.gwt.angular.client.Directive;
import com.google.gwt.angular.client.NgDirective;
import com.google.gwt.angular.client.NgElement;
import com.google.gwt.angular.client.NgInjected;
import com.google.gwt.angular.client.Scope;
import com.google.gwt.angular.client.WatchFunction;
import com.google.gwt.core.client.JavaScriptObject;

import elemental.dom.Element;
import elemental.dom.Node;
import elemental.json.JsonObject;
import elemental.util.ArrayOf;

@NgDirective("webSocket")
public class WebsockDirective implements Directive {

	private static final String ATTR_URL = "url";
	private static final String ATTR_ID = "id";
	
	@NgInjected
	public WebSocketService sample;

	//general initialization
	@Override
	public void init() {

	}
	
	@Override
	public void link(final Scope scope, final ArrayOf<NgElement> element,
			final JsonObject attrs) {

		//set default expression
		String url = "localhost:1234";
		
		//override default if attribute value is given
		if(attrs.hasKey(ATTR_URL)) {
			url = attrs.get(ATTR_URL).asString();
		}

		//get id or set random id
		element.get(0).getAttribute("id");
		
		
	}
}
