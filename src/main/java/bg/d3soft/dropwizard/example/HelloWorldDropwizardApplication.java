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

		// RAML - TODO - uncomment when new Maven repository is found!
//		bootstrap.addBundle(net.ozwolf.raml.RamlView.bundle("api-specs/example-api-spec.original.raml"));

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
//		environment.jersey().property(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LinkFilter.class); // EXCEPTION
/*
ERROR [2015-02-26 09:01:36,988] io.dropwizard.jersey.errors.LoggingExceptionMapper: Error handling a request: 14d0f572d213ed9a
! java.util.ConcurrentModificationException: null
! at java.util.Vector$Itr.checkForComodification(Vector.java:1156) ~[na:1.7.0_75]
! at java.util.Vector$Itr.next(Vector.java:1133) ~[na:1.7.0_75]
! at com.sun.jersey.server.linking.impl.RefProcessor.processLinks(RefProcessor.java:118) ~[jersey-server-linking-1.18.3.jar:1.18.3]
! at com.sun.jersey.server.linking.impl.RefProcessor.processMember(RefProcessor.java:133) ~[jersey-server-linking-1.18.3.jar:1.18.3]
...
! at com.sun.jersey.server.linking.impl.RefProcessor.processMember(RefProcessor.java:133) ~[jersey-server-linking-1.18.3.jar:1.18.3]
! at com.sun.jersey.server.linking.impl.RefProcessor.processLinks(RefProcessor.java:78) ~[jersey-server-linking-1.18.3.jar:1.18.3]
! at com.sun.jersey.server.linking.LinkFilter.filter(LinkFilter.java:82) ~[jersey-server-linking-1.18.3.jar:1.18.3]
! at com.sun.jersey.server.impl.application.WebApplicationImpl._handleRequest(WebApplicationImpl.java:1494) [jersey-server-1.18.1.jar:1.18.1]
! at com.sun.jersey.server.impl.application.WebApplicationImpl.handleRequest(WebApplicationImpl.java:1419) [jersey-server-1.18.1.jar:1.18.1]
! at com.sun.jersey.server.impl.application.WebApplicationImpl.handleRequest(WebApplicationImpl.java:1409) [jersey-server-1.18.1.jar:1.18.1]
! at com.sun.jersey.spi.container.servlet.WebComponent.service(WebComponent.java:409) [jersey-servlet-1.18.3.jar:1.18.3]
! at com.sun.jersey.spi.container.servlet.ServletContainer.service(ServletContainer.java:558) [jersey-servlet-1.18.3.jar:1.18.3]
! at com.sun.jersey.spi.container.servlet.ServletContainer.service(ServletContainer.java:733) [jersey-servlet-1.18.3.jar:1.18.3]
! at javax.servlet.http.HttpServlet.service(HttpServlet.java:848) [javax.servlet-3.0.0.v201112011016.jar:na]
! at io.dropwizard.jetty.NonblockingServletHolder.handle(NonblockingServletHolder.java:49) [dropwizard-jetty-0.7.1.jar:0.7.1]
! at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1515) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.servlets.UserAgentFilter.doFilter(UserAgentFilter.java:83) [jetty-servlets-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.servlets.GzipFilter.doFilter(GzipFilter.java:348) [jetty-servlets-9.0.7.v20131107.jar:9.0.7.v20131107]
! at io.dropwizard.jetty.BiDiGzipFilter.doFilter(BiDiGzipFilter.java:127) [dropwizard-jetty-0.7.1.jar:0.7.1]
! at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1486) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at io.dropwizard.servlets.ThreadNameFilter.doFilter(ThreadNameFilter.java:29) [dropwizard-servlets-0.7.1.jar:0.7.1]
! at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1486) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at io.dropwizard.jersey.filter.AllowedMethodsFilter.handle(AllowedMethodsFilter.java:44) [dropwizard-jersey-0.7.1.jar:0.7.1]
! at io.dropwizard.jersey.filter.AllowedMethodsFilter.doFilter(AllowedMethodsFilter.java:39) [dropwizard-jersey-0.7.1.jar:0.7.1]
! at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1486) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.servlet.ServletHandler.doHandle(ServletHandler.java:519) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.ContextHandler.doHandle(ContextHandler.java:1097) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.servlet.ServletHandler.doScope(ServletHandler.java:448) [jetty-servlet-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.ContextHandler.doScope(ContextHandler.java:1031) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.ScopedHandler.handle(ScopedHandler.java:136) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:97) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at com.codahale.metrics.jetty9.InstrumentedHandler.handle(InstrumentedHandler.java:175) [metrics-jetty9-3.0.2.jar:3.0.2]
! at io.dropwizard.jetty.ContextRoutingHandler.handle(ContextRoutingHandler.java:38) [dropwizard-jetty-0.7.1.jar:0.7.1]
! at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:97) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.RequestLogHandler.handle(RequestLogHandler.java:92) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:97) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.StatisticsHandler.handle(StatisticsHandler.java:162) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:97) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.Server.handle(Server.java:446) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.HttpChannel.handle(HttpChannel.java:271) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.server.HttpConnection.onFillable(HttpConnection.java:246) [jetty-server-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.io.AbstractConnection$ReadCallback.run(AbstractConnection.java:358) [jetty-io-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:601) [jetty-util-9.0.7.v20131107.jar:9.0.7.v20131107]
! at org.eclipse.jetty.util.thread.QueuedThreadPool$3.run(QueuedThreadPool.java:532) [jetty-util-9.0.7.v20131107.jar:9.0.7.v20131107]
! at java.lang.Thread.run(Thread.java:745) [na:1.7.0_75]
ERROR [2015-02-26 09:01:37,003] com.sun.jersey.spi.container.ContainerResponse: A message body writer for Java class io.dropwizard.jersey.errors.ErrorMessage, and Java type class io.dropwizard.jersey.errors.ErrorMessage, and MIME media type application/octet-stream was not found.
The registered message body writers compatible with the MIME media type are:
/* ->
  com.sun.jersey.core.impl.provider.entity.FormProvider
  com.sun.jersey.core.impl.provider.entity.MimeMultipartProvider
  com.sun.jersey.core.impl.provider.entity.StringProvider
  com.sun.jersey.core.impl.provider.entity.ByteArrayProvider
  com.sun.jersey.core.impl.provider.entity.FileProvider
  com.sun.jersey.core.impl.provider.entity.InputStreamProvider
  com.sun.jersey.core.impl.provider.entity.DataSourceProvider
  com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider$General
  com.sun.jersey.core.impl.provider.entity.ReaderProvider
  com.sun.jersey.core.impl.provider.entity.DocumentProvider
  com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider
  com.sun.jersey.core.impl.provider.entity.SourceProvider$SourceWriter
  com.sun.jersey.server.impl.template.ViewableMessageBodyWriter
  com.sun.jersey.core.impl.provider.entity.XMLRootElementProvider$General
  com.sun.jersey.core.impl.provider.entity.XMLListElementProvider$General
  com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
application/octet-stream ->
  com.sun.jersey.core.impl.provider.entity.ByteArrayProvider
  com.sun.jersey.core.impl.provider.entity.FileProvider
  com.sun.jersey.core.impl.provider.entity.InputStreamProvider
  com.sun.jersey.core.impl.provider.entity.DataSourceProvider
  com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider
*/

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


		// ### RESOURCES ###
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