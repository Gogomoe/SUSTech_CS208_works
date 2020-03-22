import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class A {

    public static final Comparator<int[]> comparator = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[1] - o2[1];
        }
    };

    public static void main(String[] args) throws IOException {
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            process();
        }
    }

    private static void process() throws IOException {
        int n = scanner.nextInt();
        int[][] nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            nums[i][0] = scanner.nextInt();
            nums[i][1] = scanner.nextInt();
        }
        Arrays.sort(nums, comparator);

        int count = 0;
        int lastEnd = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i][0] > lastEnd) {
                count++;
                lastEnd = nums[i][1];
            }
        }
        System.out.println(count);
    }

    private static QuickReader scanner = new QuickReader();

    private static class QuickReader {
        final private int BUFFER_SIZE = 1 << 16;
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
