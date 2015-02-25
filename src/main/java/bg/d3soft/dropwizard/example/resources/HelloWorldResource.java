package bg.d3soft.dropwizard.example.resources;

import io.dropwizard.jersey.caching.CacheControl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bg.d3soft.dropwizard.example.model.Saying;


@Path("/hello-world")
@Api("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

	private final String template;
	private final String defaultName;
	private final AtomicLong counter;


	public HelloWorldResource(String newTemplate, String newDefaultName) {
		template = newTemplate;
		defaultName = newDefaultName;
		counter = new AtomicLong();
	}

	@GET
	@ApiOperation("Sample endpoint")
	@Timed(name = "get-requests")
	@CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
	public Response sayHello(@QueryParam("name") Optional<String> name) {
//	public Saying sayHello(@QueryParam("name") Optional<String> name) {
		final String value = String.format(template, name.or(defaultName));
//		return new Saying(counter.incrementAndGet(), value);
//		return new Saying(counter.incrementAndGet(), template.render(name));
		return Response.ok(new Saying(counter.incrementAndGet(), value)).build();
	}
	
	@POST
	@ApiOperation("Sample POST endpoint")
	public void receiveHello(@Valid Saying saying) {
		LOGGER.info("Received a saying: {}", saying);
	}
	
//	@GET
//	@Timed
//	public Saying sayHello(@PathParam("name") Optional<String> name) {
//		final String value = String.format(template, name.or(defaultName));
//		return new Saying(counter.incrementAndGet(), value);
//	}

//	@GET
//	public Saying getSecretHello(@Auth User user) {
//		final String value = String.format(template, user.getName() );

//		return new Saying(counter.incrementAndGet(), value);
//	}
}