package com.redeyefrog.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CommonRs implements Serializable {

    @JsonProperty("RED_HEADER")
    private CommonHeader header;

}
