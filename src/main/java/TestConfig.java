import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    public static void main(String[] args) throws IOException {
        ///home/kochnev_a/projects/untitled/src/main/resources/config.properties
        //E:\MaksimProject\src\main\resources\config.properties
        FileInputStream fileInputStream = new FileInputStream("/home/kochnev_a/projects/untitled/src/main/resources/config.properties");

        Properties properties = new Properties();

        properties.load(fileInputStream);

        String mot = "motorway";

        int motorway = Integer.parseInt(properties.getProperty(mot));
        System.out.println(motorway);
    }
}
