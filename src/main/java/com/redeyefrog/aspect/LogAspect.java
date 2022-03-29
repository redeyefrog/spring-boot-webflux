package com.redeyefrog.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redeyefrog.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.redeyefrog.controller.*.*(..))")
    public void controllerLog() {

    }

    /**
     * Around
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "controllerLog()")
    public Object doControllerAround(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();

        log.debug("Controller: {}, Method: {}", signature.getDeclaringTypeName(), signature.getName());

        log.debug("Args: {}", JsonUtils.toJson(point.getArgs()));

        Object obj = point.proceed(point.getArgs());

        if (obj instanceof Mono) {
            Mono mono = (Mono) obj;

            return mono.map(body -> {
                log.debug("Return Mono Body: {}", toJson(body));

                return body;
            });
        } else if (obj instanceof Flux) {
            Flux flux = (Flux) obj;

            return flux.doOnNext(body -> log.debug("Return Flux Body: {}", toJson(body)));
        }

        log.debug("Return Object: {}", JsonUtils.toJson(obj));

        return obj;
    }

    private String toJson(Object obj) {
        String result = null;

        try {
            result = JsonUtils.toJson(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

}
