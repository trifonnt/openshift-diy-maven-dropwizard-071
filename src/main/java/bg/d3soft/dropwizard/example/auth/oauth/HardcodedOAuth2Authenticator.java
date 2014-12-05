package bg.d3soft.dropwizard.example.auth.oauth;

import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import bg.d3soft.dropwizard.example.model.User;


public class HardcodedOAuth2Authenticator implements Authenticator<String, User> {
	
	private String HARDCODED_PASSWORD = "secret";


	public Optional<User> authenticate(String credentials) throws AuthenticationException {
		if (HARDCODED_PASSWORD.equals( credentials )) {
			return Optional.of(new User(1, credentials) );
		}
		return Optional.absent();
	}

}