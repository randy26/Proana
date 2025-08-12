package com.proana.utils;

import java.sql.Date;

public class ProanaUtil {
	
	public static Date parseDateSql(String fecha) {
	    if (fecha == null || fecha.isEmpty()) return null;
	    return Date.valueOf(fecha); // formato yyyy-MM-dd
	}

}
