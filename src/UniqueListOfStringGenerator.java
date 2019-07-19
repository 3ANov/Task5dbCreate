import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UniqueListOfStringGenerator {
    public static List<String>  generate(Integer length){
        char [][] pairs = {{'a','z'},{'A','Z'}};
        List<String> uniqueRandomStringList = new ArrayList<String>();
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        for (int i = 0; i < 100; i++) {
            uniqueRandomStringList.add(generator.generate(length));
        }

        HashSet hs = new HashSet();
        hs.addAll(uniqueRandomStringList);
        uniqueRandomStringList.clear();
        uniqueRandomStringList.addAll(hs);

        hs.clear();
        return uniqueRandomStringList;

    }
}
