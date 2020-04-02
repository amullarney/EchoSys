package gui;

import io.ciera.runtime.summit.interfaces.IMessage;
import java.awt.EventQueue;
import javax.swing.JFrame;
import echoui.shared.IEUI;

public class SwingEchoGui extends JFrame implements EchoGui {

    public static final long serialVersionUID = 0;
        
    private Gui signalHandler;
    private JPanel holdAll = new JPanel();

    public SwingEchoGui(Gui gui) {
        // set signal handler
        signalHandler = gui;
        
        setTitle("Echo");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void Reply( String p_msg ) {
    	// @@@ TODO: implementation
    }
    
    private void sendSignal(IMessage message) {
        signalHandler.sendSignal(message);
    }
    
    @Override
    public void display() {
        setVisible(true);
    }
}
