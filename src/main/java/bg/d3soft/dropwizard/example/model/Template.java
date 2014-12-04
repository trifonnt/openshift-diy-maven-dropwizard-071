package bg.d3soft.dropwizard.example.model;

import com.google.common.base.Optional;
import static java.lang.String.format;


public class Template {
	private final String content;
	private final String defaultName;

	public Template(String newContent, String newDefaultName) {
		content = newContent;
		defaultName = newDefaultName;
	}

	public String render(Optional<String> name) {
		return format(content, name.or(defaultName));
	}
}