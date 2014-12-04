package bg.d3soft.dropwizard.example.health;

import com.codahale.metrics.health.HealthCheck;


public class DatabaseHealthCheck extends HealthCheck {

//	private final Database database;


//	public DatabaseHealthCheck(Database newDatabase) {
//		database = newDatabase;
//	}

	@Override
	protected Result check() throws Exception {
//		if (database.isConnected()) {
		if ( true ) {
			return Result.healthy();
		} else {
//			return Result.unhealthy("Cannot connect to " + database.getUrl());
			return Result.unhealthy("Cannot connect to database!");
		}
	}
}