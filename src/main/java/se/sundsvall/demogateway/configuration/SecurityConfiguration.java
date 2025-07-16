package se.sundsvall.demogateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	/**
	 * Configures the security filter chain for the application. This configuration allows all exchanges without any authentication or authorization.
	 *
	 * @param http the ServerHttpSecurity instance to configure
	 * @return the SecurityWebFilterChain instance
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
		http.authorizeExchange(exchange -> exchange.anyExchange().permitAll());
		return http.build();
	}
}


