package stellarburgers;

import java.io.IOException;
import java.util.Properties;

public class Config {
    String browserType;
    String pathYandexBrowser;
    String pathYandexDriver;

    public Config(String browserType, String pathYandexBrowser, String pathYandexDriver) {
        this.browserType = browserType;
        this.pathYandexBrowser = pathYandexBrowser;
        this.pathYandexDriver = pathYandexDriver;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getPathYandexBrowser() {
        return pathYandexBrowser;
    }

    public void setPathYandexBrowser(String pathYandexBrowser) {
        this.pathYandexBrowser = pathYandexBrowser;
    }

    public String getPathYandexDriver() {
        return pathYandexDriver;
    }

    public void setPathYandexDriver(String pathYandexDriver) {
        this.pathYandexDriver = pathYandexDriver;
    }

    public static Config initConfig() {
        String environment = System.getProperty("environment");
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(environment+".properties"));
        } catch (IOException exception){
            System.out.println("Проблема файла конфигурации");
        }

        Config config = new Config(prop.getProperty("browserType"), prop.getProperty("pathYandexBrowser"),prop.getProperty("pathYandexDriver"));
        return config;
        
    }
}
