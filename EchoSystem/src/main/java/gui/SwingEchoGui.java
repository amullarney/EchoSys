package gui;

import io.ciera.runtime.summit.interfaces.IMessage;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import echoui.shared.IEUI;

public class SwingEchoGui extends JFrame implements EchoGui {

    public static final long serialVersionUID = 0;
        
    private Gui signalHandler;
    private JButton sendMessage;
    private JLabel prompt, confirmation;
    private JTextField message;

    public SwingEchoGui(Gui gui) {
        // set signal handler
        signalHandler = gui;
        
        // Prompt for message
        prompt = new JLabel();
        prompt.setText( "Enter message: " );
        prompt.setBounds( 10, 10, 200, 100 );
        this.add( prompt );
        
        // Confirmation that message was sent
        confirmation = new JLabel();
        confirmation.setBounds( 10, 110, 200, 100 );
        this.add( confirmation );
        
        // Text field for message
        message = new JTextField();
        message.setBounds( 150, 50, 200, 30 );
        this.add( message );
        
        // Button to send message
        sendMessage = new JButton( "Send" );
        sendMessage.setBounds( 100, 100, 140, 40 );
        sendMessage.addActionListener( new ActionListener() {
        	@Override
        	public void actionPerformed( ActionEvent arg0 ) {
        		confirmation.setText( "Message has been sent." );
        	}
        });
        this.add( sendMessage );
                
        // Initialize window      
        setTitle("Echo");
        setSize(500, 300);
        setLayout(null);
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
