package com.GraphX;

import com.sun.javafx.geom.Edge;
import javafx.util.Pair;

import java.util.*;

import static java.lang.Integer.min;
import static java.lang.Integer.remainderUnsigned;

public class Algorithm {
    private int n = 0;
    private int m = 0;
    private Stack<Integer>[] masQ;
    public LinkedList<Integer>[] adjLists;
    private Stack<Pair<Integer, Integer>>[] masQS;
    public int numberOfNodes;
    public int k = 0;
    public int nZ = 0;
    private int[] num, ftr, rank;
    private int[] mrk;
    private int cmp;
    private int time = 0;
    static final int NIL = -1;
    public int ns;
    private StringBuilder sb = new StringBuilder();
    //  private Stack<Integer>[] masQs;

    public String fundamentalCycles(Graph g) {
        int i1 = 0;
        numberOfNodes = g.getAdjVertices().keySet().size();
        adjLists = new LinkedList[numberOfNodes];

        for (Map.Entry<Integer, List<Integer>> f : g.getAdjVertices().entrySet()) {
            adjLists[i1] = new LinkedList<Integer>();
            adjLists[i1].addAll(f.getValue());
            i1++;
        }
        for (List<Integer> f : adjLists) {
            Collections.sort(f);
        }

        num = new int[numberOfNodes];
        ftr = new int[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            num[i] = 0;
            ftr[i] = -1;
            n = n + 1;
            m = m + adjLists[i].size();
        }
        m = m / 2;
        masQ = new Stack[m - n + 1];
        for (int i = 0; i < m - n + 1; i++) {
            masQ[i] = new Stack<>();
        }
        cycle(0);
        StringBuilder stringBuilder = new StringBuilder("Cycles: ");
        for (int i = 0; i < masQ.length; i++) {
            //System.out.print(i + 1 + ": ");
            stringBuilder.append(i + 1 + ": ");
            Stack<Integer> stack = (Stack<Integer>) masQ[i].clone();
            while (!stack.empty()) {
                //  System.out.print(stack.pop() + 1 + ", ");
                stringBuilder.append(stack.pop() + 1 + ", ");
            }
            //System.out.println();
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void cycle(int i) {
        k = k + 1;
        num[i] = k;
        for (int j = 0; j < adjLists[i].size(); j++) {
            if (num[adjLists[i].get(j)] == 0) {
                ftr[adjLists[i].get(j)] = i;
                cycle(adjLists[i].get(j));
            } else if ((ftr[i] != adjLists[i].get(j)) && num[i] > num[adjLists[i].get(j)]) {
                save(i, adjLists[i].get(j), nZ);
                nZ++;
            }
        }
    }

    private void save(int i, int j, int nZ) {
        int z = i;
        while (z != j && z != -1) {
            masQ[nZ].push(z);
            z = ftr[z];
        }
        masQ[nZ].push(j);
        masQ[nZ].push(i);
    }

//ALGORITHM OF FUNDAMENTAL CUTS

    public String fundamentalCuts(Graph g) {
        fundamentalCycles(g);
        n = 0;
        m = 0;
        for (int i = 0; i < numberOfNodes; i++) {
            n++;
        }

        masQS = new Stack[n - 1];

        for (int i = 0; i < n - 1; i++) {
            masQS[i] = new Stack<Pair<Integer, Integer>>();
        }

        ns = 0;

        for (int i = 0; i < numberOfNodes; i++) {
            if (ftr[i] != -1) {
                CUT(i, ftr[i], ns);
                ns++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder("Cuts: ");
        for (int i = 0; i < masQS.length; i++) {
            stringBuilder.append(i + 1 + ": ");
            Stack<Pair<Integer, Integer>> stack = (Stack<Pair<Integer, Integer>>) masQS[i].clone();
            while (!stack.isEmpty()) {
                Pair<Integer, Integer> pair = stack.pop();
                stringBuilder.append("(" + (pair.getKey() + 1) + "," + (pair.getValue() + 1) + "),");
            }
            stringBuilder.append("\n");


        }
        return stringBuilder.toString();
    }


    private void CUT(int i, int i2, int ns) {
        masQS[ns].push(new Pair<>(i, i2));
        for (int k = 0; k < nZ; k++) {
            if (PROV(i, i2, k)) {
                Stack<Integer> temp = ((Stack<Integer>) masQ[k].clone());
                int r = temp.pop();
                int s = temp.pop();
                masQS[ns].push(new Pair<>(r, s));
            }
        }
    }


    private boolean PROV(int i, int i2, int k) {
        Stack<Integer> stack = (Stack<Integer>) masQ[k].clone();
        int ni = stack.pop();
        while (!stack.isEmpty()) {
            int nj = stack.pop();
            if (((ni == i) && nj == i2) || (ni == i2 && nj == i)) {
                return true;
            }
            ni = nj;
        }
        return false;
    }

    public String komponents(Graph g) {
        int i1 = 0;
        numberOfNodes = g.getAdjVertices().keySet().size();
        adjLists = new LinkedList[numberOfNodes];

        for (Map.Entry<Integer, List<Integer>> f : g.getAdjVertices().entrySet()) {
            adjLists[i1] = new LinkedList<Integer>();
            adjLists[i1].addAll(f.getValue());
            i1++;
        }
        mrk = new int[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            mrk[i] = 0;
        }
        cmp = 0;
        for (int i = 0; i < numberOfNodes; i++) {
            if (mrk[i] == 0) {
                cmp++;
                CMP(i, cmp);
            }
        }
        StringBuilder stringBuilder = new StringBuilder("Number of components ");
        stringBuilder.append(cmp + "\n");
        for (int i : mrk) {
            stringBuilder.append(i + ", ");
        }
        return stringBuilder.toString();


    }

    private void CMP(int i, int c) {
        mrk[i] = c;
        for (int j = 0; j < adjLists[i].size(); j++) {
            if (mrk[adjLists[i].get(j)] == 0) {
                CMP(adjLists[i].get(j), c);
            }
        }
    }


    public String AP(Graph g) {
        numberOfNodes = g.getAdjVertices().keySet().size();

        boolean visited[] = new boolean[numberOfNodes];
        int disc[] = new int[numberOfNodes];
        int low[] = new int[numberOfNodes];
        int parent[] = new int[numberOfNodes];
        boolean ap[] = new boolean[numberOfNodes]; // To store articulation points


        for (int i = 0; i < numberOfNodes; i++) {
            parent[i] = NIL;
            visited[i] = false;
            ap[i] = false;
        }


        for (int i = 0; i < numberOfNodes; i++)
            if (visited[i] == false)
                APUtil(g, i, visited, disc, low, parent, ap);

        StringBuilder stringBuilder = new StringBuilder("Articulation points:\n");
        for (int i = 0; i < numberOfNodes; i++)
            if (ap[i] == true)
                stringBuilder.append(i  + " ");
        return stringBuilder.toString();
    }

    private void APUtil(Graph g, int u, boolean visited[], int disc[],
                        int low[], int parent[], boolean ap[]) {

        // Count of children in DFS Tree
        int children = 0;

        visited[u] = true;

        disc[u] = low[u] = ++time;
        g.initAdjlist();

        Iterator<Integer> i = g.getAdjList()[u].iterator();
        while (i.hasNext()) {
            int v = i.next();

            if (!visited[v]) {
                children++;
                parent[v] = u;
                APUtil(g, v, visited, disc, low, parent, ap);


                low[u] = Math.min(low[u], low[v]);

                // (1) u is root of DFS tree and has two or more chilren.
                if (parent[u] == NIL && children > 1)
                    ap[u] = true;

                // (2) If u is not root and low value of one of its child

                if (parent[u] != NIL && low[v] >= disc[u])
                    ap[u] = true;
            }

            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }

    //SSC using recursion

    private void DFSUtil(Graph g, int v, boolean visited[]) {
        visited[v] = true;

        sb.append(v + " ");
        int n;

        Iterator<Integer> i = g.getAdjList()[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(g, n, visited);
        }
    }


    private void fillOrder(Graph g, int v, boolean visited[], Stack stack) {
        visited[v] = true;

        Iterator<Integer> i = g.getAdjList()[v].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                fillOrder(g, n, visited, stack);
        }

        stack.push(new Integer(v));
    }

    // The main function that finds and prints all strongly
    public String printSCCs(Graph g) {
        numberOfNodes = g.numberOfNodes;
        Stack stack = new Stack();
        g.initAdjlist();
        boolean visited[] = new boolean[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++)
            visited[i] = false;

        for (int i = 0; i < numberOfNodes; i++)
            if (visited[i] == false)
                fillOrder(g, i, visited, stack);

        Graph gr = g.getTranspose();

        for (int i = 0; i < numberOfNodes; i++)
            visited[i] = false;
        sb.append("This is SSC: \n");
        while (stack.empty() == false) {
            int v = (int) stack.pop();

            if (visited[v] == false) {
                DFSUtil(gr, v, visited);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    //Simple BFS
    public String BFS(Graph g, int s) {
        numberOfNodes = g.numberOfNodes;
        boolean visited[] = new boolean[numberOfNodes];

        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);
        g.initAdjlist();
        sb.append("First BFS search, starting from " + s + "\n");
        while (queue.size() != 0) {
            s = queue.poll();
            sb.append(s + " ");

            Iterator<Integer> i = g.getAdjList()[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return sb.toString();
    }

    //SIMPLE DFS
    private void DFSUT(Graph g, int v, boolean visited[]) {
        visited[v] = true;
        sb.append(v + " ");

        Iterator<Integer> i = g.getAdjList()[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFSUT(g, n, visited);
        }
    }

    public String DFS(Graph g, int v) {
        g.initAdjlist();
        boolean visited[] = new boolean[g.numberOfNodes];
        sb.append("First DFS search start from " + v + "\n");
        DFSUT(g, v, visited);
        return sb.toString();
    }

    //TOPOLOGICAL DFS SEARCH( NOT  KHAN"S TOPOLOGICAL SEARCH)

    private void topologicalSortUtil(Graph g,
                                     int v, boolean visited[],
                                     Stack<Integer> stack) {
        visited[v] = true;
        Integer i;


        Iterator<Integer> it = g.getAdjArrayArrayList().get(v).iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(g, i, visited, stack);
        }

        stack.push(v);
    }

    public String topologicalSort(Graph g) {
        g.initadjArrayArrayList();
        Stack<Integer> stack = new Stack<Integer>();
        numberOfNodes = g.numberOfNodes;
        boolean visited[] = new boolean[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++)
            visited[i] = false;

        for (int i = 0; i < numberOfNodes; i++)
            if (visited[i] == false)
                topologicalSortUtil(g, i, visited, stack);
        sb.append("Correct topological search:\n");
        while (stack.empty() == false)
            sb.append(stack.pop()).append(" ");
        return sb.toString();
    }

    //Total ways from A to B
    private int countWays(int mtrx[][], int vrtx,
                          int i, int dest, boolean visited[]) {
        if (i == dest) {

            return 1;
        }
        int total = 0;
        for (int j = 0; j < vrtx; j++) {
            if (mtrx[i][j] == 1 && !visited[j]) {

                visited[j] = true;

                total += countWays(mtrx, vrtx,
                        j, dest, visited);

                visited[j] = false;
            }
        }

        return total;
    }

    public String totalWays(int mtrx[][], int vrtx,
                            int src, int dest) {
        boolean[] visited = new boolean[vrtx];


        for (int i = 0; i < vrtx; i++) {
            visited[i] = false;
        }

        visited[src] = true;

        return "Total numbers of ways from " + (src + 1) + "to dest " + (dest + 1) + " " + String.valueOf(countWays(mtrx, vrtx, src, dest,
                visited));
    }


    //Eurelian cycles
    public String printEulerTour(Graph g) {
        numberOfNodes = g.numberOfNodes;
        Integer u = 0;
        Graph graph = null;
        try {
            graph = (Graph) g.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        graph.initAdjArrayList();
        for (int i = 0; i < numberOfNodes; i++) {
            if (graph.getAdjArrayList()[i].size() % 2 == 1) {
                u = i;
                break;
            }
        }
        sb.append("Euler cycle: \n");
        printEulerUtil(graph, u);
        return sb.toString();
    }

    private void printEulerUtil(Graph g, Integer u) {
        for (int i = 0; i < g.getAdjArrayList()[u].size(); i++) {
            Integer v = g.getAdjArrayList()[u].get(i);
            if (isValidNextEdge(g, u, v)) {
                sb.append(u + 1).append("-").append(v + 1).append(" ");
                g.removeEdge(u, v);
                printEulerUtil(g, v);
            }
        }
    }

    private boolean isValidNextEdge(Graph g, Integer u, Integer v) {

        if (g.getAdjArrayList()[u].size() == 1) {
            return true;
        }

        boolean[] isVisited = new boolean[numberOfNodes];
        int count1 = dfsCount(g, u, isVisited);

        g.removeEdge(u, v);
        isVisited = new boolean[numberOfNodes];
        int count2 = dfsCount(g, u, isVisited);

        g.addEdge(u, v);
        return (count1 > count2) ? false : true;
    }

    private int dfsCount(Graph g, Integer v, boolean[] isVisited) {
        isVisited[v] = true;
        int count = 1;
        for (int adj : g.getAdjArrayList()[v]) {
            if (!isVisited[adj]) {
                count = count + dfsCount(g, adj, isVisited);
            }
        }
        return count;
    }

    //Primas Algorithm, minimal tree

    private int minKey(int key[], Boolean mstSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numberOfNodes; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    private void printMST(int parent[], int graph[][]) {
        sb.append("Edge \tWeight\n");
        for (int i = 1; i < numberOfNodes; i++)
            sb.append(parent[i] + " - " + i + "\t" + graph[i][parent[i]] + "\n");
    }

    public String primMST(Graph g) {
        numberOfNodes = g.numberOfNodes;
        int parent[] = new int[numberOfNodes];

        int key[] = new int[numberOfNodes];

        Boolean mstSet[] = new Boolean[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0; // Make key 0 so that this vertex is
        parent[0] = -1; // First node is always root of MST

        for (int count = 0; count < numberOfNodes - 1; count++) {
            int u = minKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < numberOfNodes; v++)

                if (g.adjMatrix[u][v] != 0 && mstSet[v] == false && g.adjMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = g.adjMatrix[u][v];
                }

        }
        printMST(parent, g.adjMatrix);
        return sb.toString();
    }


    public String KraskalasAlgorithm(Graph graph) {
        graph.initArrayEdgelist();
        List<Graph.Edje> UT = new LinkedList<>();
        ftr = new int[graph.getEdjes().size()];
        rank = new int[graph.getEdjes().size()];
        for (int i = 0; i < graph.getEdjes().size(); i++) {
            ftr[i] = i;
            rank[i] = 0;
        }
        Collections.sort(graph.getEdjes());
        for (Graph.Edje edje : graph.getEdjes()) {
            if (Find(edje.getU()) != Find(edje.getV())) {
                UT.add(edje);
                Union(Find(edje.getU()), Find(edje.getV()));
            }
        }
        //UT.remove(UT.size()-1);
        for (Graph.Edje edge : UT) {
            sb.append(edge + "\n");
        }
        int sum = 0;
        for (Graph.Edje edje : UT) {
            sum += edje.getW();
        }
        sb.append(sum + "\n");
        return sb.toString();
    }

    private int Find(int i) {
        if (i != ftr[i] && ftr[i] != 0) {
            ftr[i] = Find(ftr[i]);
        }
        return ftr[i];
    }

    private void Union(int r, int s) {
        if (rank[r] >= rank[s]) {
            ftr[s] = r;
            if (rank[r] == rank[s]) {
                rank[r] = rank[r] + 1;
            } else {
                ftr[r] = s;
            }
        }
    }
//Djkstars stortest path algorithm
    private int minDistance(int dist[], Boolean sptSet[]) {

        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numberOfNodes; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    private void printSolution(int dist[], int n) {
        sb.append("Vertex\tDistatnce from Source\n");
        for (int i = 0; i < numberOfNodes; i++)
            sb.append((i+"\t"+dist[i]+"\n"));
    }

   public String dijkstra(Graph g, int src) {
        numberOfNodes = g.numberOfNodes;
        int dist[] = new int[numberOfNodes];

        Boolean sptSet[] = new Boolean[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < numberOfNodes - 1; count++) {
            int u = minDistance(dist, sptSet);

            sptSet[u] = true;

            for (int v = 0; v < numberOfNodes; v++)

                if (!sptSet[v] && g.adjMatrix[u][v] != 0 &&
                        dist[u] != Integer.MAX_VALUE && dist[u] + g.adjMatrix[u][v] < dist[v])
                    dist[v] = dist[u] + g.adjMatrix[u][v];
        }

        printSolution(dist, numberOfNodes);
        return sb.toString();
    }


    //Belman Ford
    public String BellmanFord(Graph g, int src)
    {

        numberOfNodes=g.numberOfNodes;
        int numberOfEdges=0;
        int dist[] = new int[numberOfNodes];

        for(int i=0;i<numberOfNodes;i++){
            for(int j=0;j<numberOfNodes;j++){
                if(!g.isOriented){
                    if(g.adjMatrix[i][j] != 0||g.adjMatrix[j][i]!=0){
                        numberOfEdges++;
                    }}
                else  if(g.isOriented){
                    if(g.adjMatrix[i][j]!=0){
                        numberOfEdges++;
                    }
                }
            }
        }
        for (int i = 0; i < numberOfNodes; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
        for(int i=0;i<g.adjMatrix.length;i++) {
            for (int j = 0; j < g.adjMatrix.length; j++) {
                if (!g.isOriented) {
                    if (g.adjMatrix[i][j] != 0 || g.adjMatrix[j][i] != 0) {
                        g.edjes.add(new Graph.Edje(i, j, g.adjMatrix[i][j] != 0 ? g.adjMatrix[i][j] : g.adjMatrix[j][i]));
                    }
                } else if (g.adjMatrix[i][j] != 0) {
                    g.edjes.add(new Graph.Edje(i, j, g.adjMatrix[i][j]));
                }
            }
        }
        Graph.Edje arr[]=new Graph.Edje[g.edjes.size()];
        for(int i=0;i<g.edjes.size();i++){
            arr[i]= (Graph.Edje) g.edjes.get(i);
        }

        for (int i = 1; i < numberOfNodes; ++i) {
            for (int j = 0; j < numberOfEdges; ++j) {
                int u = arr[j].getU();
                int v = arr[j].getV();
                int weight = arr[j].getW();
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        for (int j = 0; j < numberOfEdges; ++j) {
            int u = arr[j].getU();
            int v =arr[j].getV();
            int weight = arr[j].getW();
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                sb.append("Graph contains negative weight cycle");
                return sb.toString();
            }
        }
        printArr(dist, numberOfNodes);
        return sb.toString();
    }
 private  void printArr(int dist[], int V)
 {
     sb.append("Vertex Distance from Source\n");
     for (int i = 0; i < V; ++i)
         sb.append(i+"\t\t"+dist[i]+"\n");
 }


//
    public String floydWarshall(Graph orientedGraph) {
        int[][] graph = orientedGraph.adjMatrix;
        int vNum = graph.length;
        int INF = Integer.MAX_VALUE;
        int dist[][] = new int[vNum][vNum];
        for (int i = 0; i < vNum; i++) System.arraycopy(graph[i], 0, dist[i], 0, vNum);
        for (int k = 0; k < vNum; k++)
            for (int i = 0; i < vNum; i++)
                for (int j = 0; j < vNum; j++)
                    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
        for (int i = 0; i < vNum; i++) {
            for (int j = 0; j < vNum; j++)
                if(dist[i][j]<1000)
                sb.append(dist[i][j]+" ");
                else {
                    sb.append("Inf+ ");
                }
            sb.append("\n");
        }
        return sb.toString();
    }

    //Coloring Task
    public String greedyColoring(Graph g)
    {
        numberOfNodes=g.numberOfNodes;
        int result[] = new int[numberOfNodes];

        Arrays.fill(result, -1);

        result[0]  = 0;

        boolean available[] = new boolean[numberOfNodes];

        Arrays.fill(available, true);
        g.initAdjlist();
        for (int u = 1; u < numberOfNodes; u++)
        {
            Iterator<Integer> it = g.getAdjList()[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            int cr;
            for (cr = 0; cr < numberOfNodes; cr++){
                if (available[cr])
                    break;
            }

            result[u] = cr; // Assign the found color

            Arrays.fill(available, true);
        }

        for (int u = 0; u < numberOfNodes; u++)
            sb.append("Vertex "+u+"\t Color "+result[u]+"\n");
        return sb.toString();
    }
   private void bridgeUtil(Graph g,int u, boolean visited[], int disc[],
                    int low[], int parent[])
    {
        g.initAdjlist();
        visited[u] = true;

        disc[u] = low[u] = ++time;

        Iterator<Integer> i = g.getAdjList()[u].iterator();
        while (i.hasNext())
        {
            int v = i.next();  // v is current adjacent of u

            if (!visited[v])
            {
                parent[v] = u;
                bridgeUtil(g,v, visited, disc, low, parent);

                low[u]  = Math.min(low[u], low[v]);

                if (low[v] > disc[u])
                    sb.append(u+" "+v+"\n");
                    System.out.println(u+" "+v);
            }

            else if (v != parent[u])
                low[u]  = Math.min(low[u], disc[v]);
        }
    }


 public String    bridge(Graph g)
    {
        numberOfNodes=g.numberOfNodes;
        boolean visited[] = new boolean[numberOfNodes];
        int disc[] = new int[numberOfNodes];
        int low[] = new int[numberOfNodes];
        int parent[] = new int[numberOfNodes];


        for (int i = 0; i < numberOfNodes; i++)
        {
            parent[i] = NIL;
            visited[i] = false;
        }
        sb.append("Bridges in Graph:\n");
        for (int i = 0; i < numberOfNodes; i++)
            if (visited[i] == false)
                bridgeUtil(g,i, visited, disc, low, parent);
        return sb.toString();
    }
}

//    }
//
//
//
//
//
//
