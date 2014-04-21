package com.github.h0ru5.gwt.websock;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.angular.client.NgInject;
import com.google.gwt.core.client.Callback;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

@NgInject(name="websocket")
public class WebsockService {

	private Map<String,Websocket> sockets = new HashMap<String, Websocket>();

	public String connect(String url) {
		String newid = String.format("%d",sockets.size() + 1);
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
		if(ws==null)
			return false;
		else
			return ws != null && ws.getState() == 1;
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

	public void onMessage(String id, final Callback<String, Void> jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public void onMessage(String msg) {
					jsfunc.onSuccess(msg);
				}

				@Override
				public void onClose() {}
			};
			ws.addListener(listener);
		} else {
			jsfunc.onFailure(null);
		}
	}

	public void onOpen(String id, final Callback<Void, Void> jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {
					jsfunc.onSuccess(null);
				}

				@Override
				public void onMessage(String msg) {}

				@Override
				public void onClose() {}
			};
			ws.addListener(listener);
		} else {
			jsfunc.onFailure(null);
		}
	}
	
	public void onClose(String id, final Callback<Void, Void> jsfunc) {
		Websocket ws = get(id);
		if(ws!=null) {
			WebsocketListener listener = new WebsocketListener() {

				@Override
				public void onOpen() {}

				@Override
				public void onMessage(String msg) {}

				@Override
				public void onClose() {
					jsfunc.onSuccess(null);
				}
			};
			ws.addListener(listener);
		} else {
			jsfunc.onFailure(null);
		}
	}

	// TODO promises interface

}
