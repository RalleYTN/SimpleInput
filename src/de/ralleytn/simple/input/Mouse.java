package de.ralleytn.simple.input;

import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a mouse.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mouse extends Device {

	private List<MouseListener> listeners;
	private boolean leftDown;
	private boolean rightDown;
	private boolean middleDown;
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Mouse(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * Adds a {@linkplain MouseListener}.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void addMouseListener(MouseListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain MouseListener}.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void removeMouseListener(MouseListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain MouseListener}.
	 * @param index the index of the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void removeMouseListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain MouseListener}
	 * @return a {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public MouseListener getMouseListener(int index) {
		
		return this.listeners.get(index);
	}

	@Override
	protected void update(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(id == Identifier.Button.LEFT) {

			this.leftDown = value == 1.0F;
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Identifier.Button.RIGHT) {
			
			this.rightDown = value == 1.0F;
			
			if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Identifier.Button.MIDDLE) {
			
			this.middleDown = value == 1.0F;
			
			if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Identifier.Axis.X) {
			
			float deltaX = event.getValue();
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.LEFT)));
				
			} else if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.RIGHT)));
				
			} else if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.MIDDLE)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onMove(new MouseEvent(this, deltaX, 0, 0, null)));
			}
			
		} else if(id == Identifier.Axis.Y) {
			
			float deltaY = event.getValue();
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.LEFT)));
				
			} else if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.RIGHT)));
				
			} else if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.MIDDLE)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onMove(new MouseEvent(this, 0, deltaY, 0, null)));
			}
			
		} else if(id == Identifier.Axis.Z) {
			
			this.listeners.forEach(listener -> listener.onScroll(new MouseEvent(this, 0, 0, event.getValue(), null)));
		}
	}
	
	/**
	 * @return the X position of the cursor on the screen
	 * @since 1.0.0
	 */
	public int getX() {
		
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	/**
	 * @return the Y position of the cursor on the screen
	 * @since 1.0.0
	 */
	public int getY() {
		
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
	/**
	 * @return {@code true} if the left mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isLeftDown() {
		
		return this.leftDown;
	}
	
	/**
	 * @return {@code true} if the right mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isRightDown() {
		
		return this.rightDown;
	}
	
	/**
	 * @return {@code true} if the middle mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isMiddleDown() {
		
		return this.middleDown;
	}
}
