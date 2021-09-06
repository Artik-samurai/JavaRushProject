package Reading_configs;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Reading_configs {
    public static void main(String[] args) {
        Reading_configs solution = new Reading_configs();
        Properties properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/notExists");
        properties.list(System.out);
    }

    public Properties getProperties(String fileName)  {
        Properties properties = new Properties();
        try (InputStream reader = new FileInputStream(fileName)) {
            if (fileName.endsWith(".xml")){
                properties.loadFromXML(reader);
            } else {
                properties.load(reader);
            }
        } catch (IOException e) {
        }
        return properties;
    }
}