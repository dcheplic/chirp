import static spark.Spark.*;

import org.apache.log4j.BasicConfigurator;

public class App {
	public static void main(String[] args) {
		//port(80);
		BasicConfigurator.configure();
		after((req, res) -> res.type("application/json"));
	}
}
