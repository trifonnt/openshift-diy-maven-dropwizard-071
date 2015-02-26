package bg.d3soft.dropwizard.example;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.CachingAuthenticator;
//import io.dropwizard.auth.basic.BasicAuthFactory; // 0.8.x
import io.dropwizard.auth.basic.BasicAuthProvider; // 0.7.x
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;
import bg.d3soft.dropwizard.example.auth.basic.HardcodedBasicAuthenticator;
import bg.d3soft.dropwizard.example.cli.MyExampleCommand;
import bg.d3soft.dropwizard.example.health.TemplateHealthCheck;
import bg.d3soft.dropwizard.example.model.User;
import bg.d3soft.dropwizard.example.resources.HelloWorldResource;
import bg.d3soft.dropwizard.example.resources.HelloWorldResourcePathParam;
import bg.d3soft.dropwizard.example.resources.HelloWorldSecretResource;
import bg.d3soft.dropwizard.example.resources.IndexResource;

import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilderSpec;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.server.linking.LinkFilter;


public class HelloWorldDropwizardApplication extends Application<HelloWorldConfiguration> {

	private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();


	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		// CLI commands
		bootstrap.addCommand(new MyExampleCommand());

		// Swagger
		swaggerDropwizard.onInitialize( bootstrap );

		// Asset Bundles - TODO when we want caching
//		CacheBuilderSpec cacheBuilderSpec = null;
//		cacheBuilderSpec = (System.getenv("FILE_CACHE_ENABLED") == null) ? CacheBuilderSpec.parse("maximumSize=0") : AssetsBundle.DEFAULT_CACHE_SPEC;
//		cacheBuilderSpec = CacheBuilderSpec.parse("maximumSize=0");
//		bootstrap.addBundle(new AssetsBundle("/assets/css", cacheBuilderSpec, "/css"));

		// Serve anything under /assets/app inside my JAR file under the URL pattern /,
		// with index.html as the default file. This bundle will be named static, but name can be changed to whatever name you like.
		bootstrap.addBundle(new AssetsBundle("/assets/web", "/", "index.html", "static"));    // In order to work properly: environment.jersey().setUrlPattern("/api/*");
//		bootstrap.addBundle(new AssetsBundle("/assets/web", "/web", "index.html", "static")); // !!!old!!!
		
//		bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
//		bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
//		bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
	}

	@Override
	public void run(HelloWorldConfiguration configuration, Environment environment) {
		// Register Jersey LinkFilter
		environment.jersey().property(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LinkFilter.class);

		//   Asset bundles not able to be served from root path.
		// - https://github.com/dropwizard/dropwizard/issues/661
		environment.jersey().setUrlPattern("/api/*");

		// Swagger
		swaggerDropwizard.onRun(configuration, environment, "localhost"); // TODO - set proper hostname
//		swaggerDropwizard.onRun(configuration, environment);

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
		String realmName = "SUPER SECRET STUFF";
		HardcodedBasicAuthenticator authenticator = new HardcodedBasicAuthenticator();
		Authenticator<BasicCredentials, User> cachedAuthenticator = null;
////	cachedAuthenticator = CachingAuthenticator<BasicCredentials, User>.wrap(authenticator, configuration.getAuthenticationCachePolicy());
		CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(configuration.getAuthenticationCachePolicy());
		cachedAuthenticator = new CachingAuthenticator<BasicCredentials, User>(new MetricRegistry(), authenticator, cacheBuilderSpec);
		
//		environment.jersey().register(new BasicAuthFactory<User>(cachedAuthenticator, realmName, User.class)); // 0.8.x
		environment.jersey().register(new BasicAuthProvider<User>(cachedAuthenticator, realmName)); // 0.7.x

		// Authentication - BASIC
//		environment.jersey().register(new BasicAuthProvider<User>(authenticator, realmName));
	
		// Authentication - OAuth2 - NOT WORKING YET
//		environment.jersey().register(new OAuthProvider<User>(new OAuth2Authenticator(), realmName));
	}

	public static void main(String[] args) throws Exception {
		new HelloWorldDropwizardApplication()
			.run( args );
	}
}