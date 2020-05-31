import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import static java.lang.Math.max;

public class B {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int[][] arr = new int[n][2];
        int sum = 0;
        for (int[] ints : arr) {
            ints[0] = scanner.nextInt();
            ints[1] = scanner.nextInt();
            sum += ints[0];
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return -(o1[1] - o2[1]);
            }
        });
        int prefixSumOfCapture = 0;
        int m = 0;
        for (int i = 0; i < n; i++) {
            prefixSumOfCapture += arr[i][1];
            if (prefixSumOfCapture >= sum) {
                m = i + 1;
                break;
            }
        }

        int maxCost = m * 100 - sum;

        int[][] last = new int[m + 1][maxCost + 1];
        int[][] now = new int[m + 1][maxCost + 1];

        last[1][100 - arr[0][1]] = arr[0][0];

        for (int i = 1; i < arr.length; i++) {
            int ai = arr[i][0];
            int bi = arr[i][1];
            int cost = 100 - bi;

            for (int j = 1; j <= m; j++) {
                for (int k = 0; k <= maxCost; k++) {
                    if (k - cost >= 0 && (last[j - 1][k - cost] != 0 || (j == 1 && k == cost))) {
                        now[j][k] = max(last[j][k], last[j - 1][k - cost] + ai);
                    } else {
                        now[j][k] = last[j][k];
                    }
                }
            }

            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= maxCost; k++) {
                    last[j][k] = now[j][k];
                    now[j][k] = 0;
                }
            }
        }

        int result = 0;
        for (int i = 0; i <= maxCost; i++) {
            result = max(result, last[m][i]);
        }

        System.out.println(sum - result);

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