package com.GraphX;

import javafx.util.Pair;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphVisualiser extends JFrame {

    private JPanel mainPanel;
    private JButton createGraphButton;
    private JCheckBox orientedCheckBox;
    private JCheckBox weightedCheckBox;
    private JSlider nodesSlider;
    private JLabel numberOfNodesLabel;
    private JTabbedPane tabbedPane;
    private JPanel schemePanel;
    private JScrollPane matrixScrollPanel;
    private JPanel Algorithms;
    private JButton FundamentalCycles;
    private JButton FundamentalCuts;
    private JButton floydWarshallButton;
    private JButton fordBelmanButton;
    private JButton kraskalSAlgorithmButton;
    private JButton FindComponents;
    private JButton PrimaButton;
    private JButton eurelianPathButton;
    private JButton FindNumberFromAtoB;
    private JButton TopologicalSearch;
    private JButton DFS;
    private JButton BFS;
    private JButton BridgesandBlocks;
    private JButton SSCButton;
    public JTextArea textArea1;
    private JButton DjkstraButton;
    private JButton coloringVertexesButton;
    private JButton BridgesButton;
    private JTextField[][] matrix;
    private Graph graph;
    private int s = 0;

    public GraphVisualiser() {
        createFrame();
        AtomicBoolean sliderMoved = new AtomicBoolean(false);
        nodesSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderMoved.set(true);
                numberOfNodesLabel.setText("Число вершин: " + nodesSlider.getValue());
                GraphVisualiser.this.refreshMatrix();
            }
        });
        createGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sliderMoved.get()) {
                    graph.isOriented = orientedCheckBox.isSelected();
                    graph.isWeighted = weightedCheckBox.isSelected();
                    GraphVisualiser.this.refreshGraph();
                    GraphScheme graphScheme = new GraphScheme(graph);
                    schemePanel.removeAll();
                    schemePanel.add(graphScheme);
                   GraphVisualiser.this.repaint();
                    if(graph.isOriented){
                        kraskalSAlgorithmButton.setEnabled(false);
                        PrimaButton.setEnabled(false);
                        BridgesandBlocks.setEnabled(false);
                        BridgesButton.setEnabled(false);
                        eurelianPathButton.setEnabled(false);
                        SSCButton.setEnabled(true);
                        fordBelmanButton.setEnabled(true);
                        FindNumberFromAtoB.setEnabled(true);
                    }  else {
                        kraskalSAlgorithmButton.setEnabled(true);
                        PrimaButton.setEnabled(true);
                        BridgesButton.setEnabled(true);
                        BridgesandBlocks.setEnabled(true);
                        eurelianPathButton.setEnabled(true);
                        SSCButton.setEnabled(false);
                        FindNumberFromAtoB.setEnabled(false);
                 }
                    if(!graph.isWeighted){
                        floydWarshallButton.setEnabled(false);
                        fordBelmanButton.setEnabled(false);
                        DjkstraButton.setEnabled(false);
                        PrimaButton.setEnabled(false);
                        kraskalSAlgorithmButton.setEnabled(false);
                    } else {
                        floydWarshallButton.setEnabled(true);
                        if(graph.isOriented){
                            fordBelmanButton.setEnabled(true);
                        }  else {
                            fordBelmanButton.setEnabled(false);
                        }
                        DjkstraButton.setEnabled(true);
                        PrimaButton.setEnabled(true);
                        kraskalSAlgorithmButton.setEnabled(true);
                    }
                }
            }
        });
        FundamentalCycles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.fundamentalCycles(graph);
                textArea1.setText(a);

            }
        });
        FundamentalCuts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.fundamentalCuts(graph);
                textArea1.setText(a);
            }
        });
        FindComponents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a =algorithm.komponents(graph);
               textArea1.setText(a);
            }
        });
        BridgesandBlocks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.AP(graph);
                textArea1.setText(a);
            }
        });
        SSCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.printSCCs(graph);
                textArea1.setText(a);
            }
        });
        BFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a =algorithm.BFS(graph, Integer.parseInt(JOptionPane
                .showInputDialog("Input a number of vertex from wich start BFS")));
                textArea1.setText(a);
            }
        });
        DFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Algorithm algorithm=new Algorithm();
               String a=algorithm.DFS(graph,Integer.parseInt(JOptionPane
               .showInputDialog("Input a number of vertex from wich start DFS")));
               textArea1.setText(a);
            }
        });
        TopologicalSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.topologicalSort(graph);
                textArea1.setText(a);
            }
        });
        FindNumberFromAtoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String  a =algorithm.totalWays(graph.adjMatrix,graph.numberOfNodes,
                        Integer.parseInt(JOptionPane.showInputDialog("Input a sourse vertex"))-1,
                        Integer.parseInt(JOptionPane.showInputDialog("Input a destination vertex"))-1);
                textArea1.setText(a);
            }
        });
        eurelianPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.printEulerTour(graph);
                textArea1.setText(a);
            }
        });
        PrimaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.primMST(graph);
                textArea1.setText(a);
            }
        });
        kraskalSAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a=algorithm.KraskalasAlgorithm(graph);
                textArea1.setText(a);
            }
        });
         DjkstraButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                Algorithm algorithm=new Algorithm();
                String a =algorithm.dijkstra(graph,Integer.parseInt(JOptionPane.showInputDialog("Enter a sourse elem")));
                textArea1.setText(a);
              }
         });
         fordBelmanButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Algorithm algorithm=new Algorithm();
                 String a=algorithm.BellmanFord(graph,Integer.parseInt(JOptionPane.showInputDialog("Enter a sourse element")));
                 textArea1.setText(a);
             }
         });
         floydWarshallButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Algorithm algorithm=new Algorithm();
                 String a =algorithm.floydWarshall(graph);
                 textArea1.setText(a);
             }
         });
         coloringVertexesButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Algorithm algorithm=new Algorithm();
                 String a =algorithm.greedyColoring(graph);
                 textArea1.setText(a);
             }
         });
         BridgesButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Algorithm algorithm=new Algorithm();
                 String a=algorithm.bridge(graph);
                 textArea1.setText(a);
             }
         });
    }



    private void createFrame() {
        setTitle("Визуализатор графов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setMinimumSize(new Dimension(600, 700));
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        schemePanel.setLayout(new GridLayout());
        setVisible(true);
    }

    private void refreshMatrix() {
        graph = new Graph(nodesSlider.getValue());
        int size = graph.adjMatrix.length + 1;
        JPanel matrixPanel = new JPanel();
        matrix = new JTextField[size][size];
        matrixPanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = new JTextField();
                matrix[i][j].setText("0");
                matrix[i][j].setPreferredSize(new Dimension(80, 80));
                matrix[i][j].setHorizontalAlignment(JTextField.CENTER);
                matrix[i][j].setFont(new Font("Segoe UI", Font.BOLD, 20));
                matrixPanel.add(matrix[i][j]);
            }
        }
        matrix[0][0].setText("\\");
        //char chr = 'A';
        for (int i = 1; i < size; i++) {
            matrix[0][i].setText(String.valueOf(i)); //chr
            matrix[i][0].setText(String.valueOf(i)); //chr++
        }
        matrixScrollPanel.setLayout(new ScrollPaneLayout());
        matrixScrollPanel.getViewport().add(matrixPanel);
    }

    private void refreshGraph() {
        int size = graph.adjMatrix.length;
        graph.adjMatrix=new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                try {
                    graph.adjMatrix[i][j] = Integer.parseInt(matrix[i + 1][j + 1].getText()); //исключение
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Неверный формат", "Ошибка", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        for (int i = 1; i < size + 1; i++) {
            graph.nodeList[i - 1] = new Node(matrix[i][0].getText());
        }
        graph.initAdjVerticles();
    }

}
