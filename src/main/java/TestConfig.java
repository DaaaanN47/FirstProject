import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("E:\\MaksimProject\\src\\main\\resources\\config.properties");

        Properties properties = new Properties();

        properties.load(fileInputStream);

        String mot = "motorway";
        String m = properties.getProperty(mot);
        int motorway = Integer.parseInt(m);
        System.out.println(motorway);
    }
}
