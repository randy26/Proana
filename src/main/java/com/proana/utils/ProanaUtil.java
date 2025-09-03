package com.proana.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProanaUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	
	public static Date parseDateSql(String fecha) {
	    if (fecha == null || fecha.isEmpty()) return null;
	    return Date.valueOf(fecha); // formato yyyy-MM-dd
	}

	public static String formatDate(java.util.Date date) {
        return date != null ? DATE_FORMAT.format(date) : null;
    }
	
	public static LocalDate parseLocalDate(String fecha) {
	    if (fecha == null || fecha.isEmpty()) return null;
	    return LocalDate.parse(fecha); // acepta yyyy-MM-dd
	}
	
	  public static String formatLocalDatetoString(LocalDate fecha) {
	        return fecha != null ? fecha.format(FORMATTER) : null;
	    }
}
