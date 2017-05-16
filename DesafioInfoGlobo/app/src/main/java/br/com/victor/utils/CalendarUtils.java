package br.com.victor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils {

	public static String formatLongToString(Long valor){
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy',' HH:mm", new Locale("pt", "BR"));
		return sdf.format(valor);
	}

	public static Date formatStringToDate (String data, String formato)  {
		return formatStringToDate (data, formato, null);
	}

	public static Date formatStringToDate (String data, String formato, Locale locate)  {
		SimpleDateFormat sdf;
		if ( locate != null )
			sdf = new SimpleDateFormat(formato, locate);
		else
			sdf = new SimpleDateFormat(formato);

		Date date = null;
		try {
			date = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date formatStringToDate (String data)  {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date formatStringCompleteToDate (String data)  {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			date = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String formatLongToString(Long valor, String formato){
		SimpleDateFormat sdf = new SimpleDateFormat(formato, new Locale("pt", "BR"));
		return sdf.format(valor);
	}

	public static String formatDateToString(Date data, String format)  {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(data);
	}

}