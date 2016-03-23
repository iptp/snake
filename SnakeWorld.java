import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The SnakeWorld class is responsible for defining and creating 
 * the world, initializing the initial setting of the game, 
 * checking if the snake needs to turn and checking if the user is 
 * pressing one of the arrow keys.
 * 
 * It is also responsible for putting new Pizzas in the game and adding
 * a new part for the snake whenever it eats a pizza.
 * 
 * @author Gabriel Tibúrcio
 * @version 2016/03/23
 */
public class SnakeWorld extends World
{
    private ArrayList<Snake> snake;
    private Turn[][] turns;
    
    /**
     * Construct the world with 10x10 cells, 60x60 pixels per cell and
     * add the default snake in the middle of the screen.
     */
    public SnakeWorld() {    
        super(10, 10, 60);
        turns = new Turn[10][10];
        initializeTurns();
              
        createInitialSnake();
        putPizza();
        Greenfoot.setSpeed(25);
    }
    
    public void act() {
        checkKey();
        checkCollision();
    }
    
    /**
     * Checks to see one of the arrow keys is being pressed
     * and set a turn in the world in the position of the head
     * of the snake.
     */
    public void checkKey() {
        int r = getHeadRotation();
        int x = getHeadX();
        int y = getHeadY();
        
        if(Greenfoot.isKeyDown("up") && r != 90) {
            turns[x][y].setTurn(270);
        }
        else if(Greenfoot.isKeyDown("down") && r != 270) {
            turns[x][y].setTurn(90);
        }
        else if(Greenfoot.isKeyDown("left") && r != 0) {
            turns[x][y].setTurn(180);
        }
        else if(Greenfoot.isKeyDown("right") && r != 180) {
            turns[x][y].setTurn(0);
        }
    }
    
    /**
     * Check to see if a collision is going to happen based on the direction that
     * the head of the snake is facing and if there is another part of the
     * snake in that direction.
     */
    public void checkCollision() {
        int r = getHeadRotation();
        int x = getHeadX();
        int y = getHeadY();
        
        if(turns[x][y].hasTurn()) {
            r = turns[x][y].getRotation();
        }
        
        if(r == 0) {
            if(x == getWidth() - 1 && getObjectsAt(0, y, Snake.class).size() != 0) {
                lose();
            }
            else if(getObjectsAt(x + 1, y, Snake.class).size() != 0) {
                lose();
            }
        }
        else if(r == 180) {
            if(x == 0 && getObjectsAt(getWidth() - 1, y, Snake.class).size() != 0) {
                lose();
            }
            else if(getObjectsAt(x - 1, y, Snake.class).size() != 0) {
                lose();
            }
        }
        else if(r == 90) {
            if(y == getHeight() - 1 && getObjectsAt(x, 0, Snake.class).size() != 0) {
                lose();
            }
            else if(getObjectsAt(x, y + 1, Snake.class).size() != 0) {
                lose();
            }
        }
        else if(r == 270) {
            if(y == 0 && getObjectsAt(x, getHeight() - 1, Snake.class).size() != 0) {
                lose();
            }
            else if(getObjectsAt(x, y - 1, Snake.class).size() != 0) {
                lose();
            }
        }
    }
    
    public void lose() {
        showText("Perdeu", getWidth()/2, getHeight()/2);
        Greenfoot.stop();
    }
    
    /**
     * Add a new part of the snake based on the tail of it.
     * Also handles the case where the tail is at the edges of the world.
     */
    public void addSnake() {
        Snake tail = getTail();
        Snake s = new Snake();
        int r = tail.getRotation();
        int x, y;
        s.setRotation(r);
        if(r == 0 || r == 180) {
            /* is in the left edge so the new part 
            needs to be in the opposite side */
            if(tail.getX() == 0) {
                x = getWidth() - 1;
            }
            else {
                x = tail.getX();
            }
            y = tail.getY();
        }
        else {
            /* is in the upper edge so the new part 
            needs to be in the opposite side */
            if(tail.getY() == 0) {
                y = getHeight() - 1;
            }
            else {
                y = tail.getY();
            }
            x = tail.getX();
        }
        
        snake.add(s);
        addObject(s, x, y);
        putPizza();
    }
    
    /**
     * Put a new pizza into the world in a random position.
     */
    public void putPizza() {
        int x = Greenfoot.getRandomNumber(10);
        int y = Greenfoot.getRandomNumber(10);
        
        addObject(new Pizza(), x, y);
    }
    
    /**
     * Check wether has a turn in the world in the given x,y position
     * Return -1 case false and the rotation amount case true
     */
    public int hasTurn(int x, int y) {
        if(turns[x][y].hasTurn()) {
            return turns[x][y].getRotation();
        }
        return -1;
    }
    
    /**
     * Remove the turn in the given position x,y
     */
    public void removeTurn(int x, int y) {
        turns[x][y].removeTurn();
    }
    
    /**
     * Check wether the given Snake s is the tail of the Snake
     */
    public boolean isTail(Snake s) {
        return s.equals(snake.get(snake.size() -1));
    }
    
    /**
     * Set up the initial world with the snake in the middle
     */
    private void createInitialSnake() {
        snake = new ArrayList<Snake>();
        //head
        Snake s = new Snake();
        addObject(s, getWidth()/2, getHeight()/2);
        snake.add(s);
        
        //body
        s = new Snake();
        addObject(s, getWidth()/2 - 1, getHeight()/2);
        snake.add(s);
        
        s = new Snake();
        addObject(s, getWidth()/2 - 2, getHeight()/2);
        snake.add(s);
    }
    
    /**
     * Get the head rotation
     * 0 - Facing Right
     * 90 - Facing Down
     * 180 - Facing Left
     * 270 - Facing Up
     */
    private int getHeadRotation() {
        return snake.get(0).getRotation();
    }
    
    /**
     * Get the position X of the head of the snake
     */
    private int getHeadX() {
        return snake.get(0).getX();
    }
    
    /**
     * Get the position Y of the head of the snake
     */
    private int getHeadY() {
        return snake.get(0).getY();
    }
    
    /**
     * Initialize the turns matrix with false (default) in all objects
     */
    private void initializeTurns() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                turns[i][j] = new Turn();
            }
        }
    }
    
    /**
     * Return the Snake object that represents the tail of the snake.
     */
    private Snake getTail() {
        return snake.get(snake.size() - 1);
    }
}
