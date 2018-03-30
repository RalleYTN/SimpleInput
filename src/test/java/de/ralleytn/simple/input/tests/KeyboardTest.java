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

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Keyboard;
import de.ralleytn.simple.input.KeyboardEvent;

class KeyboardTest {
	
	@Test
	public void testIsDown() {
		
		DeviceManager.create();
		List<Keyboard> keyboards = DeviceManager.getKeyboards();
		Keyboard keyboard = keyboards.get(0);
		keyboard.startListening();
		
		JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		System.out.println("Press A, S and D and the same time");
		TestUtil.sleep(() -> !keyboard.isKeyDown(KeyboardEvent.KEY_A) ||
							 !keyboard.isKeyDown(KeyboardEvent.KEY_S) ||
							 !keyboard.isKeyDown(KeyboardEvent.KEY_D));
		
		keyboard.stopListening();
		frame.dispose();
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testRobot() {
		
		String testSentence = "Hello World";
		
		JTextField textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent event) {
				
				Keyboard.type(testSentence);
			}
		});
		
		JFrame frame = new JFrame("Robot Test");
		frame.add(textField);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		System.out.println("Click on the text Field");
		System.out.println();
		
		TestUtil.sleep(() -> !testSentence.equals(textField.getText()));
		
		frame.dispose();
	}
	
	@Test
	public void testKeyboardListener() {

		DeviceManager.create();
		KeyboardListenerTestFrame frame = new KeyboardListenerTestFrame();
		DeviceManager.addKeyboardListener(frame);
		DeviceManager.getKeyboards().forEach(Keyboard::startListening);
		frame.setVisible(true);
		
		TestUtil.sleep(() -> frame.getCheckList1().isAtLeastOneItemUnchecked() ||
							 frame.getCheckList2().isAtLeastOneItemUnchecked() ||
							 frame.getCheckList3().isAtLeastOneItemUnchecked() ||
							 frame.getCheckList4().isAtLeastOneItemUnchecked());
		
		frame.dispose();
		DeviceManager.getKeyboards().forEach(Keyboard::stopListening);
		DeviceManager.destroy();
	}
}
