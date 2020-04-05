# EchoSys
Simple demonstration of connecting external code (a GUI in this case) to code generated from an xtUML application model.  The modeled application merely receives a text message from the external code and then sends that same message, prefixed with a constant string, back to the external code.
## Importing into Eclipse
There are four Eclipse projects:
- EchoSys: Top-level project, containing the other projects.
- Echo:  Modeled application (xtUML project)
- EchoUI:  Modeled shell component representing external GUI (xtUML project)
- EchoSystem:  Modeled deployment of Echo and EchoUI (xtUML project)

After cloning the repository, import all four projects.  Then, import the Ciera runtine.
## Build
Build the system by executing `mvn install` at the top level (EchoSys).
## Run
1. Open two terminal windows.
2. In one terminal, execute `bash gui.sh` to launch the GUI.
3. In the other terminal, execute `bash run.sh` to launch the generated application.
4. Enter a text message and poke the **Submit** button.
5. Notice the reply message appears in the GUI.
