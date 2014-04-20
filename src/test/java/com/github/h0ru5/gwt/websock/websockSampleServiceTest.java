package com.github.h0ru5.gwt.websock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class websockSampleServiceTest {

	private websockSampleService fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new  websockSampleService();
	}

	@Test
	public void testGreet() throws Exception {
		Assert.assertEquals("Hello world",fixture.greet("world"));
	}

}
