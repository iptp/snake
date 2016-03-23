import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SnakePart here.
 */
public class Snake extends Actor
{
    private GreenfootImage snakeImg;
    
    public Snake() {
        configImage();
    } 
    
    public void act() {
        checkTurn();
        if(!checkWall()) {
            move(1);
        }
        lookForPizza();
    }
    
        public void checkTurn() {
        SnakeWorld s = (SnakeWorld) getWorld();
        int turn = s.hasTurn(getX(), getY());
        if(turn == -1) {
            return;
        }
        else {
            setRotation(turn);
            if(s.isTail(this)) {
                s.removeTurn(getX(), getY());
            }
        }
    }
    
    public void lookForPizza() {
        if(isTouching(Pizza.class)) {
            removeTouching(Pizza.class);
            SnakeWorld s = (SnakeWorld) getWorld();
            s.addSnake();
        }
    }
    
    /**
     * Check wether the SnakePart is on the edge of the world
     * and change it's location accordingly
     */
    public boolean checkWall() {
        if(isAtEdge()) {
            int rotation = getRotation();
            int x = getX();
            int y = getY();
            int width = getWorld().getWidth() - 1;
            int height = getWorld().getHeight() - 1;
            
            if(rotation == 0 && x == width) {
                setLocation(0, y);
                return true;
            }
            else if(rotation == 180 && x == 0) {
                setLocation(width, y);
                return true;
            }
            else if(rotation == 90 && y == height) {
                setLocation(x, 0);
                return true;
            }
            else if(rotation == 270 && y == 0) {
                setLocation(x, height);
                return true;
            }
        }
        return false;
    }
    
        private void configImage() {
        snakeImg = new GreenfootImage("snake.png");
        snakeImg.scale(60, 43);
        setImage(snakeImg);
    }
}
