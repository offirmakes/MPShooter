public class Cooldown implements Runnable {
    private ClientScreen s;
    private int refreshTime;
    private double fireRateCooldown;
    private double ultCooldown;
    private double grenadeCooldown;
    private double grenadeRate;
    private double poisonCooldown;
    private double poisonedCooldown;
    private double damageTakenCooldown;
    private double regularAbilityCooldown;
    private double healingCooldown;
    private double gameOver;

    public Cooldown(ClientScreen s) {
        this.s = s;
        reset();
    }
    public void reset(){
        refreshTime = 125;
        fireRateCooldown = 10;
        ultCooldown = 10;
        grenadeCooldown = 10;
        grenadeRate = 10;
        poisonCooldown = 10;
        poisonedCooldown = 10;
        damageTakenCooldown = 10;
        regularAbilityCooldown = 10;
        healingCooldown = 10;
        gameOver = Integer.MAX_VALUE;
    }
    @Override
    public void run() {
        while (true) {
            ultCooldown += getRefreshTime();
            fireRateCooldown += getRefreshTime();
            grenadeCooldown += getRefreshTime();
            grenadeRate += getRefreshTime();
            poisonCooldown += getRefreshTime();
            poisonedCooldown += getRefreshTime();
            damageTakenCooldown += getRefreshTime();
            regularAbilityCooldown += getRefreshTime();
            healingCooldown += getRefreshTime();
            gameOver -= getRefreshTime();
            s.decreaseBulletLifeSpan();
            s.checkFireBullet();
            s.checkRegularAbility();
            s.checkUltStatus();
            s.checkPoisonActive();
            s.checkPoisoned();
            s.checkDamageTaken();
            try {
                Thread.sleep(refreshTime); // Adjust sleep time for desired frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public double getRefreshTime(){
        return ((double)refreshTime)/1000;
    }
    public void startFireRateCooldown(){
        fireRateCooldown = 0;
    }
    public void setFireRateCooldown(double fireRateCooldown){
        this.fireRateCooldown = fireRateCooldown;
    }
    public void changeFireRateCooldown(int fireRateCooldown){
        this.fireRateCooldown = fireRateCooldown;
    }
    public double getFireRateCooldown(){
        return fireRateCooldown;
    }
    public void startUltCooldown(){
        ultCooldown = 0;
    }
    public double getUltCooldown(){
        return ultCooldown;
    }
    public void startGrenadeCooldown(){
        grenadeCooldown = 0;
    }
    public double getGrenadeCooldown(){
        return grenadeCooldown;
    }
    public void startGrenadeRate(){
        grenadeRate = 0;
    }
    public double getGrenadeRate(){
        return grenadeRate;
    }
    public void startPoisonCooldown(){
        poisonCooldown = 0;
    }
    public double getPoisonCooldown(){
        return poisonCooldown;
    }
    public void startPoisonedCooldown(){
        poisonedCooldown = 0;
    }
    public double getPoisonedCooldown(){
        return poisonedCooldown;
    }
    public void startDamageTakenCooldown(){
        damageTakenCooldown = 0;
    }
    public double getDamageTakenCooldown(){
        return damageTakenCooldown;
    }
    public void startRegularAbilityCooldown(){
        regularAbilityCooldown = 0;
    }
    public double getRegularAbilityCooldown(){
        return regularAbilityCooldown;
    }
    public void startHealingCooldown(){
        healingCooldown = 0;
    }
    public double getHealingCooldown(){
        return healingCooldown;
    }
    public void startGameOverCooldown(){
        gameOver = 5;
    }public void resetGameOverCooldown(){
        gameOver = Integer.MAX_VALUE;
    }
    public double getGameOverCooldown(){
        return gameOver;
    }
}