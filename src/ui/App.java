package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import acm.graphics.GCanvas;

import javax.swing.JTextField;

public class App extends JFrame {

    private JPanel contentPanel;

    private JButton startButton;
    private JButton stopButton;
    private JButton generateButton;
    private JButton resetButton;

    private JLabel sizeLabel;
    private JTextField sizeField;

    private GCanvas screen;
    private UIGrid lifeGrid;

    private DataGrid datagrid;

    private int delay;
    private boolean playing;
    private final int cellSize = 5;

    private PlayThread playThread;

    private LinkedList<int[][]> generations;

    public App() {
        super("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildGUI();
        regListeners();
        delay = 100;
        setVisible(true);
        startButton.setEnabled(false);
        resetButton.setEnabled(false);
        stopButton.setEnabled(false);
        generations = new LinkedList<>();
    }

    private void buildGUI() {

        generateButton = new JButton("Generate Life Grid");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        sizeLabel = new JLabel("Life Grid size:");
        resetButton = new JButton("Reset");

        sizeField = new JTextField(20);

        screen = new GCanvas();
        screen.setBackground(getBackground());

        contentPanel = new JPanel(new MigLayout("fill,center", "[50%][50%]", "[][][][grow]"));
        contentPanel.add(startButton, "growx");
        contentPanel.add(stopButton, "growx,wrap");
        contentPanel.add(sizeLabel, "grow,align right");
        contentPanel.add(sizeField, "wrap,growx");
        contentPanel.add(resetButton, "growx");
        contentPanel.add(generateButton, "wrap,growx");
        contentPanel.add(screen, "spanx,spany,grow,align center");
        setContentPane(contentPanel);
        pack();
    }

    public void regListeners() {
        generateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int n = 0;
                String size = sizeField.getText();
                try {
                    n = Integer.parseInt(size);
                    if (n <= 0)
                        throw new Exception();
                } catch (Exception exp) {
                    n = -1;
                }
                if (n > 0) {
                    lifeGrid = new UIGrid(n, cellSize);
                    datagrid = new DataGrid(n);

                    screen.add(lifeGrid, (screen.getWidth() - lifeGrid.getWidth()) / 2, (screen.getHeight() - lifeGrid.getWidth()) / 2);
                    SwingUtilities.updateComponentTreeUI(screen);
                    generateButton.setEnabled(false);
                    lifeGrid.startListening();
                    startButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    sizeField.setEnabled(false);

                }
            }
        });

        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                lifeGrid.stopListening();

                datagrid.setMatrix(lifeGrid.getDataGrid());

                playThread = new PlayThread();
                playThread.start();
            }
        });

        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                playThread.stopThread();
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                lifeGrid.startListening();
            }
        });

        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                datagrid.clear();
                lifeGrid.reset();
            }
        });
    }

    public static void main(String[] args) {
        App app = new App();
    }

    class PlayThread extends Thread {
        boolean playing = true;

        public void run() {
            while (playing) {
                generations.add(datagrid.getMatrix());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e1) {
                    interrupt();
                }
                int[][] matrix = datagrid.evolve();
                lifeGrid.reload(matrix);
                screen.repaint();
            }

        }

        ;

        public void stopThread() {
            playing = false;
        }
    }

}
