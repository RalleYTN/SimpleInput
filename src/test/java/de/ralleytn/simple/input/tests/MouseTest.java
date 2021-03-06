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

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Mouse;
import de.ralleytn.simple.input.MouseEvent;

class MouseTest {
	
	@Test
	public void testRobot() {
		
		JButton clickMe = new JButton("Click Me");
		clickMe.addActionListener(event -> clickMe.setName("Clicked"));
		
		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.add(clickMe);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		TestUtil.centerCursor();
		Mouse.click(MouseEvent.BUTTON_LEFT);
		
		TestUtil.sleep(() -> !"Clicked".equals(clickMe.getName()));
		
		frame.dispose();
	}
	
	@Test
	public void testIsDown() {
		
		DeviceManager.create();
		List<Mouse> mice = DeviceManager.getMice();
		Mouse mouse = mice.get(0);
		mouse.startListening();
		
		JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		System.out.println("Click and hold the left and right mouse button at the same time");
		TestUtil.sleep(() -> !mouse.isButtonDown(MouseEvent.BUTTON_LEFT) || !mouse.isButtonDown(MouseEvent.BUTTON_RIGHT));
		
		mouse.stopListening();
		frame.dispose();
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testMouseListener() {
		
		DeviceManager.create();
		MouseListenerTestFrame frame = new MouseListenerTestFrame();
		DeviceManager.addMouseListener(frame);
		DeviceManager.getMice().forEach(Mouse::startListening);
		frame.setVisible(true);
		
		TestUtil.sleep(() -> frame.getCheckList().isAtLeastOneItemUnchecked());
		
		frame.dispose();
		DeviceManager.getMice().forEach(Mouse::stopListening);
		DeviceManager.destroy();
	}
}
