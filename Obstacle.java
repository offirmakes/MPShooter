public class Obstacle {
    private int x, y;
    private int width, height;
    private int shape;
    private int imageType;
    public Obstacle(int x, int y, int width, int height, int shape, int imageType){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shape = shape;
        this.imageType = imageType;
    }
    public boolean intersects(Obstacle other) {
        if (shape == 0){ // rectangular
            return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
        } else if (shape == 1) { // ciruclar
            double distanceSquared = Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2);
            double radiusSumSquared = Math.pow(this.width + other.width, 2);
            return distanceSquared <= radiusSumSquared;
        }
        return false;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getShape(){
        return shape;
    }
    public int getImageType(){
        return imageType;
    }
}
