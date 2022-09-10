package com.GraphX;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GraphScheme extends JPanel {
    private final Graph graph;

    public GraphScheme(Graph graph) {
        this.graph = graph;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

        int n = graph.adjMatrix.length;                     //Число вершин графа
        double t, x, y;
        double x0 = getWidth() / 2d;                              //Середины
        double y0 = getHeight() / 2d;                             //координат
        double mainR = 4 * Math.min(x0, y0) / 4.7;                //Радиус основной окружности
        double nodeR = Math.abs(Math.min(x0, y0) - mainR) / 3.5;  //Радиус вершины
        Point2D.Double[] nodes = new Point2D.Double[n];

        g2.translate(x0, y0);                                     //Привожу начало (0; 0) координат в центр
        g2.setStroke(new BasicStroke(1.5f));                //Ширина линий

        for (int i = 0; i < n; i++) {
            t = 2 * Math.PI * i / n;                               //Поворот в радианах
            x = mainR * Math.cos(t);                               //Пармаметрическое
            y = mainR * Math.sin(t);                               //уравнение окружности
            nodes[i] = new Point2D.Double(x, y);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph.adjMatrix[i][j] != 0) {
                    if (i == j) {                                  //Петля
                        t = 2 * Math.PI * i / n;
                        x = (mainR + nodeR + 3) * Math.cos(t);
                        y = (mainR + nodeR + 3) * Math.sin(t);
                        g2.draw(new Ellipse2D.Double(x - nodeR, y - nodeR, 2.2 * nodeR, 2.2 * nodeR));
                    }
                    g2.draw(new Line2D.Double(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y)); //Ребро
                    if (graph.isOriented && i != j) {
                        g2.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
                        double len = Math.sqrt(Math.pow((nodes[i].x - nodes[j].x), 2) + Math.pow((nodes[i].y - nodes[j].y), 2));
                        double dist = len * 5;
                        double xc = nodes[j].x + (nodes[i].x - nodes[j].x) / dist * len;
                        double yc = nodes[j].y + (nodes[i].y - nodes[j].y) / dist * len;
                        g2.draw(new Line2D.Double(nodes[j].x, nodes[j].y, xc, yc));
                    }
                    g2.setColor(Color.red);
                    g2.setStroke(new BasicStroke(1.5f));

                    if (graph.isWeighted && graph.adjMatrix[i][j] != 0) {
                        Point2D.Double middle = new Point2D.Double((nodes[i].x + nodes[j].x) / 2, (nodes[i].y + nodes[j].y) / 2);
                        if (i == j)
                            g2.drawString(String.valueOf(graph.adjMatrix[i][j]), (float) (middle.x+ nodeR), (float) (middle.y + nodeR));
                        else
                            g2.drawString(String.valueOf(graph.adjMatrix[i][j]), (float) (middle.x), (float) (middle.y + 15));
                    }
                    g2.setColor(Color.black);
                }

            }
        }
        for (int i = 0; i < n; i++) {
            g2.setColor(new Color(81, 206, 27));
            g2.fill(new Ellipse2D.Double(nodes[i].x - nodeR, nodes[i].y - nodeR, 2 * nodeR, 2 * nodeR));
            g2.setColor(Color.black);
                    g2.drawString(graph.nodeList[i].name, (float) (nodes[i].x - nodeR + 10), (float) (nodes[i].y - nodeR + 17));
        }

    }

}