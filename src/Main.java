import dao.NewsDao;

public class Main {
	public static void main(String[] args) {
		new NewsDao().executeSQL("insert into news(id) values(123)");
	}
}
