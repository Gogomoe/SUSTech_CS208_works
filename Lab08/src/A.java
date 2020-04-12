import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class A {

    public static void main(String[] args) {
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            process();
        }
    }

    private static void process() {
        String s = scanner.next();
        int[] chs = new int[26];
        for (int i = 0; i < s.length(); i++) {
            chs[s.charAt(i) - 'a']++;
        }

        PriorityQueue<CharCount> pq = new PriorityQueue<>(new Comparator<CharCount>() {
            @Override
            public int compare(CharCount o1, CharCount o2) {
                return o1.count - o2.count;
            }
        });

        for (int i = 0; i < chs.length; i++) {
            if (chs[i] != 0) {
                pq.add(new CharCount((char) ('a' + i), chs[i]));
            }
        }

        if (pq.size() == 1) {
            System.out.println(pq.poll().count);
            return;
        }

        while (pq.size() != 1) {
            CharCount a = pq.poll();
            CharCount b = pq.poll();
            pq.add(new CharCount(a, b));
        }

        int[] clen = new int[26];
        pq.poll().setLayer(clen, 0);

        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += chs[i] * clen[i];
        }
        System.out.println(sum);
    }

    private static class CharCount {
        char c;
        int count;

        CharCount left;
        CharCount right;

        public static final char ERROR_CHAR = (char) 0;

        public CharCount(CharCount left, CharCount right) {
            this(ERROR_CHAR, left.count + right.count);
            this.left = left;
            this.right = right;
        }

        public CharCount(char c, int count) {
            this.c = c;
            this.count = count;
        }

        public void setLayer(int[] layer, int now) {
            if (c != ERROR_CHAR) {
                layer[c - 'a'] = now;
            }
            if (left != null) {
                left.setLayer(layer, now + 1);
                right.setLayer(layer, now + 1);
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