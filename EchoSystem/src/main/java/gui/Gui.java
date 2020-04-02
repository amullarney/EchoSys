package gui;

import io.ciera.runtime.summit.interfaces.IMessage;

public class Gui {

    EchoGui guiDisplay = null;
    ApplicationConnection server = null;
    ConnectionHandler connHandler = null;

    public void start(String[] args) {
        // Create GUI
        guiDisplay = new SwingEchoGui(this);

        // The connection handle lives for the entire duration of this program
        connHandler = new ConnectionHandler(this);

        // Start the connection handler
        connHandler.start();

        // Display the GUI
        guiDisplay.display();
    }

    public void setApplicationConnection(ApplicationConnection server) {
        this.server = server;
    }

    public void sendSignal(IMessage message) {
        server.sendSignal(message);
    }

    public EchoGui getGuiDisplay() {
        return guiDisplay;
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.start(args);
    }

}
