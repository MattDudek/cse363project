package com.test.test;

import lombok.Getter;
import lombok.Setter;

public class Error {
    @Getter
    @Setter
    private int error;

    public Error(){
        error = 0;
    }
}
