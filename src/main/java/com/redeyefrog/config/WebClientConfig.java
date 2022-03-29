package com.redeyefrog.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${rest.api.url}")
    private String apiUrl;

    @Bean
    public WebClient getWebClient() {

        return WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(getClientConnector())
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(headers -> headers.addAll(getHeaders()))
                .build();
    }

    private ClientHttpConnector getClientConnector() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> {
                    conn.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                })
                .secure(sslContextSpec -> sslContextSpec.sslContext(getSSlContext())) // https
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

        return new ReactorClientHttpConnector(httpClient);
    }

    private SslContext getSSlContext() {
        try {
            return SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (SSLException e) {
            return null;
        }
    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }

}
