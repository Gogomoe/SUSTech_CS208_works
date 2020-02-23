import java.io.*;
import java.util.*;

public class A {

    private static class Man {
        public Woman woman;
        public String name;
        public List<String> favorite;
        public int nextPropose = 0;

        public Man(String name, List<String> favorite) {
            this.name = name;
            this.favorite = favorite;
        }
    }

    private static class Woman {
        public Man man;
        public String name;
        public List<String> favorite;
        public Map<String, Integer> weight;

        public Woman(String name, List<String> favorite) {
            this.name = name;
            this.favorite = favorite;
            weight = new HashMap<>();
            for (int i = 0; i < favorite.size(); i++) {
                weight.put(favorite.get(i), i);
            }
        }
    }

    public static void main(String[] args) {
        int n = scanner.nextInt();

        HashMap<String, Man> men = new HashMap<>();
        HashMap<String, Woman> women = new HashMap<>();
        Deque<Man> freeMen = new ArrayDeque<>();

        List<Man> manList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            List<String> favorite = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                favorite.add(scanner.next());
            }
            Man man = new Man(name, favorite);
            men.put(name, man);
            manList.add(man);
            freeMen.add(man);
        }
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            List<String> favorite = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                favorite.add(scanner.next());
            }
            Woman woman = new Woman(name, favorite);
            women.put(name, woman);
        }

        while (!freeMen.isEmpty()) {
            Man man = freeMen.pollFirst();
            Woman woman = women.get(man.favorite.get(man.nextPropose++));
            if (woman.man == null) {
                woman.man = man;
                man.woman = woman;
            } else {
                Man old = woman.man;
                if (woman.weight.get(old.name) > woman.weight.get(man.name)) {
                    old.woman = null;
                    woman.man = man;
                    man.woman = woman;
                    freeMen.addLast(old);
                } else {
                    freeMen.addLast(man);
                }
            }
        }

        for (Man man : manList) {
            out.println(man.name + " " + man.woman.name);
        }

        out.close();
    }

    private static QuickReader scanner = new QuickReader(System.in);

    private static PrintStream out = new PrintStream(new BufferedOutputStream(System.out));

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