import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class B {

    private static long MOD = 998244353L;

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int[][] counts = new int[10][10];
        int[] lenCount = new int[11];

        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            int it = scanner.nextInt();
            int p = 0;
            while (it != 0) {
                counts[p][it % 10]++;

                maxLen = Math.max(maxLen, p);

                it /= 10;
                p++;
            }
            lenCount[p]++;

        }
        long result = 0;

        long postfix = 1;
        long[] postfixes = new long[20];
        for (int i = 0; i < 20; i++) {
            postfixes[i] = postfix;
            postfix = (postfix * 10) % MOD;
        }

        for (int len = 0; len <= maxLen; len++) {
            for (int i = 0; i < 10; i++) {
                if (counts[len][i] == 0) {
                    continue;
                }

                for (int j = 0; j < 10; j++) {
                    if (counts[len][j] == 0) {
                        continue;
                    }

                    long num = (i * 10 + j) * postfixes[2 * len] % MOD;
                    long count = ((long) counts[len][i] * counts[len][j]) % MOD;

                    result = (result + num * count) % MOD;

                }

                for (int endLen = 0; endLen <= len; endLen++) {
                    long num = i * postfixes[len + endLen] % MOD;
                    long count = (2 * (long) counts[len][i] * lenCount[endLen]) % MOD;

                    result = (result + num * count) % MOD;
                }

            }

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