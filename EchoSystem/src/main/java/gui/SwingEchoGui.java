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
    private JLabel prompt, replyLabel, confirmation;
    private JTextField message, reply;

    public SwingEchoGui(Gui gui) {
        // set signal handler
        signalHandler = gui;
        
        // Prompt for message
        prompt = new JLabel();
        prompt.setText( "Enter message: " );
        prompt.setBounds( 10, 10, 200, 50 );
        this.add( prompt );
        
        // Text field for message
        message = new JTextField();
        message.setBounds( 150, 10, 200, 50 );
        this.add( message );
        
        // Label for reply field
        replyLabel = new JLabel();
        replyLabel.setText( "Reply: " );
        replyLabel.setBounds( 10, 80, 200, 50 );
        this.add( replyLabel );
        
        // Text field for reply
        reply = new JTextField();
        reply.setBounds( 150, 80, 200, 50 );
        this.add( reply );
        
        // Button to send message
        sendMessage = new JButton( "Send" );
        sendMessage.setBounds( 10, 200, 100, 40 );
        sendMessage.addActionListener( new ActionListener() {
        	@Override
        	public void actionPerformed( ActionEvent arg0 ) {
        		SwingEchoGui.this.sendSignal( new IEUI.Request ( message.getText() ) );
        		confirmation.setText( "Message has been sent." );
        	}
        });
        this.add( sendMessage );
        
        // Confirmation that message was sent
        confirmation = new JLabel();
        confirmation.setBounds( 150, 200, 200, 50 );
        this.add( confirmation );
        
                
        // Initialize window      
        setTitle("Echo");
        setSize(500, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void Reply( String p_msg ) {
    	reply.setText( p_msg );
    }
    
    private void sendSignal(IMessage message) {
        signalHandler.sendSignal(message);
    }
    
    @Override
    public void display() {
        setVisible(true);
    }

}
