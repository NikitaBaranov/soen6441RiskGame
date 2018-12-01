package game.utils;

import game.Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public Properties prop = new Properties();

    public Config(){
        InputStream input = null;
        String filename = "config.properties";
        input = Game.class.getClassLoader().getResourceAsStream(filename);
        if(input==null){
            System.out.println("Sorry, unable to find " + filename);
            return;
        }

        //load a properties file from class path, inside static method
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get the property value and print it out
//        System.out.println(prop.getProperty("random_luck"));
//        System.out.println(prop.getProperty("pause_for_ai"));
    }

}
