package com.redeyefrog.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Company implements Serializable {

    private String uid;

    private String name;

    private String address;

    private String tel;

}
