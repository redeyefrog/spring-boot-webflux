package com.redeyefrog.service;

import com.redeyefrog.dto.company.FindCompanyRq;
import com.redeyefrog.dto.company.FindCompanyRs;
import com.redeyefrog.dto.common.CommonHeader;
import com.redeyefrog.enums.StatusCode;
import com.redeyefrog.exception.WebFluxRuntimeException;
import com.redeyefrog.model.Company;
import com.redeyefrog.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
public class WebFluxService {

    @Autowired
    private WebClient webClient;

    public Flux<Person> findPerson(String id) {
        Person person = new Person();

        person.setId("A123456789");

        person.setName("王XX");

        person.setAge(20);

//        return Mono.just(Arrays.asList(person));

        return Flux.just(person, person);
    }

    public Mono<FindCompanyRs> findCompany(FindCompanyRq rq) {
        CommonHeader header = rq.getHeader();

        return webClient
                .post()
                .uri("/mock/findCompany")
                .bodyValue(rq)
                .exchangeToMono(response -> {
                    if (response.statusCode().isError()) {
                        String code = StatusCode.CLIENT_OR_SERVER_ERROR.getCode();
                        String desc = MessageFormat.format(StatusCode.CLIENT_OR_SERVER_ERROR.getDesc(), response.rawStatusCode());
                        throw WebFluxRuntimeException.builder().code(code).desc(desc).header(header).build();
                    }

                    return response.bodyToMono(new ParameterizedTypeReference<List<Company>>() {}).map(companies -> {
                        header.setReturnCodeAndDesc(StatusCode.SUCCESS);

                        FindCompanyRs rs = new FindCompanyRs();
                        rs.setHeader(header);
                        rs.setCompanies(companies);

                        return rs;
                    });
                });
//                .doOnError(error -> {
//                    log.error("doOnError!!!");
//                });
//                .onErrorResume(err -> {
//                    log.error("onErrorResume!!!");
//                    return null;
//                });
    }

}
