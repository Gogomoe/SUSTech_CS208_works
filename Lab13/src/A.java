import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class A {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int c = scanner.nextInt();

        int[][][] item = new int[n][k][2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                item[i][j][0] = scanner.nextInt();
                item[i][j][1] += scanner.nextInt();
                item[i][j][1] += scanner.nextInt();
            }
        }

        int[] dp = new int[c + 1];

        for (int i = 0; i < n; i++) {
            for (int w = c; w >= 0; w--) {
                for (int j = 0; j < k; j++) {
                    if (w >= item[i][j][0]) {
                        dp[w] = max(dp[w], dp[w - item[i][j][0]] + item[i][j][1]);
                    }
                }
            }
        }

        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            max = max(dp[i], max);
        }

        System.out.println(max);
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