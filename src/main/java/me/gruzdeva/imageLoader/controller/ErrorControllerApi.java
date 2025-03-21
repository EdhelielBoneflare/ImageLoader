package me.gruzdeva.imageLoader.controller;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponseDto> handleSupportException(Except4SupportDocumented ex) {
        String id = generateUniqueId();
        String message = String.format("ID: %s. %s", id, ex.getMessage4Support());
        logger.error(message);
        ApiResponseDto response = new ApiResponseDto(
                ApiResponseDto.STATUS_ERROR,
                new ErrorDetailsDto(ex.getCodeId(), ex.getErrorCode(), "BindException: " + ex.getMessage4Support())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({ExceptInfoUser.class})
    public ModelAndView handleUserException(ExceptInfoUser ex) {
        return new ModelAndView("error", "message", ex.getMessage());
    }
}
