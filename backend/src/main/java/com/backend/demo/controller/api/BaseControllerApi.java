package com.backend.demo.controller.api;

public abstract class BaseControllerApi<S> {
    private final S service;
    public BaseControllerApi(S service) {
        super();
        this.service = service;
    }
    public S getService() {
        return service;
    }
}