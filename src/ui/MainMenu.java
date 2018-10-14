package ui;

import map_editor.StartEditor;
import utils.MapLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width, height;

    public MainMenu(String title, int width, int height) {
        super(title);
        this.width = width;
        this.height = height;

        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.width, this.height);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        //menuBar.add(newGame());
        menuBar.add(mapEditor());
        menuBar.add(exit());
        setJMenuBar(menuBar);

        getContentPane().add(startButtons());

        // pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(true);
    }

//    private JMenu newGame() {
//        JMenu newGame = new JMenu("New Game");
//        JMenuItem player2 = new JMenuItem("2 Players");
//        JMenuItem player3 = new JMenuItem("3 Players");
//        JMenuItem player4 = new JMenuItem("4 Players");
//        newGame.add(player2);
//        newGame.add(player3);
//        newGame.add(player4);
//
//        player2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                System.out.println ("DEBUG: Chosen 2 players\n ------------------------ \n");
//                int players = 2;
//                String filePath = filePath();
//                MapLoader loader = new MapLoader(players, filePath);
//            }
//        });
//        player3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                System.out.println ("DEBUG: Chosen 3 players\n ------------------------ \n");
//                int players = 3;
//                String filePath = filePath();
//                MapLoader loader = new MapLoader(players, filePath);
//            }
//        });
//        player4.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                System.out.println ("DEBUG: Chosen 4 players\n ------------------------ \n");
//                int players = 4;
//                String filePath = filePath();
//                MapLoader loader = new MapLoader(players, filePath);
//            }
//        });
//        return newGame;
//    }
    private JMenu mapEditor() {
        JMenu mapEditor = new JMenu("Map Editor");
        JMenuItem editorCLI = new JMenuItem("Open map editor with CLI");
        mapEditor.add(editorCLI);
        JMenuItem editorGUI = new JMenuItem("Open map editor with GUI");
        mapEditor.add(editorGUI);

        editorCLI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Launch CLI Map Editor\n ------------------------ \n");
                StartEditor editor = new StartEditor();
            }
        });
        editorGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Launch GUI Map Editor\n ------------------------ \n");
            }
        });
        return mapEditor;
    }
    private JMenu exit() {
        JMenu exit = new JMenu("Exit");
        JMenuItem quit = new JMenuItem(new ExitAction());
        exit.add(quit);
        return exit;
    }
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
                MapLoader loader = new MapLoader(players, filePath);
            }
        });
        player3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 3 players\n ------------------------ \n");
                int players = 3;
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath);
            }
        });
        player4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("DEBUG: Chosen 4 players\n ------------------------ \n");
                int players = 4;
                String filePath = filePath();
                MapLoader loader = new MapLoader(players, filePath);
            }
        });
        return startButtons;
    }
    class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        ExitAction() {
            putValue(NAME, "Quit");
        }
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
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