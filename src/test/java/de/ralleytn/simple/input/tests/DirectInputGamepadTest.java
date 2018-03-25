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

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.DirectInputGamepad;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.GamepadAdapter;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.MouseControl;

// TODO
// ==== 23.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// Still needs testing with Steam controller
// ====

class DirectInputGamepadTest {
	
	private boolean gamepadRemoved;
	
	@Test
	public void testRemoveListeners() {
		
		
	}
	
	@Test
	public void testRemove() {

		DeviceManager.create();
		Gamepad gamepad = getDirectInputGamepad();
		gamepad.addGamepadListener(new GamepadAdapter() {
			
			@Override
			public void onRemove() {
				
				DirectInputGamepadTest.this.gamepadRemoved = true;
			}
		});
		gamepad.startListening();
		
		TestUtil.sleep(() -> !this.gamepadRemoved);
		
		DeviceManager.destroy();
	}
	
	@Test
	public void testMouseControl() {
		
		DeviceManager.create();
		
		Gamepad gamepad = getDirectInputGamepad();
		gamepad.startListening();
		MouseControl mouseControl = TestUtil.setupMouseControl(gamepad);

		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.getContentPane().setBackground(Color.RED);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		
		System.out.println("Move the cursor with the left analog stick into the window");
		TestUtil.doCursorControlTest(frame);
		System.out.println("Now the controls are inverted");
		mouseControl.setSensity(-5.0F);
		TestUtil.doCursorControlTest(frame);
		System.out.println("Use the right analog stick this time");
		mouseControl.setSensity(5.0F);
		mouseControl.setAnalogStick(GamepadEvent.ANALOG_STICK_RIGHT);
		TestUtil.doCursorControlTest(frame);
		System.out.println("Inverted again");
		mouseControl.setSensity(-5.0F);
		TestUtil.doCursorControlTest(frame);
		
		JButton clickMe = new JButton("Click Me");
		clickMe.addActionListener(event -> clickMe.setName("Clicked"));
		frame.add(clickMe);
		frame.validate();
		TestUtil.sleep(() -> !"Clicked".equals(clickMe.getName()));

		frame.dispose();
		gamepad.stopListening();
		DeviceManager.destroy();
		System.out.println();
	}

	@Test
	public void testIsDown() {

		DeviceManager.create();
		Gamepad gamepad = getDirectInputGamepad();
		gamepad.startListening();

		System.out.println("Press and hold A, B, X, Y at the same time");
		TestUtil.waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_A, GamepadEvent.BUTTON_B, GamepadEvent.BUTTON_X, GamepadEvent.BUTTON_Y});
		
		System.out.println("Press and hold L1, L2, R1, R2 at the same time");
		TestUtil.waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_R1, GamepadEvent.BUTTON_R2, GamepadEvent.BUTTON_L1, GamepadEvent.BUTTON_L2});
		
		System.out.println("Press and hold START, SELECT, R3, L3 at the same time");
		TestUtil.waitUntilAllAreDown(gamepad, new int[] {GamepadEvent.BUTTON_L3, GamepadEvent.BUTTON_R3, GamepadEvent.BUTTON_START, GamepadEvent.BUTTON_SELECT});
		
		gamepad.stopListening();
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testGamepadListener() {
		
		DeviceManager.create();
		DirectInputGamepadListenerTestFrame frame = new DirectInputGamepadListenerTestFrame();
		DeviceManager.addGamepadListener(frame);
		DeviceManager.getGamepads().forEach(Gamepad::startListening);
		frame.setVisible(true);
		
		TestUtil.sleep(() -> frame.getButtonCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getPovCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getLeftAnalogStickCheckList().isAtLeastOneItemUnchecked() ||
							 frame.getRightAnalogStickCheckList().isAtLeastOneItemUnchecked());
		
		frame.dispose();
		DeviceManager.getGamepads().forEach(Gamepad::stopListening);
		DeviceManager.destroy();
	}
	
	private static final Gamepad getDirectInputGamepad() {
		
		for(Gamepad gamepad : DeviceManager.getGamepads()) {
			
			if(gamepad instanceof DirectInputGamepad) {
				
				return gamepad;
			}
		}
		
		return null;
	}
}
