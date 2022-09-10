package com.GraphX;

//Вершины графа описываются этим классом
public class Node {
    public String name;
    public boolean isVisited;

    public Node(String name) {
        this.name = name;
        isVisited = false;
    }
}
