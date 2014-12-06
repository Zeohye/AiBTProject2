/**
 * Created by JJ on 04-12-2014.
 */
public class NussinovAlgorithms {
    public String nussinovSingle(String rna){
        int[][] matrix = new int[rna.length()][rna.length()]; //everything is 0;
        int count = 0;
        for(int i = 0; i < rna.length() - 2; i++){
            for(int j = 0; j < rna.length() - i - 1; j++){
                matrix[j][j + i +1] = score(matrix, rna, j, j + i + 1);
            }
        }
        matrix[0][rna.length() - 1] = score(matrix,rna,0,rna.length() - 1);

        //Util.printMatrix(matrix);

        return backTrack(matrix,0,matrix[0].length-1,rna);
    }

    private String backTrack(int[][] score,int i,int j,String rna){
        int lastValue=score[i][j];
        int size = j-i+1;
        int v1,v2,v3,v4=0;
        String retl = "";
        String retr = "";
        while (lastValue != 0){
            v1 = score[i+1][j];
            v2 = score[i][j-1];
            v3 = score[i+1][j-1];


            if(lastValue == v1){
                retl = retl+".";
                i++;
            }else if(lastValue == v2){
                retr = "."+retr;
                j--;
            }else if(lastValue == v3+1){
                retr = ")"+retr;
                retl += "(";

                lastValue = v3;
                j--;
                i++;
            }else {
                for(int k = i+1; k < j-1; k++){
                    if(isPair(rna.charAt(i),rna.charAt(k))) {
                        v4 = score[i+1][k-1] + score[k+1][j]+1;
                        if (lastValue == v4) {
                            retl += backTrack(score, i, k - 1, rna);
                            retr = backTrack(score, k, j, rna) + retr;
                            return retl + retr;
                        }
                    }
                }
            }
        }
        if(size != retl.length()+retr.length())
            for(int k=retl.length()+retr.length(); k<size;k++)
                retl += ".";
        return retl+retr;
    }

    /**
     * @precondition the array is populated with the necessary values for the calculation.
     */
    private int score(int[][] matrix, String rna, int i, int j){
        if(j-i<2)
            return 0;

        int v1,v2,v3,v4 = Integer.MIN_VALUE;
        v1 = v2 = v3 = v4;

        if(isPair(rna.charAt(i),rna.charAt(j)))
            v1 = matrix[i+1][j-1]+1;

        v2 = matrix[i+1][j];
        v3 = matrix[i][j-1];

        for(int k = i+1; k < j-1; k++){
            if(isPair(rna.charAt(i),rna.charAt(k)))
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
