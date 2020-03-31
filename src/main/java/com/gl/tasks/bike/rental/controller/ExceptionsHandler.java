package com.gl.tasks.bike.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gl.tasks.bike.rental.exceptions.BikeNotFoundException;
import com.gl.tasks.bike.rental.exceptions.TypeNotFoundException;
import com.gl.tasks.bike.rental.util.Translator;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

	@ExceptionHandler(value = BikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBikeNotFound(BikeNotFoundException ex) {
		log.debug(Translator.toLocale("bike.not.found.exception.handler"));
	}
	
	@ExceptionHandler(value = TypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleBikeNotFound(TypeNotFoundException ex) {
		log.debug(Translator.toLocale("type.not.found.exception.handler"));
    }
}
