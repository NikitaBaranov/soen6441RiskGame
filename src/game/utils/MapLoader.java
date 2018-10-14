package game.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.Random;

import game.ui.Main;
import game.Game;
import game.model.Country;
import game.model.Neighbour;
import game.model.Player;

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
            Random r = new Random();
            Player player1 = new Player("Player 1 Name", Color.BLUE);
            player1.setInfantry(r.nextInt(100));
            player1.setCavalry(r.nextInt(100));
            player1.setArtillery(r.nextInt(100));
            player1.setWildcards(r.nextInt(100));
            player1.setBonus(r.nextInt(100));

            Player player2 = new Player("Player 2 Name", Color.PINK);
            player2.setInfantry(r.nextInt(100));
            player2.setCavalry(r.nextInt(100));
            player2.setArtillery(r.nextInt(100));
            player2.setWildcards(r.nextInt(100));
            player2.setBonus(r.nextInt(100));

            Player player3 = new Player("Player 3 Name", Color.CYAN);
            player3.setInfantry(r.nextInt(100));
            player3.setCavalry(r.nextInt(100));
            player3.setArtillery(r.nextInt(100));
            player3.setWildcards(r.nextInt(100));
            player3.setBonus(r.nextInt(100));

            countries.add(new Country("Canada", 100, 100, RADIUS, player1));
            countries.add(new Country("USA", 100, 300, RADIUS, player1));
            countries.add(new Country("England", 300, 100, RADIUS, player2));
            countries.add(new Country("France", 300, 200, RADIUS, player2));
            countries.add(new Country("Mexico", 150, 400, RADIUS, player2));
            countries.add(new Country("China", 400, 450, RADIUS, player3));
            countries.add(new Country("Japan", 400, 300, RADIUS, player3));

            neighbours.add(new Neighbour(countries.get(0), countries.get(1)));
            neighbours.add(new Neighbour(countries.get(1), countries.get(2)));
            neighbours.add(new Neighbour(countries.get(1), countries.get(4)));
            neighbours.add(new Neighbour(countries.get(2), countries.get(3)));

            neighbours.add(new Neighbour(countries.get(5), countries.get(6)));
            neighbours.add(new Neighbour(countries.get(3), countries.get(5)));
            neighbours.add(new Neighbour(countries.get(2), countries.get(6)));
            neighbours.add(new Neighbour(countries.get(1), countries.get(6)));

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
