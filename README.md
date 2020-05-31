# EchoSys
Simple demonstration of connecting external code (a browser-based client in this case) to code generated from an xtUML application model.  The modeled application merely receives a text message from the external code and then sends that same message, prefixed with a constant string, back to the external code.
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
2. Open two browsers.
3. Point one browser at http://localhost:8080/Reply 
4. Point the other browser at http://localhost:8080/Request?msg=Hey
5. Refresh the browser pointed at Reply after sending the Request message with the other browser and notice the data is updated according to the message that was sent by the other client.
6. Notice that the Request page always shows the previous (not the most recent) version of the request message because the Spring model data gets updated before displaying the page.
