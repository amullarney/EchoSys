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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import echosystem.RequestMessage;
import echosystem.ReplyMessage;
import echosystem.EchoUI;

@Controller
public class UIMsgController {
	private static UIMsgController singleton;  // @TODO this is a hack
	
	public UIMsgController() {
		singleton = this;
	}
	
	public static UIMsgController Singleton() {
		return singleton;
	}
	
    @MessageMapping( "/Request" )
    @SendTo( "/topic/replies" )
    public ReplyMessage request( RequestMessage message ) throws Exception {
    	System.out.printf( "UIMsgController: \n" );
    	System.out.printf( "message: %s\n", message.getMsg() );
    	try {
      	  // EchoUI.singleton().App().Request( message );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in Request()\n", e );    			
      	}
    	Thread.sleep( 1000 );
    	ReplyMessage rmsg = new ReplyMessage( "Echo: " + HtmlUtils.htmlEscape( message.getMsg() ) );
    	System.out.printf( "Reply message: %s\n", rmsg.getContent() );
    	return rmsg;
    }
    
    // @SendTo( "/topic/replies" )
    public ReplyMessage SendReplyMessage ( String msg ) throws Exception {
        // System.out.printf( "@SendTo: %s\n", msg );
        return new ReplyMessage( msg );
    }
}
