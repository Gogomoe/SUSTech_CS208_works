import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class RunningTimeSurvey {
    //       task name        function name    run times upper
    static String[][] taskList = {
            {"LinearTimeTest", "linearTime", "10000000"},
            {"LinearTimeTest", "linearTimeCollections", "10000000"},
            {"NlognTimeTest", "NlognTime", "1000000"},
            {"QuadraticTimeTest", "QuadraticTime", "100000"},
            {"CubicTimeTest", "CubicTime", "1000"},
            {"ExponentialTimeTest", "ExponentialTime", "10"},
            {"FactorialTimeTest", "BruceForceFactorial", "10"}
            /*
                     * { "NlognTimeTest",       "NlognTime",             "1000000"},
                     * { "QuadraticTimeTest",   "QuadraticTime",         "100000"},
                     * { "CubicTimeTest",       "CubicTime",             "1000"},
                     * { "ExponentialTimeTest", "ExponentialTime",       "10"},
                     * { "FactorialTimeTest",   "FactorialTime",         "10"}
                     */
    };

    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        try {
            File xlsFile = new File("RunningTimeSurvey.xls");
            // Create a workbook
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(xlsFile);

            // Create a worksheet
            WritableSheet sheet = workbook.createSheet("RunningTime", 0);
            // the first row
            for (int j = 1, n = 1; j <= 8; j++) {
                n = 10 * n;
                sheet.addCell(new Label(j + 1, 0, "n = " + n));
            }
            for (int i = 0; i < taskList.length; i++) {
                String[] taskInfo = taskList[i];

                Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
                Method method = me.getMethod(taskInfo[1], int.class);
                int upper = Integer.parseInt(taskInfo[2]);
                sheet.addCell(new Label(0, i + 1, taskInfo[0]));
                sheet.addCell(new Label(1, i + 1, taskInfo[1]));

                for (int j = 1, n = 1; Math.pow(10, j) <= upper; j++) {
                    n = 10 * n;
                    Long time = (Long) method.invoke(null, n);
                    // 向工作表中添加数据
                    sheet.addCell(new Label(j + 1, i + 1, time.toString()));
                }

            }
            workbook.write();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long linearTimeCollections(int n) {
        ArrayList<Long> arrayList = new ArrayList<Long>(n);
        generateArrayList(n, arrayList);
        long timeStart = System.currentTimeMillis();
        getMax(n, arrayList);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long linearTime(int n) {
        long[] list = new long[n];
        generateList(n, list);
        long timeStart = System.currentTimeMillis();
        getMax(n, list);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long getMax(long n, long[] list) {
        long max = list[0];
        for (int i = 1; i < n; i++) {
            if (list[i] > max) {
                max = list[i];
            }
        }
        return max;
    }

    public static void generateList(int n, long[] list) {
        for (int i = 0; i < n; i++) {
            list[i] = i;
        }
        // shuffle
        Random rnd = new Random();
        for (int i = list.length; i > 1; i--) {
            int j = rnd.nextInt(i);
            long temp = list[j];
            list[j] = list[i - 1];
            list[i - 1] = temp;
        }
    }

    public static void generateArrayList(int n, ArrayList<Long> arrayList) {
        for (long i = 0; i < n; i++) {
            arrayList.add(i);
        }
        // shuffle
        Collections.shuffle(arrayList);
    }

    public static long getMax(long n, ArrayList<Long> arrayList) {
        long max = arrayList.get(0);
        for (int i = 1; i < n; i++) {
            if (arrayList.get(i) > max) {
                max = arrayList.get(i);
            }
        }
        return max;
    }

    public static long NlognTime(int n) {
        ArrayList<Long> list = new ArrayList<>();
        generateArrayList(n, list);
        long timeStart = System.currentTimeMillis();

        list.sort(Long::compareTo);

        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long QuadraticTime(int n) {
        ArrayList<Long> list = new ArrayList<>();
        generateArrayList(n, list);
        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i; j < list.size(); j++) {
                if (list.get(j) < list.get(minIndex)) {
                    minIndex = j;
                }
            }
            long tmp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, tmp);
        }

        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long CubicTime(int n) {
        ArrayList<HashSet<Integer>> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            HashSet<Integer> set = new HashSet<>();
            for (int j = 0; j < n; j++) {
                set.add(random.nextInt());
            }
            list.add(set);
        }
        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                HashSet<Integer> set1 = list.get(i);
                HashSet<Integer> set2 = list.get(j);
                boolean disjoint = true;
                for (Integer integer : set1) {
                    if (set2.contains(integer)) {
                        disjoint = false;
                    }
                }

            }
        }

        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long ExponentialTime(int n) {
        StringBuilder val = new StringBuilder("1");
        for (int i = 0; i < n; i++) {
            val.append("0");
        }

        long timeStart = System.currentTimeMillis();

        BigInteger max = new BigInteger(val.toString());
        BigInteger i = new BigInteger("1");
        BigInteger one = new BigInteger("1");
        while (!i.equals(max)) {
            i = i.add(one);
        }

        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long BruceForceFactorial(int n) {
        // to generate you test input data
        long timeStart = System.currentTimeMillis();
        // to write a algorithm
        Factorial(n);
        long timeEnd = System.currentTimeMillis();
        long timeCost = timeEnd - timeStart;
        return timeCost;
    }

    public static long Factorial(int n) {
        if (n == 1)
            return 1;
        else {
            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Factorial(n - 1);
            }
            return sum;
        }

    }

}
