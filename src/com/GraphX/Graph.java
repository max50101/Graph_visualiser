package com.GraphX;

import javafx.util.Pair;

import java.io.Closeable;
import java.util.*;

public class Graph implements Cloneable {
    public final int numberOfNodes;
    public int numberOfLinks;
    public int[][] adjMatrix;                            //матрица смежности
    private Map<Integer, List<Integer>> adjVertices=new HashMap<>();//списки смежности
    public Node[] nodeList;                              //массив вершин
    public boolean isOriented = false;
    public boolean isWeighted = false;
    private LinkedList<Integer>[] adjList;
    public ArrayList<Edje> getEdjes() {
        return edjes;
    }

    public  ArrayList edjes = new ArrayList();
    private List<Integer>[]adjArrayList; //еще одна реалиизация списков смежности
    private ArrayList<ArrayList<Integer>> adjArrayArrayList; //и еще одна

     public static class Edje implements Comparable<Edje> {
        private int u;
        private int v;
        private int w;

        public int getU() {
            return u;
        }

        public int getV() {
            return v;
        }

        public int getW() {
            return w;
        }

        public Edje(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }


        public int compareTo(Edje e) {
            if (w != e.w) return w < e.w ? -1 : 1;
            return 0;
        }

        @Override
        public String toString() {
            return "Edje{" +
                    "u=" + u +
                    ", v=" + v +
                    ", w=" + w +
                    '}';
        }
    }
    public void initArrayEdgelist(){
        for(int i=0;i<adjMatrix.length;i++){
            for(int j=0;j<adjMatrix.length;j++){
                if(adjMatrix[i][j]>0||adjMatrix[j][i]>0){
                    edjes.add(new Edje(i,j,adjMatrix[i][j]>0?adjMatrix[i][j]:adjMatrix[j][i]));
                }
            }
        }
    }

    public LinkedList<Integer>[] getAdjList() {
        return adjList;
    }

    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        nodeList = new Node[numberOfNodes];
        adjMatrix = new int[numberOfNodes][numberOfNodes];
        adjList = new LinkedList[numberOfNodes];
        adjArrayList=new ArrayList[numberOfNodes];
        adjArrayArrayList=new ArrayList<>(numberOfNodes);
    }

    public int weight(int u, int v) {
        return adjMatrix[u][v];
    }



    public void initAdjVerticles() {
        for(int i=0;i<adjMatrix.length;i++){
            adjVertices.putIfAbsent(i,new ArrayList<>());
        }
        for(int i=0;i<adjMatrix.length;i++){
            for(int j=0;j<adjMatrix.length;j++){
                if(adjMatrix[i][j]>0||adjMatrix[j][i]>0)
                {
                    adjVertices.get(i).add(j);
                }
            }
        }
    }
    public void initAdjlist(){
        for (int i = 0; i < numberOfNodes; i++) {
        adjList[i] = new LinkedList<>();
        for (int j = 0; j < numberOfNodes; j++) {
            if(!isOriented) {
                if (adjMatrix[i][j] != 0 || adjMatrix[j][i] != 0) {
                    adjList[i].add(j);
                    numberOfLinks++;
                }
            }
             else   if(adjMatrix[i][j]!=0){
                    adjList[i].add(j);
                    numberOfLinks++;
                }
            }

    }
    numberOfLinks = numberOfLinks / 2;
     }

    public void initAdjArrayList(){
        for(int i=0;i<numberOfNodes;i++){
            adjArrayList[i]=new ArrayList<>();
            for(int j=0;j<numberOfNodes;j++){
                if(!isOriented) {
                    if (adjMatrix[i][j] != 0||adjMatrix[j][i]!=0) {
                        adjArrayList[i].add(j);
                    }
                }else{
                    if(adjMatrix[i][j]!=0){
                        adjArrayList[i].add(j);
                    }
                }
            }
        }
    }

    public List<Integer>[] getAdjArrayList() {
        return adjArrayList;
    }

    public Map<Integer, List<Integer>> getAdjVertices() {
        return adjVertices;
    }
    public void initadjArrayArrayList(){
        for(int i=0;i<numberOfNodes;i++){
            adjArrayArrayList.add(new ArrayList<Integer>());
        }
        for(int i=0;i<numberOfNodes;i++){
            for(int j=0;j<numberOfNodes;j++){
                if(adjMatrix[i][j]>0){
                    adjArrayArrayList.get(i).add(j);
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> getAdjArrayArrayList() {
        return adjArrayArrayList;
    }

    public  Graph getTranspose()
    {
        Graph graph = new Graph(numberOfNodes);
        graph.initAdjlist();
        for (int v = 0; v < numberOfNodes; v++)
        {
            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i =getAdjList()[v].listIterator();
            while(i.hasNext())
                graph.adjList[i.next()].add(v);
        }
        return graph;
    }
    public void addEdge(Integer u, Integer v)
    {
        adjArrayList[u].add(v);
        adjArrayList[v].add(u);
    }

    public void removeEdge(Integer u, Integer v)
    {
        adjArrayList[u].remove(v);
        adjArrayList[v].remove(u);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
