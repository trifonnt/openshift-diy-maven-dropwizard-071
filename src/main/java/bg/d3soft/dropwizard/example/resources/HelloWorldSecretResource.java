package bg.d3soft.dropwizard.example.resources;

import io.dropwizard.auth.Auth;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import bg.d3soft.dropwizard.example.model.Saying;
import bg.d3soft.dropwizard.example.model.User;


@Path("/hello-world3")
@Api("/hello-world3")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldSecretResource {

	private final String template;
	private final String defaultName;
	private final AtomicLong counter;


	public HelloWorldSecretResource(String newTemplate, String newDefaultName) {
		template = newTemplate;
		defaultName = newDefaultName;
		counter = new AtomicLong();
	}

	@GET
	@ApiOperation("Sample SECRET endpoint")
	@Timed
	public Response getSecretHello(@Auth(required = true) User user) {
//	public Saying getSecretHello(@Auth(required = true) User user) {
		final String value = String.format(template, user == null ? defaultName : user.getName() );
		//return new Saying(counter.incrementAndGet(), value);
		return Response.ok(new Saying(counter.incrementAndGet(), value)).build();
	}
}