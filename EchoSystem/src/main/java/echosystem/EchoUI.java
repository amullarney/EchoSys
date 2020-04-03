package echosystem;

import echosystem.echoui.EchoUIApp;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.Message;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Properties;
import echoui.shared.IEUI;

public class EchoUI extends Component<EchoUI> {

	private static GuiConnection requester = null;
    private static final int SOCKET_ERROR = -1;

    public EchoUI(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);

        LOG = null;
        EchoUIApp = null;
    }

    // domain functions

    
    // relates and unrelates


    // instance selections


    // relationship selections


    // ports
    private EchoUIApp EchoUIApp;
    public EchoUIApp App() {
        if ( null == EchoUIApp ) EchoUIApp = new EchoUIApp( this, null );
        return EchoUIApp;
    }


    // utilities
    private LOG LOG;
    public LOG LOG() {
    	if ( null == LOG ) LOG = new LOGImpl<>(this);
    	return LOG;
    }

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
		if ( SOCKET_ERROR != connect() ) {
            getRunContext().execute(new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    listen();
                }
            });
		}
		else {
			getRunContext().execute(new HaltExecutionTask());
		}
    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("EchoUIProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("EchoUIProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );

        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );

        return false;
    }

    @Override
    public EchoUI context() {
        return this;
    }
    
    public void listen() throws XtumlException {
        int signal_no = poll();
        switch (signal_no) {
        case IEUI.SIGNAL_NO_REQUEST:
        	// @@@ TODO: get message string to pass to Request()
        	final String p_msg = "EchoUI.java TODO";
        	App().Request( p_msg );
            break;
        case SOCKET_ERROR:
            LOG().LogFailure("Socket listener shuting down.");
            getRunContext().execute(new HaltExecutionTask());
            return;
        default:
            break;
        }
        getRunContext().execute(new GenericExecutionTask() {
            @Override
            public void run() throws XtumlException {
                listen();
            }
        });
    }
	
    private int connect() {
        if ( null == requester ) requester = new GuiConnection(LOG());
        try {
            requester.connect();
        } catch (UnknownHostException unknownHost) {
            LOG().LogInfo("You are trying to connect to an unknown host.");
            requester.tearDown();
            requester = null;
            return SOCKET_ERROR;
        } catch (IOException ioException) {
            LOG().LogInfo("Connection lost.");
            requester.tearDown();
            requester = null;
            return SOCKET_ERROR;
        }
        return 1;
    }
    
    private int poll() {
        if ( null != requester ) {
            try {
                return requester.poll();
            } catch ( IOException e ) {
                LOG().LogInfo("Connection lost.");
                requester.tearDown();
                requester = null;
                return SOCKET_ERROR;
            } catch ( XtumlException e ) {
                LOG().LogInfo(e.getMessage());
                return Message.NULL_SIGNAL;
            }
        }
        else return SOCKET_ERROR;
    }

    private static class GuiConnection {
    	
    	private LOG LOG;
        Socket requestSocket;
        DataOutputStream out;
        BufferedReader in;
        
        private GuiConnection(LOG log) {
        	LOG = log;
        }

        private void connect() throws IOException {
            requestSocket = new Socket("localhost", 2003);
            requestSocket.setSoTimeout( 10 ); // set read timeout for 10 milliseconds
            LOG().LogInfo("Connected to localhost on port 2003");
            out = new DataOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
        }
        
        private int poll() throws IOException, XtumlException {
            try {
                String data = in.readLine();
                if (data == null) {
                    LOG().LogInfo("Connection closed by server");
                    return SOCKET_ERROR;
                }
                //LOG().LogFailure(data);
                IMessage msg = Message.deserialize(data);
                return msg.getId();
            } catch ( IOException e ) {
                if ( e instanceof SocketTimeoutException ) { /* do nothing */ }
                else throw e;
            }
            return Message.NULL_SIGNAL;
        }
        
        private void sendMessage(IMessage msg) throws IOException, XtumlException {
            out.write(msg.serialize().getBytes());
            out.write('\n');
            out.flush();
        }
        
        private void tearDown() {
            try {
                if ( null != requestSocket ) requestSocket.close();
                if ( null != out ) out.close();
                if ( null != in ) in.close();
            } catch ( IOException e ) {}
        }
        
        private LOG LOG() {
        	return LOG;
        }
    }

}
