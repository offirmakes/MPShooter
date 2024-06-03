import java.io.Serializable;

public class Player implements Serializable{
    private int screenX, screenY, mapX, mapY, heroType, speed, weaponX, weaponY, primaryDamage, secondaryDamage, damageTaken, secondAbilityUses, ultDamage, maxHealth, currentHealth, weaponID, totalElim;
    private ArrayList<Bullet> bullets;
    private ArrayList<Grenade> grenades;
    private boolean weaponThrow, weaponHit, weaponHolding;
    private double daggerAngle;
    private String playerSpectating;
    private double mouseAngle, fireCooldown, ultCooldown, ultDuration, regularAbilityCooldown;
    private String username;
    private boolean ultActive, poisoned, alive;
    private int shieldHealth;
    public Player(int screenX, int screenY, int mapX, int mapY){
        username = "";
        reset(screenX, screenY, mapX, mapY);
    }
    public void reset(int screenX, int screenY, int mapX, int mapY){
        this.screenX = screenX;
        this.screenY = screenY;
        this.mapX = mapX;
        this.mapY = mapY;
        bullets = new ArrayList<Bullet>();
        grenades = new ArrayList<Grenade>();  
        playerSpectating = ""; 
        weaponThrow = false;
        ultActive = false;
        poisoned = false;
        weaponHit = false;
        weaponHolding = false;
        alive = false;
        weaponID = 0;
        secondAbilityUses = 0;
        secondaryDamage = 0;
        ultDamage = 0;
        fireCooldown = 0;
        ultCooldown = 0;
        speed = 0;
        heroType = 0;
        mouseAngle = 0;
        weaponX = 0;
        weaponY = 0;
        primaryDamage = 0;
        maxHealth = 0;
        currentHealth = 0;
        ultDuration = 0;
        daggerAngle = 0;
        regularAbilityCooldown = 0;
        damageTaken = 0; 
        totalElim = 0;
        shieldHealth = 0;
    }
    public boolean boundaries(int xPos, int yPos){
        double distance = Math.sqrt(Math.pow((635 - xPos), 2) + Math.pow((440 - yPos), 2));
        if (distance < 39){
            return true;
        }
        return false;
    }
    public void incrementWeaponID(){
        weaponID++;
    }
    public int getWeaponID(){
        return weaponID;
    }
    public void decreaseShieldHealth(){
        shieldHealth--;
    }
    public void setShieldHealth(int shieldHealth){
        this.shieldHealth = shieldHealth;
    }
    public int getShieldHealth(){
        return shieldHealth;
    }
    public void incrementTotalElim(){
        totalElim++;
    }
    public int getTotalElim(){
        return totalElim;
    }
    public void setDaggerAngle(double daggerAngle){
        this.daggerAngle = daggerAngle;
    }
    public double getDaggerAngle(){
        return daggerAngle;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public boolean getAlive(){
        return alive;
    }
    public void setDamageTaken(int damage){
        damageTaken = damage;
    }
    public int getDamageTaken(){
        return damageTaken;
    }
    public double distance(int xPos, int yPos){
        return Math.sqrt(Math.pow((645 - xPos), 2) + Math.pow((450 - yPos), 2));
    }
    public void setPoison(boolean poison){
        poisoned = poison;
    }
    public boolean getPoison(){
        return poisoned;
    }
    public double getRegularAbilityCooldown(){
        return regularAbilityCooldown;
    }
    public void setRegularAbilityCooldown(double regularAbilityCooldown){
        this.regularAbilityCooldown = regularAbilityCooldown;
    }
    public void setSecondAbilityUses(int secondAbilityUses){
        this.secondAbilityUses = secondAbilityUses;
    }
    public int getSecondAbilityUses(){
        return secondAbilityUses;
    }
    public void setPrimaryDamge(int damage){
        primaryDamage = damage;
    }
    public int getPrimaryDamage(){
        return primaryDamage;
    }
    public void setSecondaryDamage(int damage){
        secondaryDamage = damage;
    }
    public int getSecondaryDamage(){
        return secondaryDamage;
    }
    public void setUltDamage(int damage){
        ultDamage = damage;
    }
    public int getUltDamage(){
        return ultDamage;
    }
    public void setFireCooldown(double fireCooldown){
        this.fireCooldown = fireCooldown;
    }
    public double getFireCooldown(){
        return fireCooldown;
    }
    public void setUltCooldown(double ultCooldown){
        this.ultCooldown = ultCooldown;
    }
    public double getUltCooldown(){
        return ultCooldown;
    }
    public void setUltStatus(boolean ultStatus){
        ultActive = ultStatus;
    }
    public boolean getUltStatus(){
        return ultActive;
    }
    public void setUltDuration(double ultDuration){
        this.ultDuration = ultDuration;
    }
    public double getUltDuration(){
        return ultDuration;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getWeaponX(){
        return weaponX;
    }
    public int getWeaponY(){
        return weaponY;
    }
    public void setWeaponX(int weaponX){
        this.weaponX = weaponX;
    }
    public void setWeaponY(int weaponY){
        this.weaponY = weaponY;
    }
    public int getScreenX(){
        return screenX;
    }
    public int getSpeed(){
        return speed;
    }
    public int getScreenY(){
        return screenY;
    }
    public int getMapX(){
        return mapX;
    }
    public int getMapY(){
        return mapY;
    }
    public void changeMapX(int mapX){
        this.mapX = mapX;
    }
    public void changeMapY(int mapY){
        this.mapY = mapY;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setHeroType(int heroType){
        this.heroType = heroType;
    }
    public int getHeroType(){
        return heroType;
    }
    public void setMouseAngle(double angle){
        mouseAngle = angle;
    }
    public double getMouseAngle(){
        return mouseAngle;
    }
    public void addBullets(int x, int y, double mouseAngle, int id, int speed, boolean poison){
        bullets.add(new Bullet(x, y, mouseAngle, id, speed, poison));
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    public void addGrenades(int x, int y, double mouseAngle, int speed, int id){
        grenades.add(new Grenade(x, y, mouseAngle, speed, id));
    }
    public ArrayList<Grenade> getGrenades(){
        return grenades;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
    public void setCurrentHealth(int currentHealth){
        this.currentHealth = currentHealth;
    }
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }
    public void setPlayerSpectating(String playerUsername){
        playerSpectating = playerUsername;
    }
    public String getPlayerSpectating(){
        return playerSpectating;
    }
    public void setWeaponThrow(boolean weaponThrow){
        this.weaponThrow = weaponThrow;
    }
    public boolean getWeaponThrow(){
        return weaponThrow;
    }
    public void setWeaponHit(boolean weaponHit){
        this.weaponHit = weaponHit;
    }
    public boolean getWeaponHit(){
        return weaponHit;
    }
    public void setWeaponHolding(boolean weaponHolding){
        this.weaponHolding = weaponHolding;
    }
    public boolean getWeaponHolding(){
        return weaponHolding;
    }
    @Override
    public boolean equals(Object o){
        Player l = (Player)o;
        if (l.getUsername().equals(username)){
            return true;
        }
        return false;
    }
    // @Override
    // public int hashCode(){
    //     int first = (int) username.charAt(0) - 97;
    //     int second = (int) username.charAt(1) - 97;
    //     int third = (int) username.charAt(2) - 97;
    //     return first*676 + second*26 + third;
    // }
}
