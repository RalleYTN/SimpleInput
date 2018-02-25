package de.ralleytn.simple.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Rumbler;

/**
 * Represents a gamepad.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Gamepad extends Device {
	
	/**
	 * The maximum value the dead zone can have.
	 * @since 1.0.0
	 */
	public static final float MAX_DEAD_ZONE = 0.9999999F;
	
	private static final float DEAD_POINT_POSITIVE = (float)-1.5258789E-5;
	private static final float DEAD_POINT_NEGATIVE = -Gamepad.DEAD_POINT_POSITIVE;
	private static final Identifier[] VALID_BUTTONS = {
			
		// PC/PlayStation | XBox
		Identifier.Button._0, Identifier.Button.Y,
		Identifier.Button._1, Identifier.Button.B,
		Identifier.Button._2, Identifier.Button.A,
		Identifier.Button._3, Identifier.Button.X,
		Identifier.Button._4, Identifier.Button.LEFT_THUMB,
		Identifier.Button._5, Identifier.Button.RIGHT_THUMB,
		Identifier.Button._6, Identifier.Button.LEFT_THUMB2,
		Identifier.Button._7, Identifier.Button.RIGHT_THUMB2,
		Identifier.Button._8, Identifier.Button.SELECT,
		Identifier.Button._9, Identifier.Button.START,
		Identifier.Button._10, Identifier.Button.LEFT_THUMB3,
		Identifier.Button._11, Identifier.Button.RIGHT_THUMB3,
		Identifier.Button._12, Identifier.Button.MODE
	};
	
	private Rumbler[] rumblers;
	private List<GamepadListener> listeners;
	private Direction currentPOVDirection;
	private int buttonCount;
	private float axisY;
	private float axisX;
	private float axisRZ; // Y for right analog stick
	private float axisZ; // X for right analog stick
	private float deadZone;
	
	private boolean xDown;
	private boolean yDown;
	private boolean aDown;
	private boolean bDown;
	private boolean selectDown;
	private boolean startDown;
	private boolean modeDown;
	private boolean l1Down;
	private boolean l2Down;
	private boolean l3Down;
	private boolean r1Down;
	private boolean r2Down;
	private boolean r3Down;
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Gamepad(Controller controller) {
		
		super(controller);

		this.rumblers = controller.getRumblers();
		this.listeners = new ArrayList<>();
		
		for(Component component : controller.getComponents()) {
			
			if(Gamepad.isButton(component.getIdentifier())) {
				
				this.buttonCount++;
			}
		}
	}
	
	/**
	 * Sets the dead zone for the analog sticks.
	 * The dead zone is a value that prevents the {@link GamepadListener#onAnalogStickPush(GamepadEvent)} method from being called
	 * if the intensity with which the analog stick is pushed is not higher.
	 * @param value a value between {@code 0.0F} and <code>{@value #MAX_DEAD_ZONE}F</code> (using {@code 1.0F} would disable the analog sticks)
	 * @since 1.0.0
	 */
	public void setDeadZone(float value) {
		
		this.deadZone = value;
	}
	
	/**
	 * Adds a {@linkplain GamepadListener}.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void addGamepadListener(GamepadListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain GamepadListener}.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void removeGamepadListener(GamepadListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain GamepadListener}.
	 * @param index the index of the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void removeGamepadListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain GamepadListener}
	 * @return the {@linkplain GamepadListener} with the given index
	 * @since 1.0.0
	 */
	public GamepadListener getGamepadListener(int index) {
		
		return this.listeners.get(index);
	}
	
	/**
	 * Rumbles the gamepad with the given intensity. Does nothing if {@link #canRumble()} returns {@code false}.
	 * @param intensity the intensity with wich the gamepad should rumble <br> ({@code 0.0F} = 0%, {@code 1.0F} = 100%)
	 * @since 1.0.0
	 */
	public void rumble(float intensity) {
		
		for(Rumbler rumbler : this.rumblers) {
			
			rumbler.rumble(intensity);
		}
	}
	
	/**
	 * @return {@code true} if this gamepad has a rumble function, else {@code false}
	 * @since 1.0.0
	 */
	public boolean canRumble() {
		
		return this.rumblers.length > 0;
	}
	
	/**
	 * @return the amount of buttons on the gamepad (extra buttons are not counted)
	 * @since 1.0.0
	 */
	public int getButtonCount() {
		
		return this.buttonCount;
	}
	
	/**
	 * @return {@code true} if the X/4 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isXDown() {
		
		return this.xDown;
	}
	
	/**
	 * @return {@code true} if the Y/1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isYDown() {
		
		return this.yDown;
	}
	
	/**
	 * @return {@code true} if the A/3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isADown() {
		
		return this.aDown;
	}
	
	/**
	 * @return {@code true} if the X/2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isBDown() {
		
		return this.bDown;
	}
	
	/**
	 * @return {@code true} if the L1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL1Down() {
		
		return this.l1Down;
	}
	
	/**
	 * @return {@code true} if the L2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL2Down() {
		
		return this.l2Down;
	}
	
	/**
	 * @return {@code true} if the L3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL3Down() {
		
		return this.l3Down;
	}
	
	/**
	 * @return {@code true} if the R1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR1Down() {
		
		return this.r1Down;
	}
	
	/**
	 * @return {@code true} if the R2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR2Down() {
		
		return this.r2Down;
	}
	
	/**
	 * @return {@code true} if the R3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR3Down() {
		
		return this.r3Down;
	}
	
	/**
	 * @return {@code true} if the MODE button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isModeDown() {
		
		return this.modeDown;
	}
	
	/**
	 * @return {@code true} if the SELECT button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isSelectDown() {
		
		return this.selectDown;
	}
	
	/**
	 * @return {@code true} if the START button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isStartDown() {
		
		return this.startDown;
	}
	
	/**
	 * @return the dead zone for the analog sticks
	 * @since 1.0.0
	 */
	public float getDeadZone() {
		
		return this.deadZone;
	}

	@Override
	protected void update(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(id == Axis.POV) {
			
			Direction direction = value == 0.25F ? Direction.NORTH :
				                  value == 0.375F ? Direction.NORTH_EAST :
				                  value == 0.5F ? Direction.EAST :
				                  value == 0.625F ? Direction.SOUTH_EAST :
				                  value == 0.75F ? Direction.SOUTH :
				                  value == 0.875F ? Direction.SOUTH_WEST :
				                  value == 1.0F ? Direction.WEST :
				                  value == 0.125F ? Direction.NORTH_WEST :
				                  null;
			
			if(this.currentPOVDirection != direction) {
				
				if(this.currentPOVDirection != null) {
					
					this.listeners.forEach(listener -> {
						
						listener.onPOVRelease(new GamepadEvent(this, this.currentPOVDirection, -1, id, 0.0F));
						
						if(direction != null) {
							
							listener.onPOVPress(new GamepadEvent(this, direction, -1, id, 0.0F));
						}
					});
					
				} else {
					
					this.listeners.forEach(listener -> listener.onPOVPress(new GamepadEvent(this, direction, -1, id, 0.0F)));
				}
				
				this.currentPOVDirection = direction;
			}
			
		} else if(Gamepad.isButton(id)) {
			
			GamepadEvent gamepadEvent = new GamepadEvent(this, null, -1, id, 0.0F);
			
			if(value == 0.0F) {
				
				this.listeners.forEach(listener -> listener.onButtonRelease(gamepadEvent));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onButtonPress(gamepadEvent));
			}
			
			       if(id == Button._0  || id == Button.Y)            {this.yDown = value == 1.0F;
			} else if(id == Button._1  || id == Button.B)            {this.bDown = value == 1.0F;
			} else if(id == Button._2  || id == Button.A)            {this.aDown = value == 1.0F;
			} else if(id == Button._3  || id == Button.X)            {this.xDown = value == 1.0F;
			} else if(id == Button._4  || id == Button.LEFT_THUMB)   {this.l1Down = value == 1.0F;
			} else if(id == Button._5  || id == Button.RIGHT_THUMB)  {this.r1Down = value == 1.0F;
			} else if(id == Button._6  || id == Button.LEFT_THUMB2)  {this.l2Down = value == 1.0F;
			} else if(id == Button._7  || id == Button.RIGHT_THUMB2) {this.r2Down = value == 1.0F;
			} else if(id == Button._8  || id == Button.SELECT)       {this.selectDown = value == 1.0F;
			} else if(id == Button._9  || id == Button.START)        {this.startDown = value == 1.0F;
			} else if(id == Button._10 || id == Button.LEFT_THUMB3)  {this.l3Down = value == 1.0F;
			} else if(id == Button._11 || id == Button.RIGHT_THUMB3) {this.r3Down = value == 1.0F;
			} else if(id == Button._12 || id == Button.MODE)         {this.modeDown = value == 1.0F;
			}
			       
		} else if(id == Axis.Y) {
			
			this.axisY = Gamepad.isDead(value) ? 0.0F : value;
			float intensity = Gamepad.getIntensity(this.axisX, this.axisY);
			
			if(intensity > this.deadZone) {
				
				Direction direction = this.getDirection(value, this.axisX, this.axisY);
				this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, GamepadEvent.ANALOG_STICK_LEFT, id, intensity)));
			}
			
		} else if(id == Axis.X) {
			
			this.axisX = Gamepad.isDead(value) ? 0.0F : value;
			float intensity = Gamepad.getIntensity(this.axisX, this.axisY);
			
			if(intensity > this.deadZone) {
				
				Direction direction = this.getDirection(value, this.axisX, this.axisY);
				this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, GamepadEvent.ANALOG_STICK_LEFT, id, intensity)));
			}
			
		} else if(id == Axis.RZ) {
			
			this.axisRZ = Gamepad.isDead(value) ? 0.0F : value;
			float intensity = Gamepad.getIntensity(this.axisZ, this.axisRZ);
			
			if(intensity > this.deadZone) {
				
				Direction direction = this.getDirection(value, this.axisZ, this.axisRZ);
				this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, GamepadEvent.ANALOG_STICK_RIGHT, id, intensity)));
			}

		} else if(id == Axis.Z) {
			
			this.axisZ = Gamepad.isDead(value) ? 0.0F : value;
			float intensity = Gamepad.getIntensity(this.axisZ, this.axisRZ);
			
			if(intensity > this.deadZone) {
				
				Direction direction = this.getDirection(value, this.axisZ, this.axisRZ);
				this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, GamepadEvent.ANALOG_STICK_RIGHT, id, intensity)));
			}
		}
	}
	
	private final Direction getDirection(float value, float x, float y) {
		
		Direction direction = null;
		
		if(value != 0.0F) {
			
			double angle = Math.toDegrees(Math.atan2(y, x));
			
			       if(angle == -90.0F) {direction = Direction.NORTH;
			} else if(angle == 0.0F)   {direction = Direction.EAST;
			} else if(angle == 180.0F) {direction = Direction.WEST;
			} else if(angle == 90.0F)  {direction = Direction.SOUTH;
			} else if(angle < -90.0F)  {direction = Direction.NORTH_WEST;
			} else if(angle < 0.0F)    {direction = Direction.NORTH_EAST;
			} else if(angle > 90.0F)   {direction = Direction.SOUTH_WEST;
			} else if(angle > 0.0F)    {direction = Direction.SOUTH_EAST;
			}
		}
		
		return direction;
	}

	private static final float getIntensity(float x, float y) {
		
		double velocity = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    return (float)(velocity > 1.0F ? 1.0F : velocity);
	}
	
	private static final boolean isDead(float value) {
		
		return value == Gamepad.DEAD_POINT_NEGATIVE || value == Gamepad.DEAD_POINT_POSITIVE;
	}
	
	private static final boolean isButton(Identifier id) {
		
		for(Identifier button : Gamepad.VALID_BUTTONS) {
			
			if(button == id) {
				
				return true;
			}
		}
		
		return false;
	}
}
