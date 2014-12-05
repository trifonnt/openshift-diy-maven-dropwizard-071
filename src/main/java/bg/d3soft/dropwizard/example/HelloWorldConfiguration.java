package bg.d3soft.dropwizard.example;

import io.dropwizard.Configuration;

//import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HelloWorldConfiguration extends Configuration {

	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";

	private String authenticationCachePolicy = "";


//	@Valid
//	@NotNull
//	private DataSourceFactory database = new DataSourceFactory();


	@JsonProperty
	public String getTemplate() {
		return template;
	}
	@JsonProperty
	public void setTemplate(String newTemplate) {
		template = newTemplate;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}
	@JsonProperty
	public void setDefaultName(String newDefaultName) {
		defaultName = newDefaultName;
	}

	@JsonProperty
	public String getAuthenticationCachePolicy() {
		return authenticationCachePolicy;
	}

//	public Template buildTemplate() {
//		return new Template(template, defaultName);
//	}

//	@JsonProperty("database")
//	public DataSourceFactory getDataSourceFactory() {
//		return database;
//	}
//	@JsonProperty("database")
//	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
//		this.database = dataSourceFactory;
//	}
}