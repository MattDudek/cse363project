package com.test.test;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Search {
    @Getter
    @Setter
    private String str;

    @Override
    public String toString() {
        return str;
    }
    public Search(){

    }
}
