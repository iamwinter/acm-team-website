import dao.UserDao;
import models.User;

import java.util.Calendar;

public class Main {
	public static void main(String[] args) {
//		User user=new UserDao().findById(1);
		System.out.println(Calendar.getInstance().get(Calendar.YEAR));
	}
}
