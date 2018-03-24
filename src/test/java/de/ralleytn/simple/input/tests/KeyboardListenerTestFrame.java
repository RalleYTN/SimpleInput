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

import static de.ralleytn.simple.input.KeyboardEvent.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ralleytn.simple.input.KeyboardEvent;
import de.ralleytn.simple.input.KeyboardListener;

class KeyboardListenerTestFrame extends JFrame implements KeyboardListener {

	private static final long serialVersionUID = -7542986300762943963L;
	
	private static final String[] DATASET1 = {
		
		"A", "B", "C", "D", "E",
		"F", "G", "H", "I", "J",
		"K", "L", "M", "N", "O",
		"P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y",
		"Z"
	};
	
	private static final String[] DATASET2 = {
			
		"0", "1", "2", "3", "4",
		"5", "6", "7", "8", "9",
		"NUM0", "NUM1", "NUM2", "NUM3", "NUM4",
		"NUM5", "NUM6", "NUM7", "NUM8", "NUM9"
	};
	
	private static final String[] DATASET3 = {
		
		"ENTER", "SPACE", "BACKSPACE", "TABULATOR", "ESCAPE",
		"ARROW_LEFT", "ARROW_UP", "ARROW_RIGHT", "ARROW_DOWN",
		"INSERT", "DELETE", "PAGE_UP", "PAGE_DOWN", "END",
		"PAUSE", "CAPS_LOCK", "NUM_LOCK", "SHIFT", "CONTROL",
		"ALT", "WINDOW_LEFT"
	};
	
	private static final String[] DATASET4 = {
			
		"NUMPAD_MULTIPLY", "NUMPAD_DIVIDE", "NUMPAD_SUBTRACT", "NUMPAD_ADD",
		"PERIOD",
		"F1", "F2", "F3", "F4", "F5",
		"F6", "F7", "F8", "F9", "F10",
		"F11", "F12"
	};
	
	private CheckList<String> checkList1;
	private CheckList<String> checkList2;
	private CheckList<String> checkList3;
	private CheckList<String> checkList4;
	
