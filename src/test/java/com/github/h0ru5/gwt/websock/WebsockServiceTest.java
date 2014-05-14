package com.github.h0ru5.gwt.websock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.sksamuel.gwt.websockets.Websocket;

@RunWith(GwtMockitoTestRunner.class)
public class WebsockServiceTest {

	private static final String TESTURL = "ws://echo.websocket.org";
	private WebsockService fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new  WebsockService();
	}

	@Test
	public void testConnectString() throws Exception {
		String id = fixture.connect(TESTURL);
		assertEquals("socket1", id);
	}

	@Test
	public void testConnectStringString() throws Exception {
		String id = "mysocket";
		Websocket ws = fixture.connectWithId(TESTURL, id);
		Websocket ws2 = fixture.get(id);
		assertEquals(ws, ws2);		
	}

	@Test
	public void testIsConnected() throws Exception {
		String id = fixture.connect(TESTURL);
		assertFalse(fixture.isConnected(id));
	}

	@Test
	public void testIsConnectedNull() throws Exception {
		assertFalse(fixture.isConnected(null));
	}

	@Test
	public void testGetNull() throws Exception {
		assertNull(fixture.get(null));
	}
	
}
