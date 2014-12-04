package bg.d3soft.dropwizard.example.auth;

import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import bg.d3soft.dropwizard.example.model.User;


public class OAuth2Authenticator implements Authenticator<String, User> {

	private String HARDCODED_PASSWORD = "secret";


	public Optional<User> authenticate(String credentials) throws AuthenticationException {
		if (HARDCODED_PASSWORD.equals( credentials )) {
			return Optional.of(new User(1, credentials) );
		}
		return Optional.absent();
	}

}