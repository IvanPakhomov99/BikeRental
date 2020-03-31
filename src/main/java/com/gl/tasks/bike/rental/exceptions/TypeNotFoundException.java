package com.gl.tasks.bike.rental.exceptions;

import java.util.Arrays;

import com.gl.tasks.bike.rental.util.Translator;

public class TypeNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5401415645713326787L;

	public TypeNotFoundException(String name) {
		super(Translator.toLocale("type.not.found.exception", Arrays.asList(name)));
	}
}
