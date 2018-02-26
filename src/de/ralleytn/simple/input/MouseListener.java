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
