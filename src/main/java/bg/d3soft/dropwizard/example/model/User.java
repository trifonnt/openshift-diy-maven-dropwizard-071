package bg.d3soft.dropwizard.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private long id;

	private String name;


	public User() {
		// Jackson deserialization
	}
	public User(long newId, String newName) {
		id = newId;
		name = newName;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}
}