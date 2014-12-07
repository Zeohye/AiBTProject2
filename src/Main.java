import java.io.IOException;
import java.io.PrintWriter;
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
            writer.println(na.nussinovSingle(list.get(i)));
        }
        writer.close();
    }

}
