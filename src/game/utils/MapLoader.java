package game.utils;

import game.Game;
import game.model.Continent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapLoader {
    public static int RADIUS = 20;
    public static List<Country> countries = new ArrayList<>();
    public static List<Neighbour> neighbours = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();
    public static List<Continent> continents = new ArrayList<>();
    public String mapPath;

    public MapLoader(int numberOfPlayers, String filePath, boolean mode) {
        // If true - the program in test mode.
        boolean testMode = mode;
        mapPath = filePath;
        String line;
        // FIXME oh my god.
        String line2;
        Color[] playerColor = new Color[4];

        playerColor[0] = Color.BLUE;
        playerColor[1] = Color.ORANGE;
        playerColor[2] = Color.CYAN;
        playerColor[3] = Color.GRAY;

        //Player[] playerList = new Player[numberOfPlayers];

        int[] countriesPerPlayer = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1), playerColor[i]));
            countriesPerPlayer[i] = 0;
        }

        List<String> neighboursList = new ArrayList<String>();

        // Stub continents
        Map<String, Continent> continentsMap = new HashMap<>();
//        continentsMap.put("Eriador", new Continent("Eriador", 4, Color.GREEN));
//        continentsMap.put("Gondor", new Continent("Gondor", 3, Color.WHITE));
//        continentsMap.put("Mordor", new Continent("Mordor", 2, Color.BLACK));
//        continentsMap.put("Rhavanion", new Continent("Rhavanion", 3, Color.YELLOW));
        // FIXME Additional reading of file for continents
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean flag = false;
            while ((line2 = bufferedReader.readLine()) != null) {
                if (flag) {
                    if (line2.isEmpty()){
                        break;
                    }
//                    System.out.println(line2);
                    String[] continentDetails = line2.split("=");
                    continentsMap.put(continentDetails[0], new Continent(continentDetails[0], Integer.parseInt(continentDetails[1])));
                }
                if (line2.equals("[Continents]") || line2.equals("[continents]"))
                    flag = true;
            }
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            ex.printStackTrace();
        }

        continents.addAll(continentsMap.values());

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean flag = false;
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                if (flag) {
                    String[] countryDetails = line.split(",");
                    Country countryToAdd = new Country(countryDetails[0],
                            Integer.parseInt(countryDetails[1]),
                            Integer.parseInt(countryDetails[2]),
                            RADIUS,
                            continentsMap.get(countryDetails[3]),
                            players.get(0));
                    continentsMap.get(countryDetails[3]).getCountryList().add(countryToAdd);
                    countries.add(countryToAdd);

                    String neighbour = "";
                    for (int i = 4; i < countryDetails.length; i++) {
                        neighbour += countryDetails[i];
                        if (i != countryDetails.length - 1)
                            neighbour += ",";
                    }
                    neighboursList.add(neighbour);
                }
                if (line.equals("[Territories]") || line.equals("[territories]"))
                    flag = true;
            }
            bufferedReader.close();

            int playerDistrib = countries.size() / players.size();
            int changeDistrib = playerDistrib * numberOfPlayers;
            Random rand = new Random();


            for (int i = 0; i < countries.size(); i++) {
                int newPlayer = rand.nextInt(numberOfPlayers);

                // Check test mode
                if (testMode){
                    // Test continent bonus
                    if (countries.get(i).getContinent().getName().equals("Eriador")) {
                        newPlayer = 0;
                    } else if (countries.get(i).getContinent().getName().equals("Mordor")) {
                        newPlayer = 1;
                    } else {
                        if (i + 1 <= changeDistrib) {
                            while (countriesPerPlayer[newPlayer] >= playerDistrib)
                                newPlayer = rand.nextInt(numberOfPlayers);
                        }
                    }
                } else {
                    // Original Solution
                    if (i + 1 <= changeDistrib) {
                        while (countriesPerPlayer[newPlayer] >= playerDistrib)
                            newPlayer = rand.nextInt(numberOfPlayers);
                    }
                }

                countries.get(i).setPlayer(players.get(newPlayer));
                countriesPerPlayer[newPlayer]++;
            }

            for (int i = 0; i < countries.size(); i++) {
                String[] str = neighboursList.get(i).split(",");
                for (int j = 0; j < str.length; j++) {
                    for (int k = 0; k < countries.size(); k++)
                        if (str[j].equals(countries.get(k).getName()))
                            neighbours.add(new Neighbour(countries.get(i), countries.get(k)));
                }
                //System.out.println(str);
            }
            // Create the instance of the game class and send it to Main
            Game game = new Game();
            new Main(game, this);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            ex.printStackTrace();
        }
    }

    public String getFilePath() {
        return mapPath;
    }
}