public class Graph {
    Vertex vertList[];
    int adjMat[][];
    int verts;
    Stack stack;
    Queue queue;
    public Graph(int size){
        vertList = new Vertex[size];
        adjMat = new int[size][size];
        stack = new Stack(size);
        queue = new Queue(size);
    }    
    public void addVertex(Vertex v){
        //check empty slot
        vertList[verts++] = v;
    }    
    public void addEdge(int from, int to){
        adjMat[from][to] = 1;
        adjMat[to][from] = 1; //undirected
    }
    
    public void displayAdjMatrix() {
        // Print column headers
        System.out.print("   ");
        for (int i = 0; i < verts; i++) {
            System.out.print(vertList[i].label + " ");
        }
        System.out.println();
    
        // Print each row
        for (int i = 0; i < verts; i++) {
            System.out.print(vertList[i].label + "  ");
            for (int j = 0; j < verts; j++) {
                System.out.print(adjMat[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public int getAdjacentVertex(int row){
        for(int col=0; col<verts; col++){
            if(adjMat[row][col]==1 && vertList[col].visited == false)
                return col;
        }
        return -1;
    }
    public void displayVertex(int index){
        System.out.print(vertList[index].label);
    }
    public void DFS(){
        vertList[0].visited = true;
        displayVertex(0);
        stack.push(0);
        while(!stack.isEmpty()){
            int adjVert = getAdjacentVertex(stack.peek());
            if(adjVert == -1)
                stack.pop();
            else{
                vertList[adjVert].visited = true;
                displayVertex(adjVert);
                stack.push(adjVert);
            }
        }
        resetFlags();
        System.out.println();
    }
    public void resetFlags(){
        for(int i=0; i<verts; i++)
            vertList[i].visited = false;
    }
    public void BFS(){
        vertList[0].visited = true;
        displayVertex(0);
        queue.enQueue(0);
    
        while(!queue.isEmpty()){
            int v1 = queue.deQueue();
            int v2;
            while((v2 = getAdjacentVertex(v1)) != -1){
                vertList[v2].visited = true;
                displayVertex(v2);
                queue.enQueue(v2);
            }
        }
        resetFlags();
        System.out.println();
    }
    
    public static void main(String[] args) {
        Graph g = new Graph(10);
    
        // Add vertices A to E
        g.addVertex(new Vertex('A')); // index 0
        g.addVertex(new Vertex('B')); // index 1
        g.addVertex(new Vertex('C')); // index 2
        g.addVertex(new Vertex('D')); // index 3
        g.addVertex(new Vertex('E')); // index 4
    
        // Add edges to build the graph
        g.addEdge(0, 1); // A-B
        g.addEdge(0, 2); // A-C
        g.addEdge(1, 3); // B-D
        g.addEdge(1, 4); // B-E
    
        // Display adjacency matrix
        System.out.println("Adjacency Matrix:");
        g.displayAdjMatrex();
        System.out.println();
    
        // Depth First Search
        System.out.print("DFS traversal: ");
        g.DFS();
    
        // Breadth First Search
        System.out.print("BFS traversal: ");
        g.BFS();
    }
    
}