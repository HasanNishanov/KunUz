package com.company.exps;

public class AlreadyExistPhone extends RuntimeException {
    public AlreadyExistPhone(String massage) {
        super(massage);
    }
}
