package com.github.h0ru5.gwt.websock;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.angular.client.NgInject;
import com.google.gwt.core.client.JavaScriptObject;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

import elemental.client.Browser;

@NgInject(name="websocket")
public class WebsockService {

	private Map<String,Websocket> sockets = new HashMap<String, Websocket>();

	public String connect(String url) {
		String newid = "socket" + (sockets.size() + 1);
		sockets.put(newid,new Websocket(url));
		return newid;
	}

	public Websocket connect(String url,String id) {
		Websocket ws = new Websocket(url);
		sockets.put(id,ws);
		return ws;
	}

	public Websocket get(String id) {
		return sockets.get(id);
	}

	public boolean isConnected(String id) {
		Websocket ws = get(id);
		if(ws != null)
			return ws.getState() == 1;
		else
			return false;
	}

	public void close(String id) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.close();
	}

	public void addListener(String id, WebsocketListener listener) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.addListener(listener);
	}

	public void onMessage(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public native void onMessage(String msg) /*-{
					jsfunc(msg);
				}-*/;

				@Override
				public void onClose() {}
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}

	public void onOpen(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public native void onOpen() /*-{
					jsfunc();
				}-*/;

				@Override
				public void onMessage(String msg) {}

				@Override
				public void onClose() {}
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}
	
	public void onClose(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public void onMessage(String msg) {}

				@Override
				public native void onClose() /*-{
					jsfunc();
				}-*/;
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}

	public void send(String id, String message) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.send(message);
		else
			debugPrint("Websocket " + id + " is null");
	}

	private void debugPrint(String msg) {
		Browser.getWindow().getConsole().error(msg);
	}
	// TODO promises interface

}
