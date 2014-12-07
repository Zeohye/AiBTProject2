import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 07-12-2014.
 */
public class MCC {
    public static double mcc(String verna1, String verna2){
        double tp,tn,fp,fn = 0;
        tp = tn = fp = fn;

        for (int i = 0; i < verna1.length(); i++){
            if(verna1.charAt(i) == verna2.charAt(i)){
                if(verna1.charAt(i) == '.')
                    tn++;
                else
                    tp++;
            } else {
                if(verna1.charAt(i) == '.')
                    fn++;
                else
                    fp++;
            }
        }

        return (tp*tn - fp*fn)/Math.sqrt((tp+fn)*(tn+fp)*(tp+fp)*(tn+fn));
    }

    public static List<String> createMCCTable(String trueVerna, List<String> names, List<String> single, List<String> multiple){
        NussinovAlgorithms na = new NussinovAlgorithms();
        List<String> resultSingle = new ArrayList<String>();
        for(String s : single){
            resultSingle.add(na.nussinovSingle(s, null));
        }

        List<String> resultMultiple = na.nussinovMulti(multiple);
        List<String> resultTrue = na.findTrueStructure(multiple,trueVerna);

        List<String> table = new ArrayList<String>();

        for (int i = 0; i < names.size(); i++){
            table.add(names.get(i) + "\t\t" + Util.round(mcc(resultTrue.get(i), resultSingle.get(i)),2) + "\t" + Util.round(mcc(resultTrue.get(i), resultMultiple.get(i)), 2));
        }

        return table;
    }

}
