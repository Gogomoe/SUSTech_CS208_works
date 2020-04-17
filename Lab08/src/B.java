import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B {

    private static int n;
    private static int[][] tasks;
    private static int[] active;
    private static int[] used;

    public static void main(String[] args) {
        n = scanner.nextInt();
        tasks = new int[n][3];
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < 3; j++) {
                tasks[i][j] = scanner.nextInt();
            }
        }
        Arrays.sort(tasks, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        active = new int[n];
        used = new int[n];
        Arrays.fill(used, -1);

        int pos = 0;
        int x = 0;
        for (int[] task : tasks) {
            x = Math.max(x + 1, task[0]);
            active[pos++] = x;
        }

        Arrays.sort(tasks, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return -(o1[2] - o2[2]);
            }
        });

        for (int i = 0; i < n; i++) {
            int L = 0;
            int R = n - 1;
            while (L < R) {
                int mid = (L + R) / 2;
                if (active[mid] >= tasks[i][0]) {
                    R = mid;
                } else {
                    L = mid + 1;
                }
            }
            find(i, L);
        }

        long result = 0;
        for (int i = 0; i < n; i++) {
            if (used[i] != -1) {
                result += tasks[used[i]][2];
            }
        }
        System.out.println(result);
    }

    private static boolean find(int i, int x) {
        if (x >= n || active[x] > tasks[i][1]) {
            return false;
        }
        if (used[x] == -1) {
            used[x] = i;
            return true;
        }
        int j = used[x];
        if (tasks[i][1] > tasks[j][1]) {
            return find(i, x + 1);
        } else {
            if (find(j, x + 1)) {
                used[x] = i;
                return true;
            } else {
                return false;
            }
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