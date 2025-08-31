package com.proana.utils;

import java.util.Date;

/**
 * Clase utilitaria para realizar conversiones seguras entre tipos
 * de datos comunes. Proporciona métodos estáticos para convertir
 * objetos a tipos específicos como {@link Integer}, {@link Long},
 * {@link Double}, {@link String} y {@link Date}.
 */
public final class TypeConverter {

    private TypeConverter() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Convierte un objeto a {@link Integer} si es posible.
     *
     * @param obj el objeto a convertir
     * @return el valor como {@link Integer} o {@code null}
     * si el objeto es {@code null}
     * @throws IllegalArgumentException si el objeto
     * no puede convertirse a {@link Integer}
     */
    public static Integer toInteger(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof String) {
            return Integer.valueOf((String) obj);
        }
        throw new IllegalArgumentException(
                "No se puede convertir a Integer: " + obj);
    }

    /**
     * Convierte un objeto a {@link Long} si es posible.
     *
     * @param obj el objeto a convertir
     * @return el valor como {@link Long} o
     * {@code null} si el objeto es {@code null}
     * @throws IllegalArgumentException si el
     * objeto no puede convertirse a {@link Long}
     */
    public static Long toLong(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (obj instanceof String) {
            return Long.valueOf((String) obj);
        }
        throw new IllegalArgumentException(
                "No se puede convertir a Long: " + obj);
    }

    /**
     * Convierte un objeto a {@link Double} si es posible.
     *
     * @param obj el objeto a convertir
     * @return el valor como {@link Double} o
     * {@code null} si el objeto es {@code null}
     * @throws IllegalArgumentException si el objeto
     * no puede convertirse a {@link Double}
     */
    public static Double toDouble(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof String) {
            return Double.valueOf((String) obj);
        }
        throw new IllegalArgumentException(
                "No se puede convertir a Double: " + obj);
    }

    /**
     * Convierte un objeto a {@link String}.
     *
     * @param obj el objeto a convertir
     * @return el valor como {@link String}
     * o {@code null} si el objeto es {@code null}
     */
    public static String toString(final Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return null;
        }
    }

    /**
     * Convierte un objeto a {@link Date} si es posible.
     * Acepta instancias de {@link java.util.Date}, {@link java.sql.Date}
     * o {@link String} en formato "yyyy-MM-dd".
     *
     * @param obj el objeto a convertir
     * @return una instancia de {@link Date} o {@code null} si el objeto es
     * {@code null}
     * @throws IllegalArgumentException si el objeto no puede convertirse
     * a {@link Date}
     *                                  o el formato de la cadena es inválido
     */
    public static Date toDate(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof java.sql.Date) {
            return new Date(((java.sql.Date) obj).getTime());
        }
        if (obj instanceof java.time.LocalDate) {
            return java.sql.Date.valueOf((java.time.LocalDate) obj);
        }
        if (obj instanceof String) {
            try {
                // formato "yyyy-MM-dd"
                return java.sql.Date.valueOf((String) obj);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                    "Formato de fecha inválido: " + obj);
            }
        }
        throw new IllegalArgumentException("No se puede convertir a Date: " + obj.getClass());
    }

    
    public static String toStringSafe(Object obj) {
        if (obj == null) return null;
        return obj.toString();
    }

    public static Boolean toBoolean(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        }
        if (obj instanceof String) {
            String s = ((String) obj).toLowerCase();
            if ("true".equals(s) || "1".equals(s)) {
                return true;
            } else if ("false".equals(s) || "0".equals(s)) {
                return false;
            }
            throw new IllegalArgumentException("No se puede convertir a Boolean: " + obj);
        }
        throw new IllegalArgumentException("No se puede convertir a Boolean: " + obj);
    }

}
