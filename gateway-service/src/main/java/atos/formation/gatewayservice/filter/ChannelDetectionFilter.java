package atos.formation.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ChannelDetectionFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userAgent = exchange.getRequest().getHeaders().getFirst("User-Agent");
        String channel = detectChannel(userAgent);

        // Add the channel as a new header
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Channel", channel)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private String detectChannel(String userAgent) {
        if (userAgent == null) return "unknown";
        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("mobile")) return "mobile_app";
        if (userAgent.contains("android") || userAgent.contains("ios")) return "mobile_app";
        if (userAgent.contains("postman") || userAgent.contains("curl")) return "API";
        if (userAgent.contains("mozilla") || userAgent.contains("chrome") || userAgent.contains("safari")) return "web";

        return "unknown";
    }

    @Override
    public int getOrder() {
        return -1; // Ensure it runs early
    }
}
