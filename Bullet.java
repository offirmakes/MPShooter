import java.io.Serializable;

public class Bullet implements Serializable {
    private int x, y, id, speed;
    private double mouseAngle, lifespan;
    private boolean poison, laser;
    public Bullet(int x, int y, double mouseAngle, int id, int speed, boolean poison){
        this.x = x;
        this.y = y;
        this.id = id;
        this.mouseAngle = mouseAngle;
        this.speed = speed;
        this.poison = poison;
        lifespan = 2;
    }
    public boolean getLaser(){
        return laser;
    }
    public boolean getPoison(){
        return poison;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getSpeed(){
        return speed;
    }
    public double getMouseAngle(){
        return mouseAngle;
    }
    public void move(){
        x = Math.round((float)(x+Math.cos(mouseAngle+Math.toRadians(-90))*speed));
        y = Math.round((float)(y+Math.sin(mouseAngle+Math.toRadians(-90))*speed));
    }
    public void setMouseAngle(double mouseAngle){
        this.mouseAngle = mouseAngle;
    }
    public void decreaseLifeSpan(double refreshTime){
        lifespan -= refreshTime;
    }
    public double getLifeSpan(){
        return lifespan;
    }
    public int getID(){
        return id;
    }
    @Override
    public boolean equals(Object o){
        Bullet b = (Bullet) o;
        if (b.getID() == id){
            return true;
        }
        return false;
    }
}
