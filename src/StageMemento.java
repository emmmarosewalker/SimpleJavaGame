import java.util.ArrayList;

public class StageMemento {

    private ArrayList<bos.Pair<Integer,Integer>> characterLocations = new ArrayList<>();

    public void setState(bos.Pair<Integer, Integer> shepherdIndices, bos.Pair<Integer, Integer> sheepIndices, bos.Pair<Integer, Integer> wolfIndices, bos.Pair<Integer, Integer> playerIndices) {

        // to save the state of the game, all we need to know is the locations of the characters, because the grid indices do not change between games.
        // Add them all to an ArrayList.
        characterLocations.add(shepherdIndices);
        characterLocations.add(sheepIndices);
        characterLocations.add(wolfIndices);
        characterLocations.add(playerIndices);
    }

    /**
     * @return : Returns an ArrayList of the integer cell indices where the characters are located. Returns an ArrayList
     * for ease-of-use when restoring the values
     */
    public ArrayList<bos.Pair<Integer, Integer>> getState() {
        return this.characterLocations;
    }


}
