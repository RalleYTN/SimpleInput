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
package de.ralleytn.simple.input.internal;

import static net.java.games.input.Component.Identifier.Key.*;

import java.util.Collections;
import java.util.Map;

import net.java.games.input.Component.Identifier;

import static de.ralleytn.simple.input.KeyboardEvent.*;

public final class KeyboardKeyMapping {

	private static final Object[] MAPPING = {
			
		// JInput  , SimpleInput
		A,           KEY_A,
		B,           KEY_B,
		C,           KEY_C,
		D,           KEY_D,
		E,           KEY_E,
		F,           KEY_F,
		G,           KEY_G,
		H,           KEY_H,
		I,           KEY_I,
		J,           KEY_J,
		K,           KEY_K,
		L,           KEY_L,
		M,           KEY_M,
		N,           KEY_N,
		O,           KEY_O,
		P,           KEY_P,
		Q,           KEY_Q,
		R,           KEY_R,
		S,           KEY_S,
		T,           KEY_T,
		U,           KEY_U,
		V,           KEY_V,
		W,           KEY_W,
		X,           KEY_X,
		Y,           KEY_Y,
		Z,           KEY_Z,
		_0,          KEY_0,
		_1,          KEY_1,
		_2,          KEY_2,
		_3,          KEY_3,
		_4,          KEY_4,
		_5,          KEY_5,
		_6,          KEY_6,
		_7,          KEY_7,
		_8,          KEY_8,
		_9,          KEY_9,
		F1,          KEY_F1,
		F2,          KEY_F2,
		F3,          KEY_F3,
		F4,          KEY_F4,
		F5,          KEY_F5,
		F6,          KEY_F6,
		F7,          KEY_F7,
		F8,          KEY_F8,
		F9,          KEY_F9,
		F10,         KEY_F10,
		F11,         KEY_F11,
		F12,         KEY_F12,
		NUMPAD0,     KEY_NUMPAD_0,
		NUMPAD1,     KEY_NUMPAD_1,
		NUMPAD2,     KEY_NUMPAD_2,
		NUMPAD3,     KEY_NUMPAD_3,
		NUMPAD4,     KEY_NUMPAD_4,
		NUMPAD5,     KEY_NUMPAD_5,
		NUMPAD6,     KEY_NUMPAD_6,
		NUMPAD7,     KEY_NUMPAD_7,
		NUMPAD8,     KEY_NUMPAD_8,
		NUMPAD9,     KEY_NUMPAD_9,
		NUMPADCOMMA, KEY_NUMPAD_DECIMAL_POINT,
		RETURN,      KEY_ENTER,
		TAB,         KEY_TABULATOR,
		SPACE,       KEY_SPACE,
		LEFT,        KEY_ARROW_LEFT,
		UP,          KEY_ARROW_UP,
		RIGHT,       KEY_ARROW_RIGHT,
		DOWN,        KEY_ARROW_DOWN,
		INSERT,      KEY_INSERT,
		DELETE,      KEY_DELETE,
		BACK,        KEY_BACKSPACE,
		PAGEUP,      KEY_PAGE_UP,
		PAGEDOWN,    KEY_PAGE_DOWN,
		END,         KEY_END,
		HOME,        KEY_HOME,
		ESCAPE,      KEY_ESCAPE,
		PAUSE,       KEY_PAUSE,
		CAPITAL,     KEY_CAPS_LOCK,
		LSHIFT,      KEY_SHIFT,
		LCONTROL,    KEY_CONTROL,
		LALT,        KEY_ALT,
		LWIN,        KEY_WINDOWS_LEFT,
		RWIN,        KEY_WINDOWS_RIGHT,
		MULTIPLY,    KEY_NUMPAD_MULTIPLY,
		SUBTRACT,    KEY_NUMPAD_SUBTRACT,
		DIVIDE,      KEY_NUMPAD_DIVIDE,
		ADD,         KEY_NUMPAD_ADD,
		NUMLOCK,     KEY_NUM_LOCK,
		SCROLL,      KEY_SCROLL_LOCK,
		SEMICOLON,   KEY_SEMICOLON,
		EQUALS,      KEY_EQUALS,
		COMMA,       KEY_COMMA,
		PERIOD,      KEY_PERIOD,
		SLASH,       KEY_FORWARD_SLASH,
		BACKSLASH,   KEY_BACKSLASH,
		GRAVE,       KEY_GRAVE_ACCENT,
		LBRACKET,    KEY_BRACKET_OPEN,
		RBRACKET,    KEY_BRACKET_CLOSE,
		APOSTROPHE,  KEY_APOSTROPHE,
		APPS,        KEY_APPS
	};
	private static final Map<Identifier, Integer> MAP = Collections.unmodifiableMap(Util.toMap(KeyboardKeyMapping.MAPPING));
	
	private KeyboardKeyMapping() {}
	
	public static final Map<Identifier, Integer> getMap() {
		
		return KeyboardKeyMapping.MAP;
	}
	
	public static final int getDownKeyArraySize() {
		
		return Util.getMaxValue(KeyboardKeyMapping.MAP.values()) + 1;
	}
	
	public static final boolean isValidKey(Identifier id) {
		
		return KeyboardKeyMapping.MAP.containsKey(id);
	}
}
