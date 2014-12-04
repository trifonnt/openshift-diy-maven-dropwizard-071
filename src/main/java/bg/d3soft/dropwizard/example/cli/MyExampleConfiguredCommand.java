package bg.d3soft.dropwizard.example.cli;

import bg.d3soft.dropwizard.example.HelloWorldConfiguration;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;


public class MyExampleConfiguredCommand extends ConfiguredCommand<HelloWorldConfiguration> {

	public MyExampleConfiguredCommand() {
//		super(name, description);
		super("trifon-test-configured", "Trifon Test Configured Command - Description");
	}

	@Override
	protected void run(Bootstrap<HelloWorldConfiguration> bootstrap, Namespace namespace, HelloWorldConfiguration configuration) throws Exception {
		// TODO Auto-generated method stub
	}

}