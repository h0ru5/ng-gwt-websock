package com.github.h0ru5.gwt.websock;

import com.google.gwt.angular.client.NgInject;
import com.sksamuel.gwt.websockets.Websocket;

@NgInject(name="websockservice")
public class websockSampleService {

	private Websocket ws;

	public String greet(String name) {
		return "Hello " + name;
	}
	
	public void connect(String url) {
	   ws = new Websocket(url);	
	}
	
	public boolean isConnected() {
		return ws != null && ws.getState() == 1;
	}
	
	
}
