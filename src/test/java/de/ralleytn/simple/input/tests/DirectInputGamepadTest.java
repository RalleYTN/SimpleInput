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

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.Mouse;

class DirectInputGamepadTest {
	
	private static final void waitUntilAllAreDown(Gamepad gamepad, int[] buttons) {
	
		TestUtil.sleep(() -> {
			
			boolean allDown = true;
			
			for(int button : buttons) {
				
				if(!gamepad.isButtonDown(button)) {
					
					allDown = false;
					break;
				}
			}
			
			return allDown;
		});
	}
	
	private static final void doCursorControlTest(JFrame frame) {
		
		int screenWidth = TestUtil.SCREEN_SIZE.width;
		int screenHeight = TestUtil.SCREEN_SIZE.height;
		Mouse.setCursorPosition(screenWidth / 2, screenHeight / 2);
		frame.setLocation((int)(Math.random() * (screenWidth - frame.getWidth())), (int)(Math.random() * (screenHeight - frame.getHeight())));
		TestUtil.sleep(() -> !frame.getBounds().contains(Mouse.getX(), Mouse.getY()));
	}
	
	@Test
	public void testCursorControl() {
		
		// SETUP
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		
		Gamepad gamepad = gamepads.get(0);
		gamepad.startListening();
		gamepad.setAnalogStickControllingCursor(GamepadEvent.ANALOG_STICK_LEFT);
		gamepad.setControlCursorWithAnalogStick(true);
		gamepad.setCursorSensity(5.0F);

		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.getContentPane().setBackground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		
		// DO TESTS
		System.out.println("Move the cursor with the left analog stick into the window");
		doCursorControlTest(frame);
		System.out.println("Now the controls are inverted");
		gamepad.setCursorSensity(-5.0F);
		doCursorControlTest(frame);
		System.out.println("Use the right analog stick this time");
		gamepad.setCursorSensity(5.0F);
		gamepad.setAnalogStickControllingCursor(GamepadEvent.ANALOG_STICK_RIGHT);
		doCursorControlTest(frame);
		System.out.println("Inverted again");
		gamepad.setCursorSensity(-5.0F);
		doCursorControlTest(frame);

		// CLEANUP
		frame.dispose();
		gamepad.stopListening();
		DeviceManager.destroy();
		System.out.println();
	}

	@Test
	public void testIsDown() {

		// SETUP
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		Gamepad gamepad = gamepads.get(0);
		gamepad.startListening();

		// TEST 1
		System.out.println("Press and hold A, B, X, Y at the same time");
		waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_A, GamepadEvent.BUTTON_B, GamepadEvent.BUTTON_X, GamepadEvent.BUTTON_Y});
		
		// TEST 2
		System.out.println("Press and hold L1, L2, R1, R2 at the same time");
		waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_R1, GamepadEvent.BUTTON_R2, GamepadEvent.BUTTON_L1, GamepadEvent.BUTTON_L2});
		
		// TEST 3
		System.out.println("Press and hold START, SELECT, R3, L3 at the same time");
		waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_L3, GamepadEvent.BUTTON_R3, GamepadEvent.BUTTON_START, GamepadEvent.BUTTON_SELECT});
		
		// CLEANUP
		gamepad.stopListening();
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testGamepadListener() {
		
		// SETUP
		DeviceManager.create();
		DirectInputGamepadListenerTestFrame frame = new DirectInputGamepadListenerTestFrame();
		DeviceManager.addGamepadListener(frame);
		DeviceManager.getGamepads().forEach(Gamepad::startListening);
		frame.setVisible(true);
		
		// WAIT UNTIL DONE
		TestUtil.sleep(() -> frame.getButtonCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getPovCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getLeftAnalogStickCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getRightAnalogStickCheckList().isAtLeastOneItemUnchecked());
		
		// CLEANUP
		frame.dispose();
		DeviceManager.getGamepads().forEach(Gamepad::stopListening);
		DeviceManager.destroy();
	}
}
