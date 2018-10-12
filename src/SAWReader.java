import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SAWReader {
    List<String> contents;

    public SAWReader(String filename) {
         try {
             contents = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filename));
         } catch (IOException e){
             contents = new ArrayList();
         }
    }

    public bos.Pair<Integer, Integer> getSheepLoc() {
        return searchForPair("sheep");
    }

    public bos.Pair<Integer, Integer> getWolfLoc(){
        return searchForPair("wolf");
    }

    public bos.Pair<Integer, Integer> getShepherdLoc(){
        return searchForPair("shepherd");
    }

    public ArrayList<bos.Pair<Integer, Integer>> getBlockLocs() {return searchForMultiple("block");}

    // we can't use this method for blocks too because it'd always return the first block it finds, and we need multiple
    private bos.Pair<Integer,Integer> searchForPair(String target){
        for (String s: contents){
            Pattern p = Pattern.compile(target + ":\\s*\\((\\d*),\\s*(\\d*)\\)");
            Matcher m = p.matcher(s);
            if(m.matches()){
                return new bos.Pair( Integer.parseInt(m.group(1).trim())
                                   , Integer.parseInt(m.group(2).trim()));
            }
        }
        return new bos.Pair(0,0);
    }

    private ArrayList<bos.Pair<Integer, Integer>> searchForMultiple(String target) {
        ArrayList<bos.Pair<Integer, Integer>> matches = new ArrayList<>();
        for (String s: contents) {
            Pattern p = Pattern.compile(target + ":\\s*\\((\\d*),\\s*(\\d*)\\)");
            Matcher m = p.matcher(s);
            if (m.matches()) {
                matches.add(new bos.Pair<>(
                        Integer.parseInt(m.group(1).trim()),
                        Integer.parseInt(m.group(2).trim())
                        )
                );
            }
        }
        return matches;
    }

}
