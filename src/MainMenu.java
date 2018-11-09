import game.utils.MapLoader;
import mapeditor.StartEditor;
import mapeditor.gui.CreateMapMenu;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import mapeditor.Continent;
import mapeditor.ILoadedMap;
import mapeditor.IMapLoader;
import mapeditor.Territory;

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

        getContentPane().add(startButtons());

        // pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method generate the button for menu bar with calling additional functionality
     * Such: testing continent bonus with 4 players
     * @return file object to attach to the menu bar panel
     */
    private JMenu file() {
        JMenu file = new JMenu("File");
        JMenuItem continentBonus = new JMenuItem("test: Continent bonus with 4 players");
        file.add(continentBonus);

        continentBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Testing continent bonus with 4 players\n ------------------------ \n");
                int players = 4;
                String filePath = filePath();
                new MapLoader(players, filePath, true);
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
                    Continent.setContinents(loadedMapObj.getContinents());
                    Territory.setTerritories(loadedMapObj.getTerritories());
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

        buttonPanel.setPreferredSize(new Dimension(350, 200));
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
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false);
            }
        });
        player3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 3 players\n ------------------------ \n");
                int players = 3;
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath, false);
            }
        });
        player4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 4 players\n ------------------------ \n");
                int players = 4;
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath,false);
            }
        });
        return startButtons;
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
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        System.out.println("DEBUG: Using the default map!\n---------------------------------------\n");
        return "default.map";
    }
}
