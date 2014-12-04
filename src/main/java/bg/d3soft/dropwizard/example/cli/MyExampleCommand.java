package bg.d3soft.dropwizard.example.cli;

import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;


public class MyExampleCommand extends Command {

	public MyExampleCommand() {
//		super(name, description);
		super("trifon-test", "Trifon Test Command - Description");
	}

	@Override
	public void configure(Subparser parser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(Bootstrap<?> bootstrap, Namespace nameSpace) throws Exception {
		// TODO Auto-generated method stub
		
	}
}