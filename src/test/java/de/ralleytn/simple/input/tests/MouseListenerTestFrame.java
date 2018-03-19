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

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ralleytn.simple.input.MouseEvent;
import de.ralleytn.simple.input.MouseListener;

class MouseListenerTestFrame extends JFrame implements MouseListener {

	private static final long serialVersionUID = -3213276390930472608L;
	
	private CheckList<String> checkList;
	
	public MouseListenerTestFrame() {
		
		super("MouseListener Test");
		
		this.checkList = new CheckList<>("Things to do", new String[] {
			"CLICK LEFT",
			"CLICK MIDDLE",
			"CLICK RIGHT",
			"MOVE",
			"DRAG",
			"SCROLL UP",
			"SCROLL DOWN"
		});
		
		JPanel contentPane = new JPanel();
		contentPane.add(this.checkList.getPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public CheckList<String> getCheckList() {
		
		return this.checkList;
	}
	
	@Override public void onClick(MouseEvent event) {}

	@Override
	public void onMove(MouseEvent event) {
		
		this.checkList.check("MOVE");
	}

	@Override
	public void onDrag(MouseEvent event) {
		
		this.checkList.check("DRAG");
	}

	@Override
	public void onScroll(MouseEvent event) {
		
		this.checkList.check(event.getUnitsToScroll() < 0 ? "SCROLL DOWN" : "SCROLL UP");
	}

	@Override
	public void onRelease(MouseEvent event) {
		
		int button = event.getButton();
		
		       if(button == MouseEvent.BUTTON_LEFT)   {this.checkList.check("CLICK LEFT");
		} else if(button == MouseEvent.BUTTON_MIDDLE) {this.checkList.check("CLICK MIDDLE");
		} else if(button == MouseEvent.BUTTON_RIGHT)  {this.checkList.check("CLICK RIGHT");
		}
	}
}
