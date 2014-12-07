import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 04-12-2014.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<String> list = FASTAParser.parse("testdata/testseqs.fasta");
        List<String> listNames = FASTAParser.parseNames("testdata/testseqs.fasta");

        NussinovAlgorithms na = new NussinovAlgorithms();
        //System.out.println(na.nussinovSingle(list.get(0)));
        PrintWriter writer = new PrintWriter("output/nussinov_single.dbn", "UTF-8");
        for(int i = 0; i<list.size();i++){
            writer.println(listNames.get(i));
            writer.println(list.get(i));
            writer.println(na.nussinovSingle(list.get(i),null));
        }
        writer.close();

        list = FASTAParser.parse("testdata/testseqs_aligned.fasta");
        listNames = FASTAParser.parseNames("testdata/testseqs_aligned.fasta");

        /*list.clear();
        list.add("GUCUGGAC");
        list.add("GACUGGUC");
        list.add("GGCYGGCC");
       // list.add("GCCUGGGC");*/
        List<String> multi =  na.nussinovMulti(list);
        writer = new PrintWriter("output/nussinov_multi.dbn", "UTF-8");
        for(int i = 0; i<multi.size();i++){
            writer.println(listNames.get(i));
            writer.println(list.get(i).replace("-",""));
            writer.println(multi.get(i));
        }
        writer.close();


        List<String> listSingle = FASTAParser.parse("testdata/testseqs.fasta");
        List<String> listMultiple = FASTAParser.parse("testdata/testseqs_aligned.fasta");;
        listNames = FASTAParser.parseNames("testdata/testseqs.fasta");
        List<String> trueVernaList = FASTAParser.parse("testdata/testseqs_nussinov_multiple_pred.txt");

        List<String> mccTable = MCC.createMCCTable(trueVernaList.get(0),listNames,listSingle,listMultiple);
        for(String s : mccTable){
            System.out.println(s);
        }



        //find true structure
        List<String> resultTrue = na.findTrueStructure(listMultiple,trueVernaList.get(0));

        writer = new PrintWriter("output/nussinov_true.dbn", "UTF-8");
        for(int i = 0; i<resultTrue.size();i++){
            writer.println(listNames.get(i));
            writer.println(list.get(i).replace("-",""));
            writer.println(resultTrue.get(i));
        }
        writer.close();

    }

}
