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

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.ralleytn.simple.input.internal.Util;
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

	private static ControllerEnvironment ENVIRONMENT;
	private static List<Device> DEVICES;
	private static ControllerListener CONTROLLER_LISTENER;
	private static boolean CREATED;
	
	private DeviceManager() {}
	
	protected static synchronized final void removeDevice(Device device) {
		
		DeviceManager.DEVICES.remove(device);
	}
	
	private static synchronized final void addDevice(Controller controller) {
		
		Type type = controller.getType();
		
			   if(type == Type.MOUSE)    {DeviceManager.DEVICES.add(new Mouse(controller));
		} else if(type == Type.KEYBOARD) {DeviceManager.DEVICES.add(new Keyboard(controller));
		} else if(type == Type.STICK)    {DeviceManager.DEVICES.add(new DefaultGamepad(controller));
		} else if(type == Type.GAMEPAD)  {DeviceManager.DEVICES.add(Util.isXInput(controller) ? new XInputGamepad(controller) : new DefaultGamepad(controller));
		}
	}
	
	/**
	 * Sets the native directory dynamically.
	 * <p><i><b>Warning!</b><br>This is a hack that will not work if a security manager blocks the writing access of the reflection API!
	 * The logic behind this is to add the directory to the {@code java.library.path} system property and afterwards deleting the contents
	 * of the {@code sys_paths} field in the {@linkplain ClassLoader} with reflection so that it will re-initialize with the added directory.
	 * The usage of this method is not recommended and should only be used for testing purposes or by those that cannot find any other
	 * solution for adding the natives on the library path!</i></p>
	 * @param directory the directory containing the native libraries
	 * @throws SecurityException if the security manager says no
	 * @since 1.0.0
	 */
	public static final void setNatives(File directory) throws SecurityException {

		try {
			
			System.setProperty("java.library.path", directory.getAbsolutePath().replace("/", File.separator).replace("\\", File.separator) + ";" + System.getProperty("java.library.path"));
			Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);
			
		} catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
			
			// WILL NEVER HAPPEN!
			// THE FIELD EXISTS!
			// THE ARGUMENT IS VALID!
			// AND IT WILL BE MADE ACCESSIBLE BEFORE EDITING!
		}
	}
	
	/**
	 * Creates the context.
	 * Has to be done before using any other methods.
	 * Does nothing if it was already created and not destroyed yet.
	 * @since 1.0.0
	 */
	public static synchronized final void create() {
		
		if(!DeviceManager.CREATED) {
			
			DeviceManager.DEVICES = new ArrayList<>();
			DeviceManager.CONTROLLER_LISTENER = new Adapter();
			DeviceManager.ENVIRONMENT = ControllerEnvironment.getDefaultEnvironment();
			DeviceManager.ENVIRONMENT.addControllerListener(DeviceManager.CONTROLLER_LISTENER);
			
			for(Controller controller : DeviceManager.ENVIRONMENT.getControllers()) {
				
				DeviceManager.addDevice(controller);
			}
			
			DeviceManager.CREATED = true;
		}
	}

	/**
	 * Destroys the context.
	 * Should be called once you are done using this library.
	 * Does nothing if the {@link #create()} method wasn't called yet.
	 * @since 1.0.0
	 */
	public static synchronized final void destroy() {
		
		if(DeviceManager.CREATED) {
			
			DeviceManager.stopListening();
			DeviceManager.ENVIRONMENT.removeControllerListener(DeviceManager.CONTROLLER_LISTENER);
			DeviceManager.CONTROLLER_LISTENER = null;
			DeviceManager.ENVIRONMENT = null;
			DeviceManager.DEVICES = null;
			DeviceManager.CREATED = false;
		}
	}
	
	private static final void stopListening() {
		
		DeviceManager.getMice().forEach(Mouse::stopListening);
		DeviceManager.getKeyboards().forEach(Keyboard::stopListening);
		DeviceManager.getGamepads().forEach(Gamepad::stopListening);
	}
	
	/**
	 * Adds a {@linkplain MouseListener} to all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static synchronized final void addMouseListener(MouseListener listener) {
		
		DeviceManager.getMice().forEach(mouse -> mouse.addMouseListener(listener));
	}
	
	/**
	 * Removes a {@linkplain MouseListener} from all registered mice.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public static synchronized final void removeMouseListener(MouseListener listener) {
		
		DeviceManager.getMice().forEach(mouse -> mouse.removeMouseListener(listener));
	}
	
	/**
	 * Adds a {@linkplain KeyboardListener} to all registered keyboards.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public static synchronized final void addKeyboardListener(KeyboardListener listener) {
		
		DeviceManager.getKeyboards().forEach(keyboard -> keyboard.addKeyboardListener(listener));
	}
	
	/**
	 * Removes a {@linkplain KeyboardListener} from all registered keyboards.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public static synchronized final void removeKeyboardListener(KeyboardListener listener) {
		
		DeviceManager.getKeyboards().forEach(keyboard -> keyboard.removeKeyboardListener(listener));
	}
	
	/**
	 * Adds a {@linkplain GamepadListener} to all registered gamepads.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public static synchronized final void addGamepadListener(GamepadListener listener) {
		
		DeviceManager.getGamepads().forEach(gamepad -> gamepad.addGamepadListener(listener));
	}
	
	/**
	 * Removes a {@linkplain GamepadListener} from all registered gamepads.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public static synchronized final void removeGamepadListener(GamepadListener listener) {
		
		DeviceManager.getGamepads().forEach(gamepad -> gamepad.removeGamepadListener(listener));
	}
	
	/**
	 * @return all registered mice
	 * @since 1.0.0
	 */
	public static synchronized final List<Mouse> getMice() {
		
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
	public static synchronized final List<Keyboard> getKeyboards() {
		
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
	public static synchronized final List<Gamepad> getGamepads() {
		
		List<Gamepad> gamepads = new ArrayList<>();
		DeviceManager.DEVICES.forEach(device -> {
			
			if(device instanceof Gamepad) {
				
				gamepads.add((Gamepad)device);
			}
		});
		
		return gamepads;
	}
	
	/**
	 * @param name the name (not a unique identifier)
	 * @return all mice with the given name, or an empty list if there are no mice with this name
	 * @since 1.0.0
	 */
	public static synchronized final List<Mouse> getMiceByName(String name) {
		
		List<Mouse> mice = new ArrayList<>();
		
		for(Mouse mouse : DeviceManager.getMice()) {
			
			if(mouse.getName().equals(name)) {
				
				mice.add(mouse);
			}
		}
		
		return mice;
	}
	
	/**
	 * @param name the name (not a unique identifier)
	 * @return all keyboards with the given name, or an empty list if there are no keyboards with this name
	 * @since 1.0.0
	 */
	public static synchronized final List<Keyboard> getKeyboardsByName(String name) {
		
		List<Keyboard> keyboards = new ArrayList<>();
		
		for(Keyboard keyboard : DeviceManager.getKeyboards()) {
			
			if(keyboard.getName().equals(name)) {
				
				keyboards.add(keyboard);
			}
		}
		
		return keyboards;
	}
	
	/**
	 * @param name the name (not a unique identifier)
	 * @return all gamepads with the given name, or an empty list if there are no gamepads with this name
	 * @since 1.0.0
	 */
	public static synchronized final List<Gamepad> getGamepadsByName(String name) {
		
		List<Gamepad> gamepads = new ArrayList<>();
		
		for(Gamepad gamepad : DeviceManager.getGamepads()) {
			
			if(gamepad.getName().equals(name)) {
				
				gamepads.add(gamepad);
			}
		}
		
		return gamepads;
	}
	
	private static final class Adapter implements ControllerListener {
		
		// FIXME
		// WHY DOES A CONTROLLER LISTENER EXIST IF IT DOESN'T WORK!?
		
		@Override
		public synchronized void controllerRemoved(ControllerEvent event) {
			
			System.out.println("Removed " + event.getController().getName());
			
			List<Device> devices = new ArrayList<>();
			DeviceManager.DEVICES.forEach(device -> {

				if(device.getController() == event.getController()) {
					
					device.remove();
					
				} else {
					
					devices.add(device);
				}
			});
			
			DeviceManager.DEVICES = devices;
		}
		
		@Override
		public synchronized void controllerAdded(ControllerEvent event) {

			System.out.println("Added " + event.getController().getName());
			
			Controller controller = event.getController();
			DeviceManager.addDevice(controller);
		}
	}
}
