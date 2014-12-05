/**
 * Created by JJ on 04-12-2014.
 */
public class NussinovAlgorithms {
    public void nussinovSingle(String rna){
        int[][] matrix = new int[rna.length()][rna.length()]; //everything is 0;
        int count = 0;
        for(int i = 0; i < rna.length() - 2; i++){
            for(int j = 0; j < rna.length() - i - 1; j++){
                matrix[j][j + i +1] = score(matrix, rna, j, j + i + 1);
            }
        }
        matrix[0][rna.length() - 1] = score(matrix,rna,0,rna.length() - 1);

        Util.printMatrix(matrix);
    }

    private int backTrack(){

        return 0;
    }

    /**
     * @precondition the array is populated with the necessary values for the calculation.
     */
    private int score(int[][] matrix, String rna, int i, int j){
        int v1,v2,v3,v4 = Integer.MIN_VALUE;
        v1 = v2 = v3 = v4;

        if(isPair(rna.charAt(i),rna.charAt(j)))
            v1 = matrix[i+1][j-1]+1;

        v2 = matrix[i+1][j];
        v3 = matrix[i][j-1];

        for(int k = i+1; k < j; k++){
            v4 = Util.max(matrix[i][k] + matrix[k+1][j], v4);
        }

        return Util.max(v1,v2,v3,v4);
    }

    private boolean isPair(char a, char b){
        if((a == 'A' && b == 'U') || (a == 'U' && b == 'A'))
            return true;

        if((a == 'C' && b == 'G') || (a == 'G' && b == 'C'))
            return true;

        if((a == 'G' && b == 'U') || (a == 'U' && b == 'G'))
            return true;

        return false;
    }
}
