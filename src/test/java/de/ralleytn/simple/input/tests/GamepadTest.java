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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Direction;
import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.GamepadListener;
import de.ralleytn.simple.input.Mouse;

// TODO
// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// If someone could run this test with a XBox gamepad and send me the results, that would be nice!
// ====

class GamepadTest {

	private static final String[] BUTTONS = {
		"X", "Y", "A", "B",
		"START", "SELECT",
		"R1", "R2", "R3",
		"L1", "L2", "L3"
	};
	
	private final CheckList checkList = new CheckList();
	
	@Test
	public void testCursorControl() {
		
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		Gamepad gamepad = gamepads.get(0);
		gamepad.startListening();
		gamepad.setAnalogStickControllingCursor(GamepadEvent.ANALOG_STICK_LEFT);
		gamepad.setControlCursorWithAnalogStick(true);
		gamepad.setCursorSensity(5.0F);
		System.out.println("==== TEST cursor control ====");
		System.out.println("Left Analog Stick");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Mouse.setCursorPosition(screenSize.width / 2, screenSize.height / 2);
		
		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocation((int)(Math.random() * (screenSize.width - 100)), (int)(Math.random() * (screenSize.height - 100)));
		frame.setVisible(true);
		
		while(!frame.getBounds().contains(Mouse.getX(), Mouse.getY())) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		frame.setLocation((int)(Math.random() * (screenSize.width - 100)), (int)(Math.random() * (screenSize.height - 100)));
		Mouse.setCursorPosition(screenSize.width / 2, screenSize.height / 2);
		System.out.println("Inverted");
		gamepad.setCursorSensity(-5.0F);
		
		while(!frame.getBounds().contains(Mouse.getX(), Mouse.getY())) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		frame.setLocation((int)(Math.random() * (screenSize.width - 100)), (int)(Math.random() * (screenSize.height - 100)));
		Mouse.setCursorPosition(screenSize.width / 2, screenSize.height / 2);
		System.out.println("Right Analog Stick");
		gamepad.setCursorSensity(5.0F);
		gamepad.setAnalogStickControllingCursor(GamepadEvent.ANALOG_STICK_RIGHT);
		
		while(!frame.getBounds().contains(Mouse.getX(), Mouse.getY())) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		frame.setLocation((int)(Math.random() * (screenSize.width - 100)), (int)(Math.random() * (screenSize.height - 100)));
		Mouse.setCursorPosition(screenSize.width / 2, screenSize.height / 2);
		System.out.println("Inverted");
		gamepad.setCursorSensity(-5.0F);
		
		while(!frame.getBounds().contains(Mouse.getX(), Mouse.getY())) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		gamepad.stopListening();
		DeviceManager.destroy();
	}

