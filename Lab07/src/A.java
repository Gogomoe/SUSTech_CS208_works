import java.io.*;
import java.util.*;

public class A {

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        EdgeWeightGraph graph = new EdgeWeightGraph(n * m);
        int[] lastLine = new int[m];
        int[] line = new int[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                line[j] = scanner.nextInt();

                int it = i * m + j;

                if (i != 0) {
                    int up = (i - 1) * m + j;
                    int weight = lastLine[j] ^ line[j];
                    graph.addEdge(new Edge(it, up, weight));
                }

                if (j != 0) {
                    int left = i * m + j - 1;
                    int weight = line[j - 1] ^ line[j];
                    graph.addEdge(new Edge(it, left, weight));
                }
            }
            lastLine = line;
            line = new int[m];
        }

        Kruskal kruskal = new Kruskal(graph);

        long cost = 0;
        for (Edge edge : kruskal.mst) {
            cost += edge.weight;
        }

        System.out.println(cost);

        new LinkedList<>().sort();
    }


    public static class Kruskal {

        private ArrayList<Edge> mst;

        public Kruskal(EdgeWeightGraph graph) {
            int nodes = graph.nodes;
            mst = new ArrayList<>();
            UnionFindSet uf = new UnionFindSet(nodes);

            Edge[] arr = graph.edgeList.stream().sorted(new Comparator<Edge>() {
                @Override
                public int compare(Edge o1, Edge o2) {
                    return o1.weight - o2.weight;
                }
            }).toArray(Edge[]::new);
            int index = 0;

            while (index != arr.length && mst.size() < nodes - 1) {
                Edge e = arr[index++];
                int a = e.p1, b = e.p2;
                if (uf.connected(a, b)) {
                    continue;
                }
                uf.union(a, b);
                mst.add(e);
            }
        }

    }

    private static class EdgeWeightGraph {

        private final int nodes;
        private int edges;

        private ArrayList<Edge>[] adj;
        private ArrayList<Edge> edgeList = new ArrayList<>();

        EdgeWeightGraph(int nodes) {
            this.nodes = nodes;
            this.edges = 0;
            adj = new ArrayList[nodes];
            for (int i = 0; i < adj.length; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        void addEdge(Edge e) {
            edgeList.add(e);
            adj[e.p1].add(e);
            adj[e.p2].add(e);
            edges++;
        }
    }

    private static class Edge {
        private final int p1;
        private final int p2;
        private final int weight;

        public Edge(int p1, int p2, int weight) {
            this.p1 = p1;
            this.p2 = p2;
            this.weight = weight;
        }

        public String toString() {
            return p1 + "<->" + p2 + " " + weight;
        }

    }

    private static class UnionFindSet {

        int n;
        int[] parents;

        public UnionFindSet(int n) {
            this.n = n;
            this.parents = new int[n];
            for (int i = 0; i < n; i++) {
                this.parents[i] = i;
            }
        }

        public int find(int it) {
            if (parents[it] == it) {
                return it;
            }
            int parent = find(parents[it]);
            parents[it] = parent;
            return parent;
        }

        public boolean connected(int a, int b) {
            return find(a) == find(b);
        }

        public void union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa == pb) {
                return;
            }
            parents[pa] = pb;
        }
    }

    private static QuickReader scanner = new QuickReader();

    private static class QuickReader {
        final private int BUFFER_SIZE = 1 << 20;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public QuickReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
