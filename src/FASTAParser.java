import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils Henning on 02-09-2014.
 */
public class FASTAParser {

    public static List<String> parse(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line,result ="";

        ArrayList<String> sequences = new ArrayList<String>();

        while ((line = br.readLine())!= null) {
            if(line.charAt(0)=='>'||line.charAt(0)==';'){
                if(!result.equals("")) {
                    sequences.add(result.replaceAll("\\s","").toUpperCase());
                    result = "";
                }
            }else {
                result += line;
            }
        }
        sequences.add(result.replaceAll("\\s","").toUpperCase());
        if(br != null)br.close();

        return sequences;
    }
}
