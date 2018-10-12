package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import game.Main;
import game.Game;
import model.Country;
import model.Neighbour;
import model.Player;

public class MapLoader {
    public static int RADIUS = 20;
    public static List<Country> countries = new ArrayList<>();
    public static List<Neighbour> neighbours = new ArrayList<>();

    public MapLoader(int players, String filePath) {
        String line;
        // TODO Create players;
        // create additional array of players
        //Player player1 = new Player("Player 1 Name", Color.BLUE);

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                //TODO load the map into structure
            }
            bufferedReader.close();

            //TODO Create countries and assign player via random get from additional player array
            //countries.add(new Country("RRRRR", 130, 100, RADIUS, player1));

            //Create neighbour via getting the country from country array
            //neighbours.add(new Neighbour(countries.get(0), countries.get(1)));

            //-------------------------------------------------------------------
            // Temporary map example
            Player player1 = new Player("Player 1 Name", Color.BLUE);
            Player player2 = new Player("Player 2 Name", Color.RED);

            countries.add(new Country("Canada", 100, 100, RADIUS, player1));
            countries.add(new Country("USA", 100, 300, RADIUS, player1));
            countries.add(new Country("England", 300, 100, RADIUS, player2));
            countries.add(new Country("France", 300, 200, RADIUS, player2));
            countries.add(new Country("Mexico", 150, 400, RADIUS, player2));

            neighbours.add(new Neighbour(countries.get(0), countries.get(1)));
            neighbours.add(new Neighbour(countries.get(1), countries.get(2)));
            neighbours.add(new Neighbour(countries.get(2), countries.get(3)));
            neighbours.add(new Neighbour(countries.get(1), countries.get(4)));

            //---------------------------------------------------------------------
            // Create the instance of the game class and send it to Main
            Game game = new Game();
            new Main(game);
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            ex.printStackTrace();
        }
    }
}