	@Test
	public void testIsDown() {
		
		System.out.println();
		System.out.println("==== TEST isDown() ====");
		
		DeviceManager.create();
		List<Gamepad> gamepads = DeviceManager.getGamepads();
		Gamepad gamepad = gamepads.get(0);
		gamepad.startListening();

		System.out.println("Press A, B, X, Y together");
		while(!gamepad.isADown() || !gamepad.isBDown() || !gamepad.isXDown() || !gamepad.isYDown()) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		System.out.println("Press L1, L2, R1, R2 together");
		while(!gamepad.isR1Down() || !gamepad.isR2Down() || !gamepad.isL1Down() || !gamepad.isL2Down()) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		System.out.println("Press START, SELECT, R3, L3 together");
		while(!gamepad.isR3Down() || !gamepad.isL3Down() || !gamepad.isStartDown() || !gamepad.isSelectDown()) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException e) {}
		}
		
		gamepad.stopListening();
		DeviceManager.destroy();
	}
	
	@Test
	public void testInput() {
		
		class Adapter implements GamepadListener {

			@Override
			public void onAnalogStickPush(GamepadEvent event) {
				
				int analogStick = event.getAnalogStick();
				Direction direction = event.getDirection();
				
				if(analogStick == GamepadEvent.ANALOG_STICK_LEFT) {
					
					checkList.checkLeftAnalogStick(direction);
					
				} else if(analogStick == GamepadEvent.ANALOG_STICK_RIGHT) {
					
					checkList.checkRightAnalogStick(direction);
				}
			}

			@Override
			public void onButtonPress(GamepadEvent event) {
				
				System.out.println(event.getButton());
			}

			@Override
			public void onButtonRelease(GamepadEvent event) {
				
				int button = event.getButton();
				
				       if(button == GamepadEvent.BUTTON_A) {checkList.checkButton("A");
				} else if(button == GamepadEvent.BUTTON_B) {checkList.checkButton("B");
				} else if(button == GamepadEvent.BUTTON_X) {checkList.checkButton("X");
				} else if(button == GamepadEvent.BUTTON_Y) {checkList.checkButton("Y");
				} else if(button == GamepadEvent.BUTTON_START) {checkList.checkButton("START");
				} else if(button == GamepadEvent.BUTTON_SELECT) {checkList.checkButton("SELECT");
				} else if(button == GamepadEvent.BUTTON_R1) {checkList.checkButton("R1");
				} else if(button == GamepadEvent.BUTTON_R2) {checkList.checkButton("R2");
				} else if(button == GamepadEvent.BUTTON_R3) {checkList.checkButton("R3");
				} else if(button == GamepadEvent.BUTTON_L1) {checkList.checkButton("L1");
				} else if(button == GamepadEvent.BUTTON_L2) {checkList.checkButton("L2");
				} else if(button == GamepadEvent.BUTTON_L3) {checkList.checkButton("L3");
				}
			}

			@Override
			public void onPOVPress(GamepadEvent event) {
				
				System.out.println(event.getDirection());
			}

			@Override
			public void onPOVRelease(GamepadEvent event) {
				
				checkList.checkPov(event.getDirection());
			}
		}
		
		DeviceManager.create();
		DeviceManager.addGamepadListener(new Adapter());
		
		for(Gamepad gamepad : DeviceManager.getGamepads()) {
			
			gamepad.startListening();
		}
		
		this.checkList.setVisible(true);
		this.checkList.pack();
		this.checkList.setLocationRelativeTo(null);
		
		while(this.checkList.areButtonInputsStillMissing() ||
			  this.checkList.arePOVInputsStillMissing() ||
			  this.checkList.areRightAnalogStickInputsStillMissing() ||
			  this.checkList.areLeftAnalogStickInputsStillMissing()) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException exception) {}
		}
		
		for(Gamepad gamepad : DeviceManager.getGamepads()) {
			
			gamepad.stopListening();
		}
		
		DeviceManager.destroy();
		this.checkList.dispose();
	}
	
	private final class CheckList extends JFrame {
		
		private static final long serialVersionUID = -213386445729943967L;
		private List<JCheckBox> buttonCheckboxes = new ArrayList<>();
		private List<JCheckBox> povCheckboxes = new ArrayList<>();
		private List<JCheckBox> lasCheckboxes = new ArrayList<>();
		private List<JCheckBox> rasCheckboxes = new ArrayList<>();
		
		public CheckList() {
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
			buttonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));
			
			for(String button : BUTTONS) {
				
				JCheckBox checkbox = new JCheckBox(button);
				checkbox.setEnabled(false);
				this.buttonCheckboxes.add(checkbox);
				buttonPanel.add(checkbox);
			}
			
			JPanel povPanel = new JPanel();
			povPanel.setLayout(new BoxLayout(povPanel, BoxLayout.Y_AXIS));
			povPanel.setBorder(BorderFactory.createTitledBorder("POV"));
			
			for(Direction direction : Direction.values()) {
				
				JCheckBox checkbox = new JCheckBox(direction.toString());
				checkbox.setEnabled(false);
				this.povCheckboxes.add(checkbox);
				povPanel.add(checkbox);
			}
			
			JPanel rasPanel = new JPanel();
			rasPanel.setLayout(new BoxLayout(rasPanel, BoxLayout.Y_AXIS));
			rasPanel.setBorder(BorderFactory.createTitledBorder("Right Analog Stick"));
			
			for(Direction direction : Direction.values()) {
				
				JCheckBox checkbox = new JCheckBox(direction.toString());
				checkbox.setEnabled(false);
				this.rasCheckboxes.add(checkbox);
				rasPanel.add(checkbox);
			}
			
			JPanel lasPanel = new JPanel();
			lasPanel.setLayout(new BoxLayout(lasPanel, BoxLayout.Y_AXIS));
			lasPanel.setBorder(BorderFactory.createTitledBorder("Left Analog Stick"));
			
			for(Direction direction : Direction.values()) {
				
				JCheckBox checkbox = new JCheckBox(direction.toString());
				checkbox.setEnabled(false);
				this.lasCheckboxes.add(checkbox);
				lasPanel.add(checkbox);
			}
			
			JPanel contentPane = new JPanel();
			contentPane.add(buttonPanel);
			contentPane.add(povPanel);
			contentPane.add(rasPanel);
			contentPane.add(lasPanel);
			
			this.setContentPane(contentPane);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		private final void check(List<JCheckBox> list, String toCheck) {
			
			for(JCheckBox checkbox : list) {
				
				if(checkbox.getText().equals(toCheck)) {
					
					checkbox.setSelected(true);
				}
			}
		}
		
		public void checkButton(String name) {
			
			this.check(buttonCheckboxes, name);
		}
		
		public void checkPov(Direction direction) {
			
			this.check(povCheckboxes, direction.toString());
		}
		
		public void checkRightAnalogStick(Direction direction) {
			
			this.check(rasCheckboxes, direction.toString());
		}
		
		public void checkLeftAnalogStick(Direction direction) {
			
			this.check(lasCheckboxes, direction.toString());
		}
		
		public boolean arePOVInputsStillMissing() {
			
			return this.isOneUnchecked(povCheckboxes);
		}
		
		public boolean areButtonInputsStillMissing() {
			
			return this.isOneUnchecked(buttonCheckboxes);
		}
		
		public boolean areRightAnalogStickInputsStillMissing() {
			
			return this.isOneUnchecked(rasCheckboxes);
		}
		
		public boolean areLeftAnalogStickInputsStillMissing() {
			
			return this.isOneUnchecked(lasCheckboxes);
		}
		
		private boolean isOneUnchecked(List<JCheckBox> list) {
			
			for(JCheckBox checkbox : list) {
				
				if(!checkbox.isSelected()) {
					
					return true;
				}
			}
			
			return false;
		}
	}
}
