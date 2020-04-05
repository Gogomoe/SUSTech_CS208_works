import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class B {

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();

        EdgeWeightGraph graph = new EdgeWeightGraph(n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                int it = scanner.nextInt();
                graph.addEdge(new Edge(i, j, it));
            }
        }

        Kruskal kruskal = new Kruskal(graph);
        long cost = 0;
        for (Edge edge : kruskal.mst) {
            cost += edge.weight;
        }

        System.out.println(cost);

    }


    public static class Kruskal {

        private ArrayList<Edge> mst;

        public Kruskal(EdgeWeightGraph graph) {
            int nodes = graph.nodes;
            mst = new ArrayList<>();

            MinPQ pq = MinPQ.fromArray(graph.edgeList);
            UnionFindSet uf = new UnionFindSet(nodes);

            while (pq.size > 0 && mst.size() < nodes - 1) {
                Edge e = pq.deleteMin();
                int a = e.p1, b = e.p2;
                if (uf.connected(a, b)) {
                    continue;
                }
                uf.union(a, b);
                mst.add(e);
            }
        }

    }

    private static class MinPQ {

        private Edge[] arr;
        private int size;

        Comparator<Edge> comp = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        };

        static MinPQ fromArray(Collection<Edge> edges) {
            MinPQ p = new MinPQ();
            p.arr = new Edge[edges.size() + 1];
            p.size = edges.size();

            int pos = 1;
            for (Edge edge : edges) {
                p.arr[pos] = edge;
                pos++;
            }
            for (int i = p.size / 2; i >= 1; i--) {
                p.sink(i);
            }
            return p;
        }

        private void swim(int index) {
            while (index > 1 && comp.compare(arr[index], arr[index / 2]) < 0) {
                swap(index, index / 2);
                index /= 2;
            }
        }

        private void sink(int index) {
            while (index * 2 <= size) {
                int j = index * 2;
                if (j + 1 <= size && comp.compare(arr[j + 1], arr[j]) < 0) {
                    j++;
                }
                if (comp.compare(arr[index], arr[j]) > 0) {
                    swap(index, j);
                } else {
                    break;
                }
                index = j;
            }
        }

        private Edge deleteMin() {
            Edge min = arr[1];
            swap(1, size);
            size--;
            sink(1);
            return min;
        }

        private void insert(Edge x) {
            size++;
            arr[size] = x;
            swim(size);
        }

        private void swap(int i1, int i2) {
            Edge temp = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = temp;
        }

    }

    private static class EdgeWeightGraph {

        private final int nodes;
        private ArrayList<Edge> edgeList = new ArrayList<>();

        EdgeWeightGraph(int nodes) {
            this.nodes = nodes;
        }

        void addEdge(Edge e) {
            edgeList.add(e);
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
