import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class A {

    public static void main(String[] args) {
        int cost = scanner.nextInt();
        int[] costs = new int[90];
        int[] values = new int[90];
        int[][] dp = new int[cost + 1][31];

        for (int i = 0; i < 90; i++) {
            costs[i] = scanner.nextInt();
            values[i] = scanner.nextInt() + scanner.nextInt();
        }

        for (int i = 0; i < 90; i++) {
            for (int w = cost; w >= costs[i]; w--) {
                for (int j = 30; j > 0; j--) {
                    dp[w][j] = max(dp[w][j], dp[w - costs[i]][j - 1] + values[i]);
                }
            }
        }

        System.out.println(dp[cost][30]);

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