package game.utils;

import game.Game;
import game.ui.Main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * The tournament menu window.
 *
 * @author Dmitry Kryukov
 */
public class TournamentMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    // Required parameters for tournament mode
    public String mapFile1 = "";
    public String mapFile2 = "";
    public String mapFile3 = "";
    public String mapFile4 = "";
    public String mapFile5 = "";
    public JLabel mapLabel1 = new JLabel("Nothing");
    public JLabel mapLabel2 = new JLabel("Nothing");
    public JLabel mapLabel3 = new JLabel("Nothing");
    public JLabel mapLabel4 = new JLabel("Nothing");
    public JLabel mapLabel5 = new JLabel("Nothing");
    public String[] PlayerStrategy1 = {"Nothing", "Aggressive", "Benevolent", "Random", "Cheater"};
    public String[] PlayerStrategy2 = {"Nothing", "Aggressive", "Benevolent", "Random", "Cheater"};
    public String[] PlayerStrategy3 = {"Nothing", "Aggressive", "Benevolent", "Random", "Cheater"};
    public String[] PlayerStrategy4 = {"Nothing", "Aggressive", "Benevolent", "Random", "Cheater"};
    public JComboBox tournamentStrategy1 = new JComboBox(PlayerStrategy1);
    public JComboBox tournamentStrategy2 = new JComboBox(PlayerStrategy2);
    public JComboBox tournamentStrategy3 = new JComboBox(PlayerStrategy3);
    public JComboBox tournamentStrategy4 = new JComboBox(PlayerStrategy4);
    public Integer[] tournamentGamesList = {1, 2, 3, 4, 5};
    public Integer[] tournamentTurnsList = {10, 15, 20, 25, 30, 35, 40, 45, 50};
    public JComboBox tournamentGames = new JComboBox(tournamentGamesList);
    public JComboBox tournamentTurns = new JComboBox(tournamentTurnsList);
    String[][] results;
    private int width, height;
    private NotificationWindow notificationWindow;
    private int tournamentMapNumber = 0;
    private int tournamentGameNumber = 0;

    /**
     * The constructor of the class.
     */
    public TournamentMenu(NotificationWindow notificationWindow) {
        super("Tournament mode");
        this.notificationWindow = notificationWindow;
        this.width = 1300;
        this.height = 500;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.width, this.height);
        setResizable(false);
        setAlwaysOnTop(false);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(mapChooser(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(strategiesChooser(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(numbersChooser(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        getContentPane().add(startGame(), gbc);

//         pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method creates an panel on the window with chooser of the maps
     *
     * @return mapChooser object to attach to the window
     */
    private JPanel mapChooser() {
        JPanel mapChooserPanel = new JPanel();
        JPanel mapChooserButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Choose maps.");
        mapChooserPanel.setBorder(title);

//        mapChooserPanel.setPreferredSize(new Dimension(1100, 100));
        mapChooserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);

        JButton map1 = new JButton(("Choose Map 1"));
        JButton map2 = new JButton(("Choose Map 2"));
        JButton map3 = new JButton(("Choose Map 3"));
        JButton map4 = new JButton(("Choose Map 4"));
        JButton map5 = new JButton(("Choose Map 5"));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mapChooserPanel.add(map1, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        mapChooserPanel.add(map2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        mapChooserPanel.add(map3, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        mapChooserPanel.add(map4, gbc);
        gbc.gridx = 5;
        gbc.gridy = 1;
        mapChooserPanel.add(map5, gbc);
        gbc.gridx = 6;
        gbc.gridy = 1;

        // Labels
        gbc.gridx = 1;
        gbc.gridy = 2;

        mapChooserPanel.add(mapLabel1, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        mapChooserPanel.add(mapLabel2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        mapChooserPanel.add(mapLabel3, gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        mapChooserPanel.add(mapLabel4, gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        mapChooserPanel.add(mapLabel5, gbc);

        mapChooserButtons.add(mapChooserPanel);

        // Action Listeners
        map1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mapFile1 = mapFilePath();
                mapLabel1.setText(mapFile1.split("/")[mapFile1.split("/").length - 1]);
            }
        });
        map2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mapFile2 = mapFilePath();
                mapLabel2.setText(mapFile2.split("/")[mapFile2.split("/").length - 1]);
            }
        });
        map3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mapFile3 = mapFilePath();
                mapLabel3.setText(mapFile3.split("/")[mapFile3.split("/").length - 1]);
            }
        });
        map4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mapFile4 = mapFilePath();
                mapLabel4.setText(mapFile4.split("/")[mapFile4.split("/").length - 1]);
            }
        });
        map5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mapFile5 = mapFilePath();
                mapLabel5.setText(mapFile5.split("/")[mapFile5.split("/").length - 1]);
            }
        });
        return mapChooserButtons;
    }

    /**
     * Method creates an panel on the window with chooser of the strategies for tournament mode
     *
     * @return strategiesChooserButtons object to attach the buttons to the window
     */
    private JPanel strategiesChooser() {
        JPanel strategiesChooserPanel = new JPanel();
        JPanel strategiesChooserButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Choose player strategies.");
        strategiesChooserPanel.setBorder(title);

//        strategiesChooserPanel.setPreferredSize(new Dimension(1000, 100));
        strategiesChooserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);

        tournamentStrategy1.setSelectedIndex(1);
        tournamentStrategy2.setSelectedIndex(2);
        tournamentStrategy3.setSelectedIndex(0);
        tournamentStrategy4.setSelectedIndex(0);

        gbc.gridx = 1;
        gbc.gridy = 1;
        strategiesChooserPanel.add(new JLabel("Player strategy 1"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        strategiesChooserPanel.add(tournamentStrategy1, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        strategiesChooserPanel.add(new JLabel("Player strategy 2"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        strategiesChooserPanel.add(tournamentStrategy2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        strategiesChooserPanel.add(new JLabel("Player strategy 3"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        strategiesChooserPanel.add(tournamentStrategy3, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        strategiesChooserPanel.add(new JLabel("Player strategy 4"), gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        strategiesChooserPanel.add(tournamentStrategy4, gbc);

        strategiesChooserButtons.add(strategiesChooserPanel);

        return strategiesChooserButtons;
    }

    /**
     * Method creates an panel on the window with chooser of the settings for the game for tournament mode
     *
     * @return numbersChooserButtons object to attach the buttons to the window
     */
    private JPanel numbersChooser() {
        JPanel numberChooserPanel = new JPanel();
        JPanel numberChooserButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Choose game settings.");
        numberChooserPanel.setBorder(title);

        numberChooserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);

        tournamentGames.setSelectedIndex(0);
        tournamentTurns.setSelectedIndex(0);


        gbc.gridx = 1;
        gbc.gridy = 1;
        numberChooserPanel.add(new JLabel("Number of games on each map."), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        numberChooserPanel.add(tournamentGames, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        numberChooserPanel.add(new JLabel("Max number of turns"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        numberChooserPanel.add(tournamentTurns, gbc);

        numberChooserButtons.add(numberChooserPanel);

        return numberChooserButtons;
    }

    /**
     * Method creates an panel on the window with chooser of the settings for the game for tournament mode
     *
     * @return numbersChooserButtons object to attach the buttons to the window
     */
    private JPanel startGame() {
        JPanel startGamePanel = new JPanel();
        JPanel startGameButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Start the game");
        startGamePanel.setBorder(title);

        startGamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);

        JButton start = new JButton("Start the game");

        gbc.gridx = 0;
        gbc.gridy = 0;
        startGamePanel.add(start, gbc);

        startGameButtons.add(startGamePanel);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Pack map files.
                List<String> mapFiles = new ArrayList<>();
                if (!mapFile1.isEmpty()) {
                    mapFiles.add(mapFile1);
                }
                if (!mapFile2.isEmpty()) {
                    mapFiles.add(mapFile2);
                }
                if (!mapFile3.isEmpty()) {
                    mapFiles.add(mapFile3);
                }
                if (!mapFile4.isEmpty()) {
                    mapFiles.add(mapFile4);
                }
                if (!mapFile5.isEmpty()) {
                    mapFiles.add(mapFile5);
                }

                // Get settings
                int games = (Integer) tournamentGames.getSelectedItem();
                int turns = (Integer) tournamentTurns.getSelectedItem();

                // Pack strategies.
                List<String> tournamentStrategies = new ArrayList<>();
                String selectedStrategy1 = (String) tournamentStrategy1.getSelectedItem();
                String selectedStrategy2 = (String) tournamentStrategy2.getSelectedItem();
                String selectedStrategy3 = (String) tournamentStrategy3.getSelectedItem();
                String selectedStrategy4 = (String) tournamentStrategy4.getSelectedItem();

                int players = 0;
                if (selectedStrategy1 != "Nothing") {
                    players += 1;
                    tournamentStrategies.add(selectedStrategy1);
                }
                if (selectedStrategy2 != "Nothing") {
                    players += 1;
                    tournamentStrategies.add(selectedStrategy2);
                }
                if (selectedStrategy3 != "Nothing") {
                    players += 1;
                    tournamentStrategies.add(selectedStrategy3);
                }
                if (selectedStrategy4 != "Nothing") {
                    players += 1;
                    tournamentStrategies.add(selectedStrategy4);
                }

                if (mapFiles.size() < 1) {
                    System.out.println("At least 1 map should be chosen.");
                    new WarningWindow("At least 1 map should be chosen.", false);
                } else if (players == 0 || players == 1) {
                    System.out.println("At least 2 strategies should be chosen.");
                    new WarningWindow("At least 2 strategies should be chosen.", false);
                } else {
                    results = new String[mapFiles.size()][games];
                    runRound(mapFiles, players, tournamentStrategies, games, turns);

                    // TODO Create new loader with tournament settings.
                    // parameters: players, mapFiles, false, tournamentStrategies, games, turns
                    //MapLoader loader = new MapLoader(players, mapFiles, false, tournamentStrategies, games, turns);
                    // I think there is a need to create another maploader constructor and setup game in there.
                    // TODO add map validation before creating the game.

                    TournamentMenu.this.setVisible(false);
                    // Debug. Show existed settings
                    System.out.println("Number of maps: " + mapFiles.size());
                    for (String map : mapFiles) {
                        System.out.println("Map: " + map);
                    }
                    for (String strategy : tournamentStrategies) {
                        System.out.println("Strategy: " + strategy);
                    }
                    System.out.println("Number of games: " + games);
                    System.out.println("Number of turns: " + turns);
                }
            }
        });

        return startGameButtons;
    }

    private void runRound(List<String> mapFiles, int players, List<String> tournamentStrategies, int games, int turns) {
        try {
            System.out.println("map " + tournamentMapNumber + " start.");
            String mapFile = mapFiles.get(tournamentMapNumber);
            MapLoader loader = new MapLoader(players, mapFile, tournamentStrategies, turns, notificationWindow);
            RoundWorker roundWorker = new RoundWorker(loader, mapFiles, players, tournamentStrategies, games, turns);
            roundWorker.execute();
            System.out.println("map " + tournamentMapNumber + " done.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("no map " + tournamentMapNumber);
        }
    }

    /**
     * The method which returns the filepath of the map
     *
     * @return default.map default map file
     */
    private String mapFilePath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "maps";
        JFileChooser fileChooser = new JFileChooser(new File(path));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MAP FILES", "map", "maps");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return path + "/default.map";
    }

    /**
     * The exit functionality, triggered when the user press the close button
     */
    class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        ExitAction() {
            putValue(NAME, "Close");
        }

        public void actionPerformed(ActionEvent e) {
            // We can just close the window but also clean everything to be able start the program from scratch
            // Like clean all models variables (Country, Continent, etc)
            // dispose();
            exit(0); // exit the program
        }
    }

    public class RoundWorker extends SwingWorker<Void, String> {

        MapLoader mapLoader;
        List<String> mapFiles;
        int players;
        List<String> tournamentStrategies;
        int games;
        int turns;

        /**
         * Constructor of the class
         */
        public RoundWorker(MapLoader mapLoader, List<String> mapFiles, int players, List<String> tournamentStrategies, int games, int turns) {
            this.mapLoader = mapLoader;
            this.mapFiles = mapFiles;
            this.players = players;
            this.tournamentStrategies = tournamentStrategies;
            this.games = games;
            this.turns = turns;
        }

        /**
         * Do attack actions in the background.
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
            Main main = new Main(Game.getInstance());
            JFrame jFrame = main.createAndShowGui(true);
            jFrame.addWindowListener((new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    publish("Publish Worker is done. Result is " + Game.getInstance().getGameState().getResult());
                    dispose();
                    done();
                }
            }));
            return null;
        }

        /**
         * Debug method
         *
         * @param chunks
         */
        @Override
        protected void process(List<String> chunks) {
            for (String c : chunks) {
                System.out.println(c);
            }
        }

        /**
         * Automatic go to next turn when phase is done
         */
        @Override
        protected void done() {
            results[tournamentMapNumber][tournamentGameNumber] = Game.getInstance().getGameState().getResult();
            if (tournamentMapNumber == (mapFiles.size() - 1) && tournamentGameNumber == (games - 1)) {
                System.out.println("\n --------------------------------------- \n");
                System.out.println(" Tournament Results \n");
                System.out.printf(" %-20s", "Map");
                for (int i = 0; i < results[0].length; i++) {
                    System.out.printf(" %-20s", "Game " + (i + 1));
                }
                System.out.print("\n");
                for (int i = 0; i < results.length; i++) {
                    System.out.printf(" %-20s", mapFiles.get(i).substring(mapFiles.get(i).lastIndexOf("/") + 1));
                    for (int j = 0; j < results[0].length; j++) {
                        System.out.printf(" %-20s", results[i][j]);
                    }
                    System.out.print("\n");
                }
                exit(0);
            }
            boolean nextRun = false;
            if (tournamentMapNumber < mapFiles.size() - 1) {
                nextRun = true;
            } else {
                if (tournamentGameNumber < games - 1) {
                    tournamentGameNumber++;
                    tournamentMapNumber = -1;
                    nextRun = true;
                }
            }
            if (nextRun) {
                tournamentMapNumber++;
                runRound(mapFiles, players, tournamentStrategies, games, turns);
            }
        }
    }
}