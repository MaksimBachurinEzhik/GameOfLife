import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Максим on 17.12.2017.
 */
public class MaximGameOfLife implements GameOfLife{
    @Override
    public List<String> play(String inputFile) {
        List<String> list = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner  = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int n = 0, m = 0;
        if (scanner.hasNextLine()) {
            String[] nm = scanner.nextLine().split(" ");
            n = Integer.valueOf(nm[0]);
            m = Integer.valueOf(nm[1]);
        }
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }

        boolean[][] field = new boolean[n][n];
        boolean[][] field1 = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (list.get(i).charAt(j) == '1') {
                    field[i][j] = true;
                }
                else {
                    field[i][j] = false;
                }
            }
        }
        boolean[][] lastStep = field;
        int delej = 2;
        List<MyThread> threads = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < delej; j++) {
                for (int k = 0; k < delej; k++) {
                    MyThread myThread;
                    if (i % 2 == 0) {
                        myThread = new MyThread(field, field1,
                                n,
                                j * n / delej,
                                (j + 1) * n / delej + (j == (delej - 1) ? n % delej : 0),
                                k * n / delej,
                                (k + 1) * n / delej + (k == (delej - 1) ? n % delej : 0));
                        lastStep = field1;
                    }
                    else {
                        myThread = new MyThread(field1, field,
                                n,
                                j * n / delej,
                                (j + 1) * n / delej + (j == (delej - 1) ? n % delej : 0),
                                k * n / delej,
                                (k + 1) * n / delej + (k == (delej - 1) ? n % delej : 0));
                        lastStep = field;
                    }
                    threads.add(myThread);
                    myThread.start();
                }
            }
            for (MyThread myThread : threads) {
                try {
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] str = new char[n];
            for (int j = 0; j < n; j++) {
                if (lastStep[i][j]) {
                    str[j] = '1';
                }
                else {
                    str[j] = '0';
                }
            }
            result.add(String.valueOf(str));
        }
        return result;
    }
}
