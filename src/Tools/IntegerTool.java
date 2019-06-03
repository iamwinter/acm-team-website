package Tools;

public class IntegerTool {
	public static int strToInt(String num,int defaultNum){
		// 字符串转整数，异常则返回defaultNum
		try{
			return Integer.valueOf(num);
		}catch (Exception e){
			return defaultNum;
		}
	}
	public static int strToInt(String num){
		// 字符串转整数
		return strToInt(num,0);
	}

}
