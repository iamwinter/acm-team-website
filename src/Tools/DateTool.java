package Tools;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateTool {
	private static String defaultFormat="yyyy/MM/dd HH:mm:ss";

	// util.Date ==> String
	public static String dateToStr(java.util.Date date){
		return dateToStr(date,defaultFormat);
	}
	public static String dateToStr(java.util.Date date, String strFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(strFormat);
		String str = sf.format(date);
		return str;
	}

	// String转util.Date
	public static java.util.Date strToUtilDate(String strDate){
		return strToUtilDate(strDate,defaultFormat);
	}
	public static java.util.Date strToUtilDate(String strDate, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		java.util.Date date = null;
		try {
			date = sf.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("String ==> java.util.Date 失败"+dateFormat);
		}
		return date;
	}

	// sql.Timestamp ==> String
	public static String dateToStr(java.sql.Timestamp date){
		return dateToStr(date,defaultFormat);
	}
	public static String dateToStr(java.sql.Timestamp time, String strFormat) {
		DateFormat df = new SimpleDateFormat(strFormat);
		String str = df.format(time);
		return str;
	}

	// String转sql.Timestamp
	public static java.sql.Timestamp strToSqlDate(String strDate){
		return strToSqlDate(strDate,defaultFormat);
	}
	public static java.sql.Timestamp strToSqlDate(String strDate, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		java.util.Date date = null;
		try {
			date = sf.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
		return dateSQL;
	}
}
