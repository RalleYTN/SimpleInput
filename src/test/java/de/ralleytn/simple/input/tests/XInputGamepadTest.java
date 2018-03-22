package de.ralleytn.simple.input.tests;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.internal.Util;

class XInputGamepadTest {

	@Test
	public void testIsXInput() {
		
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		
		for(Gamepad gamepad : gamepads) {
			
			if(Util.isXInput(gamepad.getController())) {
				
				
			}
		}
	}
}
