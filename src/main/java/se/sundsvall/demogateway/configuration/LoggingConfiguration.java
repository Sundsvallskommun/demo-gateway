package se.sundsvall.demogateway.configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class LoggingConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConfiguration.class);
	private static final String X_REQUEST_ID = "x-request-id";

	@Bean
	public GlobalFilter customLoggingFilter() {
		return (exchange, chain) -> {
			var requestId = Optional.of(exchange.getRequest().getHeaders())
				.map(headers -> headers.get(X_REQUEST_ID))
				.map(List::getFirst)
				.orElse(UUID.randomUUID().toString());
			LOGGER.info("""
				Incoming Request: {}
				HTTP Method: {}
				Request URI: {}
				""", requestId, exchange.getRequest().getMethod(), exchange.getRequest().getURI());
			return chain.filter(exchange)
				.then(Mono.fromRunnable(() -> LOGGER.info("""
					Response for Request: {}
					Status Code: {}
					""", requestId, exchange.getResponse().getStatusCode())));
		};
	}
}
