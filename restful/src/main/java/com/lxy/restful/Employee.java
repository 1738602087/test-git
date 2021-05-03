package com.lxy.restful;

import lombok.*;

@Setter
@Getter
@ToString
public class Employee {
    private Long id;
    private String name;

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee() {
    }
}

