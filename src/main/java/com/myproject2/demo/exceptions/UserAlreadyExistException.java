package com.myproject2.demo.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String s) {
        super(s);
    }

    public UserAlreadyExistException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UserAlreadyExistException(Throwable throwable) {
        super(throwable);
    }

    protected UserAlreadyExistException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
