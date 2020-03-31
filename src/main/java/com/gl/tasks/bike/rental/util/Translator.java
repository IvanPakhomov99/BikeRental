package com.gl.tasks.bike.rental.util;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public final class Translator {
	
	private static ResourceBundleMessageSource messageSource;
	
	@Autowired
	Translator(ResourceBundleMessageSource messageSource) {
		Translator.messageSource = messageSource;
	}
	
	public static String toLocale(final String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, null, locale);
	}
	
	public static String toLocale(final String msgCode, final List<String> params) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, params.stream().toArray(String[]::new), locale);
	}
}
