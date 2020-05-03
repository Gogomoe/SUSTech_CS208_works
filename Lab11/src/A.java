import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class A {

    private static final List<VCN> list = new ArrayList<>();

    static {
        VCN last = new VCN(1, 1, 0, 2);
        list.add(last);
        while (last.len * 2 <= 100_0000_0000_0000_0000L) {
            VCN next = new VCN(last.V + last.N, last.C + last.V, last.N + last.C, last.len * 2);
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
        long R = scanner.nextLong();
        System.out.print(V(R, list.size() - 1) + " ");
        System.out.print(C(R, list.size() - 1) + " ");
        System.out.println(N(R, list.size() - 1));
    }

    private static long V(long R, int i) {
        if (R == 1) {
            return 1;
        }
        VCN VCN = list.get(i);
        long result = 0;
        if (R < VCN.len) {
            result += V(R, i - 1);
        } else {
            result += VCN.V;
        }
        if (R > VCN.len) {
            result += N(R - VCN.len, i);
        }

        return result;
    }

    private static long C(long R, int i) {
        if (R == 1) {
            return 0;
        }
        VCN VCN = list.get(i);
        long result = 0;
        if (R < VCN.len) {
            result += C(R, i - 1);
        } else {
            result += VCN.C;
        }
        if (R > VCN.len) {
            result += V(R - VCN.len, i);
        }

        return result;
    }

    private static long N(long R, int i) {
        if (R == 1) {
            return 0;
        }
        VCN VCN = list.get(i);
        long result = 0;
        if (R < VCN.len) {
            result += N(R, i - 1);
        } else {
            result += VCN.N;
        }
        if (R > VCN.len) {
            result += C(R - VCN.len, i);
        }

        return result;
    }

    private static class VCN {
        long V;
        long C;
        long N;
        long len;

        public VCN(long v, long c, long n, long len) {
            V = v;
            C = c;
            N = n;
            this.len = len;
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