import dao.UserDao;
import models.User;

public class Main {
	public static void main(String[] args) {
		User user=new UserDao().findById(1);
		System.out.println(user);
	}
}
