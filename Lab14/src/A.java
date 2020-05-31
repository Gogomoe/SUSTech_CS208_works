import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.Math.min;

public class A {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Dinic dinic = new Dinic(n + 2, 0, n + 1);
        for (int i = 1; i <= n; i++) {
            int color = scanner.nextInt();
            if (color == 1) {
                dinic.addEdge(0, i, 1);
            } else {
                dinic.addEdge(i, n + 1, 1);
            }
        }
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            dinic.addEdge(u, v, 1);
            dinic.addEdge(v, u, 1);
        }
        System.out.println(dinic.maxFlow());
    }

    private static class Edge {
        Node from, to;
        int cap, flow;
        Edge reverse;

        public Edge(Node from, Node to, int cap, int flow) {
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.flow = flow;
        }

    }

    private static class Node {
        ArrayList<Edge> out = new ArrayList<>();
        boolean visit = false;
        int depth;
    }

    private static class Dinic {
        int n, m, s, t;
        Node[] nodes;

        public Dinic(int n, int s, int t) {
            this.n = n;
            this.s = s;
            this.t = t;
            nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node();
            }
        }

        public void addEdge(int from, int to, int cap) {
            Node f = nodes[from];
            Node t = nodes[to];
            Edge e = new Edge(f, t, cap, 0);
            Edge r = new Edge(t, f, 0, 0);
            e.reverse = r;
            r.reverse = e;
            f.out.add(e);
            t.out.add(r);
            m += 2;
        }

        public boolean BFS() {
            for (Node node : nodes) {
                node.visit = false;
            }
            Queue<Node> queue = new ArrayDeque<>();
            queue.add(nodes[s]);
            nodes[s].depth = 0;
            nodes[s].visit = true;
            while (!queue.isEmpty()) {
                Node from = queue.poll();
                for (Edge edge : from.out) {
                    if (!edge.to.visit && edge.cap > edge.flow) {
                        edge.to.visit = true;
                        edge.to.depth = from.depth + 1;
                        queue.add(edge.to);
                    }
                }
            }
            return nodes[t].visit;
        }

        public int DFS(Node node, int flowRemain) {
            if (node == nodes[t] || flowRemain == 0) {
                return flowRemain;
            }
            int flowOut = 0;
            for (Edge edge : node.out) {
                if (node.depth + 1 == edge.to.depth) {
                    int innerFlowOut = DFS(edge.to, min(flowRemain, edge.cap - edge.flow));
                    if (innerFlowOut > 0) {
                        edge.flow += innerFlowOut;
                        edge.reverse.flow -= innerFlowOut;
                        flowOut += innerFlowOut;
                        flowRemain -= innerFlowOut;
                        if (flowRemain == 0) {
                            break;
                        }
                    }
                }
            }
            return flowOut;
        }

        int maxFlow() {
            int flow = 0;
            while (BFS()) {
                flow += DFS(nodes[s], Integer.MAX_VALUE);
            }
            return flow;
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