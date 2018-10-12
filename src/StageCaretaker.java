import bos.GameBoard;

public class StageCaretaker implements KeyObserver {
    private StageMemento savedMemento;
    private Stage stage;

    public StageCaretaker(Stage stage) {
        this.stage = stage;
    }

    /**@Override
     *
     * @param c : Is the char that the user typed, as KeyListened by Stage class.
     * @param gb : The current gameboard, used in other implementations of KeyObserver, but not this one.
     */
    public void notify(char c, GameBoard<Cell> gb) {

        // Either restore or save a game, depending on whether space (save) or 'r' (restore) is pressed.
        if (c == ' ') {
            // Save the game
            System.out.println("GAME SAVED");
            savedMemento = stage.createMemento();
        }

        if (c == 'r') {
            // Restore the game. Note a Memento is saved when game is first initialised to prevent NullPointerException
            // If 'r' is pressed before space.
            stage.setMemento(savedMemento);
        }
    }

}
