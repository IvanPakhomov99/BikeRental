package com.gl.tasks.bike.rental.exceptions;

import java.util.Arrays;


import com.gl.tasks.bike.rental.util.Translator;

public class BikeNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 2311655521521409639L;

	public BikeNotFoundException(String id) {
		super(Translator.toLocale("bike.not.found.exception", Arrays.asList(id)));
	}
}
