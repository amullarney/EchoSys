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
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import echosystem.EchoUI;
import echosystem.UImodel;

@Controller
public class UIMsgController {
			
	public UIMsgController() {
	}
	 
	// Eventually, this should be a @PostMapping because GET messages 
	// should be safe and idempotent.
    @GetMapping( "/Request" )
    public String Request( 
      @RequestParam( value = "msg", required=true ) String msg, Model model ) {
	      System.out.printf( "Request(): storing/sending: %s\n", msg ); 
	      if ( UImodel.Singleton() == null )
	    	  new UImodel();
          UImodel.Singleton().setMsg( msg );
    	  try {
    	    EchoUI.Singleton().App().Request( msg );
    	  }
    	  catch ( Exception e ) {
    	    System.out.printf( "Exception, %s, in Request()\n", e );    			
    	  }
    	  return "request";  // return name of HTML template
    }
    
    @GetMapping( "/Reply" )
    public String Reply() { 
    	  return "reply";  // return name of HTML template
    }
    
    public static void SendReplyMessage ( String reply ) throws Exception {
        System.out.printf( "SendReplyMessage: %s\n", reply );
        UImodel.Singleton().setReply( reply );
    }
    
    @ModelAttribute( "UI" )
    public void populateModel( Model model ) {
    	if ( UImodel.Singleton() != null ) {
    	  model.addAttribute( "msg", UImodel.Singleton().getMsg() );
    	  model.addAttribute( "reply", UImodel.Singleton().getReply() );
    	}
    }
}
