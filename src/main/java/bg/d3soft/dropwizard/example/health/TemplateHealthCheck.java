package bg.d3soft.dropwizard.example.health;

import com.codahale.metrics.health.HealthCheck;
//import com.google.common.base.Optional;
//import d3soft.dropwizard.example.core.Template;


public class TemplateHealthCheck extends HealthCheck {

//	private final Template template;
	private final String templateStr;


//	public TemplateHealthCheck(Template newTemplate) {
//		template = newTemplate;
//	}
	public TemplateHealthCheck(String newTemplateStr) {
		templateStr = newTemplateStr;
	}

//	@Override
//	protected Result check() throws Exception {
//		template.render(Optional.of("woo"));
//		template.render(Optional.<String>absent());
//		return Result.healthy();
//	}
	
	@Override
	protected Result check() throws Exception {
		final String saying = String.format(templateStr, "TEST");
		if (!saying.contains("TEST")) {
			return Result.unhealthy("template doesn't include a name");
		}
		return Result.healthy();
	}
}