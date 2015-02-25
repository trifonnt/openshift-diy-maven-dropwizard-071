package bg.d3soft.dropwizard.example.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import bg.d3soft.dropwizard.example.model.Saying;


@Path("/hello-world2")
@Api("/hello-world2")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResourcePathParam {

	private final String template;
	private final String defaultName;
	private final AtomicLong counter;


	public HelloWorldResourcePathParam(String newTemplate, String newDefaultName) {
		template = newTemplate;
		defaultName = newDefaultName;
		counter = new AtomicLong();
	}

//   JAX-RS @PathParam example
// - http://www.mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/
	@GET
	@ApiOperation("Sample endpoint with path param")
	@Path("{name}")
	@Timed
	public Response sayHello(@PathParam("name") String name) {
//	public Saying sayHello(@PathParam("name") String name) {
		final String value = String.format(template, name == null ? defaultName : name);
		//return new Saying(counter.incrementAndGet(), value);
		return Response.ok(new Saying(counter.incrementAndGet(), value)).build();
	}
	// Original - !!! NOT working !!!
//	public Saying sayHello(@PathParam("name") Optional<String> name) {
//		final String value = String.format(template, name.or(defaultName));
//		return new Saying(counter.incrementAndGet(), value);
//	}
//	@GET
//	@Path("{id}")
//	public Response getUserById(@PathParam("id") String id) {
//		return Response.status(200).entity("getUserById is called, id : " + id).build();
//	}

//	@GET
//	public Saying getSecretHello(@Auth User user) {
//		final String value = String.format(template, user.getName() );

//		return new Saying(counter.incrementAndGet(), value);
//	}
}