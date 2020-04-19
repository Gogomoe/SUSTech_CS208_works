import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class A {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            arr[i][0] = scanner.nextInt();
            arr[i][1] = scanner.nextInt();
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        }.thenComparing(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        }));

        int[][] distinct = new int[n][];
        int count = 0;

        long result = 0;

        for (int i = 0; i < arr.length; ) {
            int j = i + 1;
            while (j < arr.length && arr[j][0] == arr[i][0] && arr[j][1] == arr[i][1]) {
                j++;
            }
            result += (long) (j - i) * (j - i - 1);

            distinct[count++] = arr[i];
            arr[i][2] = j - i;

            i = j;
        }

        long before = 0;
        for (int i = 0; i < count; i++) {
            result += before * distinct[i][2];
            before += distinct[i][2];
        }

        result -= merge(distinct, new int[count][], 0, count);

        System.out.println(result);
    }

    private static long merge(int[][] arr, int[][] temp, int L, int R) {
        if (L >= R - 1) {
            return 0;
        }
        int mid = (L + R) / 2;

        long result = 0;
        result += merge(arr, temp, L, mid);
        result += merge(arr, temp, mid, R);

        int i = L;
        int j = mid;
        int k = L;

        long before = 0;
        while (i != mid && j != R) {
            if (arr[i][1] <= arr[j][1]) {
                temp[k] = arr[i];
                result += before * arr[i][2];
                i++;
                k++;
            } else {
                temp[k] = arr[j];
                before += arr[j][2];
                j++;
                k++;
            }
        }
        while (i != mid) {
            temp[k] = arr[i];
            result += before * arr[i][2];
            i++;
            k++;
        }
        while (j != R) {
            temp[k] = arr[j];
            before += arr[j][2];
            j++;
            k++;
        }

        System.arraycopy(temp, L, arr, L, R - L);

        return result;
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