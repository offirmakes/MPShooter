import java.io.Serializable;

public class Grenade implements Serializable {
    private int x, y, speed, id;
    private double mouseAngle, lifespan;
    private boolean grenadeActive, explosionActive;
    public Grenade(int x, int y, double mouseAngle, int speed, int id){
        this.x = x;
        this.y = y;
        this.mouseAngle = mouseAngle;
        this.speed = speed;
        lifespan = 1.275;
        grenadeActive = false;
        explosionActive = false;
        this.id = id;
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
    public void setGrenadeStatus(boolean grenadeStatus){
        grenadeActive = grenadeStatus;
    }
    public boolean getGrenadeStatus(){
        return grenadeActive;
    }
    public void setExplosionActive(boolean explosionStatus){
        explosionActive = explosionStatus;
    }
    public boolean getExplosionStatus(){
        return explosionActive;
    } 
    public int getID(){
        return id;
    }
    @Override
    public boolean equals(Object o){
        Grenade g = (Grenade) o;
        if (g.getID() == id){
            return true;
        }
        return false;
    }
}
