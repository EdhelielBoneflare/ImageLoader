package me.gruzdeva.imageLoader.controller;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@ControllerAdvice
public class ErrorControllerApi {
    private static final Logger logger = LoggerFactory.getLogger(ErrorControllerApi.class);

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }


    @ExceptionHandler({Except4SupportDocumented.class})
    public ModelAndView handleSupportException(Except4SupportDocumented ex) {

        ErrorDetailsDto errorDetailsDto= new ErrorDetailsDto(ex.getCodeId(), ex.getErrorCode(),
                "BindException: " + ex.getMessage4Support());
        ApiResponseDto response = new ApiResponseDto(
                ApiResponseDto.STATUS_ERROR,
                errorDetailsDto
        );
        String message = String.format("ID: %s. %s", ex.getCodeId(), ex.getMessage4User());
        logger.error(errorDetailsDto.getErrorMessage());
        return new ModelAndView("error", "message", message);
    }

    @ExceptionHandler({ExceptInfoUser.class})
    public ModelAndView handleUserException(ExceptInfoUser ex) {
        return new ModelAndView("error", "message", ex.getMessage());
    }
}
