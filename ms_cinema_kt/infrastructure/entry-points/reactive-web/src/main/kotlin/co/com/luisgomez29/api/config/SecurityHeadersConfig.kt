package co.com.luisgomez29.api.config

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class SecurityHeadersConfig : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.path.toString()

        if (path.contains("swagger") || path.contains("api-docs")) {
            return chain.filter(exchange)
        }

        val headers = exchange.response.headers
        headers["Content-Security-Policy"] = CSP
        headers["Strict-Transport-Security"] = "max-age=31536000; includeSubDomains; preload"
        headers["X-Content-Type-Options"] = "nosniff"
        headers.remove("Server")
        headers["Cache-Control"] = "no-store"
        headers["Pragma"] = "no-cache"
        headers["Referrer-Policy"] = "strict-origin-when-cross-origin"
        return chain.filter(exchange)
    }

    companion object {
        private const val CSP = "default-src 'none'; img-src 'self'; script-src 'self'; style-src 'self'; " +
                "object-src 'none'; frame-ancestors 'none'; upgrade-insecure-requests"
    }
}