/*
 * MIT License
 * 
 * Copyright (c) 2018 Ralph Niemitz
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.ralleytn.simple.input.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.Keyboard;
import de.ralleytn.simple.input.Mouse;

class DeviceManagerTest {

	@Test
	public void testGetDeviceByName() {
		
		DeviceManager.create();
		
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		List<Keyboard> keyboards = DeviceManager.getKeyboards();
		List<Mouse> mice = DeviceManager.getMice();
		
		String gamepadName = null;
		String keyboardName = null;
		String mouseName = null;
		
		int gamepadsWithThatName = 0;
		int keyboardsWithThatName = 0;
		int miceWithThatName = 0;
		
		for(int index = 0; index < gamepads.size(); index++) {
			
			if(index == 0) {
				
				gamepadName = gamepads.get(index).getName();
				gamepadsWithThatName++;
				
			} else if(gamepads.get(index).getName().equals(gamepadName)) {
				
				gamepadsWithThatName++;
			}
		}
		
		for(int index = 0; index < keyboards.size(); index++) {
			
			if(index == 0) {
				
				keyboardName = keyboards.get(index).getName();
				keyboardsWithThatName++;
				
			} else if(keyboards.get(index).getName().equals(keyboardName)) {
				
				keyboardsWithThatName++;
			}
		}
		
		for(int index = 0; index < mice.size(); index++) {
			
			if(index == 0) {
				
				mouseName = mice.get(index).getName();
				miceWithThatName++;
				
			} else if(mice.get(index).getName().equals(mouseName)) {
				
				miceWithThatName++;
			}
		}
		
		List<Gamepad> foundGamepads = DeviceManager.getGamepadsByName(gamepadName);
		List<Keyboard> foundKeyboards = DeviceManager.getKeyboardsByName(keyboardName);
		List<Mouse> foundMice = DeviceManager.getMiceByName(mouseName);
		
		assertEquals(gamepadsWithThatName, foundGamepads.size());
		assertEquals(keyboardsWithThatName, foundKeyboards.size());
		assertEquals(miceWithThatName, foundMice.size());
		
		DeviceManager.destroy();
	}
	
	@Test
	public void test() {
		
		try {
			
			DeviceManager.create();
			
			List<Gamepad> gamepads = DeviceManager.getGamepads();
			List<Keyboard> keyboards = DeviceManager.getKeyboards();
			List<Mouse> mice = DeviceManager.getMice();
			
			// FIXME
			// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
			// getController().getPortNumber() always returns 0. Why?
			// ====
			
			System.out.println("==== " + gamepads.size() + " Gamepad(s) ====");
			
			for(Gamepad gamepad : gamepads) {
				
				System.out.println("Name: " + gamepad.getName());
				System.out.println("Port Number: " + gamepad.getController().getPortNumber());
				System.out.println("Button Count: " + gamepad.getButtonCount());
				System.out.println("Can Rumble: " + gamepad.canRumble());
				System.out.println();
			}
			
			System.out.println("==== " + keyboards.size() + " Keyboard(s) ====");
			
			for(Keyboard keyboard : keyboards) {
				
				System.out.println("Name: " + keyboard.getName());
				System.out.println("Port Number: " + keyboard.getController().getPortNumber());
				System.out.println();
			}
			
			System.out.println("==== " + mice.size() + " Mouse/Mice ====");
			
			for(Mouse mouse : mice) {
				
				System.out.println("Name: " + mouse.getName());
				System.out.println("Port Number: " + mouse.getController().getPortNumber());
				System.out.println("Button Count: " + mouse.getButtonCount());
				System.out.println();
			}
			
			System.out.println("====");
			
			DeviceManager.destroy();
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
			fail(exception.getMessage());
		}
	}
}
