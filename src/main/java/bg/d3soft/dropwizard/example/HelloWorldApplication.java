package bg.d3soft.dropwizard.example;

import bg.d3soft.dropwizard.example.auth.OAuth2Authenticator;
import bg.d3soft.dropwizard.example.auth.SimpleAuthenticator;
import bg.d3soft.dropwizard.example.cli.MyExampleCommand;
import bg.d3soft.dropwizard.example.health.DatabaseHealthCheck;
import bg.d3soft.dropwizard.example.health.TemplateHealthCheck;
import bg.d3soft.dropwizard.example.model.User;
import bg.d3soft.dropwizard.example.resources.HelloWorldResource;
import bg.d3soft.dropwizard.example.resources.HelloWorldResourcePathParam;
import bg.d3soft.dropwizard.example.resources.HelloWorldSecretResource;
import bg.d3soft.dropwizard.example.resources.IndexResource;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// CLI commands
		bootstrap.addCommand(new MyExampleCommand());

		// Asset Bundles
//		bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
//		bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
//		bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
	}

	@Override
	public void run(HelloWorldConfiguration configuration, Environment environment) {
		// Health check - Template
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);

		// Health check - Database
//		final DatabaseHealthCheck dbHealthCheck = new DatabaseHealthCheck( configuration.getDatabase() );
//		environment.healthChecks().register("database", dbHealthCheck);


		// First resource
		final HelloWorldResource resourceOne = new HelloWorldResource(
				  configuration.getTemplate()
				, configuration.getDefaultName()
		);
		environment.jersey().register( resourceOne );

		// Second resource
		final HelloWorldResourcePathParam resourceTwo = new HelloWorldResourcePathParam(
			  configuration.getTemplate()
			, configuration.getDefaultName()
		);
		environment.jersey().register( resourceTwo );

		// Third resource
		final HelloWorldSecretResource resourceThree = new HelloWorldSecretResource(
			  configuration.getTemplate()
			, configuration.getDefaultName()
		);
		environment.jersey().register( resourceThree );

		// Fourth resource
		environment.jersey().register( new IndexResource() );


		// Authentication - BASIC
		environment.jersey().register(new BasicAuthProvider<User>(new SimpleAuthenticator(), "SUPER SECRET STUFF"));
	
		// Authentication - OAuth2 - NOT WORKING YET
//		environment.jersey().register(new OAuthProvider<User>(new OAuth2Authenticator(), "SUPER SECRET STUFF"));
	}

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication()
			.run( args );
	}
}