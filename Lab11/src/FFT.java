import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

public class FFT {

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

    private static long[] polynomialMultiply(int[] a1, int[] a2) {
        int size = 1;
        while (size < a1.length + a2.length) {
            size *= 2;
        }
        Complex[] c1 = new Complex[size], c2 = new Complex[size];
        for (int i = 0; i < size; i++) {
            c1[i] = new Complex(0, 0);
            c2[i] = new Complex(0, 0);
        }
        for (int i = 0; i < a1.length; i++) {
            c1[i].real = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            c2[i].real = a2[i];
        }

        FFT(size, c1, 1);
        FFT(size, c2, 1);

        for (int i = 0; i < size; i++) {
            c1[i] = c1[i].times(c2[i]);
        }
        FFT(size, c1, -1);
        long[] result = new long[a1.length + a2.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = (long) (c1[i].real / size + 0.5);
        }
        return result;
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

    public static void main(String[] args) {
        for (int t = 0; t < 10; t++) {
            int[] a1 = randomArray();
            int[] a2 = randomArray();
            long[] ans = new long[a1.length + a2.length - 1];
            for (int i = 0; i < a1.length; i++) {
                for (int j = 0; j < a2.length; j++) {
                    ans[i + j] += a1[i] * a2[j];
                }
            }
            long[] res = polynomialMultiply(a1, a2);
            if (!Arrays.equals(ans, res)) {
                System.out.println("not equal");
            }
        }

        for (int t = 0; t < 10; t++) {
            int[] a1 = randomArray();
            int[] a2 = a1;
            long[] ans = new long[a1.length + a2.length - 1];
            for (int i = 0; i < a1.length; i++) {
                for (int j = 0; j < a2.length; j++) {
                    ans[i + j] += a1[i] * a2[j];
                }
            }
            long[] res = polynomialMultiply(a1);
            if (!Arrays.equals(ans, res)) {
                System.out.println("not equal");
            }
        }
    }

    private static int[] randomArray() {
        int size = (int) (100000 * Math.random()) + 2;
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (100 * Math.random());
        }
        return arr;
    }

}
