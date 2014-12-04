package bg.d3soft.dropwizard.example.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import bg.d3soft.dropwizard.example.model.Saying;


@Path("/hello-world2")
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
	@Path("{name}")
	@Timed
	public Saying sayHello(@PathParam("name") String name) {
		final String value = String.format(template, name == null ? defaultName : name);
		return new Saying(counter.incrementAndGet(), value);
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