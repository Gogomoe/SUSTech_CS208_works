import java.util.*;

public class B {

    private static int n;
    private static int[] scores;
    private static int[] now;
    private static int tieCount;
    private static int noTieCount;
    private static Map<Long, Long> cache = new HashMap<>();

    private static long MOD = 998244353;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        scores = new int[n];
        now = new int[n];

        int sumScore = 0;
        for (int i = 0; i < n; i++) {
            scores[i] = scanner.nextInt();
            sumScore += scores[i];
        }
        scores = Arrays.stream(scores).boxed().sorted(Comparator.reverseOrder()).mapToInt(it -> it).toArray();

        int compCount = n * (n - 1) / 2;
        noTieCount = sumScore - 2 * compCount;
        tieCount = 3 * compCount - sumScore;

        System.out.println(dfs(0, 1) % MOD);
    }

    private static long dfs(int a, int b) {
        if (a == n - 1) {
            return 1;
        }
        if (now[a] + 3 * (n - b) < scores[a]) {
            return 0;
        }
        if (b == n) {
            int[] diff = new int[n];
            for (int i = a + 1; i < n; i++) {
                diff[i] = scores[i] - now[i];
            }
            Arrays.sort(diff);
            long hash = 0;
            for (int i = a + 1; i < n; i++) {
                hash = hash * 28 + diff[i];
            }
            if (!cache.containsKey(hash)) {
                long value = dfs(a + 1, a + 2);
                cache.put(hash, value);
            }
            return cache.get(hash);
        }
        long result = 0;
        if (now[a] + 3 <= scores[a] && noTieCount != 0) {
            now[a] += 3;
            noTieCount--;
            result += dfs(a, b + 1);
            now[a] -= 3;
            noTieCount++;
        }
        if (now[a] + 2 <= scores[a] && now[b] + 1 <= scores[b] && noTieCount != 0) {
            now[a] += 2;
            now[b] += 1;
            noTieCount--;
            result += dfs(a, b + 1);
            now[a] -= 2;
            now[b] -= 1;
            noTieCount++;
        }
        if (now[a] + 1 <= scores[a] && now[b] + 1 <= scores[b] && tieCount != 0) {
            now[a] += 1;
            now[b] += 1;
            tieCount--;
            result += dfs(a, b + 1);
            now[a] -= 1;
            now[b] -= 1;
            tieCount++;
        }
        if (now[a] + 1 <= scores[a] && now[b] + 2 <= scores[b] && noTieCount != 0) {
            now[a] += 1;
            now[b] += 2;
            noTieCount--;
            result += dfs(a, b + 1);
            now[a] -= 1;
            now[b] -= 2;
            noTieCount++;
        }
        if (now[b] + 3 <= scores[b] && noTieCount != 0) {
            now[b] += 3;
            noTieCount--;
            result += dfs(a, b + 1);
            now[b] -= 3;
            noTieCount++;
        }
        return result % MOD;
    }

}
