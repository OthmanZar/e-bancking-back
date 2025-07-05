package atos.formation.gatewayservice.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Component
public class AddClientIpHeaderFilter implements GlobalFilter, Ordered {


    private String getClientIp(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String ipFromHeader = headers.getFirst("X-Forwarded-For");
        if (ipFromHeader != null && !ipFromHeader.isEmpty()) {
            return ipFromHeader.split(",")[0]; // first IP in the chain
        }

        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress().getHostAddress();
        }

        return "UNKNOWN";
    }

    @Override
    public int getOrder() {
        return -1; // Ensure this runs early
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String clientIp = getClientIp(exchange);

        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-Real-IP", clientIp)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
}
