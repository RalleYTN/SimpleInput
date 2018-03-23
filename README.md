[![Build Status](https://travis-ci.org/RalleYTN/SimpleInput.svg?branch=master)](https://travis-ci.org/RalleYTN/SimpleInput)
[![Coverage Status](https://coveralls.io/repos/github/RalleYTN/SimpleInput/badge.svg?branch=master)](https://coveralls.io/github/RalleYTN/SimpleInput?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a133cfa2a84d47b3b6f1ab7b71995999)](https://www.codacy.com/app/ralph.niemitz/SimpleInput?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=RalleYTN/SimpleInput&amp;utm_campaign=Badge_Grade)

# Description

This library provides simple input listeners for keyboard, mouse and gamepad that work outside of AWT.
It is possible to listen to input devices individually.

### Example code

Listening to the first mouse:

```java
DeviceManager.create();
List<Mouse> mice = DeviceManager.getMice();
Mouse mouse = mice.get(0);
mouse.addMouseListener(new MouseAdapter() {
	
	@Override
	public void onRelease(MouseEvent event) {
	
		if(event.getButton() == MouseEvent.BUTTON_LEFT) {
		
			System.out.println("Released the left mouse button");
		}
	}
});
mouse.startListening();
```

## Changelog

### Version 1.0.0

- Release

## License

```
MIT License

Copyright (c) 2018 Ralph Niemitz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Links

- [Download](https://github.com/RalleYTN/SimpleInput/releases)
- [Online Documentation](https://ralleytn.github.io/SimpleInput/)