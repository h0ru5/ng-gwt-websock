package com.github.h0ru5.gwt.websock;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.angular.client.NgInject;
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
	
	public void addListener(String id, WebsocketListener listener) {
		Websocket ws = get(id);
		if(ws!=null)
			ws.addListener(listener);
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
	
	
}
