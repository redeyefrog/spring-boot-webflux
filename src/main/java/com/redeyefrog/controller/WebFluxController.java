package com.redeyefrog.controller;

import com.redeyefrog.dto.company.FindCompanyRq;
import com.redeyefrog.dto.company.FindCompanyRs;
import com.redeyefrog.model.Person;
import com.redeyefrog.service.WebFluxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class WebFluxController {

    @Autowired
    private WebFluxService service;

    @PostMapping("/findPerson")
    public Flux<Person> findPerson(@RequestBody String id) {

        return service.findPerson(id);
    }

    @PostMapping("findCompany")
    public Mono<FindCompanyRs> findCompany(@RequestBody FindCompanyRq rq) {

        return service.findCompany(rq);
    }

}
