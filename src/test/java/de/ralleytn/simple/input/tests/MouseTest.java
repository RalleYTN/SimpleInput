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

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.input.DeviceManager;
import de.ralleytn.simple.input.Mouse;

class MouseTest {

	@Test
	public void testIsDown() {
		
		// SETUP
		DeviceManager.create();
		List<Mouse> mice = DeviceManager.getMice();
		Mouse mouse = mice.get(0);
		mouse.startListening();
		
		JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// TEST
		System.out.println("Click and hold the left and right mouse button at the same time");
		TestUtil.sleep(() -> !mouse.isLeftDown() || !mouse.isRightDown());
		
		// CLEANUP
		mouse.stopListening();
		frame.dispose();
		DeviceManager.destroy();
		System.out.println();
	}
	
	@Test
	public void testMouseListener() {
		
		// SETUP
		DeviceManager.create();
		MouseListenerTestFrame frame = new MouseListenerTestFrame();
		DeviceManager.addMouseListener(frame);
		DeviceManager.getMice().forEach(Mouse::startListening);
		frame.setVisible(true);
		
		// WAIT UNTIL DONE
		TestUtil.sleep(() -> frame.getCheckList().isAtLeastOneItemUnchecked());
		
		// CLEANUP
		frame.dispose();
		DeviceManager.getMice().forEach(Mouse::stopListening);
		DeviceManager.destroy();
	}
}
