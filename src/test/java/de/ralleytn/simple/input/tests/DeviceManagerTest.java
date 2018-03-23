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

import java.util.List;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.Keyboard;
import de.ralleytn.simple.input.Mouse;
import de.ralleytn.simple.input.XInputGamepad;

class DeviceManagerTest {
	
	@Test
	public void testGetMouseByName() {
		
		// SETUP
		DeviceManager.create();
		List<Mouse> mice = DeviceManager.getMice();
		int miceWithThatName = 0;
		String mouseName = null;
		
		for(int index = 0; index < mice.size(); index++) {
			
			if(index == 0) {
				
				mouseName = mice.get(index).getName();
				miceWithThatName++;
				
			} else if(mice.get(index).getName().equals(mouseName)) {
				
				miceWithThatName++;
			}
		}
		
		// DO TEST
		List<Mouse> foundMice = DeviceManager.getMiceByName(mouseName);
		assertEquals(miceWithThatName, foundMice.size());
		
		// CLEANUP
		DeviceManager.destroy();
	}
	
	@Test
	public void testGetKeyboardByName() {
		
		// SETUP
		DeviceManager.create();
		List<Keyboard> keyboards = DeviceManager.getKeyboards();
		int keyboardsWithThatName = 0;
		String keyboardName = null;
		
		for(int index = 0; index < keyboards.size(); index++) {
			
			if(index == 0) {
				
				keyboardName = keyboards.get(index).getName();
				keyboardsWithThatName++;
				
			} else if(keyboards.get(index).getName().equals(keyboardName)) {
				
				keyboardsWithThatName++;
			}
		}
		
		// DO TEST
		List<Keyboard> foundKeyboards = DeviceManager.getKeyboardsByName(keyboardName);
		assertEquals(keyboardsWithThatName, foundKeyboards.size());
		
		// CLEANUP
		DeviceManager.destroy();
	}
	
	@Test
	public void testGetGamepadByName() {
		
		// SETUP
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		int gamepadsWithThatName = 0;
		String gamepadName = null;
		
		for(int index = 0; index < gamepads.size(); index++) {
			
			if(index == 0) {
				
				gamepadName = gamepads.get(index).getName();
				gamepadsWithThatName++;
				
			} else if(gamepads.get(index).getName().equals(gamepadName)) {
				
				gamepadsWithThatName++;
			}
		}
		
		// DO TEST
		List<Gamepad> foundGamepads = DeviceManager.getGamepadsByName(gamepadName);
		assertEquals(gamepadsWithThatName, foundGamepads.size());
		
		// CLEANUP
		DeviceManager.destroy();
	}
	
	@Test
	public void testGetMice() {
		
		// FIXME
		// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
		// getController().getPortNumber() always returns 0. Why?
		// ====
		
		DeviceManager.create();
		List<Mouse> mice = DeviceManager.getMice();
		System.out.println("==== " + mice.size() + " Mouse/Mice ====");
		
		for(int index = 0; index < mice.size(); index++) {
			
			if(index != 0) {
				
				System.out.println("--");
			}
			
			Mouse mouse = mice.get(index);
			
			System.out.println("Name: " + mouse.getName());
			System.out.println("Port Number: " + mouse.getPortNumber());
			System.out.println("Button Count: " + mouse.getButtonCount());
		}
		
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testGetKeyboards() {
		
		// FIXME
		// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
		// getController().getPortNumber() always returns 0. Why?
		// ====
				
		DeviceManager.create();
		List<Keyboard> keyboards = DeviceManager.getKeyboards();
		System.out.println("==== " + keyboards.size() + " Keyboard(s) ====");
		
		for(int index = 0; index < keyboards.size(); index++) {
			
			if(index != 0) {
				
				System.out.println("--");
			}
			
			Keyboard keyboard = keyboards.get(index);
			
			System.out.println("Name: " + keyboard.getName());
			System.out.println("Port Number: " + keyboard.getPortNumber());
		}
		
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testGetGamepads() {
		
		// FIXME
		// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
		// getController().getPortNumber() always returns 0. Why?
		// ====
				
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		System.out.println("==== " + gamepads.size() + " Gamepad(s) ====");
		
		for(int index = 0; index < gamepads.size(); index++) {
			
			if(index != 0) {
				
				System.out.println("--");
			}
			
			Gamepad gamepad = gamepads.get(index);
			
			System.out.println("Name: " + gamepad.getName());
			System.out.println("Port Number: " + gamepad.getPortNumber());
			System.out.println("Button Count: " + gamepad.getButtonCount());
			System.out.println("Can Rumble: " + gamepad.canRumble());
			System.out.println("XInput: " + (gamepad instanceof XInputGamepad));
		}
		
		DeviceManager.destroy();
		System.out.println();
	}
}
