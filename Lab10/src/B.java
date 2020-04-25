import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class B {

    private static int n;
    private static int d;
    private static final Comparator<int[]> yComp = new Comparator<int[]>() {

        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[1] - o2[1];
        }
    };

    private static final Comparator<int[]> yzComp = new Comparator<int[]>() {

        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[1] - o2[1] != 0 ? o1[1] - o2[1] : o1[2] - o2[2];
        }
    };

    public static void main(String[] args) throws IOException {
        n = scanner.nextInt();
        d = scanner.nextInt();
        if (n == 1) {
            int[] arr = new int[d];
            for (int i = 0; i < d; i++) {
                arr[i] = scanner.nextInt();
            }
            for (int i = 0; i < 2; i++) {
                System.out.print(arr[0]);
                for (int j = 1; j < d; j++) {
                    System.out.print(" " + arr[j]);
                }
                System.out.println();
            }

            return;
        }
        if (d == 1) {
            process1();
        } else if (d == 2) {
            process2();
        } else if (d == 3) {
            process3();
        }
    }


    private static void process1() {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);
        int i = 1;
        for (int j = 1; j < n; j++) {
            if (arr[j] - arr[j - 1] < arr[i] - arr[i - 1]) {
                i = j;
            }
        }
        System.out.println(arr[i - 1]);
        System.out.println(arr[i]);
    }

    private static void process2() {
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = scanner.nextInt();
            arr[i][1] = scanner.nextInt();
        }
        Comparator<int[]> xyComp = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o1[1] - o2[1];
            }
        };
        Arrays.sort(arr, xyComp);
        Result res = find2(arr, new int[n][], 0, n);
        int[][] ps = new int[][]{res.p1, res.p2};
        Arrays.sort(ps, xyComp);
        for (int[] p : ps) {
            System.out.println(p[0] + " " + p[1]);
        }
    }

    private static Result find2(int[][] arr, int[][] tmp, int L, int R) {
        if (R - L <= 3) {
            Result res = new Result(arr[L], arr[L + 1], distance2(arr[L], arr[L + 1]));
            for (int i = L; i < R; i++) {
                for (int j = i + 1; j < R; j++) {
                    updateResult2(res, arr[i], arr[j]);
                }
            }
            Arrays.sort(arr, L, R, yComp);
            return res;
        }
        int M = (L + R) / 2;
        int midx = arr[M][0];
        Result LR = find2(arr, tmp, L, M);
        Result RR = find2(arr, tmp, M, R);

        Result result;
        if (LR.h < RR.h) {
            result = new Result(LR.p1, LR.p2, LR.h);
        } else {
            result = new Result(RR.p1, RR.p2, RR.h);
        }

        double h = Math.sqrt(Math.min(LR.h, RR.h));
        merge(arr, tmp, L, R, M, yComp);

        int addedSize = 0;
        for (int i = L; i < R; i++) {
            if (Math.abs(arr[i][0] - midx) < h) {
                for (int j = addedSize - 1; j >= 0 && arr[i][1] - tmp[j][1] < h; j--) {
                    updateResult2(result, arr[i], tmp[j]);
                }
                tmp[addedSize++] = arr[i];
            }
        }
        return result;
    }

    private static void merge(int[][] arr, int[][] tmp, int L, int R, int M, Comparator<int[]> comp) {
        int l = L;
        int r = M;
        int k = 0;
        while (l != M && r != R) {
            if (comp.compare(arr[l], arr[r]) <= 0) {
                tmp[k] = arr[l];
                k++;
                l++;
            } else {
                tmp[k] = arr[r];
                k++;
                r++;
            }
        }
        while (l != M) {
            tmp[k] = arr[l];
            k++;
            l++;
        }
        while (r != R) {
            tmp[k] = arr[r];
            k++;
            r++;
        }
        System.arraycopy(tmp, 0, arr, L, R - L);
    }

    private static void process3() {
        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            arr[i][0] = scanner.nextInt();
            arr[i][1] = scanner.nextInt();
            arr[i][2] = scanner.nextInt();
        }
        Comparator<int[]> xyzComp = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0] != 0 ? o1[0] - o2[0] : (o1[1] - o2[1] != 0 ? o1[1] - o2[1] : o1[2] - o2[2]);
            }
        };
        Arrays.sort(arr, xyzComp);
        Result res = find3(arr, new int[n][], 0, n);
        int[][] ps = new int[][]{res.p1, res.p2};
        Arrays.sort(ps, xyzComp);
        for (int[] p : ps) {
            System.out.println(p[0] + " " + p[1] + " " + p[2]);
        }
    }

    private static Result find3(int[][] arr, int[][] tmp, int L, int R) {
        if (R - L <= 3) {
            Result res = new Result(arr[L], arr[L + 1], distance3(arr[L], arr[L + 1]));
            for (int i = L; i < R; i++) {
                for (int j = i + 1; j < R; j++) {
                    updateResult3(res, arr[i], arr[j]);
                }
            }
            Arrays.sort(arr, L, R, yzComp);
            return res;
        }
        int M = (L + R) / 2;
        int midx = arr[M][0];
        Result LR = find3(arr, tmp, L, M);
        Result RR = find3(arr, tmp, M, R);

        Result result;
        if (LR.h < RR.h) {
            result = new Result(LR.p1, LR.p2, LR.h);
        } else {
            result = new Result(RR.p1, RR.p2, RR.h);
        }

        double h = Math.sqrt(Math.min(LR.h, RR.h));
        merge(arr, tmp, L, R, M, yzComp);

        int addedSize = 0;
        for (int i = L; i < R; i++) {
            if (Math.abs(arr[i][0] - midx) < h) {
                for (int j = addedSize - 1; j >= 0 && arr[i][1] - tmp[j][1] < h; j--) {
                    updateResult3(result, arr[i], tmp[j]);
                }
                tmp[addedSize++] = arr[i];
            }
        }
        return result;
    }


    private static class Result {
        int[] p1;
        int[] p2;
        long h;

        public Result(int[] p1, int[] p2, long h) {
            this.p1 = p1;
            this.p2 = p2;
            this.h = h;
        }
    }

    private static void updateResult2(Result res, int[] p1, int[] p2) {
        long dis = distance2(p1, p2);
        if (dis < res.h) {
            res.p1 = p1;
            res.p2 = p2;
            res.h = dis;
        }
    }

    private static void updateResult3(Result res, int[] p1, int[] p2) {
        long dis = distance3(p1, p2);
        if (dis < res.h) {
            res.p1 = p1;
            res.p2 = p2;
            res.h = dis;
        }
    }

    private static long distance2(int[] p1, int[] p2) {
        return (long) (p1[0] - p2[0]) * (p1[0] - p2[0]) + (long) (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }

    private static long distance3(int[] p1, int[] p2) {
        return (long) (p1[0] - p2[0]) * (p1[0] - p2[0]) + (long) (p1[1] - p2[1]) * (p1[1] - p2[1]) + (long) (p1[2] - p2[2]) * (p1[2] - p2[2]);
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

        public int nextInt() {
            try {
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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