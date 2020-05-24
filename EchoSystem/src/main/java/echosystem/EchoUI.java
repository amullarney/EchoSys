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

    public EchoUI(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);

        LOG = null;
        EchoUIApp = null;
    }

    // domain functions
    public void Reply( final String p_msg ) throws XtumlException {
      // @TODO send reply to client
    }

    
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
    
}
