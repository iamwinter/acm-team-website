package Tools;

public class IntegerTool {
	public static Integer strToInt(String num,Integer defaultNum){
		// 字符串转整数，异常则返回defaultNum
		try{
			return Integer.valueOf(num);
		}catch (Exception e){
			return defaultNum;
		}
	}
	public static Integer strToInt(String num){
		// 字符串转整数
		return strToInt(num,null);
	}

}
