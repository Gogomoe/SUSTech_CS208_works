import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class B {

    private static final List<CV> list = new ArrayList<>();

    static {
        CV last = new CV(1, 0, 1);
        list.add(last);
        while (last.mid * 2 <= 100_0000_0000_0000_0000L) {
            CV next = new CV(last.V + last.C + 1, last.V + last.C, last.mid * 2);
            list.add(next);
            last = next;
        }
    }

    public static void main(String[] args) {
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            process();
        }
    }

    private static void process() {
        long L = scanner.nextLong();
        long R = scanner.nextLong();
        System.out.println(V(L, R, list.size() - 1));
    }

    private static long V(long L, long R, int i) {
        CV cv = list.get(i);
        long mid = cv.mid;
        if (L == 1 && R == mid * 2 - 1) {
            return cv.V;
        }
        long result = 0;
        if (L < mid) {
            result += V(L, min(R, mid - 1), i - 1);
        }
        if (L <= mid && R >= mid) {
            result += 1;
        }
        if (R > mid) {
            long start = max(L, mid + 1);
            result += C(2 * mid - R, 2 * mid - start, i - 1);
        }
        return result;
    }

    private static long C(long L, long R, int i) {
        CV cv = list.get(i);
        long mid = cv.mid;
        if (L == 1 && R == mid * 2 - 1) {
            return cv.C;
        }
        long result = 0;
        if (L < mid) {
            result += C(L, min(R, mid - 1), i - 1);
        }
        if (L <= mid && R >= mid) {
            result += 0;
        }
        if (R > mid) {
            long start = max(L, mid + 1);
            result += V(2 * mid - R, 2 * mid - start, i - 1);
        }
        return result;
    }

    private static class CV {
        long V;
        long C;
        long mid;

        public CV(long v, long c, long midPox) {
            V = v;
            C = c;
            this.mid = midPox;
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