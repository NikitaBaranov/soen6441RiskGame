package game.utils;

import game.Game;
import game.model.*;
import game.strategies.PlayerStrategies.PlayerStrategyEnum;
import game.strategies.PlayerStrategies.PlayerStrategyFactory;
import game.ui.Main;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

/**
 * The map loader class. Responsible for loading and validation of the map.
 *
 * @author Ksenia Popova, Dmitry Kryukov
 * @see Main
 * @see Continent
 * @see Country
 * @see Neighbour
 * @see Player
 * @see Game
 */
public class MapLoader {
    public static int RADIUS = 20;
    public static List<Country> countries = new ArrayList<>();
    public static List<Neighbour> neighbours = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();
    public static List<Continent> continents = new ArrayList<>();
    public String mapPath;
    public boolean invalidMap;

    PlayerStrategyFactory playerStrategyFactory = new PlayerStrategyFactory();

    /**
     * Constructor of the class.
     * Loading and validation of the map.
     *
     * @param numberOfPlayers number of players
     * @param filePath        path to the map
     * @param mode            boolean mode that can be used to start the program in special mode to test features
     */
    public MapLoader(int numberOfPlayers, String filePath, boolean mode) {
        // If true - the program in test mode.
        boolean testMode = mode;
        mapPath = filePath;
        String line;
        // FIXME oh my god.
        invalidMap = false;
        String line2;
        Color[] playerColor = new Color[4];

        playerColor[0] = Color.BLUE;
        playerColor[1] = Color.ORANGE;
        playerColor[2] = Color.CYAN;
        playerColor[3] = Color.GRAY;

        //Player[] playerList = new Player[numberOfPlayers];

        int[] countriesPerPlayer = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1), playerColor[i], playerStrategyFactory.getStrategy(PlayerStrategyEnum.HUMAN_STRATEGY)));
            countriesPerPlayer[i] = 0;
        }

        List<String> neighboursList = new ArrayList<String>();

        // Stub continents
        Map<String, Continent> continentsMap = new HashMap<>();
        // FIXME Additional reading of file for continents
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean flag = false;
            while ((line2 = bufferedReader.readLine()) != null) {
                if (flag) {
                    if (line2.isEmpty()) {
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
                    if (line.isEmpty()) {
                        continue;
                    }
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
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
            ex.printStackTrace();
        }

        int playerDistrib = countries.size() / players.size();
        int changeDistrib = playerDistrib * numberOfPlayers;
        Random rand = new Random();


        for (int i = 0; i < countries.size(); i++) {
            int newPlayer = rand.nextInt(numberOfPlayers);

            // Check test mode
            if (testMode) {
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

        // Map Validation
        try {
            // Validate that exist at least 1 Country per player
            if (countries.size() < players.size()) {
                throw new InvalidObjectException("Map should contain at least one Country per player");
            }

            // Validate empty continents
            for (Continent continent : continents) {
                if (continent.getCountryList().size() == 0) {
                    throw new InvalidObjectException("Continent " + continent.getName() + " does not have Countries.");
                }
            }

            // Validate that graph is connected
            Queue<Country> toExplore = new LinkedList<>();
            Set<Country> seenCountries = new HashSet<>();
            toExplore.add(countries.get(0));
            while (!toExplore.isEmpty()) {
                Country current = ((LinkedList<Country>) toExplore).pop();
                if (!seenCountries.contains(current)) {
                    seenCountries.add(current);
                    toExplore.addAll(current.getNeighbours());
                }
            }
            if (seenCountries.size() != countries.size()) {
                throw new InvalidObjectException("Map has disconnected component.");
            }

        } catch (Exception e) {
            invalidMap = true;
            new WarningWindow("Map is not valid. \n " + e.getMessage() + "\n Please, use another one.");
            // Comment the line below to stop exiting every time
            // System.exit(1);
        }

        // Create the instance of the game class and send it to Main
        GameState gameState = new GameState();
        gameState.setCountries(countries);
        gameState.setNeighbours(neighbours);
        gameState.setPlayers(players);
        gameState.setContinents(continents);
        Game game = Game.getInstance();
        game.setGameState(gameState);

        new Main(game, this);
    }

    /**
     * File path getter.
     *
     * @return filePath to the map
     */
    public String getFilePath() {
        return mapPath;
    }
}