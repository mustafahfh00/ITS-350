public class Graph {
    Vertex[] vertList;
    int[][] adjMat;
    int verts;
    int size;

    public Graph(int size) {
        this.size = size;
        vertList = new Vertex[size];
        adjMat = new int[size][size];
    }

    public void addVertex(Vertex v) {
        vertList[verts++] = v;
    }

    public void addEdge(int from, int to) {
        adjMat[from][to] = 1;
        adjMat[to][from] = 1; // Undirected
    }

    public void displayAdjMatrex() {
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < verts; i++) {
            for (int j = 0; j < verts; j++) {
                System.out.print(adjMat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getAdjacentVertex(int row) {
        for (int col = 0; col < verts; col++) {
            if (adjMat[row][col] == 1 && !vertList[col].visited) {
                return col;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addVertex(new Vertex("A"));
        g.addVertex(new Vertex("B"));
        g.addVertex(new Vertex("C"));
        g.addVertex(new Vertex("D"));
        g.addVertex(new Vertex("E"));

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        g.displayAdjMatrex();

        System.out.println("First unvisited adjacent to vertex 0: " + g.getAdjacentVertex(0));
    }
}
