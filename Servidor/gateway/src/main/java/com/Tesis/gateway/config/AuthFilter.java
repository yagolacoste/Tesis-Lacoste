package com.Tesis.gateway.config;

//import org.graalvm.compiler.core.common.CompilationRequestIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{


    private WebClient.Builder webClient;

    @Autowired
    private AuthFeign authFeign;


    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String [] chunks = tokenHeader.split(" ");
            if(chunks.length != 2 || !chunks[0].equals("Bearer"))
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            RefreshTokenDto refreshTokenDto=authFeign.validate(chunks[1]);
            if(refreshTokenDto==null){
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }else
//            String s=result.getStatusCode().toString();
                    return webClient.build()
                            .post()
                            .uri("http://auth-service/auth/validate?token=" + chunks[1])
                            .retrieve().bodyToMono(RefreshTokenDto.class)
                            .map(t -> {
                                t.getToken();
//                                if(exchange.getResponse().getStatusCode()==HttpStatus.BAD_REQUEST)
//                                     return onError(exchange, HttpStatus.UNAUTHORIZED);
                                return exchange;
                            }).flatMap(chain::filter);
        }));

    }


        public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
////
////        if (authHeader != null && authHeader.startsWith("Bearer ")) {
////            String authToken = authHeader.substring(7);
//        if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
//                return onError(exchange, HttpStatus.BAD_REQUEST);
//            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            String [] chunks = tokenHeader.split(" ");
//            if(chunks.length != 2 || !chunks[0].equals("Bearer"))
//                return onError(exchange, HttpStatus.BAD_REQUEST);
//            if( webClient.build()
//                .post()
//                .uri("http://auth-service/auth/validate?token=" + chunks[1])
//                .retrieve().bodyToMono(TokenDto.class)
//                .map(t -> {
//                    return t.getToken();
//                }).equals(null))
//            try {
//                return chain.filter(exchange);
//
//                // Validar el token de autenticación
//                // Si el token es válido, establecer el atributo 'user' en el intercambio
//                // Si el token no es válido, regresar una respuesta de error
//            } catch (Exception e) {
//                // Manejar cualquier excepción que ocurra durante la validación del token
//            }
//         else {
//            return onError(exchange,  HttpStatus.BAD_REQUEST);
//        }
//
//        return chain.filter(exchange);
//    }

    public static class Config {}
}
