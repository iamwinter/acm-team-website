package Tools;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class MyConfig {
	private static ResourceBundle rb = ResourceBundle.getBundle("config");
	public static String get(String key){
		try {
			return new String(rb.getString(key).getBytes("ISO-8859-1"),"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "未知";
		}
	}
}
