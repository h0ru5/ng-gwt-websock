package com.github.h0ru5.gwt.websock;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.angular.client.NgInject;
import com.google.gwt.core.client.JavaScriptObject;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

import elemental.client.Browser;


/**
 * The Class WebSocketService is an Angular service to interact with websockets.
 */
@NgInject(name="websocket")
public class WebSocketService {

	/** The internal sockets map */
	private Map<String,Websocket> sockets = new HashMap<String, Websocket>();

	/**
	 * Connect a new socket to the given url and immediately open it
	 *
	 * @param url the url to connect including scheme
	 * @return the id assigned to the connected socket 
	 */
	public String connect(String url) {
		String newid = "socket" + (sockets.size() + 1);
		Websocket ws = new Websocket(url);
		sockets.put(newid,ws);
		ws.open();
		return newid;
	}
	
	/**
	 * Prepare a new socket to the given url, you have to manually open() it later.
	 *
	 * @param url the url to connect including scheme
	 * @return the id assigned to the connected socket 
	 */
	public String prepare(String url) {
		String newid = "socket" + (sockets.size() + 1);
		Websocket ws = new Websocket(url);
		sockets.put(newid,ws);
		return newid;
	}
	
	/**
	 * Prepare a new socket to the given url ,
	 * referencing it using the given id.
	 * You have to manually open() it later.
	 *
	 * @param url the url to connect including scheme
	 * @param id the id that the socket should be assigned
	 * @return the websocket itself as GWT object
	 */
	public Websocket prepareWithId(String url,String id) {
		Websocket ws = new Websocket(url);
		sockets.put(id,ws);
		ws.open();
		return ws;
	}

	/**
	 * Connect a new socket to the given url  and immediately open it, 
	 * referencing it using the given id
	 *
	 * @param url the url to connect including scheme
	 * @param id the id that the socket should be assigned
	 * @return the websocket itself as GWT object
	 */
	public Websocket connectWithId(String url,String id) {
		Websocket ws = new Websocket(url);
		sockets.put(id,ws);
		ws.open();
		return ws;
	}

	/**
	 * Gets the websocket corresponding to the id
	 *
	 * @param id the id
	 * @return the websocket
	 */
	public Websocket get(String id) {
		debugPrint("getting id " + id + ": " + sockets.get(id));
		return sockets.get(id);
	}

	/**
	 * Open (connect) websocket with the given id 
	 *
	 * @param id the id
	 */
	public void open(String id) {
		get(id).open();
	}
	
	/**
	 * Checks if the websocket is connected.
	 *
	 * @param id the id
	 * @return true, if is connected
	 */
	public boolean isConnected(String id) {
		Websocket ws = get(id);
		if(ws != null) {
			debugPrint("ws " + id + " is in state " + ws.getState());
			return ws.getState() == 1;
		} else
			return false;
	}

	/**
	 * Close a given websocket
	 *
	 * @param id the id
	 */
	public void close(String id) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.close();
	}

	/**
	 * Adds a listener to the websocket under id
	 *
	 * @param id the id
	 * @param listener the listener
	 */
	public void addListener(String id, WebsocketListener listener) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.addListener(listener);
	}

	/**
	 *  assign an onMessage-callback (javascript) for the given websocket-id.
	 *
	 * @param id the id
	 * @param jsfunc the callback in the form function(message){}
	 */
	public void onMessage(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public void onMessage(String msg) {
					debugPrint("got message " + msg);
					sendout(jsfunc,msg);
				} 

				@Override
				public void onClose() {}
				
				public native void sendout(JavaScriptObject jsfunc, String msg) /*-{
					jsfunc(msg);
				}-*/;
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}

	/**
	 * assign an onOpen-callback (javascript) for the given websocket-id.
	 *
	 * @param id the id
	 * @param jsfunc the callback in the form function(){}
	 */
	public void onOpen(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public  void onOpen() {
					callNativeCallback(jsfunc);					
				}

				@Override
				public void onMessage(String msg) {}

				@Override
				public void onClose() {}
				
				public native void callNativeCallback(JavaScriptObject jsfunc)/*-{
					jsfunc();
				}-*/;
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}
	
	/**
	 * assign an onClose-callback (javascript) for the given websocket-id.
	 *
	 * @param id the id
	 * @param jsfunc  the callback in the form function(){}
	 */
	public void onClose(String id, final JavaScriptObject jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public void onMessage(String msg) {}

				@Override
				public void onClose() {
					callNativeCallback(jsfunc);
				}
				
				public native void callNativeCallback(JavaScriptObject jsfunc)/*-{
					jsfunc();
				}-*/;
			};
			ws.addListener(listener);
		} else {
			debugPrint("Websocket " + id + " is null");
		}
	}

	/**
	 * Send a message on the given websocket
	 *
	 * @param id the id
	 * @param message the message
	 */
	public void send(String id, String message) {
		Websocket ws = get(id);
		debugPrint("sending " + message + " on " + id);
		if(ws!=null) {
			if(isConnected(id)) {
				ws.send(message);
				debugPrint("sent");
			} else {
				debugPrint("cannot send, " + id + " is not connected, state " + ws.getState());
			}
		} else
			debugPrint("Websocket " + id + " is null");
	}

	/**
	 * Debug print, currently console with info level
	 *
	 * @param msg the message to print
	 */
	private void debugPrint(String msg) {
		Browser.getWindow().getConsole().log(msg);
	}
	
	// TODO promises interface

}
