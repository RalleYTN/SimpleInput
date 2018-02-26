package de.ralleytn.simple.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;

/**
 * Manages the input devices.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DeviceManager {

	private static List<Controller> CONTROLLERS;
	private static ControllerEnvironment ENVIRONMENT;
	private static List<Device> DEVICES;
	private static ControllerListener CONTROLLER_LISTENER;
	
	private DeviceManager() {}
	
	/**
	 * Creates the context.
	 * Has to be done before using any other methods.
	 * @since 1.0.0
	 */
	public static final void create() {
		
		DeviceManager.CONTROLLERS = new ArrayList<>();
		DeviceManager.DEVICES = new ArrayList<>();
		DeviceManager.CONTROLLER_LISTENER = new ControllerListener() {
			
			// FIXME
			// WHY DOES A CONTROLLER LISTENER EXIST IF IT DOESN'T WORK!?
			
			@Override
			public void controllerRemoved(ControllerEvent event) {
				
				// System.out.println("Removed " + event.getController().getName());
				
				List<Device> devices = new ArrayList<>();
				DeviceManager.DEVICES.forEach(device -> {

					if(device.getController().equals(event.getController())) {
						
						device.remove();
						
					} else {
						
						devices.add(device);
					}
				});
				
				DeviceManager.DEVICES = devices;
			}
			
			@Override
			public void controllerAdded(ControllerEvent event) {
				
				// System.out.println("Added " + event.getController().getName());
				
				Controller controller = event.getController();
				Type type = controller.getType();
				
				if(type == Type.MOUSE) {
					
					DeviceManager.DEVICES.add(new Mouse(controller));
					DeviceManager.CONTROLLERS.add(controller);
					
				} else if(type == Type.KEYBOARD) {
					
					DeviceManager.DEVICES.add(new Keyboard(controller));
					DeviceManager.CONTROLLERS.add(controller);
					
				} else if(type == Type.GAMEPAD || type == Type.STICK) {
					
					DeviceManager.DEVICES.add(new Gamepad(controller));
					DeviceManager.CONTROLLERS.add(controller);
				}
			}
		};
		DeviceManager.ENVIRONMENT = ControllerEnvironment.getDefaultEnvironment();
		DeviceManager.ENVIRONMENT.addControllerListener(DeviceManager.CONTROLLER_LISTENER);
		
		for(Controller controller : DeviceManager.ENVIRONMENT.getControllers()) {
			
			Type type = controller.getType();
			
			if(type == Type.MOUSE) {
				
				DeviceManager.DEVICES.add(new Mouse(controller));
				DeviceManager.CONTROLLERS.add(controller);
				
			} else if(type == Type.KEYBOARD) {
				
				DeviceManager.DEVICES.add(new Keyboard(controller));
				DeviceManager.CONTROLLERS.add(controller);
				
			} else if(type == Type.GAMEPAD || type == Type.STICK) {
				
				DeviceManager.DEVICES.add(new Gamepad(controller));
				DeviceManager.CONTROLLERS.add(controller);
			}
		}
	}

	/**
	 * Destroys the context.
	 * Should be called once you are done using this library.
	 * @since 1.0.0
	 */
	public static final void destroy() {
		
		DeviceManager.ENVIRONMENT.removeControllerListener(DeviceManager.CONTROLLER_LISTENER);
		DeviceManager.CONTROLLER_LISTENER = null;
		DeviceManager.ENVIRONMENT = null;
		DeviceManager.CONTROLLERS = null;
		DeviceManager.DEVICES = null;
	}
	
	/**
	 * Adds a {@linkplain MouseListener} to all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static final void addMouseListener(MouseListener listener) {
		
		DeviceManager.getMice().forEach(mouse -> mouse.addMouseListener(listener));
	}
	
	/**
	 * Removes a {@linkplain MouseListener} from all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static final void removeMouseListener(MouseListener listener) {
		
		DeviceManager.getMice().forEach(mouse -> mouse.removeMouseListener(listener));
	}
	
	/**
	 * @return all registered mice
	 * @since 1.0.0
	 */
	public static final List<Mouse> getMice() {
		
		List<Mouse> mice = new ArrayList<>();
		DeviceManager.DEVICES.forEach(device -> {
			
			if(device instanceof Mouse) {
				
				mice.add((Mouse)device);
			}
		});
		
		return mice;
	}
	
	/**
	 * @return all registered keyboards
	 * @since 1.0.0
	 */
	public static final List<Keyboard> getKeyboards() {
		
		List<Keyboard> keyboards = new ArrayList<>();
		DeviceManager.DEVICES.forEach(device -> {
			
			if(device instanceof Keyboard) {
				
				keyboards.add((Keyboard)device);
			}
		});
		
		return keyboards;
	}
	
	/**
	 * @return all registered gamepads
	 * @since 1.0.0
	 */
	public static final List<Gamepad> getGamepads() {
		
		List<Gamepad> gamepads = new ArrayList<>();
		DeviceManager.DEVICES.forEach(device -> {
			
			if(device instanceof Gamepad) {
				
				gamepads.add((Gamepad)device);
			}
		});
		
		return gamepads;
	}
}
