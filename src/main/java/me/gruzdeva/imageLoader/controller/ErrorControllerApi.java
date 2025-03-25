package me.gruzdeva.imageLoader.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.gruzdeva.Except;
import me.gruzdeva.Except4Support;
import me.gruzdeva.Except4SupportDocumented;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerApi {
    private static final Logger logger = LoggerFactory.getLogger(ErrorControllerApi.class);

    @ExceptionHandler({Except4Support.class, Except4SupportDocumented.class})
    public ModelAndView handleSupportException(HttpServletRequest req, Except ex) {

        ErrorDetailsDto errorDetailsDto= new ErrorDetailsDto(ex.getCodeId(), ex.getErrorCode(),
                "BindException: " + ex.getMessage4Support());
        logger.error(errorDetailsDto.getErrorMessage());
        ModelAndView modelAndView = new ModelAndView("redirect:" + ErrorControllerFront.DEFAULT_URL_ERROR);
        modelAndView.addObject(ControllerBase.PARAMETER_URL, req.getRequestURL());
        modelAndView.addObject(ControllerBase.PARAMETER_ERROR_MESSAGE, ex.getMessage4User());
        return modelAndView;
    }
}
