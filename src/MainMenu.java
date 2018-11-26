import game.utils.MapLoader;
import game.utils.TournamentMenu;
import mapeditor.Continent;
import mapeditor.ILoadedMap;
import mapeditor.IMapLoader;
import mapeditor.Territory;
//import mapeditor.gui.MapEditor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * This class contains the main menu of the game
 * @author Dmitry Kryukov, Ksenia Popova, Rodolfo Miranda
 * @see MapLoader
 */

public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width, height;
    private ILoadedMap loadedMapObj;

    /**
     * The constructor of the class.
     * Creates the window and put the buttons on there.
     * @param title of the window
     * @param width of the window
     * @param height of the window
     */
    public MainMenu(String title, int width, int height) {
        super(title);
        this.width = width;
        this.height = height;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.width, this.height);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file());
        menuBar.add(mapEditor());
        menuBar.add(exit());
        setJMenuBar(menuBar);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(startButtons(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(aiButtons(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(tournamentButtons(), gbc);

        pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method generate the button for menu bar with calling additional functionality
     * Such: testing continent bonus with 4 players
     * @return file object to attach to the menu bar panel
     */
    private JMenu file() {
        JMenu file = new JMenu("Risk");
        JMenuItem loadGame = new JMenuItem("Load game");
        JMenuItem continentBonus = new JMenuItem("test: Feature for testing continent bonus with 4 players");
        file.add(loadGame);
        file.add(continentBonus);

        continentBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Testing continent bonus with 4 players\n ------------------------ \n");
                List<String> playersModes = new ArrayList<>();
                int players = 4;
                playersModes.add("Human");
                playersModes.add("Human");
                playersModes.add("Human");
                playersModes.add("Human");

                String filePath = filePath();
                new MapLoader(players, filePath, true, playersModes);
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Load game\n ------------------------ \n");
                // TODO load the game from file
                String savedGame = savedGamefilePath();
            }
        });
        return file;
    }
    /**
     * Method generates the button for menu bar with calling map editor
     * @return mapEditor object to attach it to the menu bar panel
     */
    private JMenu mapEditor() {
        JMenu mapEditor = new JMenu("Map Editor");
        JMenuItem createMAP = new JMenuItem("Create Map");
        mapEditor.add(createMAP);
        JMenuItem editMAP = new JMenuItem("Edit Map");
        mapEditor.add(editMAP);

        createMAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                EventQueue.invokeLater(() -> {
                    IMapLoader mapLoaderObj = new mapeditor.MapLoader("", 1);
                    loadedMapObj = mapLoaderObj.getLoadedMap();
                    //Continent.setContinents(loadedMapObj.getContinents());
                    //Territory.setTerritories(loadedMapObj.getTerritories());
//                    MapEditor ex = new MapEditor("", loadedMapObj);
//                    ex.setVisible(true);
                });

            }
        });
        editMAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                EventQueue.invokeLater(() -> {
                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                    int returnValue = jfc.showOpenDialog(null);
                    // int returnValue = jfc.showSaveDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = jfc.getSelectedFile();
                        IMapLoader mapLoaderObj = new mapeditor.MapLoader(selectedFile.getAbsolutePath(), 1);
                        loadedMapObj = mapLoaderObj.getLoadedMap();
                        Continent.setContinents(loadedMapObj.getContinents());
                        Territory.setTerritories(loadedMapObj.getTerritories());
//                        MapEditor ex = new MapEditor(selectedFile.getAbsolutePath(), loadedMapObj);
//                        ex.setVisible(true);
                    }else{
                        //Deu pau manda mensagem de erro ai tiosao
                    }

                });
            }
        });
        return mapEditor;
    }

    /**
     * Method generates the button for menu bar with calling exit
     * @return exit object to attach it to the menu bar panel
     */
    private JMenu exit() {
        JMenu exit = new JMenu("Exit");
        JMenuItem quit = new JMenuItem(new ExitAction());
        exit.add(quit);
        return exit;
    }

    /**
     * Method generates the buttons for menu bar with calling start game with different number of players
     * @return startButtons object to attach the buttons to the window
     */
    private JPanel startButtons() {
        JPanel buttonPanel = new JPanel();
        JPanel startButtons = new JPanel();


        TitledBorder title = BorderFactory.createTitledBorder("Quick access");
        buttonPanel.setBorder(title);


        buttonPanel.setPreferredSize(new Dimension(600, 50));
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,0,0);
        JButton player2 = new JButton(("2 Players"));
        JButton player3 = new JButton(("3 Players"));
        JButton player4 = new JButton(("4 Players"));

        buttonPanel.add(player2, gbc);
        buttonPanel.add(player3, gbc);
        buttonPanel.add(player4, gbc);

        startButtons.add(buttonPanel);

        player2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 2 players\n ------------------------ \n");
                List<String> playersModes = new ArrayList<>();
                int players = 2;
                playersModes.add("Human");
                playersModes.add("Human");
                String filePath = filePath();
                MainMenu.this.setVisible(false);
                MapLoader loader = new MapLoader(players, filePath, false, playersModes);
            }
        });
        player3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 3 players\n ------------------------ \n");
                List<String> playersModes = new ArrayList<>();
                int players = 3;
                playersModes.add("Human");
                playersModes.add("Human");
                playersModes.add("Human");

                String filePath = filePath();
                MainMenu.this.setVisible(false);
                MapLoader loader = new MapLoader(players, filePath, false, playersModes);
            }
        });
        player4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 4 players\n ------------------------ \n");
                List<String> playersModes = new ArrayList<>();
                int players = 4;
                playersModes.add("Human");
                playersModes.add("Human");
                playersModes.add("Human");
                playersModes.add("Human");

                String filePath = filePath();
                MainMenu.this.setVisible(false);
                MapLoader loader = new MapLoader(players, filePath,false, playersModes);
            }
        });
        return startButtons;
    }

    /**
     * Method generates the buttons for menu bar with calling start game with AI
     * in Single game mode.
     *
     * @return aiButtons object to attach the buttons to the window
     */
    private JPanel aiButtons() {
        JPanel aiPanel = new JPanel();
        JPanel aiButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Single game mode.");
        aiPanel.setBorder(title);

        aiPanel.setPreferredSize(new Dimension(600, 100));
        aiPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 0);

        String[] PlayerMode1 = { "Nothing", "Human", "Aggressive", "Benevolent", "Random", "Cheater" };
        String[] PlayerMode2 = { "Nothing", "Human", "Aggressive", "Benevolent", "Random", "Cheater" };
        String[] PlayerMode3 = { "Nothing", "Human", "Aggressive", "Benevolent", "Random", "Cheater" };
        String[] PlayerMode4 = { "Nothing", "Human", "Aggressive", "Benevolent", "Random", "Cheater" };
        JComboBox PlayerModeList1 = new JComboBox(PlayerMode1);
        JComboBox PlayerModeList2 = new JComboBox(PlayerMode2);
        JComboBox PlayerModeList3 = new JComboBox(PlayerMode3);
        JComboBox PlayerModeList4 = new JComboBox(PlayerMode4);
        PlayerModeList1.setSelectedIndex(1);
        PlayerModeList2.setSelectedIndex(2);
        PlayerModeList3.setSelectedIndex(0);
        PlayerModeList4.setSelectedIndex(0);

        JButton startAIGame = new JButton(("Start game!"));


        gbc.gridx = 1;
        gbc.gridy = 1;
        aiPanel.add(new JLabel("Player 1"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        aiPanel.add(PlayerModeList1, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        aiPanel.add(new JLabel("Player 2"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        aiPanel.add(PlayerModeList2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        aiPanel.add(new JLabel("Player 3"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        aiPanel.add(PlayerModeList3, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        aiPanel.add(new JLabel("Player 4"), gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        aiPanel.add(PlayerModeList4, gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        aiPanel.add(startAIGame, gbc);

        aiButtons.add(aiPanel);

        startAIGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: start Ai game\n ------------------------ \n");
                List<String> playersModes = new ArrayList<>();
                String selectedPlayerMode1 = (String) PlayerModeList1.getSelectedItem();
                String selectedPlayerMode2 = (String) PlayerModeList2.getSelectedItem();
                String selectedPlayerMode3 = (String) PlayerModeList3.getSelectedItem();
                String selectedPlayerMode4 = (String) PlayerModeList4.getSelectedItem();
                int players = 0;
                if (selectedPlayerMode1 != "Nothing") {
                    players+=1;
                    playersModes.add(selectedPlayerMode1);
                }
                if (selectedPlayerMode2 != "Nothing") {
                    players+=1;
                    playersModes.add(selectedPlayerMode2);
                }
                if (selectedPlayerMode3 != "Nothing") {
                    players+=1;
                    playersModes.add(selectedPlayerMode3);
                }
                if (selectedPlayerMode4 != "Nothing") {
                    players+=1;
                    playersModes.add(selectedPlayerMode4);
                }
                if (players == 0 || players == 1){
                    System.out.println("You can't play alone or without any players at all.");
                } else {
                    String filePath = filePath();
                    MainMenu.this.setVisible(false);
                    MapLoader loader = new MapLoader(players, filePath, false, playersModes);
                }
            }
        });
        return aiButtons;
    }

    /**
     * Method generates the buttons for menu bar with calling AI game
     * Tournament mode
     *
     * @return tournamentButtons object to attach the buttons to the window
     */
    private JPanel tournamentButtons() {
        JPanel tournamentPanel = new JPanel();
        JPanel tournamentButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Tournament mode");
        tournamentPanel.setBorder(title);

        tournamentPanel.setPreferredSize(new Dimension(600, 50));
        tournamentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 0);
        JButton tournament = new JButton(("Tournament game"));

        gbc.gridx = 1;
        gbc.gridy = 1;
        tournamentPanel.add(tournament, gbc);

        tournamentButtons.add(tournamentPanel);

        tournament.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen tournament\n ------------------------ \n");
                MainMenu.this.setVisible(false);
                new TournamentMenu();
            }
        });
        return tournamentButtons;
    }

    /**
     * Exit functionality
     * Eventlistener that call exit function
     */
    class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        ExitAction() {
            putValue(NAME, "Quit");
        }
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * The method which returns the filepath of the map
     * @return filepath path to the map file
     * or
     * @return default.map default map file
     */
    private String filePath() {
        //JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "maps";
        JFileChooser fileChooser = new JFileChooser(new File(path));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MAP FILES", "map", "maps");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        System.out.println("DEBUG: Using the default map!\n---------------------------------------\n");
        return path + "/default.map";
    }

    /**
     * The method which returns the filepath of the saved game
     *
     * @return start the new game
     */
    private String savedGamefilePath() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved game files", "risk", "risk");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        System.out.println("DEBUG: Start the new game!\n---------------------------------------\n");
        return "Start the new game";
    }
}