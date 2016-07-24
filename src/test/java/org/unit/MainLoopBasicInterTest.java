package org.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class MainLoopBasicInterTest extends MainLoopTest {

	@Test
	public void containsNothing() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertFalse(mainloop.contains(mockObj));
		
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
	
	@Test
	public void addsObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		mainloop.add(mockObj);
		assertTrue(mainloop.contains(mockObj));
		
		mainloopValidate.invoke(mainloop);
		verifyZeroInteractions(mockObj);
	}
}
