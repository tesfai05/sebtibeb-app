package com.tesfai.sebtibeb.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<LocalDate> {

	@Override
	public String print(LocalDate date, Locale locale) {		
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		return f.format(date);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		LocalDate localDate = null;
		String[] date = text.split("/");
		if (text.indexOf('/') == -1 || date.length != 3) {
			return null;
		} else {
			int y = Integer.valueOf(date[0]);
			int m = Integer.valueOf(date[1]);
			int d = Integer.valueOf(date[2]);
			localDate = LocalDate.of(y, m, d);
		}
		return localDate;
	}

}
