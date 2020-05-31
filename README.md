# EchoSys
Simple demonstration of connecting external code (browser-based clients in this case) to code generated from an xtUML application model.  The modeled application merely receives a text message from the external code and then sends that same message, prefixed with a constant string, back to the external code.
## Importing into Eclipse
There are four Eclipse projects:
- EchoSys: Top-level project, containing the other projects.
- Echo:  Modeled application (xtUML project)
- EchoUI:  Modeled shell component representing external GUI (xtUML project)
- EchoSystem:  Modeled deployment of Echo and EchoUI (xtUML project)

After cloning the repository...
- Import all four Eclipse projects.  
- Import the Ciera runtine.
## Build
Build the system by executing `mvn install` at the top level (EchoSys).
## Run
1. Open a terminal window and execute `bash ./run.sh` to launch the servlet-wrapped application.
2. Open two browsers, pointing one each at http://localhost:8080/north.html and http://localhost:8080/south.html
3. Poke "Connect" on both browsers.
4. Enter a message in one browser and poke "Send".
5. Enter another message in the other browser and poke "Send".
6. Any message containing "north" will be echoed only to the North window, while any message containing "south" and not "north" will be echoed only to the South window.  
7. Messages containing neither "north" nor "south" are echoed to both windows.
