import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Created by JJ on 04-12-2014.
 */
public class Util {
    public static int max(int... numbers){
        Arrays.sort(numbers);
        return numbers[numbers.length-1];
        //return Arrays.stream(numbers).max().getAsInt();
    }
    public static double max(double... numbers){
        Arrays.sort(numbers);
        return numbers[numbers.length-1];
        //return Arrays.stream(numbers).max().getAsInt();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void printMatrix(int[][] m){
        try{
            int rows = m.length;
            int columns = m[0].length;
            String str = "|\t";

            for(int i=0;i<rows;i++){
                for(int j=0;j<columns;j++){
                    str += m[i][j] + "\t";
                }

                System.out.println(str + "|");
                str = "|\t";
            }

        }catch(Exception e){System.out.println("Matrix is empty!!");}
    }
    public static void printMatrix(double[][] m){
        try{
            int rows = m.length;
            int columns = m[0].length;
            String str = "|\t";

            for(int i=0;i<rows;i++){
                for(int j=0;j<columns;j++){
                    str += m[i][j] + "\t";
                }

                System.out.println(str + "|");
                str = "|\t";
            }

        }catch(Exception e){System.out.println("Matrix is empty!!");}
    }

    public static int getExp(String data,int index,boolean forward) {
        if (forward) {
            int level = 1;
            int length = data.length();
            char ch = ' ';
            while (level > 0 && length - 1 >= index) {
                ch = data.charAt(index);
                if (ch == '(') level++;
                else if (ch == ')') level--;
                index++;
            }
            return index - 1;
        } else {
            int level = 1;
            char ch = ' ';
            while (level > 0 && index >= 0) {
                ch = data.charAt(index);
                if (ch == ')') level++;
                else if (ch == '(') level--;
                index--;
            }
            return index + 1;
        }
    }
}
