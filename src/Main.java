import java.io.IOException;
import java.util.List;

/**
 * Created by JJ on 04-12-2014.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<String> list = FASTAParser.parse("testdata/testseqs.fasta");
        NussinovAlgorithms na = new NussinovAlgorithms();
        //na.nussinovSingle(list.get(0));
        for(String s : list) {
            na.nussinovSingle(s);
            System.out.println();
        }

    }

}
