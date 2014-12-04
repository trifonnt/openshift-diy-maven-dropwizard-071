package bg.d3soft.dropwizard.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import com.google.common.base.Optional;

import bg.d3soft.dropwizard.example.model.User;


public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

	private String HARDCODED_PASSWORD = "secret";

//	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (HARDCODED_PASSWORD.equals(credentials.getPassword())) {
			return Optional.of(new User(1, credentials.getUsername()));
		}
		return Optional.absent();
	}
}