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
 * Listens to gamepad events.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface GamepadListener extends DeviceListener {

	/**
	 * Called when an analog stick was pushed.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event the {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onAnalogStickPush(GamepadEvent event);
	
	/**
	 * Called when a button was pressed.
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onButtonPress(GamepadEvent event);
	
	/**
	 * Called when a button was released.
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onButtonRelease(GamepadEvent event);
	
	/**
	 * Called when the POV was pressed.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onPOVPress(GamepadEvent event);
	
	/**
	 * Called when the POV was released.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onPOVRelease(GamepadEvent event);
}
