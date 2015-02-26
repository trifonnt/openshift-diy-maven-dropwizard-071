package bg.d3soft.dropwizard.example;

import io.dropwizard.Configuration;

//import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HelloWorldConfiguration extends Configuration {

	@NotEmpty
	@JsonProperty
	private String template;

	@NotEmpty
	@JsonProperty
	private String defaultName = "Stranger";

	@JsonProperty
	private String authenticationCachePolicy = "";


//	@Valid
//	@NotNull
//	@JsonProperty("database")
//	private DataSourceFactory database = new DataSourceFactory();


	public String getTemplate() {
		return template;
	}
	public void setTemplate(String newTemplate) {
		template = newTemplate;
	}

	public String getDefaultName() {
		return defaultName;
	}
	public void setDefaultName(String newDefaultName) {
		defaultName = newDefaultName;
	}

	public String getAuthenticationCachePolicy() {
		return authenticationCachePolicy;
	}

//	public Template buildTemplate() {
//		return new Template(template, defaultName);
//	}

//	public DataSourceFactory getDataSourceFactory() {
//		return database;
//	}
//	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
//		this.database = dataSourceFactory;
//	}
}