/**
 * Class to represent wether there is a turn on the field.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turn {
    private int rotation;
    private boolean turn;
    
    /**
     * Construct a new Turn
     */
    public Turn() {
        this.turn = false;
    }
    
    public int getRotation() {
        return rotation;
    }
    
    public void setTurn(int r) {
        this.turn = true;
        this.rotation = r;
    }
    
    public void removeTurn() {
        this.turn = false;
    }
    
    public boolean hasTurn() {
        return turn;
    }
}
