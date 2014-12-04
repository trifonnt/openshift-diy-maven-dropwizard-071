package bg.d3soft.dropwizard.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
//import com.google.common.base.MoreObjects;


public class Saying {

	private long id;

	@Length(max = 3)
	private String content;

	
	public Saying() {
		// Jackson deserialization
	}

	public Saying(long newId, String newContent) {
		id = newId;
		content = newContent;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this)
//			.add("id", id)
//			.add("content", content)
//			.toString();
//	}
}