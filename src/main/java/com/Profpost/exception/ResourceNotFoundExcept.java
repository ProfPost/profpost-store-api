package com.Profpost.exception;

public class ResourceNotFoundExcept extends RuntimeException {
    public ResourceNotFoundExcept() {
        super();
    }
    public ResourceNotFoundExcept(String message) {
        super(message);
    }
}
