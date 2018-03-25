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
package de.ralleytn.simple.input;

/**
 * Represents an event that was triggered by a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class KeyboardEvent extends DeviceEvent {
	
	/** @since 1.0.0 */ public static final int KEY_A = 65;
	/** @since 1.0.0 */ public static final int KEY_B = 66;
	/** @since 1.0.0 */ public static final int KEY_C = 67;
	/** @since 1.0.0 */ public static final int KEY_D = 68;
	/** @since 1.0.0 */ public static final int KEY_E = 69;
	/** @since 1.0.0 */ public static final int KEY_F = 70;
	/** @since 1.0.0 */ public static final int KEY_G = 71;
	/** @since 1.0.0 */ public static final int KEY_H = 72;
	/** @since 1.0.0 */ public static final int KEY_I = 73;
	/** @since 1.0.0 */ public static final int KEY_J = 74;
	/** @since 1.0.0 */ public static final int KEY_K = 75;
	/** @since 1.0.0 */ public static final int KEY_L = 76;
	/** @since 1.0.0 */ public static final int KEY_M = 77;
	/** @since 1.0.0 */ public static final int KEY_N = 78;
	/** @since 1.0.0 */ public static final int KEY_O = 79;
	/** @since 1.0.0 */ public static final int KEY_P = 80;
	/** @since 1.0.0 */ public static final int KEY_Q = 81;
	/** @since 1.0.0 */ public static final int KEY_R = 82;
	/** @since 1.0.0 */ public static final int KEY_S = 83;
	/** @since 1.0.0 */ public static final int KEY_T = 84;
	/** @since 1.0.0 */ public static final int KEY_U = 85;
	/** @since 1.0.0 */ public static final int KEY_V = 86;
	/** @since 1.0.0 */ public static final int KEY_W = 87;
	/** @since 1.0.0 */ public static final int KEY_X = 88;
	/** @since 1.0.0 */ public static final int KEY_Y = 89;
	/** @since 1.0.0 */ public static final int KEY_Z = 90;
	/** @since 1.0.0 */ public static final int KEY_1 = 49;
	/** @since 1.0.0 */ public static final int KEY_2 = 50;
	/** @since 1.0.0 */ public static final int KEY_3 = 51;
	/** @since 1.0.0 */ public static final int KEY_4 = 52;
	/** @since 1.0.0 */ public static final int KEY_5 = 53;
	/** @since 1.0.0 */ public static final int KEY_6 = 54;
	/** @since 1.0.0 */ public static final int KEY_7 = 55;
	/** @since 1.0.0 */ public static final int KEY_8 = 56;
	/** @since 1.0.0 */ public static final int KEY_9 = 57;
	/** @since 1.0.0 */ public static final int KEY_0 = 48;
	/** @since 1.0.0 */ public static final int KEY_ENTER = 13;
	/** @since 1.0.0 */ public static final int KEY_TABULATOR = 9;
	/** @since 1.0.0 */ public static final int KEY_SPACE = 32;
	/** @since 1.0.0 */ public static final int KEY_ARROW_LEFT = 37;
	/** @since 1.0.0 */ public static final int KEY_ARROW_DOWN = 40;
	/** @since 1.0.0 */ public static final int KEY_ARROW_RIGHT = 39;
	/** @since 1.0.0 */ public static final int KEY_ARROW_UP = 38;
	/** @since 1.0.0 */ public static final int KEY_INSERT = 45;
	/** @since 1.0.0 */ public static final int KEY_DELETE = 46;
	/** @since 1.0.0 */ public static final int KEY_BACKSPACE = 8;
	/** @since 1.0.0 */ public static final int KEY_PAGE_UP = 33;
	/** @since 1.0.0 */ public static final int KEY_PAGE_DOWN = 34;
	/** @since 1.0.0 */ public static final int KEY_END = 35;
	/** @since 1.0.0 */ public static final int KEY_HOME = 36;
	/** @since 1.0.0 */ public static final int KEY_ESCAPE = 27;
	/** @since 1.0.0 */ public static final int KEY_PAUSE = 19;
	/** @since 1.0.0 */ public static final int KEY_CAPS_LOCK = 20;
	/** @since 1.0.0 */ public static final int KEY_SHIFT = 16;
	/** @since 1.0.0 */ public static final int KEY_CONTROL = 17;
	/** @since 1.0.0 */ public static final int KEY_ALT = 18;
	/** @since 1.0.0 */ public static final int KEY_WINDOWS_LEFT = 91;
	/** @since 1.0.0 */ public static final int KEY_WINDOWS_RIGHT = 92;
	/** @since 1.0.0 */ public static final int KEY_SELECT = 93; // NOT IN JINPUT
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_0 = 96;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_1 = 97;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_2 = 98;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_3 = 99;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_4 = 100;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_5 = 101;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_6 = 102;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_7 = 103;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_8 = 104;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_9 = 105;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_MULTIPLY = 106;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_ADD = 107;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_SUBTRACT = 109;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_DECIMAL_POINT = 110;
	/** @since 1.0.0 */ public static final int KEY_NUMPAD_DIVIDE = 111;
	/** @since 1.0.0 */ public static final int KEY_F1 = 112;
	/** @since 1.0.0 */ public static final int KEY_F2 = 113;
	/** @since 1.0.0 */ public static final int KEY_F3 = 114;
	/** @since 1.0.0 */ public static final int KEY_F4 = 115;
	/** @since 1.0.0 */ public static final int KEY_F5 = 116;
	/** @since 1.0.0 */ public static final int KEY_F6 = 117;
	/** @since 1.0.0 */ public static final int KEY_F7 = 118;
	/** @since 1.0.0 */ public static final int KEY_F8 = 119;
	/** @since 1.0.0 */ public static final int KEY_F9 = 120;
	/** @since 1.0.0 */ public static final int KEY_F10 = 121;
	/** @since 1.0.0 */ public static final int KEY_F11 = 122;
	/** @since 1.0.0 */ public static final int KEY_F12 = 123;
	/** @since 1.0.0 */ public static final int KEY_NUM_LOCK = 144;
	/** @since 1.0.0 */ public static final int KEY_SCROLL_LOCK = 145;
	/** @since 1.0.0 */ public static final int KEY_SEMICOLON = 186;
	/** @since 1.0.0 */ public static final int KEY_EQUALS = 187;
	/** @since 1.0.0 */ public static final int KEY_COMMA = 188;
	/** @since 1.0.0 */ public static final int KEY_DASH = 189; // NOT IN JINPUT
	/** @since 1.0.0 */ public static final int KEY_PERIOD = 190;
	/** @since 1.0.0 */ public static final int KEY_FORWARD_SLASH = 191;
	/** @since 1.0.0 */ public static final int KEY_GRAVE_ACCENT = 192;
	/** @since 1.0.0 */ public static final int KEY_BRACKET_OPEN = 219;
	/** @since 1.0.0 */ public static final int KEY_BACKSLASH = 220;
	/** @since 1.0.0 */ public static final int KEY_BRACKET_CLOSE = 221;
	/** @since 1.0.0 */ public static final int KEY_APOSTROPHE = 222;
	/** @since 1.0.0 */ public static final int KEY_APPS = 93;
	
	/**
	 * No key
	 * @since 1.0.0
	 */
	public static final int KEY_NONE = -1;
	
	private final int keyCode;
	private final String keyName;
	
	/**
	 * @param device the device that fired this event
	 * @param keyCode the code of the key that is associated with this event
	 * @param keyName the name of the key that is associated with this event
	 * @since 1.0.0
	 */
	public KeyboardEvent(Device device, int keyCode, String keyName) {
		
		super(device);
		
		this.keyCode = keyCode;
		this.keyName = keyName;
	}
	
	/**
	 * @return the code of the key associated with this event
	 * @since 1.0.0
	 */
	public final int getKeyCode() {

		return this.keyCode;
	}
	
	/**
	 * @return the name of the key associated with this event (depends on localization)
	 * @since 1.0.0
	 */
	public final String getKeyName() {
		
		return this.keyName;
	}
}
