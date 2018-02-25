package de.ralleytn.simple.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

/**
 * Manages the input devices.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DeviceManager {

	private static Controller[] CONTROLLERS;
	private static ControllerEnvironment ENVIRONMENT;
	private static List<Device> DEVICES;
	private static int NUMBER_OF_MICE;
	private static int NUMBER_OF_KEYBOARDS;
	private static int NUMBER_OF_GAMEPADS;
	
	private DeviceManager() {}
	
	/**
	 * Creates the context.
	 * Has to be done before using any other methods.
	 * @since 1.0.0
	 */
	public static final void create() {
		
		DeviceManager.ENVIRONMENT = ControllerEnvironment.getDefaultEnvironment();
		DeviceManager.update();
	}
	
	/**
	 * Updates the context.
	 * Only important if you want to get devices that were plugged in after creating the context.
	 * This will create new object references for the devices, that means all listeners have to be applied on them again.
	 * @since 1.0.0
	 */
	public static final void update() {
		
		DeviceManager.CONTROLLERS = DeviceManager.ENVIRONMENT.getControllers();
		DeviceManager.DEVICES = new ArrayList<>();
		
		for(Controller controller : DeviceManager.CONTROLLERS) {
			
			Type type = controller.getType();
			
			if(type == Type.MOUSE) {
				
				DeviceManager.DEVICES.add(new Mouse(controller));
				DeviceManager.NUMBER_OF_MICE++;
				
			} else if(type == Type.KEYBOARD) {
				
				DeviceManager.DEVICES.add(new Keyboard(controller));
				DeviceManager.NUMBER_OF_KEYBOARDS++;
				
			} else if(type == Type.GAMEPAD || type == Type.STICK) {
				
				DeviceManager.DEVICES.add(new Gamepad(controller));
				DeviceManager.NUMBER_OF_GAMEPADS++;
			}
		}
	}

	/**
	 * Destroys the context.
	 * Should be called once you are done using this library.
	 * @since 1.0.0
	 */
	public static final void destroy() {
		
		DeviceManager.ENVIRONMENT = null;
		DeviceManager.CONTROLLERS = null;
		DeviceManager.DEVICES = null;
		
		DeviceManager.NUMBER_OF_MICE = 0;
		DeviceManager.NUMBER_OF_KEYBOARDS = 0;
		DeviceManager.NUMBER_OF_GAMEPADS = 0;
	}
	
	/**
	 * Adds a {@linkplain MouseListener} to all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static final void addMouseListener(MouseListener listener) {
		
		for(Mouse mouse : DeviceManager.getMice()) {
			
			mouse.addMouseListener(listener);
		}
	}
	
	/**
	 * Removes a {@linkplain MouseListener} from all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static final void removeMouseListener(MouseListener listener) {
		
		for(Mouse mouse : DeviceManager.getMice()) {
			
			mouse.removeMouseListener(listener);
		}
	}
	
	/**
	 * @return all registered mice
	 * @since 1.0.0
	 */
	public static final Mouse[] getMice() {
		
		Mouse[] mice = new Mouse[DeviceManager.NUMBER_OF_MICE];
		int index = 0;
		
		for(Device device : DeviceManager.DEVICES) {
			
			if(device instanceof Mouse) {
				
				mice[index] = (Mouse)device;
				index++;
			}
		}
		
		return mice;
	}
	
	/**
	 * @return all registered keyboards
	 * @since 1.0.0
	 */
	public static final Keyboard[] getKeyboards() {
		
		Keyboard[] keyboards = new Keyboard[DeviceManager.NUMBER_OF_KEYBOARDS];
		int index = 0;
		
		for(Device device : DeviceManager.DEVICES) {
			
			if(device instanceof Keyboard) {
				
				keyboards[index] = (Keyboard)device;
				index++;
			}
		}
		
		return keyboards;
	}
	
	/**
	 * @return all registered gamepads
	 * @since 1.0.0
	 */
	public static final Gamepad[] getGamepads() {
		
		Gamepad[] gamepads = new Gamepad[DeviceManager.NUMBER_OF_GAMEPADS];
		int index = 0;
		
		for(Device device : DeviceManager.DEVICES) {
			
			if(device instanceof Gamepad) {
				
				gamepads[index] = (Gamepad)device;
				index++;
			}
		}
		
		return gamepads;
	}
}
