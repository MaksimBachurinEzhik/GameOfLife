/**
 * Created by Максим on 17.12.2017.
 */
public class MyThread extends Thread {
    private boolean[][] field;
    private boolean[][] result;
    private int n;
    private int x_start, x_end, y_start, y_end;
    public MyThread(boolean[][] field, boolean[][] result, int n, int x_start, int x_end, int y_start, int y_end) {
        this.x_start = x_start;
        this.x_end = x_end;
        this.y_start = y_start;
        this.y_end = y_end;
        this.field = field;
        this.result = result;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = x_start; i < x_end; i++) {
            for (int j = y_start; j < y_end; j++) {
                int[] arrX = { i - 1 >= 0 ? i - 1 : 0, i, i + 1 < n ? i + 1 : n - 1};
                int[] arrY = { j - 1 >= 0 ? j - 1 : 0, j, j + 1 < n ? j + 1 : n - 1};
                //System.out.println(x_start + " " + x_end + " " + y_start + " " + y_end);
                int live = 0;
                for (int x : arrX) {
                    for (int y : arrY) {
                        if (field[x][y]) {
                            if (x != i || y != j)
                                live++;
                        }
                    }
                }
                if (field[i][j]) {
                    if (live == 2 || live == 3) {
                        result[i][j] = true;
                    }
                    else {
                        result[i][j] = false;
                    }
                }
                else {
                    if (live == 3) {
                        result[i][j] = true;
                    }
                    else {
                        result[i][j] = false;
                    }
                }
            }
        }
    }
}
