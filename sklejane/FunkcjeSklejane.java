package sklejane;
import java.util.Scanner;

//jak x jest pomiędzy dwoma przedziałami to biorę wartość tego niższego
public class FunkcjeSklejane {
    public static float[] gauss(float v[][], int r, int c) {
        float t[] = new float[c];
        int i = 0, j = 0, k = 0, m = 0, n = 0, x = 0;
        float temp = 0;
        for (i = 0; i < r - 1; i++) {
            temp = v[i][i];
            for (j = 0; j < c; j++) {
                t[j] = v[i][j] / temp;
                v[i][j] = t[j];
            }
            k = 0;
            for (m = i + 1; m < r; m++) {
                temp = v[m][x];
                for (n = 0; n < c; n++) {
                    v[m][n] = v[m][n] - (t[n] * temp);
                }
            }
            for (k = 0; k < c; k++) {
                t[k] = 0;
            }
            temp = 0;
            x++;
        }
        for (i = r - 1; i > 0; i--) {
            v[i][c - 1] = v[i][c - 1] / v[i][i];
            v[i][i] = 0;
            for (j = i - 1; j >= 0; j--) {
                v[j][c - 1] = v[j][c - 1] - (v[j][i] * v[i][c - 1]);
                v[j][i] = 0;
            }
        }
        float vector[] = new float[r];
        for (i = 0; i < r; i++) {
            vector[i] = Math.round(v[i][c - 1]);
        }
        return vector;
    }
    public static void main(String[] args) {
        int lwezlow;
        Scanner scanner = new Scanner(System.in);
        System.out.println("liczba węzłów interpolacji: ");

        lwezlow = Integer.parseInt(scanner.nextLine());
        float xfun;
        float[] x = new float[lwezlow];
        float[] y = new float[lwezlow + 2];
        float[][] mx = new float[4 + lwezlow - 2][4 + lwezlow - 1];
        float[] wsp = new float[4 + lwezlow - 2];
        int moment = -1;
        float wynik = 0;
        for (int i = 0; i < lwezlow; i++) {
            System.out.println("Podaj x" + i);
            x[i] = Float.parseFloat(scanner.nextLine());
            System.out.println("Podaj y" + i);
            y[i] = Float.parseFloat(scanner.nextLine());
        }
        System.out.println("Podaj wartość pochodnej x0");
        y[lwezlow] = Float.parseFloat(scanner.nextLine());
        System.out.println("Podaj wartość pochodnej ostatniego x" + (lwezlow - 1));
        y[lwezlow + 1] = Float.parseFloat(scanner.nextLine());
        System.out.println("Podaj x do znalezienia wartosci funkcji");
        xfun = Float.parseFloat(scanner.nextLine());
        for (int i=0; i<lwezlow; i++) {
            for (int j=0; j<lwezlow; j++){
                if(j!=lwezlow-1)
                    mx[i][j] = (float) Math.pow(x[i], j);
                if(j>0)
                    if (i <= j) {
                        mx[i][lwezlow - 2 + j] = 0;
                    } else {
                        mx[i][lwezlow - 2 + j] = (float) Math.pow(x[i] - x[j], 3);
                    }
                if (i == 0 && j!=lwezlow-1) {
                    mx[lwezlow + i][j] = (float) ((j) * Math.pow(x[i], j - 1));
                }
                else if (i > 0 && i<2 && j!=lwezlow-1) {
                    mx[lwezlow + i][j] = (float) ((j) * Math.pow(x[lwezlow - 1], j - 1));
                    mx[lwezlow + i][lwezlow - 2 + j] = (float) (3 * Math.pow(x[lwezlow - 1] - x[j], 2));
                }
            }
        }
        for (int i = 0; i < y.length; i++) {
            mx[i][4 + lwezlow - 2] = y[i];
        }

        wsp = gauss(mx, 4 + lwezlow - 2, 4 + lwezlow - 2 + 1);
        for (int i = 0; i < lwezlow; i++) {
            if (xfun > x[i] && xfun < x[lwezlow - 1]) {
                moment = i;
            }
        }
        if (moment != -1) {
            for (int i = 0; i < moment + 4; i++) {
                if (i > 3) {
                    wynik += wsp[i] * Math.pow(xfun - x[i - 3], 3);
                } else {
                    wynik += wsp[i] * Math.pow(xfun, i);
                }
            }
            System.out.println("S3(" + xfun + ") = " + wynik + ".");
        } else {
            System.out.println("Podany x nie znajduje sie w środku tego przedziału. ");
        }
    }
}