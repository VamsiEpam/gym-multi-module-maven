package com.epam.gatewayserver.filter;

import com.epam.gatewayserver.customexception.MissingAuthorizationException;
import com.epam.gatewayserver.customexception.UnAuthorizedException;
import com.epam.gatewayserver.helper.StringConstants;
import com.epam.gatewayserver.proxy.WebFluxAuthenticationProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class AuthenticationGateWayFilter extends AbstractGatewayFilterFactory<AuthenticationGateWayFilter.Config> {


    private final RouteValidator routeValidator;
    private final WebFluxAuthenticationProxy authenticationProxy;

    public static class Config {

    }

    public AuthenticationGateWayFilter(RouteValidator routeValidator, WebFluxAuthenticationProxy authenticationProxy) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.authenticationProxy = authenticationProxy;
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"apply",this.getClass().getName());
        GatewayFilter gatewayFilter =  ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                log.info("it is not one of the bypass endpoints.");
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.info("User missing Authorization headers.");
                    throw new MissingAuthorizationException("Missing Authorization Header");
                }
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    log.info("Fetching headers from given request to validate");
                    authHeader = authHeader.substring(7);
                }
                try {
                    log.info("try block");
                    authenticationProxy.validateToken(authHeader);

                }catch (Exception exception) {
                    log.info("exception class : {}",exception.getClass().getName());
                    throw new UnAuthorizedException("Un Authorized access to Application");
                }
            }
            return chain.filter(exchange);
        });
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"apply");
        return gatewayFilter;
    }
}
