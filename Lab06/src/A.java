import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class A {
    public static void main(String[] args) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        EdgeWeightGraph graph = new EdgeWeightGraph(n);

        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            int weight = scanner.nextInt();

            graph.addEdge(new DirectedEdge(from, to, weight));
        }

        Dijkstra d = new Dijkstra(graph, 0);
        int now = n - 1;
        long result = 1;

        while (now != 0) {
            DirectedEdge edge = d.edgeTo[now];
            result = (result * edge.originWeight) % 19260817;
            now = edge.from;
        }
        System.out.println(result);
    }

    private static class Dijkstra {

        private DirectedEdge[] edgeTo;
        private double[] distTo;
        private PriorityQueue<IntDoublePair> pq;

        private static final int INFINITY = -1;

        public Dijkstra(EdgeWeightGraph graph, int source) {
            int nodes = graph.nodes;
            edgeTo = new DirectedEdge[nodes];
            distTo = new double[nodes];
            pq = new PriorityQueue<>(nodes, Comparator.comparingDouble(it -> it.weight));

            for (int i = 0; i < nodes; i++) {
                distTo[i] = INFINITY;
            }
            distTo[source] = 0;

            pq.add(new IntDoublePair(source, 0));
            while (!pq.isEmpty()) {
                relax(graph, pq.poll().key);
            }
        }

        private void relax(EdgeWeightGraph graph, int node) {
            for (DirectedEdge e : graph.adj[node]) {
                if (distTo[e.to] == INFINITY || distTo[e.to] > distTo[e.from] + e.weight) {
                    double oldDist = distTo[e.to];
                    edgeTo[e.to] = e;
                    distTo[e.to] = distTo[e.from] + e.weight;

                    IntDoublePair query = new IntDoublePair(e.to, oldDist);
                    if (pq.contains(query)) {
                        pq.remove(query);
                        pq.add(new IntDoublePair(e.to, distTo[e.to]));
                    } else {
                        pq.add(new IntDoublePair(e.to, distTo[e.to]));
                    }
                }
            }
        }

    }

    private static class EdgeWeightGraph {

        private final int nodes;
        private int edges;

        private ArrayList<DirectedEdge>[] adj;

        EdgeWeightGraph(int nodes) {
            this.nodes = nodes;
            this.edges = 0;
            adj = new ArrayList[nodes];
            for (int i = 0; i < adj.length; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        void addEdge(DirectedEdge e) {
            adj[e.from].add(e);
            edges++;
        }
    }

    private static class DirectedEdge {
        private final int from;
        private final int to;
        private final int originWeight;
        private final double weight;

        DirectedEdge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.originWeight = weight;
            this.weight = Math.log(weight);
        }

        @Override
        public String toString() {
            return from + "->" + to + " " + originWeight;
        }
    }

    private static class IntDoublePair {
        final int key;
        final double weight;

        public IntDoublePair(int left, double right) {
            this.key = left;
            this.weight = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntDoublePair intPair = (IntDoublePair) o;
            return key == intPair.key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    private static QuickReader scanner = new QuickReader(System.in);

    private static class QuickReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public QuickReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }

}
