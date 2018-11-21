import game.utils.MapLoader;
import mapeditor.Continent;
import mapeditor.ILoadedMap;
import mapeditor.IMapLoader;
import mapeditor.Territory;
import mapeditor.gui.CreateMapMenu;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
        JMenuItem continentBonus = new JMenuItem("test: Continent bonus with 4 players");
        file.add(loadGame);
        file.add(continentBonus);

        continentBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Testing continent bonus with 4 players\n ------------------------ \n");
                int players = 4;
                String game_mode = "human";
                String filePath = filePath();
                new MapLoader(players, filePath, true, game_mode);
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
                    CreateMapMenu ex = new CreateMapMenu("", loadedMapObj);
                    ex.setVisible(true);
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
                        CreateMapMenu ex = new CreateMapMenu(selectedFile.getAbsolutePath(), loadedMapObj);
                        ex.setVisible(true);
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

        TitledBorder title = BorderFactory.createTitledBorder("Human game");
        buttonPanel.setBorder(title);

        buttonPanel.setPreferredSize(new Dimension(380, 50));
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
                int players = 2;
                String game_mode = "human";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        player3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 3 players\n ------------------------ \n");
                int players = 3;
                String game_mode = "human";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        player4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 4 players\n ------------------------ \n");
                int players = 4;
                String game_mode = "human";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath,false, game_mode);
            }
        });
        return startButtons;
    }

    /**
     * Method generates the buttons for menu bar with calling start game with AI
     *
     * @return aiButtons object to attach the buttons to the window
     */
    private JPanel aiButtons() {
        JPanel aiPanel = new JPanel();
        JPanel aiButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("AI game");
        aiPanel.setBorder(title);

        aiPanel.setPreferredSize(new Dimension(380, 100));
        aiPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 0);
        JButton aiAggressive = new JButton(("Aggressive"));
        JButton aiBenevolent = new JButton(("Benevolent"));
        JButton aiRandom = new JButton(("Random"));
        JButton aiCheater = new JButton(("Cheater"));

        gbc.gridx = 1;
        gbc.gridy = 1;
        aiPanel.add(aiAggressive, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        aiPanel.add(aiBenevolent, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        aiPanel.add(aiRandom, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        aiPanel.add(aiCheater, gbc);

        aiButtons.add(aiPanel);

        aiAggressive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen Aggressive\n ------------------------ \n");
                int players = 2;
                String game_mode = "aggressive";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        aiBenevolent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen Benevolent\n ------------------------ \n");
                int players = 2;
                String game_mode = "benevolent";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        aiRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen Random\n ------------------------ \n");
                int players = 2;
                String game_mode = "random";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        aiCheater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen Cheater\n ------------------------ \n");
                int players = 2;
                String game_mode = "cheater";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        return aiButtons;
    }

    /**
     * Method generates the buttons for menu bar with calling AI game
     *
     * @return tournamentButtons object to attach the buttons to the window
     */
    private JPanel tournamentButtons() {
        JPanel tournamentPanel = new JPanel();
        JPanel tournamentButtons = new JPanel();

        TitledBorder title = BorderFactory.createTitledBorder("Tournament");
        tournamentPanel.setBorder(title);

        tournamentPanel.setPreferredSize(new Dimension(380, 50));
        tournamentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 0);
        JButton tournament = new JButton(("Tournament"));

        gbc.gridx = 1;
        gbc.gridy = 1;
        tournamentPanel.add(tournament, gbc);

        tournamentButtons.add(tournamentPanel);

        tournament.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("DEBUG: Chosen tournament\n ------------------------ \n");
                int players = 2;
                String game_mode = "tournament";
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false, game_mode);
            }
        });
        return tournamentButtons;
    }

    /**
     * Exit functionality
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