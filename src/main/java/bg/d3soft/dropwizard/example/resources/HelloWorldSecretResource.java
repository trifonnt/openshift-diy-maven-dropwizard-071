package bg.d3soft.dropwizard.example.resources;

import io.dropwizard.auth.Auth;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import bg.d3soft.dropwizard.example.model.Saying;
import bg.d3soft.dropwizard.example.model.User;


@Path("/hello-world3")
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
	@Timed
	public Saying getSecretHello(@Auth(required = true) User user) {
		final String value = String.format(template, user == null ? defaultName : user.getName() );
		return new Saying(counter.incrementAndGet(), value);
	}
}