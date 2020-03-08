import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A_2 {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }

        int[] count = new int[10000001];
        int[] list = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int it = scanner.nextInt();
            count[it]++;
            list[i] = it;
        }

        scanner = null;
        System.gc();

        int[] prevs = new int[10000001];
        int[] succs = new int[10000001];


        int last = 0;
        for (int i = 1; i <= 10000000; i++) {
            if (count[i] > 0) {
                prevs[i] = last;
                succs[last] = i;

                last = i;
            }
        }

        long result = 0;
        for (int i = n; i > 1; i--) {
            int it = list[i];
            int prev = prevs[it];
            int succ = succs[it];

            if (count[it] == 1) {
                if (prev == 0) {
                    result += (succ - it);
                } else if (succ == 0) {
                    result += (it - prev);
                } else {
                    if ((succ - it) < (it - prev)) {
                        result += (succ - it);
                    } else {
                        result += (it - prev);
                    }
                }
            }

            count[it]--;
            if (count[it] == 0) {
                succs[prev] = succ;
                prevs[succ] = prev;
            }
        }
        result += list[1];

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