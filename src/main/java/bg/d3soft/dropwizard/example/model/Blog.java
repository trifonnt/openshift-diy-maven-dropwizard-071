package bg.d3soft.dropwizard.example.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


public class Blog {

	private String id = UUID.randomUUID().toString();

	@NotBlank
	private String title;

	@URL
	@NotBlank
	private String url;

	private final Date publishedOn = new Date();

	
	public Blog() {
		// Jackson deserialization
	}

	public Blog(String newTitle, String newUrl) {
		title = newTitle;
		url = newUrl;
	}

	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}
}