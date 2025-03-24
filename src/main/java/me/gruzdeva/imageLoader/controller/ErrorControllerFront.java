package me.gruzdeva.imageLoader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControllerFront extends ControllerBase{
    public static final String JSP_ERROR = "error";
    public static final String DEFAULT_URL_ERROR = ROUTE_BASE + JSP_ERROR;

    @GetMapping(DEFAULT_URL_ERROR)
    public String error() {
        return JSP_ERROR;
    }
}
