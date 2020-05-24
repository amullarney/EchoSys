package echosystem;


import echosystem.Echo;
import echosystem.EchoUI;

import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.ILogger;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class EchoSystemApplication implements IApplication {

    private IComponent<?>[] components;
    private ApplicationExecutor[] executors;

    public EchoSystemApplication() {
        components = new IComponent<?>[2];
        executors = new ApplicationExecutor[1];
    }

    @Override
    public void run() {
        for (ApplicationExecutor executor : executors) {
            executor.run();
        }
    }

    @Override
    public void setup( String[] args, ILogger logger ) {
        for ( int i = 0; i < executors.length; i++ ) {
            if ( null != logger ) {
                executors[i] = new ApplicationExecutor( "EchoSystemApplicationExecutor" + i, args, logger );
            }
            else {
                executors[i] = new ApplicationExecutor( "EchoSystemApplicationExecutor" + i, args );
            }
        }
        components[0] = new Echo(this, executors[0], 0);
        components[1] = new EchoUI(this, executors[0], 1);
        ((EchoUI)components[1]).App().satisfy(((Echo)components[0]).UI());
        ((Echo)components[0]).UI().satisfy(((EchoUI)components[1]).App());
    }

    public Echo Echo() {
        return (Echo)components[0];
    }
    public EchoUI EchoUI() {
        return (EchoUI)components[1];
    }

    @Override
    public void initialize() {
        for ( IComponent<?> component : components ) {
            component.getRunContext().execute( new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }

    @Override
    public void start() {
        if (1 == executors.length) {
            executors[0].run();
        }
        else {
            for ( ApplicationExecutor executor : executors ) {
                executor.start();
            }
        }
    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }

    @Override
    public void stop() {
        for ( ApplicationExecutor executor : executors ) {
            executor.execute(new HaltExecutionTask());
        }
    }

    public static void main( String[] args ) {
        IApplication app = new EchoSystemApplication();
        app.setup( args, null );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
            app.printVersions();
        }
        else {
            app.initialize();
            System.out.printf("Invoking SpringApplication.run()\n");
            SpringApplication.run( EchoSystemApplication.class, args );             
            app.start();
        }
    }
    
    @GetMapping( "/Request" )
    public String Request( String msg ) {
    	return String.format( "Request received." );
    }

}
