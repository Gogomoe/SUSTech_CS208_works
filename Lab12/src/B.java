import java.io.DataInputStream;
import java.io.IOException;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class B {

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] cnt = new int[m + 1];
        int result = 0;
        for (int i = 0; i < n; i++) {
            int it = scanner.nextInt();
            cnt[it]++;
            if (cnt[it] >= 9) {
                cnt[it] -= 3;
                result++;
            }
        }
        int[][] last = new int[9][9];
        int[][] now = new int[9][9];

        result += (cnt[1] / 3);
        cnt[1] %= 3;

        for (int p = 2; p <= m; p++) {
            for (int i = 0; i <= cnt[p - 1]; i++) {
                for (int j = 0; j <= cnt[p]; j++) {
                    for (int k = 0; k <= min(min(cnt[p - 2], cnt[p - 1] - i), cnt[p] - j); k++) {
                        now[i][j] = max(now[i][j], last[k][i + k] + k + (cnt[p] - k - j) / 3);
                    }
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    last[i][j] = now[i][j];
                    now[i][j] = 0;
                }
            }
        }

        result += last[0][0];

        System.out.println(result);
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