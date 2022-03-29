package com.redeyefrog.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redeyefrog.enums.StatusCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonHeader implements Serializable {

    @JsonProperty("txn_seq")
    private String txnSeq;

    @JsonProperty("return_code")
    private String returnCode;

    @JsonProperty("return_desc")
    private String returnDesc;

    public void setReturnCodeAndDesc(StatusCode code) {
        this.returnCode = code.getCode();
        this.returnDesc = code.getDesc();
    }

}
