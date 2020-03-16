import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A {

    private static int result = 0;
    private static boolean[][] reachable;
    private static boolean[][] visited;
    private static int[][] open;
    private static int n;
    private static int roads;

    private static int[][] dir = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public static void main(String[] args) {
        n = scanner.nextInt();
        reachable = new boolean[n][n];
        visited = new boolean[n][n];
        open = new int[n][n];
        roads = n * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                open[i][j] = 4;
            }
        }
        for (int i = 0; i < n; i++) {
            String line = scanner.next();
            for (int j = 0; j < n; j++) {
                reachable[i][j] = line.charAt(j) == '.';
                if (reachable[i][j]) {
                    continue;
                }
                roads -= 1;
                for (int[] ints : dir) {
                    int y = i + ints[0];
                    int x = j + ints[1];
                    if (inBound(y, x)) {
                        open[y][x]--;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            open[0][i]--;
            open[n - 1][i]--;
            open[i][0]--;
            open[i][n - 1]--;
        }
        if (!reachable[n - 1][0] || !reachable[0][0]) {
            System.out.println(0);
            return;
        }
        dfs(0, 0, 1);
        System.out.println(result);
    }

    private static void dfs(int i, int j, int cnt) {
        if (!canReach(i, j)) {
            return;
        }
        if (i == n - 1 && j == 0) {
            if (cnt == roads) {
                result++;
            }
            return;
        }
        lock(i, j);

        boolean reachUp = canReach(i - 1, j);
        boolean reachDown = canReach(i + 1, j);
        boolean reachLeft = canReach(i, j - 1);
        boolean reachRight = canReach(i, j + 1);

        if ((!inBound(i, j - 1) || visited[i][j - 1]) && (inBound(i, j + 1) && visited[i][j + 1]) && reachUp && reachDown) {
            unlock(i, j);
            return;
        } else if ((inBound(i, j - 1) && visited[i][j - 1]) && (!inBound(i, j + 1) || visited[i][j + 1]) && reachUp && reachDown) {
            unlock(i, j);
            return;
        } else if ((!inBound(i - 1, j) || visited[i - 1][j]) && (inBound(i + 1, j) && visited[i + 1][j]) && reachLeft && reachRight) {
            unlock(i, j);
            return;
        } else if ((inBound(i - 1, j) && visited[i - 1][j]) && (!inBound(i + 1, j) || visited[i + 1][j]) && reachLeft && reachRight) {
            unlock(i, j);
            return;
        } else if (reachUp && !reachDown && !reachLeft && !reachRight) {
            dfs(i - 1, j, cnt + 1);
        } else if (!reachUp && reachDown && !reachLeft && !reachRight) {
            dfs(i + 1, j, cnt + 1);
        } else if (!reachUp && !reachDown && reachLeft && !reachRight) {
            dfs(i, j - 1, cnt + 1);
        } else if (!reachUp && !reachDown && !reachLeft && reachRight) {
            dfs(i, j + 1, cnt + 1);
        } else if (j == 1 && !reachRight && reachUp && reachLeft) {
            dfs(i - 1, j, cnt + 1);
        } else if (i == 1 && !reachDown && reachUp && reachLeft) {
            dfs(i, j - 1, cnt + 1);
        } else if (j == n - 2 && !reachLeft && reachUp && reachRight) {
            dfs(i - 1, j, cnt + 1);
        } else if (i == n - 2 && !reachUp && reachDown && reachRight) {
            dfs(i, j + 1, cnt + 1);
        } else if (reachUp && open[i - 1][j] == 1) {
            dfs(i - 1, j, cnt + 1);
        } else if (reachDown && open[i + 1][j] == 1) {
            dfs(i + 1, j, cnt + 1);
        } else if (reachLeft && open[i][j - 1] == 1) {
            dfs(i, j - 1, cnt + 1);
        } else if (reachRight && open[i][j + 1] == 1) {
            dfs(i, j + 1, cnt + 1);
        } else {
            dfs(i - 1, j, cnt + 1);
            dfs(i + 1, j, cnt + 1);
            dfs(i, j - 1, cnt + 1);
            dfs(i, j + 1, cnt + 1);
        }

        unlock(i, j);
    }

    private static boolean canReach(int i, int j) {
        return inBound(i, j) && reachable[i][j] && !visited[i][j];
    }

    private static boolean inBound(int y, int x) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }

    private static void lock(int i, int j) {
        visited[i][j] = true;
        for (int k = 0; k < dir.length; k++) {
            int y = i + dir[k][0];
            int x = j + dir[k][1];
            if (canReach(y, x)) {
                open[y][x]--;
            }
        }
    }

    private static void unlock(int i, int j) {
        visited[i][j] = false;
        for (int k = 0; k < dir.length; k++) {
            int y = i + dir[k][0];
            int x = j + dir[k][1];
            if (canReach(y, x)) {
                open[y][x]++;
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