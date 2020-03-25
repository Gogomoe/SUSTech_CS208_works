import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B {

    public static final Comparator<Perform> COMPARATOR = new Comparator<Perform>() {
        @Override
        public int compare(Perform o1, Perform o2) {
            return o1.end - o2.end;
        }
    };
    private static int n;
    private static int[][] arr;
    private static int minTime = Integer.MAX_VALUE;

    public static void main(String[] args) {
        n = scanner.nextInt();
        arr = new int[n][2];

        int left = 0;
        int right = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            arr[i][0] = scanner.nextInt();
            arr[i][1] = scanner.nextInt();
            right = Math.min(right, arr[i][1] - arr[i][0] + 1);
            minTime = Math.min(minTime, arr[i][0]);
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (check(mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(left);
    }

    private static boolean check(int mid) {
        if (mid == 0) {
            return true;
        }
        PriorityQueue<Perform> performs = new PriorityQueue<>(COMPARATOR);
        int start = 0;
        for (int currentTime = 0; currentTime <= 10000; currentTime++) {
            while (start < n && arr[start][0] == currentTime) {
                performs.add(new Perform(arr[start][0], arr[start][1]));
                start++;
            }
            if (performs.isEmpty()) {
                continue;
            }
            Perform top = performs.peek();
            if (currentTime > top.end) {
                return false;
            }
            top.count++;
            if (top.count == mid) {
                performs.remove(top);
            }
        }
        return performs.isEmpty();
    }

    private static class Perform {
        int start;
        int end;
        int count = 0;

        public Perform(int start, int end) {
            this.start = start;
            this.end = end;
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