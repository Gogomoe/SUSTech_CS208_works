import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class A {

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        int[][] ps = new int[n][2];
        for (int i = 0; i < n; i++) {
            ps[i][0] = scanner.nextInt();
            ps[i][1] = scanner.nextInt();
        }

        Arrays.sort(ps, new Comparator<int[]>() {
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

        int[] stack = new int[n + 1];
        Arrays.fill(stack, -1);
        boolean[] used = new boolean[n];

        int sp = 0;
        stack[sp++] = 0;

        for (int i = 1; i < n; i++) {
            while (sp >= 2 && turn(ps[stack[sp - 2]], ps[stack[sp - 1]], ps[i]) < 0) {
                sp--;
                used[stack[sp]] = false;
                stack[sp] = -1;
            }
            used[i] = true;
            stack[sp++] = i;
        }
        int halfCount = sp;
        for (int i = n - 2; i >= 0; i--) {
            if (used[i]) {
                continue;
            }
            while (sp >= halfCount + 1 && turn(ps[stack[sp - 2]], ps[stack[sp - 1]], ps[i]) < 0) {
                sp--;
                used[stack[sp]] = false;
                stack[sp] = -1;
            }
            used[i] = true;
            stack[sp++] = i;
        }
        System.out.println(sp - 1);
    }

    private static long turn(int[] p1, int[] p2, int[] p3) {
        long x1 = (long) p2[0] - p1[0];
        long y1 = (long) p2[1] - p1[1];
        long x2 = (long) p3[0] - p2[0];
        long y2 = (long) p3[1] - p2[1];
        return x1 * y2 - x2 * y1;
    }

    private static QuickReader scanner = new QuickReader();

    private static class QuickReader {
        final private int BUFFER_SIZE = 1 << 20;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public QuickReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}