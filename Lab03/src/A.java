import java.io.*;
import java.util.*;

public class A {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }

        long result = 0;
        TreeSet<Integer> set = new TreeSet<>();

        int first = scanner.nextInt();
        result += first;
        set.add(first);

        for (int i = 1; i < n; i++) {
            int it = scanner.nextInt();
            Integer left = set.floor(it);
            if (left != null && left == it) {
                continue;
            }
            Integer right = set.ceiling(it);
            if (left == null) {
                result += (right - it);
            } else if (right == null) {
                result += (it - left);
            } else {
                if ((right - it) < (it - left)) {
                    result += (right - it);
                } else {
                    result += (it - left);
                }
            }
            set.add(it);
        }

        System.out.println(result);

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