import java.util.Arrays;

/**
 * Created by JJ on 04-12-2014.
 */
public class Util {
    public static int max(int... numbers){
        return Arrays.stream(numbers).max().getAsInt();
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
}
