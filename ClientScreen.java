import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientScreen extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener {
    private JButton startBtn;
    private JButton exitBtn;
    private int totalPlayers;
    private int playersReady, userReady;
    private double xVelocity, yVelocity, xAcceleration, yAcceleration, xRecoilVelocity, yRecoilVelocity, xRecoilAcceleration, yRecoilAcceleration, weaponTime, dashTime;
    private JTextField userNameInput;
    private String hero;
    private String userNameValidMesage;
    private boolean selectedClass, selectedUserName;
    private boolean reset;
    private boolean gameStart, gameOver, gameOverCooldown;
    private int xDiff, yDiff;
    private int playersAlive;
    private String playerAliveUsername;
    private int weaponX, weaponY;
    private int mapWidth, mapHeight, screenWidth, screenHeight;
    private double weaponAnimationTime, recoilTime; 
    private ArrayList<Player> players;
    private ArrayList<Obstacle> obstacles;
    private Animate a;
    private BufferedImage bush, dagger, pistolBullet, explosion, grenade, poisonCloud, ragePotion, shield, playerInvisible, playerRage, dashIcon, daggerInvisible, sniperBullet, teleportation, poisonBullet, poisonBulletIcon, machineGunBullet, menuScreen, sword, sniper, pistol, machineGun, player, rotatedPlayer, background, rotatedSword, rotatedPistol, rotatedSniper, rotatedDagger, rotatedEnemyDagger, rotatedEnemySword, rotatedEnemyPistol, rotatedEnemySniper, grenadeIcon;
    private Player p1;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private boolean up, down, right, left;
    private int grenadeID;
    private double weaponOffset, currentPullOffset;
    private double weaponCooldownHit, weaponCooldownThrow, weaponThrowAnimationTime;
    private String poisonUsername;
    private int period;
    private int globalMousePosX, globalMousePosY;
    private boolean firing, checkMouseMoved, regularAbilityHeld;
    private boolean recoilActive;
    private Cooldown cooldown;
    private boolean poisonActive;
    private Pair<String, Bullet> bulletHit; // Makes sure that a bullet that has yet to be deleted can not hit the user twice
    private Pair<String, Grenade> grenadeHit; // MAKES THIS INTO AN ARRAYLIST
    private Pair<String, Integer> weaponHit;

    public ClientScreen(){

        this.setLayout(null);
        players = new ArrayList<Player>();
        try { 
			dagger = ImageIO.read(new File("Images/Dagger.png"));
            sword = ImageIO.read(new File("Images/Sword.png"));
            sniper = ImageIO.read(new File("Images/Sniper.png"));
            pistol = ImageIO.read(new File("Images/Pistol.png"));
            machineGun = ImageIO.read(new File("Images/MachineGun.png"));
            
            BufferedImage pistolBulletTemp = ImageIO.read(new File("Images/PistolBullet.png"));
            pistolBullet = resize(pistolBulletTemp, pistolBulletTemp.getWidth()/4, pistolBulletTemp.getHeight()/4);
            BufferedImage sniperBulletTemp = ImageIO.read(new File("Images/SniperBullet.png"));
            sniperBullet = resize(sniperBulletTemp, sniperBulletTemp.getWidth()/4, sniperBulletTemp.getHeight()/4);
            BufferedImage poisonBulletTemp = ImageIO.read(new File("Images/PoisonBullet.png"));
            poisonBullet = resize(poisonBulletTemp, poisonBulletTemp.getWidth()/4, poisonBulletTemp.getHeight()/4);
            BufferedImage poisonBulletIconTemp = ImageIO.read(new File("Images/PoisonBulletIcon.png"));
            poisonBulletIcon = resize(poisonBulletIconTemp, poisonBulletIconTemp.getWidth()/3, poisonBulletIconTemp.getHeight()/3);
            
            poisonCloud = ImageIO.read(new File("Images/PoisonCloud.png"));
            
            BufferedImage machineGunBulletTemp = ImageIO.read(new File("Images/MachineGunBullet.png"));
            machineGunBullet = resize(machineGunBulletTemp, machineGunBulletTemp.getWidth()/4, machineGunBulletTemp.getHeight()/4);

            BufferedImage teleportationTemp = ImageIO.read(new File("Images/Teleportation.png"));
            teleportation = resize(teleportationTemp, teleportationTemp.getWidth()/3, teleportationTemp.getHeight()/3);
            
            BufferedImage playerInvisibleTemp = ImageIO.read(new File("Images/PlayerInvisible.png"));
            playerInvisible = resize(playerInvisibleTemp, playerInvisibleTemp.getWidth()/4, playerInvisibleTemp.getHeight()/4);
            BufferedImage playerRageTemp = ImageIO.read(new File("Images/PlayerRage.png"));
            playerRage = resize(playerRageTemp, playerRageTemp.getWidth()/4, playerRageTemp.getHeight()/4);

            BufferedImage ragePotionTemp = ImageIO.read(new File("Images/RagePotion.png"));
            ragePotion = resize(ragePotionTemp, ragePotionTemp.getWidth()/5, ragePotionTemp.getHeight()/5);

            BufferedImage shieldTemp = ImageIO.read(new File("Images/Shield.png"));
            shield = resize(shieldTemp, shieldTemp.getWidth()/4, shieldTemp.getHeight()/4);

            BufferedImage daggerInvisibleTemp = ImageIO.read(new File("Images/DaggerInvisible.png"));
            daggerInvisible = resize(daggerInvisibleTemp, daggerInvisibleTemp.getWidth(), daggerInvisibleTemp.getHeight());

            BufferedImage grenadeTemp = ImageIO.read(new File("Images/Grenade.png"));
            grenade = resize(grenadeTemp, grenadeTemp.getWidth()/10, grenadeTemp.getHeight()/10);
            grenadeIcon = resize(grenadeTemp, grenadeTemp.getWidth()/6, grenadeTemp.getHeight()/6);
            BufferedImage explosionTemp = ImageIO.read(new File("Images/Explosion.png"));
            explosion = resize(explosionTemp, explosionTemp.getWidth()/2, explosionTemp.getHeight()/2);
            BufferedImage dashIconTemp = ImageIO.read(new File("Images/Dash.png"));
            dashIcon = resize(dashIconTemp, dashIconTemp.getWidth()/5, dashIconTemp.getHeight()/5);
            
            BufferedImage bushTemp = ImageIO.read(new File("Images/Bush2.png"));
            bush = resize(bushTemp, 2*bushTemp.getWidth()/3, 2*bushTemp.getHeight()/3);

            background = ImageIO.read(new File("Images/Background.png")); 
            menuScreen = ImageIO.read(new File("Images/MenuScreen.jpg")); 
            BufferedImage playerTemp = ImageIO.read(new File("Images/Player.png"));
            player = resize(playerTemp, playerTemp.getWidth()/4, playerTemp.getHeight()/4);
		} catch (IOException e) {
			e.printStackTrace();
		}
        startClientScreen();

        rotatedDagger = dagger;
        rotatedPistol = pistol;
        rotatedSword = sword;
        rotatedSniper = sniper;

        xDiff = (int)(Math.random()*background.getWidth())-645;
        yDiff = (int)(Math.random()*background.getHeight())-450;
        p1 = new Player(645-player.getWidth()/2, 450-player.getHeight()/2, xDiff+645, yDiff+450);
        rotatedPlayer = transform(player, p1.getMouseAngle());

        startBtn = new JButton();
        startBtn.setFont(new Font("Arial", Font.BOLD, 18));
        startBtn.setHorizontalAlignment(SwingConstants.CENTER);
        startBtn.setBounds(495, 700, 300, 75);
        startBtn.setText("Ready Up");
        startBtn.setFont(new Font("Arial", Font.BOLD, 25));
        startBtn.setBackground(new Color(144, 238, 144));
        startBtn.setForeground(Color.BLACK);
        startBtn.setEnabled(false);
        this.add(startBtn);
        startBtn.addActionListener(this);

        userNameInput = new JTextField(10); 
		userNameInput.setBounds(545,600, 200, 30); 
        userNameInput.setFont(new Font("Arial", Font.BOLD, 15));
        userNameInput.setHorizontalAlignment(SwingConstants.CENTER);
        userNameInput.setText("Username");
		this.add(userNameInput); 
        userNameInput.addActionListener(this);

        exitBtn = new JButton();
        exitBtn.setFont(new Font("Arial", Font.BOLD, 18));
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setBounds(1100, 800, 150, 50);
        exitBtn.setText("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        exitBtn.setBackground(new Color(135, 206, 250));
        exitBtn.setForeground(Color.BLACK);
        this.add(exitBtn);
        exitBtn.addActionListener(this);

        a = new Animate(this);
        Thread animateThread = new Thread(a);
        animateThread.start();

        cooldown = new Cooldown(this);
        Thread cooldownThread = new Thread(cooldown);
        cooldownThread.start();

		this.setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        
	}
    public void startClientScreen(){
        
        mapWidth = 3000;
        mapHeight = 2001;
        screenWidth = 1290;
        screenHeight = 900;

        obstacles = new ArrayList<Obstacle>();
        Random random = new Random();  
        
        int bushCount = 1;
        while (obstacles.size() < bushCount) {
            int x = random.nextInt(mapWidth - bush.getWidth());
            int y = random.nextInt(mapHeight - bush.getHeight());
            Obstacle newBush = new Obstacle(x, y, bush.getWidth(), bush.getHeight(), 1, 1);

            boolean overlaps = false;
            for (int i = 0; i < obstacles.size(); i++) {
                if (newBush.intersects(obstacles.get(i))) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                obstacles.add(newBush);
            }
        }

        obstacles.add(new Obstacle(1000, 1000, bush.getWidth(), bush.getHeight(), 1, 0));
        obstacles.add(new Obstacle(2000, 500, bush.getWidth(), bush.getHeight(), 1, 0));

        playersAlive = 0;
        playerAliveUsername = "";

        userNameValidMesage = "";
        hero = "Select a Class";
        weaponAnimationTime = 0;
        selectedClass = false;
        selectedUserName = false;
        gameStart = false;
        recoilActive = false;
        reset = false;
        up = false;
        down = false;
        right = false;
        left = false;
        period = 10;
        weaponCooldownHit = 2;
        weaponCooldownThrow = 2;
        totalPlayers = 1;
        playersReady = 0;
        userReady = 0;
        grenadeID = 0;
        firing = false;
        gameOver = false;
        gameOverCooldown = false;
        checkMouseMoved = false;
        bulletHit = new Pair<String, Bullet>("null", null);
        grenadeHit = new Pair<String, Grenade>("null", null);
        weaponHit = new Pair<String, Integer>("null", -1);
        regularAbilityHeld = false;
        poisonActive = false;
        weaponOffset = 73;
        currentPullOffset = 73;
        weaponTime = 0;
        dashTime = 0;
        recoilTime = 0;
        weaponThrowAnimationTime = 0;
        
        xVelocity = 0;
        yVelocity = 0;
        xAcceleration = 0;
        yAcceleration = 0;

        xRecoilVelocity = 0;
        yRecoilVelocity = 0;
        xRecoilAcceleration = 0;
        yRecoilAcceleration = 0;
    }
    public Dimension getPreferredSize() {
		return new Dimension(screenWidth, screenHeight); 
	}
    public static BufferedImage transform(BufferedImage image, double angle) {
        int w0 = image.getWidth();
        int h0 = image.getHeight();
        int w1 = w0;
        int h1 = h0;

        int centerX = w0 / 2;
        int centerY = h0 / 2;

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(angle, centerX, centerY);

        AffineTransformOp opRotated = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage transformedImage = new BufferedImage(w1, h1, image.getType());

        opRotated.filter(image, transformedImage);
        return transformedImage;
    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        
        return dimg;
    }
    public void close(){
        if (gameStart){
            if (playersAlive <= 1){
                sendMessage(MessagesIds.GAME_RESET, null);
            }
            sendMessage(MessagesIds.EXIT, p1.getUsername());
        } else {
            sendMessage(MessagesIds.EXIT, null);
        }
    }
    public void playSound(String fileName) {

        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public void checkMapBoundaries(){
        if (yDiff < -screenHeight/2){
            yDiff = -screenHeight/2;
        }
        if (xDiff < -screenWidth/2){
            xDiff = -screenWidth/2;
        }
        if (xDiff > mapWidth-screenWidth/2){
            xDiff = mapWidth-screenWidth/2;
        }
        if (yDiff > mapHeight-screenHeight/2){
            yDiff = mapHeight-screenHeight/2;
        }
    }
    public void calcPlayerPos(){
        int nextXDiff = xDiff;
        int nextYDiff = yDiff;
        if (p1.getAlive()){
            if (p1.getHeroType() == 1){
                if (dashTime >= weaponAnimationTime){
                    xVelocity = 0;
                    yVelocity = 0;
                    xAcceleration = 0;
                    yAcceleration = 0;
                }  else {
                    nextXDiff += Math.round(xVelocity + xAcceleration*weaponTime);
                    nextYDiff += Math.round(yVelocity + yAcceleration*weaponTime);
                    xVelocity += xAcceleration;
                    yVelocity += yAcceleration;
                }
            } else if (p1.getHeroType() == 2){
                if (weaponTime >= weaponAnimationTime){
                    xVelocity = 0;
                    yVelocity = 0;
                    xAcceleration = 0;
                    yAcceleration = 0;
                    if (!p1.getWeaponHolding()){
                        p1.setWeaponThrow(false);
                    }
                } else {
                    nextXDiff += Math.round(xVelocity + xAcceleration*weaponTime);
                    nextYDiff += Math.round(yVelocity + yAcceleration*weaponTime);
                    xVelocity += xAcceleration;
                    yVelocity += yAcceleration;
                }
            } else if (p1.getHeroType() == 4){
                if (recoilTime >= p1.getUltDuration()){
                    xRecoilVelocity = 0;
                    yRecoilVelocity = 0;
                    xRecoilAcceleration = 0;
                    yRecoilAcceleration = 0;
                    recoilActive = false;
                    p1.setUltStatus(false);
                } else {
                    nextXDiff += Math.round(xRecoilVelocity + xRecoilAcceleration*recoilTime);
                    nextYDiff += Math.round(yRecoilVelocity + yRecoilAcceleration*recoilTime);
                    xRecoilVelocity += xRecoilAcceleration;
                    yRecoilVelocity += yRecoilAcceleration;
                }
            }
            if (up && right){
                nextYDiff -= p1.getSpeed();
                nextXDiff += p1.getSpeed();
            } else if (up && left){
                nextYDiff -= p1.getSpeed();
                nextXDiff -= p1.getSpeed();
            } else if (down && right){
                nextYDiff += p1.getSpeed();
                nextXDiff += p1.getSpeed();
            } else if (down && left){
                nextYDiff += p1.getSpeed();
                nextXDiff -= p1.getSpeed();
            } else {
                if (up){
                    nextYDiff -= Math.round(p1.getSpeed()*Math.sqrt(2));
                }
                if (down){
                    nextYDiff += Math.round(p1.getSpeed()*Math.sqrt(2));
                }
                if (right){
                    nextXDiff += Math.round(p1.getSpeed()*Math.sqrt(2));
                }
                if (left){
                    nextXDiff -= Math.round(p1.getSpeed()*Math.sqrt(2));
                }
            }
            // boolean collision = false;
            // for (int i = 0; i < obstacles.size(); i++) {
            //     Obstacle obstacle = obstacles.get(i);
            //     if (obstacle.getShape() == 1) { // Circle obstacle
            //         double distanceSquared = Math.pow(nextXDiff+screenWidth/2+player.getWidth()/2 - obstacle.getX()+obstacle.getWidth()/2, 2) + Math.pow(nextYDiff+screenHeight/2+player.getHeight()/2 - obstacle.getY()+obstacle.getWidth()/2, 2);
            //         double radiusSumSquared = Math.pow(player.getWidth() + obstacle.getWidth(), 2);
            //         System.out.println(Math.sqrt(distanceSquared));
            //         if (distanceSquared <= radiusSumSquared) {
            //             collision = true;
            //             break;
            //         }
            //     } else { // Rectangle obstacle
            //         if (isColliding(nextXDiff, nextYDiff, player.getWidth() * 2, player.getWidth() * 2, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight())) {
            //             collision = true;
            //             break;
            //         }
            //     }
            // }
            // if (!collision) {
            //     xDiff = nextXDiff;
            //     yDiff = nextYDiff;
            // }
            xDiff = nextXDiff;
            yDiff = nextYDiff;
        } 
        checkMapBoundaries();
        p1.changeMapX(xDiff+645);
        p1.changeMapY(yDiff+450);

        if (p1.getWeaponThrow() && p1.getHeroType() == 1){
            weaponX += Math.round((float)(Math.cos(p1.getMouseAngle()+Math.toRadians(-90))*5));
            weaponY += Math.round((float)(Math.sin(p1.getMouseAngle()+Math.toRadians(-90))*5));
            if (cooldown.getFireRateCooldown() >= p1.getFireCooldown()*6){
                p1.setWeaponThrow(false);
            }
        } else {
            weaponX = p1.getScreenX()-8 + (int) (Math.cos((p1.getMouseAngle()+Math.toRadians(-74))) * weaponOffset);
            weaponY = p1.getScreenY()-8 + (int) (Math.sin((p1.getMouseAngle()+Math.toRadians(-74))) * weaponOffset);
        }
        
        
        p1.setWeaponX(xDiff+weaponX);
        p1.setWeaponY(yDiff+weaponY);
    }
    private boolean isColliding(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        return x1 < x2 + w2 &&
               x1 + w1 > x2 &&
               y1 < y2 + h2 &&
               y1 + h1 > y2;
    }    
    public void drawBackground(Graphics g){
        g.setColor(new Color(105, 142, 63));
        g.fillRect(-xDiff, -yDiff, mapWidth, mapHeight);
        g.setColor(new Color(88, 119, 64));
        for (int i = 0; i < 58; i++){
            g.fillRect((i*70)-xDiff, -yDiff, 2, mapHeight);
        }
        for (int i = 0; i < 43; i++){
            g.fillRect(-xDiff, (i*70)-yDiff, mapWidth, 2);
        }
        for (int i = 0; i < obstacles.size(); i++){
            Obstacle currentObstacle = obstacles.get(i);
            if (currentObstacle.getImageType() == 1){
                g.drawImage(bush, currentObstacle.getX()-xDiff, currentObstacle.getY()-yDiff, null);
            }  
        }
        
    }
    public void paintComponent(Graphics g){
		super.paintComponent(g);

        if (gameStart){ 
            // drawBackground(g);
            g.setColor(new Color(173, 216, 230));
            g.fillRect(-1000, -1000, 5000, 5000);
            g.drawImage(background, -xDiff, -yDiff, null);

            if (p1.getAlive()){
                if (checkMouseMoved){
                    if (p1.getHeroType() == 1){
                        if (!p1.getUltStatus()){
                            rotatedDagger = transform(dagger, p1.getMouseAngle());
                        } else {
                            rotatedDagger = transform(daggerInvisible, p1.getMouseAngle());
                        }
                    } else if (p1.getHeroType() == 2){
                        rotatedSword = transform(sword, p1.getMouseAngle());
                    } else if (p1.getHeroType() == 3){
                        if (!p1.getUltStatus()){
                            rotatedPistol = transform(pistol, p1.getMouseAngle());
                        } else {
                            rotatedPistol = transform(machineGun, p1.getMouseAngle());
                        }
                    } else if (p1.getHeroType() == 4){
                        rotatedSniper = transform(sniper, p1.getMouseAngle());
                    }
                }
                if (p1.getUltStatus() && p1.getHeroType() == 1){
                    rotatedPlayer = transform(playerInvisible, p1.getMouseAngle());
                } else if (p1.getUltStatus() && p1.getHeroType() == 2) {
                    rotatedPlayer = transform(playerRage, p1.getMouseAngle());      
                } else {
                    rotatedPlayer = transform(player, p1.getMouseAngle());      
                }
                g.drawImage(rotatedPlayer, p1.getScreenX(), p1.getScreenY(), null); // Rotates and draws player and weapons
                if (p1.getShieldHealth() >= 1) {
                    g.drawImage(shield, p1.getScreenX(), p1.getScreenY(), null);
                }
                if (p1.getHeroType() == 1){
                    g.drawImage(rotatedDagger, weaponX, weaponY, null);
                } else if (p1.getHeroType() == 2){
                    g.drawImage(rotatedSword, weaponX, weaponY, null);
                } else if (p1.getHeroType() == 3){
                    g.drawImage(rotatedPistol, weaponX, weaponY, null);
                } else if (p1.getHeroType() == 4){
                    g.drawImage(rotatedSniper, weaponX, weaponY, null);
                }
    
                int startHealth = p1.getCurrentHealth();
                boolean playerSpectatingFound = false;
                for (int i = 0; i < players.size(); i++){ // Displays enemies
                    Player currentPlayer = players.get(i);
                    if (currentPlayer.getAlive()){
                        if (!(currentPlayer.getHeroType() == 1 && currentPlayer.getUltStatus())){
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("Arial", Font.BOLD, 15));

                            BufferedImage rotatedEnemyPlayer = transform(player, currentPlayer.getMouseAngle());  
                            if (currentPlayer.getUltStatus() && currentPlayer.getHeroType() == 2) {
                                rotatedEnemyPlayer = transform(playerRage, currentPlayer.getMouseAngle());      
                            }    
                            g.drawImage(rotatedEnemyPlayer, (currentPlayer.getMapX()-xDiff)-player.getWidth()/2, (currentPlayer.getMapY()-yDiff)-player.getHeight()/2, null); // Draw Enemy Player

                            if (currentPlayer.getShieldHealth() >= 1) {
                                g.drawImage(shield, (currentPlayer.getMapX()-xDiff)-player.getWidth()/2, (currentPlayer.getMapY()-yDiff)-player.getHeight()/2, null);
                            }
                            if (currentPlayer.getHeroType() == 1) { // Draw Enemy Weapon
                                rotatedEnemyDagger = transform(dagger, currentPlayer.getMouseAngle());
                                g.drawImage(rotatedEnemyDagger, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                            } else if (currentPlayer.getHeroType() == 2) {
                                rotatedEnemySword = transform(sword, currentPlayer.getMouseAngle());
                                g.drawImage(rotatedEnemySword, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                            } else if (currentPlayer.getHeroType() == 3) {
                                if (!currentPlayer.getUltStatus()){
                                    rotatedEnemyPistol = transform(pistol, currentPlayer.getMouseAngle());
                                } else {
                                    rotatedEnemyPistol = transform(machineGun, currentPlayer.getMouseAngle());
                                }
                                g.drawImage(rotatedEnemyPistol, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                            } else if (currentPlayer.getHeroType() == 4) {   
                                rotatedEnemySniper = transform(sniper, currentPlayer.getMouseAngle());           
                                g.drawImage(rotatedEnemySniper, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                            }
                            g.drawString(currentPlayer.getUsername(), (currentPlayer.getMapX()-xDiff)-currentPlayer.getUsername().length()*5, (currentPlayer.getMapY()-yDiff)-80); // Draw Enemy Username
                            drawSmallHealthBar(g, currentPlayer.getMaxHealth(), currentPlayer.getCurrentHealth(), currentPlayer.getMapX()-p1.getMapX(), currentPlayer.getMapY()-p1.getMapY()); // Draw Health Bar
                            
                            if ((currentPlayer.getHeroType() == 1 && currentPlayer.getWeaponHit()) || (currentPlayer.getHeroType() == 2 && (currentPlayer.getWeaponHit() || (currentPlayer.getWeaponThrow() && !currentPlayer.getWeaponHolding())))){
                                int enemyWeaponXTip = (currentPlayer.getScreenX() + 30 + (int) (Math.cos((currentPlayer.getMouseAngle()+Math.toRadians(-81))) * 124))+currentPlayer.getMapX()-p1.getMapX();
                                int enemyWeaponYTip = (currentPlayer.getScreenY() + 30 + (int) (Math.sin((currentPlayer.getMouseAngle()+Math.toRadians(-81))) * 124))+currentPlayer.getMapY() - p1.getMapY();

                                int enemyWeaponXMiddle = (currentPlayer.getScreenX() + 30 + (int) (Math.cos((currentPlayer.getMouseAngle()+Math.toRadians(-81))) * 80))+currentPlayer.getMapX()-p1.getMapX();
                                int enemyWeaponYMiddle = (currentPlayer.getScreenY() + 30 + (int) (Math.sin((currentPlayer.getMouseAngle()+Math.toRadians(-81))) * 80))+currentPlayer.getMapY() - p1.getMapY();

                                if (p1.boundaries(enemyWeaponXTip, enemyWeaponYTip) || p1.boundaries(enemyWeaponXMiddle, enemyWeaponYMiddle)){
                                    if (!(currentPlayer.getUsername().equals(weaponHit.getKey()) && currentPlayer.getWeaponID() == weaponHit.getValue())){
                                        weaponHit = new Pair<String, Integer>(currentPlayer.getUsername(), currentPlayer.getWeaponID());
                                        if (currentPlayer.getWeaponHit()){
                                            shieldHit(currentPlayer.getPrimaryDamage());
                                        } else{
                                            shieldHit(currentPlayer.getSecondaryDamage());
                                        }
                                    }
                                }   
                            } else if (currentPlayer.getHeroType() == 1 && currentPlayer.getWeaponThrow()){
                                int enemyWeaponX = (currentPlayer.getWeaponX()+40)-xDiff;
                                int enemyWeaponY = (currentPlayer.getWeaponY()+40)-yDiff;

                                if (p1.boundaries(enemyWeaponX, enemyWeaponY)){
                                    if (!(currentPlayer.getUsername().equals(weaponHit.getKey()) && currentPlayer.getWeaponID() == weaponHit.getValue())){
                                        weaponHit = new Pair<String, Integer>(currentPlayer.getUsername(), currentPlayer.getWeaponID());
                                        sendMessage(MessagesIds.REMOVE_DAGGER, new Pair<String, Integer>(currentPlayer.getUsername(), currentPlayer.getWeaponID()));
                                        shieldHit(currentPlayer.getSecondaryDamage());
                                    }
                                }
                            }
                            
                            ArrayList<Bullet> enemyBullets = currentPlayer.getBullets();
                            BufferedImage rotateEnemyBullet;
                            for (int j = 0; j < enemyBullets.size(); j++){
                                Bullet currentBullet = enemyBullets.get(j);
                                int xPos = currentBullet.getX()-xDiff;
                                int yPos = currentBullet.getY()-yDiff;
                                if (currentPlayer.getHeroType() == 3) {
                                    if (currentPlayer.getUltStatus()){
                                        rotateEnemyBullet = transform(machineGunBullet, currentBullet.getMouseAngle());
                                    } else {
                                        rotateEnemyBullet = transform(pistolBullet, currentBullet.getMouseAngle());
                                    }
                                    g.drawImage(rotateEnemyBullet, xPos, yPos, null);
                                } else if (currentPlayer.getHeroType() == 4) {
                                    if (currentBullet.getPoison()){
                                        rotateEnemyBullet = transform(poisonBullet, currentBullet.getMouseAngle());
                                    } else {
                                        rotateEnemyBullet = transform(sniperBullet, currentBullet.getMouseAngle());
                                    }
                                    
                                    g.drawImage(rotateEnemyBullet, xPos, yPos, null);
                                }
                                if (p1.boundaries(xPos, yPos) || p1.boundaries(Math.round((float)(currentBullet.getX()-Math.cos(currentBullet.getMouseAngle()+Math.toRadians(-90))*currentBullet.getSpeed())), Math.round((float)(currentBullet.getY()+Math.sin(currentBullet.getMouseAngle()+Math.toRadians(-90))*currentBullet.getSpeed())))){
                                    if (!(currentPlayer.getUsername().equals(bulletHit.getKey()) && currentBullet.equals(bulletHit.getValue()))){
                                        bulletHit = new Pair<String, Bullet>(currentPlayer.getUsername(), currentBullet);
                                        shieldHit(currentPlayer.getPrimaryDamage());
                                        if (currentBullet.getPoison()){
                                            p1.setPoison(true);
                                            poisonUsername = currentPlayer.getUsername();
                                            cooldown.startPoisonedCooldown();
                                        }
                                        sendMessage(MessagesIds.REMOVE_BULLET, new Pair<String, Integer>(currentPlayer.getUsername(), currentBullet.getID()));
                                    }
                                }
                            }
                            ArrayList<Grenade> enemyGrenades = currentPlayer.getGrenades();
                            BufferedImage rotateEnemyGrenade;
                            for (int j = 0; j < enemyGrenades.size(); j++){
                                Grenade currentGrenade = enemyGrenades.get(j);
                                int xPos = currentGrenade.getX()-xDiff;
                                int yPos = currentGrenade.getY()-yDiff;
                                if (currentGrenade.getGrenadeStatus()){
                                    rotateEnemyGrenade = transform(grenade, currentGrenade.getMouseAngle());
                                    g.drawImage(rotateEnemyGrenade, xPos, yPos, null);
                                } else if (currentGrenade.getExplosionStatus()){
                                    g.drawImage(explosion, xPos, yPos, null);
                                    if (p1.distance(xPos+explosion.getWidth()/2, yPos+explosion.getHeight()/2) <= (player.getHeight()/2 + (explosion.getHeight()/2 + explosion.getWidth()/2)/2)){
                                        if (!(currentPlayer.getUsername().equals(grenadeHit.getKey()) && currentGrenade.equals(grenadeHit.getValue()))){
                                            grenadeHit = new Pair<String, Grenade>(currentPlayer.getUsername(), currentGrenade);
                                            shieldHit(currentPlayer.getSecondaryDamage());
                                        }
                                    }
                                }
                            }
                            if (currentPlayer.getDamageTaken() > 0){
                                g.setColor(Color.RED); // Draw Damage Taken Text
                                g.setFont(new Font("Arial", Font.BOLD, 20));
                                g.drawString(Integer.toString(currentPlayer.getDamageTaken()), (currentPlayer.getMapX()-p1.getMapX()) + 645-Integer.toString(currentPlayer.getDamageTaken()).length()*5, (currentPlayer.getMapY()-p1.getMapY()) + 350);
                            }
                            if (p1.getCurrentHealth() <= 0 && !playerSpectatingFound){
                                playerSpectatingFound = true;
                                playerDied(currentPlayer.getUsername());
                            }
                        } else {       
                            if (distanceBetweenObjects(p1.getMapX(), currentPlayer.getMapX(), p1.getMapY(), currentPlayer.getMapY()) < 200){
                                sendMessage(MessagesIds.PLAYER_FOUND, currentPlayer.getUsername());
                            }
                        }
                    }
                }
                g.setColor(Color.WHITE); 
                g.setFont(new Font("Arial", Font.BOLD, 15));
                int endHealth = p1.getCurrentHealth();     
                if (startHealth - endHealth > 0){
                    p1.setDamageTaken((startHealth - endHealth) + p1.getDamageTaken());
                    cooldown.startDamageTakenCooldown();
                }
    
                g.drawString(p1.getUsername(), 645-p1.getUsername().length()*4, 400); // Displays Username
    
                ArrayList<Bullet> bullets = p1.getBullets(); // Draw Bullets
                BufferedImage rotateBullet;
                for (int i = 0; i < bullets.size(); i++){
                    Bullet currentBullet = bullets.get(i);
                    if (p1.getHeroType() == 3){
                        if (p1.getUltStatus()){
                            rotateBullet = transform(machineGunBullet, currentBullet.getMouseAngle());
                        } else {
                            rotateBullet = transform(pistolBullet, currentBullet.getMouseAngle());
                        }
                        g.drawImage(rotateBullet, currentBullet.getX()-xDiff, currentBullet.getY()-yDiff, null);
                    } else if (p1.getHeroType() == 4){
                        if (currentBullet.getPoison()){
                            rotateBullet = transform(poisonBullet, currentBullet.getMouseAngle());
                        } else {
                            rotateBullet = transform(sniperBullet, currentBullet.getMouseAngle());
                        }
                        g.drawImage(rotateBullet, currentBullet.getX()-xDiff, currentBullet.getY()-yDiff, null);
                    }
                }
                ArrayList<Grenade> grenades = p1.getGrenades(); // Draw Grenades
                BufferedImage rotateGrenade;
                for (int i = 0; i < grenades.size(); i++){
                    Grenade currentGrenade = grenades.get(i);
                    if (currentGrenade.getGrenadeStatus()){
                        rotateGrenade = transform(grenade, currentGrenade.getMouseAngle());
                        g.drawImage(rotateGrenade, currentGrenade.getX()-xDiff, currentGrenade.getY()-yDiff, null);
                    } else if (currentGrenade.getExplosionStatus()){
                        g.drawImage(explosion, currentGrenade.getX()-xDiff, currentGrenade.getY()-yDiff, null);
                    }
                }
    
                if (p1.getCurrentHealth() < 0){ // Makes sure the player can't have a negative health for now
                    p1.setCurrentHealth(0);
                }
    
                drawHealthBar(p1.getMaxHealth(), p1.getCurrentHealth(), g);
    
                int timeUntilUlt = 0;
                if (!p1.getUltStatus()){
                    timeUntilUlt = (int) Math.ceil((float)(p1.getUltCooldown()-cooldown.getUltCooldown()));
                } else {
                    timeUntilUlt = (int) Math.ceil((float)(p1.getUltDuration()-cooldown.getUltCooldown()));
                }
                if (timeUntilUlt < 0){
                    timeUntilUlt = 0;
                }
    
                int regularAbilityCooldown = 0;
                if (p1.getHeroType() == 1  || p1.getHeroType() == 2){
                    regularAbilityCooldown = (int) Math.ceil(((float)(p1.getRegularAbilityCooldown() - cooldown.getRegularAbilityCooldown())));
                } else if (p1.getHeroType() == 3){
                    regularAbilityCooldown = (int) Math.ceil(((float)(p1.getRegularAbilityCooldown() - cooldown.getGrenadeCooldown())));
                } else if (p1.getHeroType() == 4){
                    regularAbilityCooldown = (int) Math.ceil(((float)(p1.getRegularAbilityCooldown() - cooldown.getPoisonCooldown())));
                }
                
                if (regularAbilityCooldown < 0){
                    regularAbilityCooldown = 0;
                }
    
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 17));
                if (p1.getHeroType() == 1){
                    g.setColor(Color.BLACK); // Draw Ult Cooldown
                    BufferedImage rotatedPlayerInvisible = transform(playerInvisible, Math.toRadians(-90));
                    g.drawImage(rotatedPlayerInvisible, 1160, 810, null);
                    g.drawString(Integer.toString(timeUntilUlt), 1202-((Integer.toString(timeUntilUlt)).length()-1)*5, 857);
                    g.drawImage(dashIcon, 1150, 700, null);
                    g.drawString(Integer.toString(regularAbilityCooldown), 1207-((Integer.toString(regularAbilityCooldown)).length()-1)*5, 760);
                } else if (p1.getHeroType() == 2){
                    g.drawImage(ragePotion, 1162, 805, null);
                    g.drawString(Integer.toString(timeUntilUlt), 1191-((Integer.toString(timeUntilUlt)).length()-1)*5, 860);
                    g.drawImage(player, 1150, 700, null);
                    g.drawImage(shield, 1150, 700, null);
                    g.drawString(Integer.toString(regularAbilityCooldown), 1191-((Integer.toString(regularAbilityCooldown)).length()-1)*5, 752);
                } else if (p1.getHeroType() == 3){
                    BufferedImage rotatedMachineGun = transform(machineGun, Math.toRadians(-90));
                    g.drawImage(rotatedMachineGun, 1125, 800, null);
                    g.drawString(Integer.toString(timeUntilUlt), 1196-((Integer.toString(timeUntilUlt)).length()-1)*5, 857);
                    g.drawImage(grenadeIcon, 1140, 690, null);
                    g.drawString(Integer.toString(regularAbilityCooldown), 1191-((Integer.toString(regularAbilityCooldown)).length()-1)*5, 755);
                } else if (p1.getHeroType() == 4){
                    g.drawImage(teleportation, 1145, 798, null);
                    g.drawString(Integer.toString(timeUntilUlt), 1196-((Integer.toString(timeUntilUlt)).length()-1)*5, 855);
                    g.drawImage(poisonBulletIcon, 1149, 690, null);
                    g.drawString(Integer.toString(regularAbilityCooldown), 1195-((Integer.toString(regularAbilityCooldown)).length()-1)*5, 755);
                }
                if (p1.getDamageTaken() > 0){
                    g.setColor(Color.RED); // Draw Damage Taken Text
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString(Integer.toString(p1.getDamageTaken()), 645-Integer.toString(p1.getDamageTaken()).length()*5, 380);
                }    
            } else {
                for (int i = 0; i < players.size(); i++){ // Displays enemies
                    Player currentPlayer = players.get(i);
                    if (currentPlayer.getUsername().equals(p1.getPlayerSpectating())){
                        if (currentPlayer.getAlive()){
                            xDiff = currentPlayer.getMapX()-645;
                            yDiff = currentPlayer.getMapY()-450;
                            p1.changeMapX(xDiff+645);
                            p1.changeMapY(yDiff+450);
                            g.setColor(Color.WHITE);
                            g.setFont(new Font("Arial", Font.BOLD, 40));
                            g.drawString("Spectating...", 540, 100);
                            drawHealthBar(currentPlayer.getMaxHealth(), currentPlayer.getCurrentHealth(), g);
                        } else {
                            p1.setPlayerSpectating(currentPlayer.getPlayerSpectating());
                            if (p1.getPlayerSpectating() == p1.getUsername()){ // Players killed each other
                                for (int j = 0; j < players.size(); j++){
                                    if (players.get(j).getAlive()){
                                        p1.setPlayerSpectating(players.get(j).getUsername());
                                    }
                                }
                            }
                        }
                    }
                    if (currentPlayer.getAlive()){
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 15));

                        BufferedImage rotatedEnemyPlayer = transform(player, currentPlayer.getMouseAngle());    
                        if (currentPlayer.getUltStatus() && currentPlayer.getHeroType() == 1){
                            rotatedEnemyPlayer = transform(playerInvisible, currentPlayer.getMouseAngle());
                        } else if (currentPlayer.getUltStatus() && currentPlayer.getHeroType() == 2){
                            rotatedEnemyPlayer = transform(playerRage, currentPlayer.getMouseAngle());
                        } 
                        g.drawImage(rotatedEnemyPlayer, (currentPlayer.getMapX()-xDiff)-player.getWidth()/2, (currentPlayer.getMapY()-yDiff)-player.getHeight()/2, null); // Draw Enemy Player

                        if (currentPlayer.getShieldHealth() >= 1) {
                            g.drawImage(shield, (currentPlayer.getMapX()-xDiff)-player.getWidth()/2, (currentPlayer.getMapY()-yDiff)-player.getHeight()/2, null);
                        }
                        if (currentPlayer.getHeroType() == 1) { // Draw Enemy Weapon
                            if (!currentPlayer.getUltStatus()){
                                rotatedEnemyDagger = transform(dagger, currentPlayer.getMouseAngle());
                            } else {
                                rotatedEnemyDagger = transform(daggerInvisible, currentPlayer.getMouseAngle());
                            }
                            g.drawImage(rotatedEnemyDagger, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                        } else if (currentPlayer.getHeroType() == 2) {
                            rotatedEnemySword = transform(sword, currentPlayer.getMouseAngle());
                            g.drawImage(rotatedEnemySword, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                        } else if (currentPlayer.getHeroType() == 3) {
                            if (!currentPlayer.getUltStatus()){
                                rotatedEnemyPistol = transform(pistol, currentPlayer.getMouseAngle());
                            } else {
                                rotatedEnemyPistol = transform(machineGun, currentPlayer.getMouseAngle());
                            }
                            g.drawImage(rotatedEnemyPistol, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                        } else if (currentPlayer.getHeroType() == 4) {   
                            rotatedEnemySniper = transform(sniper, currentPlayer.getMouseAngle());           
                            g.drawImage(rotatedEnemySniper, currentPlayer.getWeaponX()-xDiff, currentPlayer.getWeaponY()-yDiff, null);
                        }
                        int yDisplacementUsername = 80;
                        if (currentPlayer.getUsername().equals(p1.getPlayerSpectating())){
                            yDisplacementUsername = 50;
                        }
                        g.drawString(currentPlayer.getUsername(), (currentPlayer.getMapX()-xDiff)-currentPlayer.getUsername().length()*4, (currentPlayer.getMapY()-yDiff)-yDisplacementUsername); // Draw Enemy Username
                        if (!currentPlayer.getUsername().equals(p1.getPlayerSpectating())){
                            drawSmallHealthBar(g, currentPlayer.getMaxHealth(), currentPlayer.getCurrentHealth(), currentPlayer.getMapX()-p1.getMapX(), currentPlayer.getMapY()-p1.getMapY());
                        }
                        ArrayList<Bullet> enemyBullets = currentPlayer.getBullets();
                        BufferedImage rotateEnemyBullet;
                        for (int j = 0; j < enemyBullets.size(); j++){
                            Bullet currentBullet = enemyBullets.get(j);
                            int xPos = currentBullet.getX()-xDiff;
                            int yPos = currentBullet.getY()-yDiff;
                            if (currentPlayer.getHeroType() == 3) {
                                if (currentPlayer.getUltStatus()){
                                    rotateEnemyBullet = transform(machineGunBullet, currentBullet.getMouseAngle());
                                } else {
                                    rotateEnemyBullet = transform(pistolBullet, currentBullet.getMouseAngle());
                                }
                                g.drawImage(rotateEnemyBullet, xPos, yPos, null);
                            } else if (currentPlayer.getHeroType() == 4) {
                                if (currentBullet.getPoison()){
                                    rotateEnemyBullet = transform(poisonBullet, currentBullet.getMouseAngle());
                                } else {
                                    rotateEnemyBullet = transform(sniperBullet, currentBullet.getMouseAngle());
                                }
                                
                                g.drawImage(rotateEnemyBullet, xPos, yPos, null);
                            }
                        }
                        ArrayList<Grenade> enemyGrenades = currentPlayer.getGrenades();
                        BufferedImage rotateEnemyGrenade;
                        for (int j = 0; j < enemyGrenades.size(); j++){
                            Grenade currentGrenade = enemyGrenades.get(j);
                            int xPos = currentGrenade.getX()-xDiff;
                            int yPos = currentGrenade.getY()-yDiff;
                            if (currentGrenade.getGrenadeStatus()){
                                rotateEnemyGrenade = transform(grenade, currentGrenade.getMouseAngle());
                                g.drawImage(rotateEnemyGrenade, xPos, yPos, null);
                            } else if (currentGrenade.getExplosionStatus()){
                                g.drawImage(explosion, xPos, yPos, null);
                            }
                        }
                        if (currentPlayer.getDamageTaken() > 0){
                            g.setColor(Color.RED); // Draw Damage Taken Text
                            g.setFont(new Font("Arial", Font.BOLD, 20));
                            int yDisplacementDamageTaken = 350;
                            if (currentPlayer.getUsername().equals(p1.getPlayerSpectating())){
                                yDisplacementDamageTaken = 380;
                            }
                            g.drawString(Integer.toString(currentPlayer.getDamageTaken()), (currentPlayer.getMapX()-p1.getMapX()) + 645-Integer.toString(currentPlayer.getDamageTaken()).length()*5, (currentPlayer.getMapY()-p1.getMapY()) + yDisplacementDamageTaken);
                        }
                    }
                }
            }
            playerAliveUsername = "";
            if (p1.getAlive()){
                playerAliveUsername = p1.getUsername();
            }
            for (int i = 0; i < players.size(); i++){
                if (players.get(i).getAlive()){
                    playerAliveUsername = players.get(i).getUsername();
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 23));
            g.drawString("Players Remaining: " + playersAlive, 15, 30);
            g.drawString("Total Elimination: " + p1.getTotalElim(), 1035, 30);
            if (playersAlive <= 1){
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                if (playersAlive == 1){
                    g.drawString(playerAliveUsername + " Won!", 610 - playerAliveUsername.length()*10, 200);
                } else {
                    g.setColor(Color.WHITE);
                    g.drawString("Draw", 600, 200);
                }
                g.setColor(Color.YELLOW);
                g.drawString(Integer.toString((int)Math.ceil(cooldown.getGameOverCooldown())), 635, 250);
            }
            if (cooldown.getGameOverCooldown() <= 0 && gameOver){
                resetGame();
            }
        }
        else { //Start Menu UI
            // g.setColor(new Color(173, 216, 230));
            // g.fillRect(0, 0, 1290, 900);
            g.drawImage(menuScreen, 0, 0, null);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Players Ready: " + " " + playersReady + "/" + totalPlayers, 540, 820);
            
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Players Ready: " + " " + playersReady + "/" + totalPlayers, 540, 820);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("Predator Strike", 433, 100);
            
            int x = 411;
            for (int i = 0; i < 4; i++){
                if (i+1 == p1.getHeroType()){
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x+120*i, 250, 106, 106);
                g.setColor(new Color(225, 198, 153));
                g.fillRect(x+3+120*i, 253, 100, 100);
            }
            if (selectedClass & selectedUserName){ 
                startBtn.setEnabled(true);
            }
            
            g.setColor(Color.WHITE); // Hero Type Text
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(hero, 645-hero.length()*6, 430);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            if (p1.getHeroType() == 1){
                g.drawString("Dagger Slash: Hold Left Click", 540, 470);
                g.drawString("Dagger Throw: Press Shift or Right Click", 480, 500);
                g.drawString("Dash: Press Q", 580, 530);
                g.drawString("Invisibility: Press E", 560, 560);
            } else if (p1.getHeroType() == 2){
                g.drawString("Sword Slash: Hold Left Click", 540, 470);
                g.drawString("Sword Launch: Hold and Release Shift or Right Click", 440, 500);
                g.drawString("Shield: Press Q", 580, 530);
                g.drawString("Rage: Press E", 585, 560);
            } else if (p1.getHeroType() == 3){
                g.drawString("Pistol Shot: Hold Left Click", 540, 470);
                g.drawString("Grenade: Press Q", 580, 500);
                g.drawString("Machine Gun: Press E", 560, 530);
            } else if (p1.getHeroType() == 4){
                g.drawString("Sniper Shot: Hold Left Click", 540, 470);
                g.drawString("Poison: Press Q", 580, 500);
                g.drawString("KnockBack: Press E", 565, 530);
            }
            
            g.drawImage(dagger, 415, 253, null); // Weapon images
            g.drawImage(sword, 535, 253, null);
            g.drawImage(pistol, 655, 243, null);
            g.drawImage(sniper, 775, 253, null);

            g.setColor(Color.RED); // Username Error Message
            g.setFont(new Font("Arial", Font.BOLD, 17));
            g.drawString(userNameValidMesage, 645-userNameValidMesage.length()*4, 560);
        }
    }
    public void resetGame(){
        startClientScreen();
        reset = true;
        
        cooldown.reset();
        players.clear();
        xDiff = (int)(Math.random()*background.getWidth())-645;
        yDiff = (int)(Math.random()*background.getHeight())-450;
        p1.reset(645-player.getWidth()/2, 450-player.getHeight()/2, xDiff+645, yDiff+450);
        rotatedPlayer = transform(player, p1.getMouseAngle());
        rotatedDagger = transform(dagger, p1.getMouseAngle());
        rotatedPistol = transform(pistol, p1.getMouseAngle());
        rotatedSword = transform(sword, p1.getMouseAngle());
        rotatedSniper = transform(sniper, p1.getMouseAngle());
        selectedUserName = true;
        
        startBtn.setEnabled(false); // Enables all menu buttons and fields
        startBtn.setVisible(true);
        sendMessage(MessagesIds.PLAYER_UNREADY, null);
        sendMessage(MessagesIds.PLAYER_COUNT, null);
        sendMessage(MessagesIds.GAME_RESET, null);
        startBtn.setText("Ready Up");
        startBtn.setBackground(new Color(144, 238, 144));

        exitBtn.setEnabled(true); 
        exitBtn.setVisible(true);

        userNameInput.setEnabled(false); 
        userNameInput.setVisible(true);
        
    }
    public void checkPlayersAlive(){
        if (playersAlive <= 1){
            if (!gameOverCooldown){
                cooldown.startGameOverCooldown();
                gameOverCooldown = true;
            }
            gameOver = true;            
        } else if (playersAlive > 1){
            if (!gameOverCooldown){
                cooldown.resetGameOverCooldown();
            }
            gameOverCooldown = false;
            gameOver = false;
        }
    }
    public void shieldHit(int damage){
        if (p1.getShieldHealth() > 0){
            p1.decreaseShieldHealth();
            if (p1.getShieldHealth() <= 0){
                cooldown.startRegularAbilityCooldown();
            }  
        } else {
            p1.setCurrentHealth(p1.getCurrentHealth()-damage);
        }
    }
    public double distanceBetweenObjects(int x1, int x2, int y1, int y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)); 
    }
    public void drawHealthBar(int maxHealth, int currentHealth, Graphics g){
        int barX = 645; // Draw Health Bar
        int barY = 800;
        int barWidth = maxHealth*2;
        int barHeight = 40;
        int greenBarWidth = Math.round((float)(barWidth*(1.00-(((float)(maxHealth)-(float)(currentHealth))/(float)(maxHealth)))));
        g.setColor(Color.BLACK);
        g.fillRect(barX-(barWidth)/2, barY, barWidth+4, barHeight);
        g.setColor(new Color(150, 255, 150));
        g.fillRect(barX+2-(barWidth)/2, barY+2, barWidth, barHeight-4);
        g.setColor(new Color(89, 247, 89));
        g.fillRect(barX+2-(barWidth)/2, barY+2, greenBarWidth, barHeight-4);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(Integer.toString(currentHealth), barX-Integer.toString(currentHealth).length()*5, barY+27);
    }
    public void drawSmallHealthBar(Graphics g, int maxHealth, int currentHealth, int xDisplacement, int yDisplacement){
        int barX = 645 + xDisplacement; // Draw Health Bar
        int barY = 385 + yDisplacement;
        int barWidth = maxHealth;
        int barHeight = 20;
        int greenBarWidth = Math.round((float)(barWidth*(1.00-(((float)(maxHealth)-(float)(currentHealth))/(float)(maxHealth)))));
        g.setColor(Color.BLACK);
        g.fillRect(barX-(barWidth)/2, barY, barWidth+4, barHeight);
        g.setColor(new Color(150, 255, 150));
        g.fillRect(barX+2-(barWidth)/2, barY+2, barWidth, barHeight-4);
        g.setColor(new Color(89, 247, 89));
        g.fillRect(barX+2-(barWidth)/2, barY+2, greenBarWidth, barHeight-4);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(Integer.toString(currentHealth), barX-Integer.toString(currentHealth).length()*4, barY+15);
    }
    public void moveProjectiles(){
        ArrayList<Bullet> bullets = p1.getBullets(); // Draw Bullets
        for (int i = 0; i < bullets.size(); i++){
            Bullet currentBullet = bullets.get(i);
            try {
                currentBullet.move();
            } catch (NullPointerException ex){
                System.out.println("Bullet does not exist Exception");
                System.out.println(ex.getMessage());
            }
        }
        ArrayList<Grenade> grenades = p1.getGrenades(); // Draw Grenades
        for (int i = 0; i < grenades.size(); i++){
            Grenade currentGrenade = grenades.get(i);
            try {
                if (currentGrenade.getGrenadeStatus()){
                    currentGrenade.move();
                }
            } catch (NullPointerException ex){
                System.out.println("Grenade does not exist Exception");
                System.out.println(ex.getMessage());
            }
        }
    }
    public void decreaseBulletLifeSpan(){
        ArrayList<Bullet> bullets = p1.getBullets();
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).decreaseLifeSpan(cooldown.getRefreshTime());
            if (bullets.get(i).getLifeSpan() <= 0){
                bullets.remove(i);
                i--;
            }
        }
    }
    public void changeKnifePos(){
        if (p1.getHeroType() == 1){
            if (weaponCooldownHit <= 0.09){
                weaponOffset += 2;
            } else if (weaponCooldownHit > 0.09 && weaponCooldownHit <= 0.18) {
                weaponOffset -= 2;
            } else {
                p1.setWeaponHit(false);
                weaponOffset = 73;
            }
            // if (p1.getWeaponThrow()){
            //     int xDaggerDistance = weaponX - globalMousePosX;
            //     int yDaggerDistance = -(weaponY - globalMousePosY);
                    
            //     p1.setDaggerAngle(Math.atan2(xDaggerDistance, yDaggerDistance));
            // }
        }
        if (p1.getHeroType() == 2){
            if (p1.getWeaponHolding() && p1.getWeaponThrow()){
                p1.setSpeed(2);
                weaponOffset -= 0.25;
                if (weaponOffset < 50){
                    weaponOffset = 50;
                }
                currentPullOffset = weaponOffset;
            } else if (p1.getWeaponHit()){
                if (weaponCooldownHit <= 0.09){
                    weaponOffset += 2;
                } else if (weaponCooldownHit > 0.09 && weaponCooldownHit <= 0.18) {
                    weaponOffset -= 2;
                } else {
                    p1.setWeaponHit(false);
                    weaponOffset = 73;
                }
            }
            else {
                p1.setSpeed(3);
                weaponOffset += 5;
                if (weaponOffset > 73){
                    weaponOffset = 73;
                }
            } 
        }
    }
    public void checkDamageTaken(){
        if  (cooldown.getDamageTakenCooldown() > 1){
            p1.setDamageTaken(0);
        }
        if (cooldown.getDamageTakenCooldown() > 5 && cooldown.getHealingCooldown() > 5){
            p1.setCurrentHealth(p1.getCurrentHealth()+1);
            if (p1.getCurrentHealth() > p1.getMaxHealth()){
                p1.setCurrentHealth(p1.getMaxHealth());
            }
        }
    }
    public void checkPoisonActive(){
        if (poisonActive){
            cooldown.startPoisonCooldown();
        }
    }
    public void playerDied(String enemyUsername){
        p1.setPlayerSpectating(enemyUsername);
        p1.setAlive(false);
        if (!p1.getJoinLate()){
            sendMessage(MessagesIds.PLAYER_DEAD, new Pair<String, String>(enemyUsername, p1.getUsername()));
        }
    }
    public void checkPoisoned(){
        if (p1.getPoison()){
            if (cooldown.getPoisonedCooldown() <= 3 && (cooldown.getPoisonedCooldown() == 1 || cooldown.getPoisonedCooldown() == 2 || cooldown.getPoisonedCooldown() == 3)){
                if (p1.getShieldHealth() > 0){
                    p1.decreaseShieldHealth();
                    if (p1.getShieldHealth() <= 0){
                        cooldown.startRegularAbilityCooldown();
                    }  
                } else {
                    p1.setCurrentHealth(p1.getCurrentHealth()-10);
                    p1.setDamageTaken(p1.getDamageTaken() + 10);
                    cooldown.startDamageTakenCooldown();
                    if (p1.getCurrentHealth() <= 0){
                        playerDied(poisonUsername);
                    }
                }
                
            } else if  (cooldown.getPoisonedCooldown() > 3){
                p1.setPoison(false);
            }
        }
    }
    public void decreaseGrenadeLifeSpan(){
        if (p1.getHeroType() == 3){
            ArrayList<Grenade> grenades = p1.getGrenades();
            for (int i = 0; i < grenades.size(); i++){
                Grenade currentGrenade = grenades.get(i);
                currentGrenade.decreaseLifeSpan(((double)period)/1000);
                if (currentGrenade.getLifeSpan() <= 1 && currentGrenade.getLifeSpan() > 0){
                    currentGrenade.setExplosionActive(true);
                    currentGrenade.setGrenadeStatus(false);
                } else if (currentGrenade.getLifeSpan() <= 0){
                    currentGrenade.setExplosionActive(false);
                    currentGrenade.setGrenadeStatus(false);
                    grenades.remove(i);
                    i--;
                }
            }
        }   
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == userNameInput){
            sendMessage(MessagesIds.PLAYER_USERNAME, userNameInput.getText());
        }
        if (e.getSource() == startBtn){
            if (userReady == 0){
                userReady++;
                playersReady++;
                sendMessage(MessagesIds.PLAYER_READY, null);
                startBtn.setBackground(new Color(255, 51, 51));
                startBtn.setText("Cancel");
                startBtn.setEnabled(false);
                startBtn.setEnabled(true);
            } else {
                userReady--;
                playersReady--;
                sendMessage(MessagesIds.PLAYER_UNREADY, null);
                startBtn.setText("Ready Up");
                startBtn.setBackground(new Color(144, 238, 144));
                startBtn.setEnabled(false);
                startBtn.setEnabled(true);
            }
        }
        if (e.getSource() == exitBtn){
            sendMessage(MessagesIds.EXIT, null);
        }
	}
    public int getPlayersReady(){
        return playersReady;
    }
    public void incrementPlayersReady(){
        playersReady++;
    }
    public int isReady(){
        return userReady;
    }
    public void connect() throws IOException{
        String hostName = "localhost";
        int portNumber = 1024;
        Socket serverSocket = new Socket(hostName, portNumber);
        outObj = new ObjectOutputStream(serverSocket.getOutputStream());
        inObj = new ObjectInputStream(serverSocket.getInputStream());
        
        while (true){
            Pair<MessagesIds, Object> fromServer = null;
            try {
                fromServer = (Pair<MessagesIds, Object>) inObj.readObject(); 
            } catch (IOException e) {
                System.err.println("Error connecting to " + hostName);
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException e){
                System.err.println("Cannot cast recieved object");
                System.err.println(e.getMessage());
            }

            if (fromServer == null) continue;

            switch (fromServer.getKey()){
                case EXIT:
                    System.exit(0);
                    break;
                case PLAYER_COUNT:
                    totalPlayers = (int) fromServer.getValue();
                    break;
                case PLAYER_READY_COUNT:
                    playersReady = (int) fromServer.getValue();
                    break;
                case PLAYER_READY_DECREASE:
                    playersReady--;
                    break;
                case USERNAME_DUPLICATE:
                    if ((Boolean) fromServer.getValue()){
                        selectedUserName = true;
                        userNameInput.setEnabled(false);
                        p1.setUsername(userNameInput.getText());
                        userNameValidMesage = "";
                    } else {
                        userNameValidMesage = "Username Duplicate Found: Input a Different Username";
                    }
                    break;
                case USERNAME_TOO_SHORT:
                    userNameValidMesage = "Username too short: Input a Longer Username";
                    break;
                case GAME_START:
                    if (!gameStart){
                        p1.setAlive(true);
                        if (p1.getHeroType() == 1) { // Set stats
                            p1.setPrimaryDamge(50);
                            p1.setSecondaryDamage(50);
                            p1.setMaxHealth(100);
                            p1.setCurrentHealth(p1.getMaxHealth());
                            p1.setFireCooldown(0.5);
                            p1.setUltCooldown(30);
                            p1.setUltDuration(5);
                            p1.setSpeed(3);
                            p1.setRegularAbilityCooldown(5);
                        } else if (p1.getHeroType() == 2) {
                            p1.setPrimaryDamge(40);
                            p1.setSecondaryDamage(40);
                            p1.setUltDamage(20);
                            p1.setMaxHealth(160);
                            p1.setCurrentHealth(p1.getMaxHealth());
                            p1.setFireCooldown(1);
                            p1.setUltCooldown(25);
                            p1.setUltDuration(8);
                            p1.setSpeed(3);
                            p1.setRegularAbilityCooldown(10);
                        } else if (p1.getHeroType() == 3) {
                            p1.setPrimaryDamge(25);
                            p1.setSecondaryDamage(40);
                            p1.setMaxHealth(150);
                            p1.setCurrentHealth(p1.getMaxHealth());
                            p1.setFireCooldown(0.75);
                            p1.setUltCooldown(25);
                            p1.setUltDuration(7);
                            p1.setSpeed(3);
                            p1.setRegularAbilityCooldown(3);
                        } else if (p1.getHeroType() == 4) {              
                            p1.setPrimaryDamge(50);
                            p1.setSecondaryDamage(10);
                            p1.setUltDamage(0);
                            p1.setMaxHealth(125);
                            p1.setCurrentHealth(p1.getMaxHealth());
                            p1.setFireCooldown(1.5);
                            p1.setUltCooldown(15);
                            p1.setUltDuration(0.4);
                            p1.setSecondAbilityUses(3);
                            p1.setSpeed(3);
                            p1.setRegularAbilityCooldown(7);
                        }
                        
                        boolean gameHasStarted = (boolean) fromServer.getValue();
                        p1.setJoinLate(gameHasStarted);
                        if (gameHasStarted){
                            p1.setCurrentHealth(0);
                        }
                        if (!reset){
                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                // int count = 0;
                                // double startTime;
                                // double endTime;
                                @Override
                                public void run(){
                                    // count++;
                                    // endTime = System.currentTimeMillis();
                                    // if (endTime - startTime > 11){
                                    //     System.out.println(endTime - startTime);
                                    // }
                                    // startTime = System.currentTimeMillis();
                                    calcPlayerPos();
                                    moveProjectiles();
                                    changeKnifePos();
                                    decreaseGrenadeLifeSpan();
                                    weaponCooldownHit += ((double)period)/1000;
                                    weaponCooldownThrow += ((double)period)/1000;
                                    weaponTime += ((double)period)/1000;
                                    dashTime += ((double)period)/1000;
                                    recoilTime += ((double)period)/1000;
                                    if (gameStart){
                                        sendMessage(MessagesIds.PLAYER_OBJECT, p1);
                                    } else {
                                        players.clear();
                                    }
                                    // else if (count > 1){
                                    //     sendMessage(MessagesIds.PLAYER_OBJECT, p1);
                                    //     count = 0;
                                    // }
                                }
                            }, 0, period);
                        }

                        playersAlive = totalPlayers;
                        if (gameHasStarted){
                            playersAlive--;
                        }
                        cooldown.reset();
                        cooldown.startRegularAbilityCooldown();
                        cooldown.startUltCooldown();
                        cooldown.startGrenadeCooldown();
                        cooldown.startPoisonCooldown();
                        
                        startBtn.setEnabled(false); //Disables all menu buttons and fields
                        startBtn.setVisible(false);
                        exitBtn.setEnabled(false); 
                        exitBtn.setVisible(false);
                        userNameInput.setEnabled(false); 
                        userNameInput.setVisible(false);
                        gameStart = true;
                        // playSound("Audio/BackgroundAudio.mp3");
                        
                    }
                    break;
                case PLAYER_OBJECT:
                    Player playerObject = (Player) fromServer.getValue();
                    if (!playerObject.getUsername().equals(p1.getUsername())){
                        boolean playerFound = false;
                        for (int i = 0; i < players.size(); i++){
                            if (players.get(i).equals(playerObject)){
                                playerFound = true;
                                players.set(i, playerObject);
                            }
                        }
                        if (!playerFound){
                            players.add(playerObject);
                        }
                    }
                    break;
                case REMOVE_BULLET:
                    Pair<String, Integer> receivedBulletID = (Pair<String, Integer>) fromServer.getValue();
                    String receivedUsername = receivedBulletID.getKey();
                    int receivedBullet = receivedBulletID.getValue();
                    if (receivedUsername.equals(p1.getUsername())){
                        ArrayList<Bullet> bullets = p1.getBullets();
                        for (int i = 0; i < bullets.size(); i++){
                            if (bullets.get(i).getID() == receivedBullet){
                                bullets.remove(i);
                            }
                        }
                    }
                    break;
                case REMOVE_DAGGER:
                    Pair<String, Integer> receivedDaggerID = (Pair<String, Integer>) fromServer.getValue();
                    String receivedUsernameDagger = receivedDaggerID.getKey();
                    int receivedIDDagger = receivedDaggerID.getValue();
                    if (receivedUsernameDagger.equals(p1.getUsername())){
                        if (p1.getWeaponID() == receivedIDDagger){
                            p1.setWeaponThrow(false);
                            cooldown.changeFireRateCooldown((int)(p1.getFireCooldown()*6));
                        }
                    }
                    break;
                case REMOVE_PLAYER: 
                    String removeUsername = (String) fromServer.getValue();
                    for (int i = 0; i < players.size(); i++){
                        Player currentPlayer = players.get(i);
                        if (currentPlayer.getUsername().equals(removeUsername)){   
                            if (currentPlayer.getAlive()){
                                playersAlive--;
                                checkPlayersAlive();
                            } 
                            players.remove(i);
                            if (p1.getPlayerSpectating().equals(removeUsername)){
                                for (int j = 0; j < players.size(); j++){
                                    if (players.get(j).getAlive()){
                                        p1.setPlayerSpectating(players.get(j).getUsername());
                                    }
                                }
                            }  
                            break;
                        }
                    }
                    break;
                case PLAYER_FOUND:
                    String playerFoundUsername = (String) fromServer.getValue();
                    if (p1.getUsername().equals(playerFoundUsername)){
                        p1.setUltStatus(false);
                        cooldown.startUltCooldown();
                    }
                    break;
                case PLAYER_DEAD:
                    Pair<String, String> usernamesElimation = (Pair<String, String>) fromServer.getValue();
                    String playerEliminationUsername = (String) usernamesElimation.getKey();
                    String playerElimatedUsername = (String) usernamesElimation.getValue();
                    if (p1.getUsername().equals(playerEliminationUsername)){
                        p1.incrementTotalElim();
                        
                        int healthIncrease = Math.round((float)(p1.getMaxHealth()*0.15));
                        p1.setMaxHealth(p1.getMaxHealth()+healthIncrease);
                        p1.setCurrentHealth(p1.getCurrentHealth()+healthIncrease);
                        
                        int primaryDamageIncrease = Math.round((float)(p1.getPrimaryDamage()*0.15));
                        int setSecondaryDamage = Math.round((float)(p1.getSecondaryDamage()*0.15));
                        int ultDamageIncrease = Math.round((float)(p1.getUltDamage()*0.15));

                        p1.setPrimaryDamge(p1.getPrimaryDamage()+primaryDamageIncrease);
                        p1.setSecondaryDamage(p1.getSecondaryDamage()+setSecondaryDamage);
                        p1.setUltDamage(p1.getUltDamage()+ultDamageIncrease);
                        
                    } else if (p1.getUsername().equals(playerElimatedUsername)){
                        if (!p1.getJoinLate()){
                            playersAlive--;
                            checkPlayersAlive();
                        }
                    }
                    for (int i = 0; i < players.size(); i++){
                        Player currentPlayer = players.get(i);
                        if (currentPlayer.getUsername().equals(playerElimatedUsername)){   
                            if (!currentPlayer.getJoinLate()){
                                playersAlive--;
                                checkPlayersAlive();
                            }
                        }
                    }
                    // else {
                    //     for (int i = 0; i < players.size(); i++){
                    //         if (players.get(i).getUsername().equals(playerEliminatedUsername)){
                    //             players.get(i).setAlive(false);
                    //         }
                    //     }
                    // }
                    break;
                default:
                    System.out.println("Unsupported ID: " + fromServer.getKey());
                    break;

            }
        }
    }
    public synchronized void sendMessage(MessagesIds id, Object obj)
    {
        try {
            outObj.writeObject(new Pair<MessagesIds, Object>(id, obj));
            outObj.flush();
            outObj.reset();
        } catch (IOException e){
            System.err.println("Error sending ID: " + id + "\n" + e.getMessage());
        }
        
    }
    public void checkFireBullet(){
        if (firing){ // If user is pressing the fire button and the cooldown has been reset, a bullet will spawn
            if (p1.getHeroType() == 1 && weaponCooldownHit >= p1.getFireCooldown()){
                hitDagger();
            } else if (p1.getHeroType() == 2 && weaponCooldownHit >= p1.getFireCooldown()){
                hitSword();
            } else if (p1.getHeroType() == 3 && cooldown.getFireRateCooldown() >= p1.getFireCooldown()){
                cooldown.startHealingCooldown();
                fireBullet(115, 30, 81, 9);
            } else if (p1.getHeroType() == 4 && cooldown.getFireRateCooldown() >= p1.getFireCooldown()){
                cooldown.startHealingCooldown();
                fireBullet(140, 24, 82, 10);
            }
        }
        if (p1.getWeaponHolding()){
            if (p1.getHeroType() == 2 && weaponCooldownThrow >= weaponThrowAnimationTime && !p1.getWeaponThrow() && !p1.getWeaponHit()){
                pullSword();
            }
        }
    }
    public void checkRegularAbility(){
        if (regularAbilityHeld){ // If user is pressing the fire button and the cooldown has been reset, the ability will spawn
            if (p1.getHeroType() == 3 && cooldown.getGrenadeRate() >= p1.getRegularAbilityCooldown()){
                fireRegularAbility(57, 12, 117, 10);
            } 
        }
    }
    public void checkUltStatus(){
        if (p1.getUltStatus() && cooldown.getUltCooldown() >= p1.getUltDuration()){
            if (p1.getHeroType() == 1){
                p1.setUltStatus(false);
                cooldown.startUltCooldown();
            } else if (p1.getHeroType() == 2){
                p1.setPrimaryDamge(40);
                p1.setSecondaryDamage(40);
                p1.setUltStatus(false);
                cooldown.startUltCooldown();
            } else if (p1.getHeroType() == 3){
                p1.setFireCooldown(0.75);
                p1.setSpeed(3);
                p1.setUltStatus(false);
                cooldown.startUltCooldown();
            }
        }
    }
    public void recoil(double velocityFactor, double accelerationFactor){
        xRecoilVelocity += Math.cos(p1.getMouseAngle()+Math.toRadians(90))*velocityFactor;
        yRecoilVelocity += Math.sin(p1.getMouseAngle()+Math.toRadians(90))*velocityFactor;
        xRecoilAcceleration -= Math.cos(p1.getMouseAngle()+Math.toRadians(90))*accelerationFactor;
        yRecoilAcceleration -= Math.sin(p1.getMouseAngle()+Math.toRadians(90))*accelerationFactor;
        recoilActive = true;
        recoilTime = 0;
    }
    public void fireBullet(int offset, int displacement, int angle, int speed){
        cooldown.startFireRateCooldown();
    
        if (p1.getHeroType() == 3){
            playSound("Audio/PistolAudio.wav");
        } else if (p1.getHeroType() == 4){
            playSound("Audio/SniperAudio.wav");
            // recoil(3, 0.1);
        }
        int bulletX = p1.getScreenX() + displacement + (int) (Math.cos((p1.getMouseAngle()+Math.toRadians(-angle))) * offset);
        int bulletY = p1.getScreenY() + displacement + (int) (Math.sin((p1.getMouseAngle()+Math.toRadians(-angle))) * offset);
        p1.addBullets(bulletX+xDiff, bulletY+yDiff, p1.getMouseAngle(), p1.getWeaponID(), speed, poisonActive);
        poisonActive = false;
        p1.incrementWeaponID();
    }

    public void fireRegularAbility(int offset, int displacement, int angle, int speed){
        cooldown.startGrenadeRate();
        cooldown.startGrenadeCooldown();
        int grenadeX = p1.getScreenX() + displacement + (int) (Math.cos((p1.getMouseAngle()+Math.toRadians(-angle))) * offset);
        int grenadeY = p1.getScreenY() + displacement + (int) (Math.sin((p1.getMouseAngle()+Math.toRadians(-angle))) * offset);
        p1.addGrenades(grenadeX+xDiff, grenadeY+yDiff, p1.getMouseAngle(), speed, grenadeID);
        grenadeID++;
        ArrayList<Grenade> grenades = p1.getGrenades();
        for (int i =0; i < grenades.size(); i++){
            Grenade currentGrenade = grenades.get(i);
            if (currentGrenade.getID() == grenadeID - 1){
                currentGrenade.setGrenadeStatus(true);
            }
        }
    }

    public void updatePlayerRotation(int mousePosX, int mousePosY){
        int yDistance = (450) - mousePosY;
        int xDistance = -((645) - mousePosX);
            
        p1.setMouseAngle(Math.atan2(xDistance, yDistance)); // x/y because plane of rotation is tilted 90 degrees
        checkMouseMoved = true;

        globalMousePosX = mousePosX;
        globalMousePosY = mousePosY;
    }
    public void fireDagger(){
        cooldown.startHealingCooldown();
        cooldown.startFireRateCooldown();
        p1.setWeaponThrow(true);
        p1.incrementWeaponID();
        if (p1.getUltStatus()){
            p1.setUltStatus(false);
            cooldown.startUltCooldown();
        }
    }
    public void hitDagger(){
        playSound("Audio/KnifeAudio.wav");
        cooldown.startHealingCooldown();
        weaponCooldownHit = 0;
        p1.setWeaponHit(true);
        p1.incrementWeaponID();
        if (p1.getUltStatus()){
            p1.setUltStatus(false);
            cooldown.startUltCooldown();
        }
    }
    public void pullSword(){
        p1.setWeaponHolding(true);
        cooldown.startHealingCooldown();
        weaponCooldownThrow = 0;
        p1.setWeaponThrow(true);
    }
    public void hitSword(){
        playSound("Audio/KnifeAudio.wav");
        cooldown.startHealingCooldown();
        weaponCooldownHit = 0;
        p1.setWeaponHit(true);
        p1.incrementWeaponID();
    }
    public void mousePressed(MouseEvent e) {
        if (gameStart && p1.getAlive()){
            if (p1.getHeroType() == 1){
                if (e.getButton() == MouseEvent.BUTTON1) {      
                    firing = true;    
                    if (weaponCooldownHit >= p1.getFireCooldown() && !p1.getWeaponThrow()){   
                        hitDagger();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (cooldown.getFireRateCooldown() >= p1.getFireCooldown()*8  && !p1.getWeaponHit()){
                        fireDagger();
                    } else if (p1.getWeaponThrow()){
                        p1.setWeaponThrow(false);
                        cooldown.setFireRateCooldown(p1.getFireCooldown()*6);
                    }
                }
            } else if (p1.getHeroType() == 2){
                if (e.getButton() == MouseEvent.BUTTON1) {    
                    firing = true;      
                    if (weaponCooldownHit >= p1.getFireCooldown() && !p1.getWeaponThrow() && !p1.getWeaponHit()){  
                        hitSword();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    p1.setWeaponHolding(true);
                    if (weaponCooldownThrow >= weaponThrowAnimationTime && !p1.getWeaponHit() && !p1.getWeaponThrow()){
                        pullSword();
                    } 
                }  
            } else if (p1.getHeroType() == 3){
                firing = true;
                if (cooldown.getFireRateCooldown() >= p1.getFireCooldown()){
                    cooldown.startHealingCooldown();
                    fireBullet(115, 30, 80, 9);
                }
            }
            else if (p1.getHeroType() == 4){
                firing = true;
                if (cooldown.getFireRateCooldown() >= p1.getFireCooldown()){
                    cooldown.startHealingCooldown();
                    fireBullet(140, 24, 82, 10);
                }
            }
        } else {
            if (e.getX() >= 425 & e.getX() <= 533 && e.getY() >= 250 && e.getY() <= 358){
                hero = "Assassin";
                p1.setHeroType(1);
                selectedClass = true;
            } else if (e.getX() >= 545 & e.getX() <= 653 && e.getY() >= 250 && e.getY() <= 358){
                hero = "Swordsman";
                p1.setHeroType(2);
                selectedClass = true;
            } else if (e.getX() >= 665 & e.getX() <= 773 && e.getY() >= 250 && e.getY() <= 358){
                hero = "Gunner";
                p1.setHeroType(3);
                selectedClass = true;
            } else if (e.getX() >= 785 & e.getX() <= 893 && e.getY() >= 250 && e.getY() <= 358){
                hero = "Sniper";
                p1.setHeroType(4);
                selectedClass = true;
            }
        }
	}
    public void shootSword(){
        playSound("Audio/KnifeAudio.wav");
        weaponTime = 0;
        int minWeaponOffset = 50;
        int maxWeaponOffset = 73;
        int maxDamage = 40;
        if (p1.getUltStatus()){
            maxDamage = 55;
        }
        int minDamage = 1;
        double damage = minDamage + (maxDamage - minDamage) * ((maxWeaponOffset - currentPullOffset) / (maxWeaponOffset - minWeaponOffset));
        p1.setSecondaryDamage(Math.round((float)damage));
        
        double timeDashFactor = 0.013;
        double velocityDashFactor = 0.4;
        if (p1.getUltStatus()){
            velocityDashFactor = 0.7;
        }
        weaponAnimationTime = (73 - currentPullOffset)*timeDashFactor;
        xVelocity += (73 - currentPullOffset)*Math.cos(p1.getMouseAngle()+Math.toRadians(-90))*velocityDashFactor;
        yVelocity += (73 - currentPullOffset)*Math.sin(p1.getMouseAngle()+Math.toRadians(-90))*velocityDashFactor;
        p1.incrementWeaponID();

        currentPullOffset = 73;
        p1.setWeaponHolding(false);
    }
	public void mouseReleased(MouseEvent e) {
        firing = false;
        if (p1.getHeroType() == 2){
            if (p1.getWeaponHolding()){
                shootSword();
            }
        }
    }
    
    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        if (gameStart){
            updatePlayerRotation(e.getX(), e.getY());
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (gameStart){
            updatePlayerRotation(e.getX(), e.getY());
        }
    }

    public void keyPressed(KeyEvent e) {
        if (gameStart && p1.getAlive()){
            if (e.getKeyCode() == 37 || e.getKeyCode() == 65){
                left = true;
            }
            if (e.getKeyCode() == 39 || e.getKeyCode() == 68){
                right = true;
            }
            if (e.getKeyCode() == 38 || e.getKeyCode() == 87){
                up = true;
            }
            if (e.getKeyCode() == 40 || e.getKeyCode() == 83){
                down = true;
            }
            if (e.getKeyCode() == 16){ // Shift
                if (p1.getHeroType() == 1 && cooldown.getFireRateCooldown() >= p1.getFireCooldown()*8  && !p1.getWeaponHit() && !p1.getWeaponThrow()){
                    fireDagger();
                }
                if (p1.getHeroType() == 2 && weaponCooldownThrow >= weaponThrowAnimationTime && !p1.getWeaponThrow() && !p1.getWeaponHit()){
                    p1.setWeaponHolding(true);
                    pullSword();
                }
            }
            if (e.getKeyCode() == 69){ // Ult abilities
                if (cooldown.getUltCooldown() >= p1.getUltCooldown() && !p1.getUltStatus()){
                    if (p1.getHeroType() == 1){
                        p1.setUltStatus(true);
                        cooldown.startUltCooldown();
                    } else if (p1.getHeroType() == 2){
                        p1.setPrimaryDamge(55);
                        p1.setSecondaryDamage(55);
                        p1.setUltStatus(true);
                        cooldown.startUltCooldown();
                    } else if (p1.getHeroType() == 3){
                        p1.setFireCooldown(0.25);
                        p1.setSpeed(4);
                        p1.setUltStatus(true);
                        cooldown.startUltCooldown();
                    } else if (p1.getHeroType() == 4){
                        // Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                        // SwingUtilities.convertPointFromScreen(mouseLoc, this); 
                        // xDiff -= 645 - mouseLoc.getX();
                        // yDiff -= 450 - mouseLoc.getY();

                        fireBullet(140, 24, 82, 10);
                        recoil(13, 0.1);
                        p1.setUltStatus(true);
                        cooldown.startUltCooldown();
                    }
                }
            }
            if (e.getKeyCode() == 81){ // Regular Ability
                regularAbilityHeld = true;
                if (p1.getHeroType() == 1){
                    if (cooldown.getRegularAbilityCooldown() >= p1.getRegularAbilityCooldown()){
                        if (p1.getUltStatus()){
                            p1.setUltStatus(false);
                            cooldown.startUltCooldown();
                        }
                        dashTime = 0;
                        weaponAnimationTime = 0.2;
                        xVelocity += 12*Math.cos(p1.getMouseAngle()+Math.toRadians(-90));
                        yVelocity += 12*Math.sin(p1.getMouseAngle()+Math.toRadians(-90));
                        cooldown.startRegularAbilityCooldown();
                    }
                } else if (p1.getHeroType() == 2){
                    if (cooldown.getRegularAbilityCooldown() >= p1.getRegularAbilityCooldown()){
                        p1.setShieldHealth(1);
                    }
                } else if (p1.getHeroType() == 3){
                    if (cooldown.getGrenadeRate() >= p1.getRegularAbilityCooldown()){
                        fireRegularAbility(57, 12, 117, 12);
                    }
                } else if (p1.getHeroType() == 4){
                    if (cooldown.getPoisonCooldown() >= p1.getRegularAbilityCooldown()){
                        poisonActive = true;
                        cooldown.startPoisonCooldown();
                    }                    
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        if (gameStart){ 
            if (e.getKeyCode() == 37 || e.getKeyCode() == 65){
                left = false;
            }
            if (e.getKeyCode() == 39 || e.getKeyCode() == 68){
                right = false;
            }
            if (e.getKeyCode() == 38 || e.getKeyCode() == 87){
                up = false;
            }
            if (e.getKeyCode() == 40 || e.getKeyCode() == 83){
                down = false;
            }
            if (e.getKeyCode() == 81){
                regularAbilityHeld = false;
            }
            if (e.getKeyCode() == 16){
                if (p1.getHeroType() == 2){
                    shootSword();
                }
            }
        }
    }
    public void keyTyped(KeyEvent e) {}
}
