import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Math.*;
import static java.lang.Math.PI;

public class B {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int p = scanner.nextInt();

        int primitiveRoot = findPrimitive(p);

        int[] expToRem = new int[p];
        int[] remToExp = new int[p];

        int s = 1;
        for (int i = 1; i < p; i++) {
            s = (s * primitiveRoot) % p;

            remToExp[s] = i;
            expToRem[i] = s;
        }

        int[] arr = new int[p];
        int remZero = 0;
        for (int i = 0; i < n; i++) {
            int reminder = scanner.nextInt() % p;
            if (reminder == 0) {
                remZero++;
                continue;
            }
            int exp = remToExp[reminder];
            arr[exp]++;
        }

        long[] square = polynomialMultiply(arr);
        long[] result = new long[p];

        for (int i = 1; i < square.length; i++) {
            int exp = (i - 1) % (p - 1) + 1;
            int rem = expToRem[exp];
            result[rem] += square[i];
        }

        result[0] = (long) remZero * n + (long) (n - remZero) * remZero;

        PrintStream out = new PrintStream(new BufferedOutputStream(System.out, 64000));
        for (int i = 0; i < result.length; i++) {
            out.println(result[i]);
        }
        out.close();

    }

    private static int findPrimitive(int p) {
        boolean[] have = new boolean[p + 1];
        int res = 2;
        while (res < p) {
            int s = 1;
            for (int i = 1; i <= p; i++) {
                if (i == p) {
                    return res;
                }
                s = (s * res) % p;
                if (s == 0 || have[s]) {
                    break;
                }
                have[s] = true;
            }
            Arrays.fill(have, false);
            res++;
        }
        throw new RuntimeException("no permitive");
    }

    private static class Complex {
        double real, imag;

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public Complex plus(Complex other) {
            return new Complex(real + other.real, imag + other.imag);
        }

        public Complex minus(Complex other) {
            return new Complex(real - other.real, imag - other.imag);
        }

        public Complex times(Complex other) {
            return new Complex(
                    real * other.real - imag * other.imag,
                    real * other.imag + imag * other.real
            );
        }
    }


    private static void FFT(int size, Complex[] arr, int type) {
        if (size == 1) {
            return;
        }
        Complex[] a1 = new Complex[size / 2], a2 = new Complex[size / 2];
        for (int i = 0; i < size; i += 2) {
            a1[i / 2] = arr[i];
            a2[i / 2] = arr[i + 1];
        }

        FFT(size / 2, a1, type);
        FFT(size / 2, a2, type);

        Complex omega = new Complex(
                cos(2 * PI / size),
                type * sin(2 * PI / size)
        );
        Complex w = new Complex(1, 0);
        for (int i = 0; i < size / 2; i++) {
            arr[i] = a1[i].plus(w.times(a2[i]));
            arr[i + size / 2] = a1[i].minus(w.times(a2[i]));
            w = w.times(omega);
        }
    }

    private static long[] polynomialMultiply(int[] a1) {
        int size = 1;
        while (size < 2 * a1.length) {
            size *= 2;
        }
        Complex[] c1 = new Complex[size];
        for (int i = 0; i < size; i++) {
            c1[i] = new Complex(0, 0);
        }
        for (int i = 0; i < a1.length; i++) {
            c1[i].real = a1[i];
        }

        FFT(size, c1, 1);
        for (int i = 0; i < size; i++) {
            c1[i] = c1[i].times(c1[i]);
        }
        FFT(size, c1, -1);

        long[] result = new long[a1.length * 2 - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = (long) (c1[i].real / size + 0.5);
        }
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
