package game.utils;

import game.Game;
import game.model.Continent;
import game.model.Country;
import game.model.GameState;
import game.model.Neighbour;
import game.model.Player;
import game.ui.Main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import static game.strategies.PlayerStrategies.PlayerStrategyEnum.AI_AGGRESSIVE_STRATEGY;
import static game.strategies.PlayerStrategies.PlayerStrategyEnum.AI_BENEVOLENT_STRATEGY;
import static game.strategies.PlayerStrategies.PlayerStrategyEnum.AI_CHEATER_STRATEGY;
import static game.strategies.PlayerStrategies.PlayerStrategyEnum.AI_RANDOM_STRATEGY;
import static game.strategies.PlayerStrategies.PlayerStrategyEnum.HUMAN_STRATEGY;

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
    public List<Country> countries = new ArrayList<>();
    public List<Neighbour> neighbours = new ArrayList<>();
    public List<Player> players = new ArrayList<>();
    public List<Continent> continents = new ArrayList<>();
    public String mapPath;
    public boolean invalidMap;

    Game game = Game.getInstance();

    /**
     * Constructor of the class.
     * Loading and validation of the map.
     *
     * @param numberOfPlayers number of players
     * @param filePath        path to the map
     * @param mode            boolean mode that can be used to start the program in special mode to test features
     * @param playersModes    The modes for every player in the game. Choose strategies.
     */
    public MapLoader(int numberOfPlayers, String filePath, boolean mode, List<String> playersModes, NotificationWindow notificationWindow) {
        // If true - the program in test mode.
        boolean testMode = mode;
        mapPath = filePath;
        String line;
        invalidMap = false;
        String line2;
        Color[] playerColor = new Color[8];

        playerColor[0] = Color.BLUE;
        playerColor[1] = Color.ORANGE;
        playerColor[2] = Color.CYAN;
        playerColor[3] = Color.GRAY;
        playerColor[4] = Color.PINK;
        playerColor[5] = Color.MAGENTA;
        playerColor[6] = Color.DARK_GRAY;
        playerColor[7] = Color.BLACK;

        ArrayList<Color> pColors = new ArrayList<>();
        pColors.add(playerColor[0]);
        pColors.add(playerColor[1]);
        pColors.add(playerColor[2]);
        pColors.add(playerColor[3]);
        pColors.add(playerColor[4]);
        pColors.add(playerColor[5]);
        pColors.add(playerColor[6]);
        pColors.add(playerColor[7]);

        Random colorRand = new Random();


        //Player[] playerList = new Player[numberOfPlayers];
        // This var needs for testing mode when we test continent bonus
        // just highlighted it with to do to not forget what is going on
        int[] countriesPerPlayer = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            countriesPerPlayer[i] = 0;
        }

        // Players creation. The numberOfPlayers should be the same as size of playersModes.
        if (numberOfPlayers == playersModes.size()) {
            for (String playermode : playersModes) {
                switch (playermode) {
                    case "Human":
                        int humanCounter = 1;
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Human")) {
                                    humanCounter += 1;
                                }
                            }
                            players.add(new Player("Human Player " + humanCounter, pColors.remove(0), HUMAN_STRATEGY, false));
                        } else {
                            players.add(new Player("Human Player " + humanCounter, pColors.remove(0), HUMAN_STRATEGY, false));
                        }
                        break;
                    case "Aggressive":
                        int aggrCounter = 1;
                        int aggrCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Aggressive")) {
                                    aggrCounter += 1;
                                }
                            }
                            players.add(new Player("Aggressive AI " + aggrCounter, pColors.remove(aggrCol), AI_AGGRESSIVE_STRATEGY, true));
                        } else {
                            players.add(new Player("Aggressive AI " + aggrCounter, pColors.remove(aggrCol), AI_AGGRESSIVE_STRATEGY, true));
                        }
                        break;
                    case "Benevolent":
                        int benevCounter = 1;
                        int benevCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Benevolent")) {
                                    benevCounter += 1;
                                }
                            }
                            players.add(new Player("Benevolent AI " + benevCounter, pColors.remove(benevCol), AI_BENEVOLENT_STRATEGY, true));
                        } else {
                            players.add(new Player("Benevolent AI " + benevCounter, pColors.remove(benevCol), AI_BENEVOLENT_STRATEGY, true));
                        }
                        break;
                    case "Random":
                        int randCounter = 1;
                        int randCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Random")) {
                                    randCounter += 1;
                                }
                            }
                            players.add(new Player("Random AI " + randCounter, pColors.remove(randCol), AI_RANDOM_STRATEGY, true));
                        } else {
                            players.add(new Player("Random AI " + randCounter, pColors.remove(randCol), AI_RANDOM_STRATEGY, true));
                        }
                        break;
                    case "Cheater":
                        int cheatCounter = 1;
                        int cheatCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Cheater")) {
                                    cheatCounter += 1;
                                }
                            }
                            players.add(new Player("Cheater AI " + cheatCounter, pColors.remove(cheatCol), AI_CHEATER_STRATEGY, true));
                        } else {
                            players.add(new Player("Cheater AI " + cheatCounter, pColors.remove(cheatCol), AI_CHEATER_STRATEGY, true));
                        }
                        break;
                }
            }
        } else {
            System.out.println("Error. Number of players doesnt equal to players modes number");
            new WarningWindow("Error. Number of players doesnt equal to players modes number");
        }

        // Stub continents
        Map<String, Continent> continentsMap = new HashMap<>();
        // Additional reading of file for continents
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
        List<String> neighboursList = new ArrayList<String>();

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

            //  The feature for testing purposes. Needs to run game with specific rules to test continent bonus feature.
            // Just highlighed it with TO DO to not forget what is going on
            // It assign to 2 players the whole continents every time
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
                throw new InvalidObjectException("Map has disconnected component. (Graph is not connected)");
            }

            // Validate that subgraphs are connected
            for (Continent continent : continents) {
                Queue<Country> toExploreSubgraph = new LinkedList<>();
                Set<Country> seenCountriesSubgraph = new HashSet<>();
                List<Country> countriesSubgraph = new ArrayList<>();
                countriesSubgraph = continent.getCountryList();
                toExploreSubgraph.add(countriesSubgraph.get(0));
                while (!toExploreSubgraph.isEmpty()) {
                    Country current = ((LinkedList<Country>) toExploreSubgraph).pop();
                    if (!seenCountriesSubgraph.contains(current)) {
                        seenCountriesSubgraph.add(current);

                        for (Country neighbour : current.getNeighbours()) {
                            if (countriesSubgraph.contains(neighbour)) {
                                toExploreSubgraph.add(neighbour);
                            }
                        }
                    }
                }
                if (seenCountriesSubgraph.size() != countriesSubgraph.size()) {
                    throw new InvalidObjectException("Map has disconnected continent: " + continent.getName() + ". (Subgraph is not connected)");
                }
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
        gameState.setMapFilePath(mapPath);

        game = Game.getInstance();
        game.setGameState(gameState);
        game.setNotification(notificationWindow);

        Main main = new Main(game);
        main.createAndShowGui(false);
    }

    /**
     * Second constructor of the class specially for tournament mode
     * Loading and validation of the map.
     *
     * @param numberOfPlayers number of players
     * @param filePath        path to the map
     * @param playersModes    The modes for every player in the game. Choose strategies.
     */
    public MapLoader(int numberOfPlayers, String filePath, List<String> playersModes, int turns, NotificationWindow notificationWindow) {
        mapPath = filePath;
        String line;
        invalidMap = false;
        String line2;
        // games - number of games for each map
        // turns - max number of turns

        Color[] playerColor = new Color[8];

        playerColor[0] = Color.BLUE;
        playerColor[1] = Color.ORANGE;
        playerColor[2] = Color.CYAN;
        playerColor[3] = Color.GRAY;
        playerColor[4] = Color.PINK;
        playerColor[5] = Color.MAGENTA;
        playerColor[6] = Color.DARK_GRAY;
        playerColor[7] = Color.BLACK;

        ArrayList<Color> pColors = new ArrayList<>();
        pColors.add(playerColor[0]);
        pColors.add(playerColor[1]);
        pColors.add(playerColor[2]);
        pColors.add(playerColor[3]);
        pColors.add(playerColor[4]);
        pColors.add(playerColor[5]);
        pColors.add(playerColor[6]);
        pColors.add(playerColor[7]);

        Random colorRand = new Random();

        int[] countriesPerPlayer = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            countriesPerPlayer[i] = 0;
        }

        // Players creation. The numberOfPlayers should be the same as size of playersModes.
        if (numberOfPlayers == playersModes.size()) {
            for (String playermode : playersModes) {
                switch (playermode) {
                    case "Human":
                        int humanCounter = 1;
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Human")) {
                                    humanCounter += 1;
                                }
                            }
                            players.add(new Player("Human Player " + humanCounter, pColors.remove(0), HUMAN_STRATEGY, false));
                        } else {
                            players.add(new Player("Human Player " + humanCounter, pColors.remove(0), HUMAN_STRATEGY, false));
                        }
                        break;
                    case "Aggressive":
                        int aggrCounter = 1;
                        int aggrCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Aggressive")) {
                                    aggrCounter += 1;
                                }
                            }
                            players.add(new Player("Aggressive AI " + aggrCounter, pColors.remove(aggrCol), AI_AGGRESSIVE_STRATEGY, true));
                        } else {
                            players.add(new Player("Aggressive AI " + aggrCounter, pColors.remove(aggrCol), AI_AGGRESSIVE_STRATEGY, true));
                        }
                        break;
                    case "Benevolent":
                        int benevCounter = 1;
                        int benevCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Benevolent")) {
                                    benevCounter += 1;
                                }
                            }
                            players.add(new Player("Benevolent AI " + benevCounter, pColors.remove(benevCol), AI_BENEVOLENT_STRATEGY, true));
                        } else {
                            players.add(new Player("Benevolent AI " + benevCounter, pColors.remove(benevCol), AI_BENEVOLENT_STRATEGY, true));
                        }
                        break;
                    case "Random":
                        int randCounter = 1;
                        int randCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Random")) {
                                    randCounter += 1;
                                }
                            }
                            players.add(new Player("Random AI " + randCounter, pColors.remove(randCol), AI_RANDOM_STRATEGY, true));
                        } else {
                            players.add(new Player("Random AI " + randCounter, pColors.remove(randCol), AI_RANDOM_STRATEGY, true));
                        }
                        break;
                    case "Cheater":
                        int cheatCounter = 1;
                        int cheatCol = colorRand.nextInt(pColors.size());
                        if (players.size() > 0) {
                            for (Player player : players) {
                                String existedName = player.getName();
                                if (existedName.contains("Cheater")) {
                                    cheatCounter += 1;
                                }
                            }
                            players.add(new Player("Cheater AI " + cheatCounter, pColors.remove(cheatCol), AI_CHEATER_STRATEGY, true));
                        } else {
                            players.add(new Player("Cheater AI " + cheatCounter, pColors.remove(cheatCol), AI_CHEATER_STRATEGY, true));
                        }
                        break;
                }
            }
        } else {
            System.out.println("Error. Number of players doesnt equal to players modes number");
            new WarningWindow("Error. Number of players doesnt equal to players modes number");
        }

        List<String> neighboursList = new ArrayList<String>();

        // Stub continents
        Map<String, Continent> continentsMap = new HashMap<>();
        // Additional reading of file for continents
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean flag = false;
            while ((line2 = bufferedReader.readLine()) != null) {
                if (flag) {
                    if (line2.isEmpty()) {
                        break;
                    }
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
            // Original Solution
            if (i + 1 <= changeDistrib) {
                while (countriesPerPlayer[newPlayer] >= playerDistrib)
                    newPlayer = rand.nextInt(numberOfPlayers);
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
                throw new InvalidObjectException("Map has disconnected component. (Graph is not connected)");
            }

            // Validate that subgraphs are connected
            for (Continent continent : continents) {
                Queue<Country> toExploreSubgraph = new LinkedList<>();
                Set<Country> seenCountriesSubgraph = new HashSet<>();
                List<Country> countriesSubgraph = new ArrayList<>();
                countriesSubgraph = continent.getCountryList();
                toExploreSubgraph.add(countriesSubgraph.get(0));
                while (!toExploreSubgraph.isEmpty()) {
                    Country current = ((LinkedList<Country>) toExploreSubgraph).pop();
                    if (!seenCountriesSubgraph.contains(current)) {
                        seenCountriesSubgraph.add(current);

                        for (Country neighbour : current.getNeighbours()) {
                            if (countriesSubgraph.contains(neighbour)) {
                                toExploreSubgraph.add(neighbour);
                            }
                        }
                    }
                }
                if (seenCountriesSubgraph.size() != countriesSubgraph.size()) {
                    throw new InvalidObjectException("Map has disconnected continent: " + continent.getName() + ". (Subgraph is not connected)");
                }
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
        gameState.setMapFilePath(mapPath);
        gameState.setMaxNumberOfTurns(turns);
        gameState.setTurnament(true);

        game.setGameState(gameState);
        game.setNotification(notificationWindow);

        //Tournament mode, do not need to create game window there.
    }

    public MapLoader(GameState gameState, NotificationWindow notificationWindow) {
        gameState.setIPanelObservers(new LinkedList<>());
        gameState.setJustLoad(true);

        game = Game.getInstance();
        game.setGameState(gameState);
        for (Player player : gameState.getPlayers()) {
            player.initStategy();
        }
        game.setNotification(notificationWindow);

        Main main = new Main(game);
        main.createAndShowGui(false);
    }
}