# spring-boot-webflux

Logging Spring WebClient Calls
-
* Spring Boot 2.2.6
```java
TcpClient tcpClient = TcpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .doOnConnected(conn -> {
            conn.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        });

HttpClient httpClient = HttpClient.from(tcpClient)
        .tcpConfiguration(tcp -> tcp.bootstrap(bootstrap -> BootstrapHandlers.updateLogSupport(bootstrap, new CustomLoggingHandler(HttpClient.class))));

WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
```

```java
public class CustomLoggingHandler extends LoggingHandler {

    public CustomLoggingHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) arg;
            
            return msg.toString(StandardCharsets.UTF_8);
        }

        return super.format(ctx, eventName, arg);
    }

}
```

* Spring Boot 2.4.0
```java
HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn -> {
            conn.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        })
        .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
```

Reference
--
* [Logging Spring WebClient Calls](https://www.baeldung.com/spring-log-webclient-calls "Logging Spring WebClient Calls")
* [how to log Spring 5 WebClient call](https://stackoverflow.com/questions/46154994/how-to-log-spring-5-webclient-call "how to log Spring 5 WebClient call")
