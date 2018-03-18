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
 * Listens to mouse events.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MouseListener extends DeviceListener {

	/**
	 * Called when a mouse is moved and not dragged.
	 * @param event object holding the event data
	 * @since 1.0.0
	 */
	public void onMove(MouseEvent event);
	
	/**
	 * Called when the mouse is moved while a button is down.
	 * @param event object holding the event data
	 * @since 1.0.0
	 */
	public void onDrag(MouseEvent event);
	
	/**
	 * Called when the scroll wheel of the mouse was used.
	 * @param event object holding the event data
	 * @since 1.0.0
	 */
	public void onScroll(MouseEvent event);
	
	/**
	 * Called when a mouse button was clicked.
	 * @param event object holding the event data
	 * @since 1.0.0
	 */
	public void onClick(MouseEvent event);
	
	/**
	 * Called when a mouse button was released.
	 * @param event object holding the event data
	 * @since 1.0.0
	 */
	public void onRelease(MouseEvent event);
}
