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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;
import echosystem.RequestMessage;
import echosystem.ReplyMessage;
import echosystem.EchoUI;

@Controller
public class UIMsgController {
	private static UIMsgController singleton;  // @TODO this is a hack
	
	private SimpMessagingTemplate template;  
	
	@Autowired
	public UIMsgController( SimpMessagingTemplate template ) {
		singleton = this;
		this.template = template;
	}
	
	public static UIMsgController Singleton() {
		return singleton;
	}
	
    @MessageMapping( "/Request" )
    public void request( RequestMessage message ) throws Exception {
    	System.out.printf( "UIMsgController: \n" );
    	System.out.printf( "message: %s\n", message.getMsg() );
    	try {
      	  EchoUI.singleton().App().Request( message.getMsg() );
      	}
      	catch ( Exception e ) {
        	  System.out.printf( "Exception, %s, in Request()\n", e );    			
      	}
    }
    
    public void SendReplyMessage ( String msg ) throws Exception {
    	ReplyMessage rmsg = new ReplyMessage( HtmlUtils.htmlEscape( msg ) );
        System.out.printf( "Sending reply: %s\n", rmsg.getContent() );
        this.template.convertAndSend( "/topic/replies", rmsg );
    }
}
