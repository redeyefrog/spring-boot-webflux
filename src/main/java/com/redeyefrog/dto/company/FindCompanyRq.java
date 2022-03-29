package com.redeyefrog.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redeyefrog.dto.common.CommonRq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FindCompanyRq extends CommonRq {

    @JsonProperty("company_uid")
    private String uid;

    @JsonProperty("company_name")
    private String name;

}
