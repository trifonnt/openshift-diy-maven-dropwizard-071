package bg.d3soft.dropwizard.example.model;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import bg.d3soft.dropwizard.example.resources.PersonResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.jersey.server.linking.Ref;
import com.sun.jersey.server.linking.Binding;


//@Data
//@ToString(exclude = {"partner", "mother", "father"})
//@EqualsAndHashCode(exclude = {"partner", "mother", "father"})
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
 
	@NotNull
	private String name;

	private Person father;

	private Person mother;

	@JsonIgnore
	private Person partner;

	@Ref(
		// value = "{id}", <= Default
		resource = PersonResource.class,
		method = "get",
		condition = "${not empty(instance.partner)}",
		bindings = @Binding(name = "id", value = "${instance.partner.id}"))
    private URI partnerLink;

}