package com.proana.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ProanaUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date parseDateSql(String fecha) {
	    if (fecha == null || fecha.isEmpty()) return null;
	    return Date.valueOf(fecha); // formato yyyy-MM-dd
	}

	public static String formatDate(java.util.Date date) {
        return date != null ? DATE_FORMAT.format(date) : null;
    }
	
}
