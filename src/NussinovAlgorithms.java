import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 04-12-2014.
 */
public class NussinovAlgorithms {
    public String nussinovSingle(String rna,double[][] mij){
        if(mij==null)
            mij = new double[rna.length()][rna.length()];

        double[][] matrix = new double[rna.length()][rna.length()]; //everything is 0;
        for(int i = 0; i < rna.length() - 2; i++){
            for(int j = 0; j < rna.length() - i - 1; j++){
                matrix[j][j + i +1] = score(matrix, rna, j, j + i + 1,mij);
            }
        }
        matrix[0][rna.length() - 1] = score(matrix,rna,0,rna.length() - 1,mij);

       // Util.printMatrix(matrix);

        return backTrack(matrix,0,matrix[0].length-1,mij);
    }


    public List<String> nussinovMulti(List<String> rnas){
        double[][] mij = mutualInformation(rnas);
        ArrayList<String> result= new ArrayList<String>();
        for(String s : rnas)
            result.add(nussinovSingle(s, mij));

        //refine?

        return gapRemoval(result, rnas);
    }

    public List<String> findTrueStructure(List<String> aligned, String trueVerna){
        List<String> trueList = new ArrayList<String>();
        for(String s : aligned){
            trueList.add(trueVerna);
        }

        return gapRemoval(trueList,aligned);
    }

    public List<String> gapRemoval(List<String> verna, List<String> aligned){
        ArrayList<String> res= new ArrayList<String>();
        for(int i=0; i<aligned.size();i++){
            String s = aligned.get(i);
            String r = verna.get(i);
            while(s.contains("-")) {
                int index = s.indexOf('-');
                if(r.charAt(index)=='(') {
                    int end = Util.getExp(r, index + 1,true);
                    s = s.substring(0, index) + s.substring(index+1);
                    r = r.substring(0, index)+r.substring(index+1,end)+"."+r.substring(end+1);

                }else if(r.charAt(index)==')'){
                    int start = Util.getExp(r, index - 1,false);
                    s = s.substring(0, index) + s.substring(index+1);
                    if(start ==0)
                        r = "."+r.substring(start+1,index)+r.substring(index+1);
                    else
                        r = r.substring(0,start)+"."+r.substring(start+1,index)+r.substring(index+1);

                }else if(r.charAt(index)=='.'){
                    if(index==0){
                        s=s.substring(1);
                        r=r.substring(1);
                    }else {
                        s = s.substring(0, index) + s.substring(index + 1);
                        r = r.substring(0, index) + r.substring(index + 1);
                    }
                }
            }
            res.add(r);
        }

        /*
        for(int i = 0; i < res.size(); i++){
            System.out.println("Printing out number: " + i);
            System.out.println(aligned.get(i));
            System.out.println(verna.get(i));
            System.out.println(res.get(i));
            System.out.println();
        }*/


        return res;
    }

    public double[][] mutualInformation(List<String> rnas){
        double[][] matrix = new double[rnas.get(0).length()][rnas.get(0).length()]; //everything is 0;
        char[] chars = {'A','C','G','U','-'};
        double size = rnas.size();
        for(int i=0; i<rnas.get(0).length();i++){
            for(int j=rnas.get(0).length()-1; j>i;j--) {
                double value = 0;
                for(char xj : chars){
                    for(char xi : chars) {
                        if(!isPair(xj,xi))continue;
                        double fxi = 0;
                        double fxj = 0;
                        double fxixjPair = 0;
                        for (String s : rnas) {
                            if (s.charAt(i) == xi)
                                fxi++;
                            if (s.charAt(j) == xj)
                                fxj++;
                            if ((s.charAt(i) == xi && s.charAt(j) == xj) && isPair(s.charAt(i), s.charAt(j)))
                                fxixjPair++;
                        }
                        if (fxixjPair != 0) {
                            value += (fxixjPair / size) * log2((fxixjPair / size) / ((fxi / size) * (fxj / size)));
                        }
                    }
                }
                matrix[i][j] = value;
            }
        }
        return matrix;
    }

    private double log2(double i) {
        return Math.log(i)/Math.log(2);
    }

    private String backTrack(double[][] score,int i,int j,double[][]mij){
        double lastValue=score[i][j];
        int size = j-i+1;
        double v1,v2,v3,v4=0;
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
            }else if(lastValue == v3+1+mij[i+1][j-1]){
                retr = ")"+retr;
                retl += "(";

                lastValue = v3;
                j--;
                i++;
            }else {
                for(int k = i+1; k < j-1; k++){
                        v4 = score[i][k] + score[k+1][j];
                        if (lastValue == v4) {
                            retl += backTrack(score, i, k,mij);
                            retr = backTrack(score, k+1, j,mij) + retr;
                            return retl + retr;
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
    private double score(double[][] matrix, String rna, int i, int j,double[][]mij){
        if(j-i<2)
            return 0;

        double v1,v2,v3,v4 = Integer.MIN_VALUE;
        v1 = v2 = v3 = v4;

        if(isPair(rna.charAt(i),rna.charAt(j)))
            v1 = matrix[i+1][j-1]+1+mij[i+1][j-1];

        v2 = matrix[i+1][j];
        v3 = matrix[i][j-1];

        for (int k = i+1; k < j-1; k++){
            v4 = Util.max(matrix[i][k] + matrix[k+1][j], v4);
        }

        return Util.max(v1,v2,v3,v4);
    }

    private boolean isPair(char a, char b){
        if(a=='-' && b !='-' || a!='-' && b =='-')
            return true;

        if((a == 'A' && b == 'U') || (a == 'U' && b == 'A'))
            return true;

        if((a == 'C' && b == 'G') || (a == 'G' && b == 'C'))
            return true;

        if((a == 'G' && b == 'U') || (a == 'U' && b == 'G'))
            return true;

        return false;
    }

}
