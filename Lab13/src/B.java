import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B {

    private static final int MOD = 998244353;

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        long result = 1;
        boolean inContinuousUnknown = false;

        long[][] last = new long[201][2];
        long[][] now = new long[201][2];

        if (arr[0] == -1) {
            inContinuousUnknown = true;
            for (int j = 1; j <= 200; j++) {
                last[j][0] = 1;
            }
        } else {
            last[arr[0]][0] = 1;
        }

        for (int i = 1; i < n; i++) {
            int curr = arr[i];
            int prev = arr[i - 1];

            if (arr[i] == -1) {
                if (!inContinuousUnknown) {
                    inContinuousUnknown = true;
                    for (int j = prev + 1; j <= 200; j++) {
                        now[j][0] = (last[prev][0] + last[prev][1]) % MOD;
                    }
                    for (int j = 1; j < prev; j++) {
                        now[j][1] = last[prev][1];
                    }
                    now[prev][1] = (last[prev][0] + last[prev][1]) % MOD;
                } else {
                    long add = 0;
                    for (int j = 1; j <= 200; j++) {
                        add = (add + last[j - 1][0]) % MOD;
                        add = (add + last[j - 1][1]) % MOD;
                        now[j][0] = add;
                    }

                    add = 0;
                    for (int j = 200; j >= 1; j--) {
                        add = (add + last[j][1]) % MOD;
                        now[j][1] = (add + last[j][0]) % MOD;
                    }
                }
                for (int j = 1; j <= 200; j++) {
                    last[j][0] = now[j][0];
                    last[j][1] = now[j][1];
                    now[j][0] = 0;
                    now[j][1] = 0;
                }
            } else {
                if (!inContinuousUnknown) {
                    if (curr > prev) {
                        now[curr][0] = (last[prev][0] + last[prev][1]) % MOD;
                    } else if (curr == prev) {
                        now[curr][1] = (last[prev][0] + last[prev][1]) % MOD;
                    } else {
                        now[curr][1] = last[prev][1];
                    }
                    last[prev][0] = 0;
                    last[prev][1] = 0;

                } else {
                    inContinuousUnknown = false;

                    long add = 0;
                    for (int k = 1; k < curr; k++) {
                        add = (add + last[k][0]) % MOD;
                        add = (add + last[k][1]) % MOD;
                    }
                    now[curr][0] = add;

                    add = 0;
                    for (int k = 200; k >= curr; k--) {
                        add = (add + last[k][1]) % MOD;
                    }
                    now[curr][1] = (add + last[curr][0]) % MOD;

                    for (int j = 1; j <= 200; j++) {
                        last[j][0] = 0;
                        last[j][1] = 0;
                    }
                }
                last[curr][0] = now[curr][0];
                last[curr][1] = now[curr][1];
                now[curr][0] = 0;
                now[curr][1] = 0;
            }
        }

        if (inContinuousUnknown) {
            long partRes = 0;
            for (int j = 1; j <= 200; j++) {
                partRes = (partRes + last[j][1]) % MOD;
            }

            result = (result * partRes) % MOD;
        } else {
            result = last[arr[n - 1]][1];
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