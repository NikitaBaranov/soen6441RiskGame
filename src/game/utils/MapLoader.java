package game.utils;

import game.Game;
import game.model.Country;
import game.model.Neighbour;
import game.model.Player;
import game.ui.Main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLoader {
    public static int RADIUS = 20;
    public static List<Country> countries = new ArrayList<>();
    public static List<Neighbour> neighbours = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();
    public String mapPath;

    public MapLoader(int numberOfPlayers, String filePath) {
        mapPath = filePath;
        String line;
        Color[] playerColor = new Color[4];

        playerColor[0] = Color.BLUE;
        playerColor[1] = Color.ORANGE;
        playerColor[2] = Color.CYAN;
        playerColor[3] = Color.GRAY;

        //Player[] playerList = new Player[numberOfPlayers];

        int[] countriesPerPlayer = new int[numberOfPlayers];
        for (int i=0; i<numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1), playerColor[i]));
            countriesPerPlayer[i] = 0;
        }

        List<String> neighboursList = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean flag = false;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (flag) {
                    String[] countryDetails = line.split(",");
                    countries.add(new Country(countryDetails[0], Integer.parseInt(countryDetails[1]), Integer.parseInt(countryDetails[2]), RADIUS, players.get(0)));
                    String neighbour = "";
                    for (int i=4; i<countryDetails.length; i++) {
                        neighbour += countryDetails[i];
                        if (i!=countryDetails.length-1)
                            neighbour += ",";
                    }
                    neighboursList.add(neighbour);
                }
                if (line.equals("[Territories]"))
                    flag = true;
            }
            bufferedReader.close();

            int playerDistrib = countries.size() / players.size();
            int changeDistrib = playerDistrib * numberOfPlayers;
            Random rand = new Random();

            for (int i = 0; i < countries.size(); i++) {
                int newPlayer = rand.nextInt(numberOfPlayers);
                if (i + 1 <= changeDistrib) {
                    while (countriesPerPlayer[newPlayer] >= playerDistrib)
                        newPlayer = rand.nextInt(numberOfPlayers);
                }
                countries.get(i).setPlayer(players.get(newPlayer));
                countriesPerPlayer[newPlayer]++;
            }

            for (int i=0; i < countries.size(); i++) {
                String[] str = neighboursList.get(i).split(",");
                for (int j=0; j<str.length; j++) {
                    for (int k=0; k<countries.size(); k++)
                        if (str[j].equals(countries.get(k).getName()))
                            neighbours.add(new Neighbour(countries.get(i), countries.get(k)));
                }
                //System.out.println(str);
            }
            // Create the instance of the game class and send it to Main
            Game game = new Game();
            new Main(game, this);
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            ex.printStackTrace();
        }
    }
    public String getFilePath(){
        return mapPath;
    }
}