	public KeyboardListenerTestFrame() {
		
		super("KeyboardListener Test");
		
		this.checkList1 = new CheckList<>("CheckList 1", DATASET1);
		this.checkList2 = new CheckList<>("CheckList 2", DATASET2);
		this.checkList3 = new CheckList<>("CheckList 3", DATASET3);
		this.checkList4 = new CheckList<>("CheckList 4", DATASET4);
		
		JPanel contentPane = new JPanel();
		contentPane.add(this.checkList1.getPanel());
		contentPane.add(this.checkList2.getPanel());
		contentPane.add(this.checkList3.getPanel());
		contentPane.add(this.checkList4.getPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public CheckList<String> getCheckList1() {
		
		return this.checkList1;
	}
	
	public CheckList<String> getCheckList2() {
		
		return this.checkList2;
	}
	
	public CheckList<String> getCheckList3() {
		
		return this.checkList3;
	}
	
	public CheckList<String> getCheckList4() {
		
		return this.checkList4;
	}

	@Override public void onKeyPress(KeyboardEvent event) {}

	@Override
	public void onKeyRelease(KeyboardEvent event) {
		
		int key = event.getKeyCode();
		
		switch(key) {
		
			case KEY_A: this.checkList1.check("A"); break;
			case KEY_B: this.checkList1.check("B"); break;
			case KEY_C: this.checkList1.check("C"); break;
			case KEY_D: this.checkList1.check("D"); break;
			case KEY_E: this.checkList1.check("E"); break;
			case KEY_F: this.checkList1.check("F"); break;
			case KEY_G: this.checkList1.check("G"); break;
			case KEY_H: this.checkList1.check("H"); break;
			case KEY_I: this.checkList1.check("I"); break;
			case KEY_J: this.checkList1.check("J"); break;
			case KEY_K: this.checkList1.check("K"); break;
			case KEY_L: this.checkList1.check("L"); break;
			case KEY_M: this.checkList1.check("M"); break;
			case KEY_N: this.checkList1.check("N"); break;
			case KEY_O: this.checkList1.check("O"); break;
			case KEY_P: this.checkList1.check("P"); break;
			case KEY_Q: this.checkList1.check("Q"); break;
			case KEY_R: this.checkList1.check("R"); break;
			case KEY_S: this.checkList1.check("S"); break;
			case KEY_T: this.checkList1.check("T"); break;
			case KEY_U: this.checkList1.check("U"); break;
			case KEY_V: this.checkList1.check("V"); break;
			case KEY_W: this.checkList1.check("W"); break;
			case KEY_X: this.checkList1.check("X"); break;
			case KEY_Y: this.checkList1.check("Y"); break;
			case KEY_Z: this.checkList1.check("Z"); break;
			
			case KEY_0: this.checkList2.check("0"); break;
			case KEY_1: this.checkList2.check("1"); break;
			case KEY_2: this.checkList2.check("2"); break;
			case KEY_3: this.checkList2.check("3"); break;
			case KEY_4: this.checkList2.check("4"); break;
			case KEY_5: this.checkList2.check("5"); break;
			case KEY_6: this.checkList2.check("6"); break;
			case KEY_7: this.checkList2.check("7"); break;
			case KEY_8: this.checkList2.check("8"); break;
			case KEY_9: this.checkList2.check("9"); break;
			
			case KEY_NUMPAD_0: this.checkList2.check("NUM0"); break;
			case KEY_NUMPAD_1: this.checkList2.check("NUM1"); break;
			case KEY_NUMPAD_2: this.checkList2.check("NUM2"); break;
			case KEY_NUMPAD_3: this.checkList2.check("NUM3"); break;
			case KEY_NUMPAD_4: this.checkList2.check("NUM4"); break;
			case KEY_NUMPAD_5: this.checkList2.check("NUM5"); break;
			case KEY_NUMPAD_6: this.checkList2.check("NUM6"); break;
			case KEY_NUMPAD_7: this.checkList2.check("NUM7"); break;
			case KEY_NUMPAD_8: this.checkList2.check("NUM8"); break;
			case KEY_NUMPAD_9: this.checkList2.check("NUM9"); break;
			
			case KEY_F1:  this.checkList4.check("F1");  break;
			case KEY_F2:  this.checkList4.check("F2");  break;
			case KEY_F3:  this.checkList4.check("F3");  break;
			case KEY_F4:  this.checkList4.check("F4");  break;
			case KEY_F5:  this.checkList4.check("F5");  break;
			case KEY_F6:  this.checkList4.check("F6");  break;
			case KEY_F7:  this.checkList4.check("F7");  break;
			case KEY_F8:  this.checkList4.check("F8");  break;
			case KEY_F9:  this.checkList4.check("F9");  break;
			case KEY_F10: this.checkList4.check("F10"); break;
			case KEY_F11: this.checkList4.check("F11"); break;
			case KEY_F12: this.checkList4.check("F12"); break;
			
			case KEY_ARROW_DOWN:   this.checkList3.check("ARROW_DOWN");  break;
			case KEY_ARROW_LEFT:   this.checkList3.check("ARROW_LEFT");  break;
			case KEY_ARROW_RIGHT:  this.checkList3.check("ARROW_RIGHT"); break;
			case KEY_ARROW_UP:     this.checkList3.check("ARROW_UP");    break;
			case KEY_ENTER:        this.checkList3.check("ENTER");       break;
			case KEY_SPACE:        this.checkList3.check("SPACE");       break;
			case KEY_BACKSPACE:    this.checkList3.check("BACKSPACE");   break;
			case KEY_TABULATOR:    this.checkList3.check("TABULATOR");   break;
			case KEY_ESCAPE:       this.checkList3.check("ESCAPE");      break;
			case KEY_INSERT:       this.checkList3.check("INSERT");      break;
			case KEY_DELETE:       this.checkList3.check("DELETE");      break;
			case KEY_PAGE_UP:      this.checkList3.check("PAGE_UP");     break;
			case KEY_PAGE_DOWN:    this.checkList3.check("PAGE_DOWN");   break;
			case KEY_END:          this.checkList3.check("END");         break;
			case KEY_PAUSE:        this.checkList3.check("PAUSE");       break;
			case KEY_CAPS_LOCK:    this.checkList3.check("CAPS_LOCK");   break;
			case KEY_NUM_LOCK:     this.checkList3.check("NUM_LOCK");    break;
			case KEY_SHIFT:        this.checkList3.check("SHIFT");       break;
			case KEY_CONTROL:      this.checkList3.check("CONTROL");     break;
			case KEY_ALT:          this.checkList3.check("ALT");         break;
			case KEY_WINDOWS_LEFT: this.checkList3.check("WINDOW_LEFT"); break;
			
			case KEY_NUMPAD_MULTIPLY:      this.checkList4.check("NUMPAD_MULTIPLY");      break;
			case KEY_NUMPAD_ADD:           this.checkList4.check("NUMPAD_ADD");           break;
			case KEY_NUMPAD_DIVIDE:        this.checkList4.check("NUMPAD_DIVIDE");        break;
			case KEY_NUMPAD_SUBTRACT:      this.checkList4.check("NUMPAD_SUBTRACT");      break;
			case KEY_PERIOD:               this.checkList4.check("PERIOD");               break;
		}
	}
}
