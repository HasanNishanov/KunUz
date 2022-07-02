package com.company.exps;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String massage) {
        super(massage);
    }
}
