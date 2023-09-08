package retenu.flappybird.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import      com.badlogic.gdx.utils.Array;
import    com.badlogic.gdx.graphics.Texture.TextureFilter;
import     com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import retenu.flappybird.game.GameState;

import com.badlogic.gdx.graphics.Camera;
import    com.badlogic.gdx.utils.viewport.Viewport;

public class BaseActor extends Actor {
    private Animation<TextureRegion> animation;
    private float elapsedTime;//how long the animation has been playing
    private boolean animationPaused;
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public Vector2 getVelocityVec() {
        return velocityVec;
    }

    public Vector2 getAccelerationVec() {
        return accelerationVec;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setVelocityVecX(float vecX) {
        this.velocityVec.x = vecX;
    }

    public void setVelocityVecY(float vecY) {
        this.velocityVec.y = vecY;
    }

    private Vector2 velocityVec;

    public void setAccelerationVecY(float acceY) {
        this.accelerationVec.y = acceY;
    }

    public void setAccelerationVecX(float acceX) {
        this.accelerationVec.x = acceX;
    }

    private Vector2 accelerationVec;
    private float acceleration;

    protected float repelAngle = 0; // Initialize the repulsion angle to a default value


    public float getMaxSpeed() {
        return maxSpeed;
    }

    private float maxSpeed;
    private float deceleration;

    private static Rectangle worldBounds;// staic not instance but an independent variable from all instances of the class

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

// center camera on actor
cam.position.set(this.

    getX() +this.

    getOriginX(), this.

    getY() +this.

    getOriginY(), 0);
// bound camera to layout
    cam.position.x =MathUtils.clamp(cam.position.x,
    cam.viewportWidth/2,worldBounds.width -cam.viewportWidth/2);
    cam.position.y =MathUtils.clamp(cam.position.y,
    cam.viewportHeight/2,worldBounds.height -cam.viewportHeight/2);
cam.update();
}
    public  static void setWorldBounds(float width,float height){
        worldBounds=new Rectangle(0,0,width,height);

    }

    public  static Rectangle getWorldBounds(){
        return worldBounds;

    }

    public  static void setWorldBounds(BaseActor b){
        setWorldBounds(b.getWidth(),b.getHeight());
    }
boolean isBoundToWorld;

    public BaseActor() {
        super();
        textureRegion = new TextureRegion(); // Initialize the textureRegion to a blank texture region


        rectangle = new Rectangle();

        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        velocityVec = new Vector2(0,0);

        accelerationVec = new Vector2(0,0);
        acceleration = 0;

        maxSpeed = 1000;
        deceleration = 0;
    }

    public BaseActor(float x, float y, Stage s)
    {
// call constructor from Actor class
        super();

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        textureRegion = new TextureRegion(); // Initialize the textureRegion to a blank texture region


        rectangle = new Rectangle();
// perform additional initialization tasks


        velocityVec = new Vector2(0,0);

        accelerationVec = new Vector2(0,0);
        acceleration = 0;

        maxSpeed = 1000;
        deceleration = 0;

        repelAngle=0;

        setPosition(x,y);
        s.addActor(this);
    }


    public void boundToWorld(){
        isBoundToWorld=false;
        if(getX()<0) {
            isBoundToWorld=true;
            setX(0);
        }
        // check right edge
        if (getX() + getWidth() > worldBounds.width) {
            isBoundToWorld=true;
            setX(worldBounds.width - getWidth());
        }
// check bottom edge
        if (getY() < 0) {
            isBoundToWorld=true;
            setY(0);
        }
// check top edge
        if (getY() + getHeight() > worldBounds.height) {
            setY(worldBounds.height - getHeight());
            isBoundToWorld=true;
        }
        if(isBoundToWorld)return;
        else
            isBoundToWorld = false;

    }

    public void repelToWorld(float dt){
        isBoundToWorld = false;

        if (getX() < 0) {
            isBoundToWorld = true;
            setX(0);
            setVelocityVecX(-getVelocityVec().x);
            setAccelerationVecX(-getAccelerationVec().x);

            repelAngle=180;
        }

        // Check right edge
        if (getX() + getWidth() > worldBounds.width) {
            isBoundToWorld = true;
            setX(worldBounds.width - getWidth());
            setVelocityVecX(-getVelocityVec().x);
            setAccelerationVecX(-getAccelerationVec().x);

            repelAngle=0;
        }

        // Check bottom edge
        if (getY() < 0) {
            isBoundToWorld = true;
            setY(0);
            setVelocityVecY(-getVelocityVec().y);
            setAccelerationVecY(-getAccelerationVec().y);

            repelAngle=90;
        }

        // Check top edge
        if (getY() + getHeight() > worldBounds.height) {
            isBoundToWorld = true;
            setY(worldBounds.height - getHeight());
            setVelocityVecY(-getVelocityVec().y);
            setAccelerationVecY(-getAccelerationVec().y);


            repelAngle=270;
        }

    }
    float x = 0,y=0;
float angle=(float) Math.toRadians(45);
    public void setMaxSpeed(float ms)
    {
        maxSpeed = ms;
    }
    public void setDeceleration(float dec)
    {
        deceleration = dec;
    }
    public void setAcceleration(float acc)
    {
        acceleration = acc;
    }

    public void updateOrigin(){

        setOrigin(getWidth() / 2, getHeight() / 2);//update origin point

    }
    public void accelerateAtAngle(float angle)
    {
        // Calculate the new angle by adding the repelAngle and randomInt
        float newAngle = angle + repelAngle ;

        // Make sure the newAngle is in the range [0, 360] degrees
        newAngle = (newAngle + 360) % 360;
      //  System.out.println("new angle"+ newAngle);//not the proplem
        updateOrigin();
         accelerationVec.add( new Vector2(acceleration, 0).setAngle(newAngle) );
    }

    public void accelerateForward()
    {
        updateOrigin();
        accelerateAtAngle( getRotation() );
    }
    public void setSpeed(float speed)
    {
// if length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0)
            velocityVec.set(speed, 0);
        else
            velocityVec.setLength(speed);
    }
    public float getSpeed()
    {
        return velocityVec.len();
    }
    public void setMotionAngle(float angle)
    {
        velocityVec.setAngle(angle);
    }
    public float getMotionAngle()
    {
        return velocityVec.angle();
    }

    public boolean isMoving()
    {
        return (getSpeed() > 0);
    }

public void movingPhysics(float dt){//no declaration only for repeling object

    velocityVec.add(accelerationVec.x*dt,accelerationVec.y*dt);

    float speed =getSpeed();//speed velocity

    velocityVec.add(accelerationVec.x*dt,accelerationVec.y*dt);

    speed=MathUtils.clamp(speed,0,maxSpeed);


    velocityVec.setLength(speed);//instead of setSpeed(speed);

    //apply chnages to actor
    moveBy(velocityVec.x*dt,velocityVec.y*dt);

}

    public void applyPhysics(float dt){


        velocityVec.add(accelerationVec.x*dt,accelerationVec.y*dt);

        float speed =getSpeed();//speed velocity

        if(accelerationVec.len()==0)
            speed-=deceleration*dt;

        speed=MathUtils.clamp(speed,5,maxSpeed);


        setSpeed(speed);//update speed (velocity)

        //apply chnages to actor
        moveBy(velocityVec.x*dt,velocityVec.y*dt);

        accelerationVec.set(0,0);

    }


    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;

        TextureRegion tr = animation.getKeyFrame(0);

        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();

        setSize(w, h);//size of actor

   setOrigin(w / 2, h / 2);//center of actor

    }

    public Animation<TextureRegion> loadTexture(String fileName)
    {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,
                                                           float frameDuration, boolean loop) {
        int fileCount = fileNames.length;

        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int n = 0; n < fileCount; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        if (animation == null)
            setAnimation(anim);



        return anim;

    }
// if using spritsheet
//    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols,
//                                                           float frameDuration, boolean loop) {
//        Texture texture = new Texture(Gdx.files.internal(fileName), true);
//        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//        int frameWidth = texture.getWidth() / cols;
//        int frameHeight = texture.getHeight() / rows;
//        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
//
//
//    Array<TextureRegion> textureArray = new Array<TextureRegion>();
//for(int r = 0;r<rows;r++)
//            for(int c = 0;c<cols;c++)
//            textureArray.add(temp[r][c]);
//    Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration,
//            textureArray);
//if(loop)
//            anim.setPlayMode(Animation.PlayMode.LOOP);
//else
//        anim.setPlayMode(Animation.PlayMode.NORMAL);
//if(animation ==null)
//
//    setAnimation(anim);
//return anim;
//}

    public void setTexture(Texture t)
    {
        textureRegion.setRegion(t);
        setSize( t.getWidth(), t.getHeight() );
        rectangle.setSize( t.getWidth(), t.getHeight() );
    }

    public void removeTexture()
    {
        //setSize(0, 0); // Optionally, reset the size as well if needed
       // rectangle.setSize(0, 0);

    }
    public boolean overlaps(BaseActor other) {
        Rectangle thisRectangle = getCollisionRectangle();
        Rectangle otherRectangle = other.getCollisionRectangle();

        return thisRectangle.overlaps(otherRectangle);
    }

    public Rectangle getCollisionRectangle() {
        Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        rect.setCenter(getX() + getOriginX(), getY() + getOriginY());
        return rect;
    }

    //like update but for actor
    public void act(float dt) {

        super.act(dt);

        if(!animationPaused){//if not paused
            elapsedTime+=dt;
        }

    }



    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor(); // used to apply tint color effect
        batch.setColor(c.r, c.g, c.b, c.a);


        if (isVisible()&&animation!=null)
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        else if(isVisible()){
            batch.draw(textureRegion,
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }


    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}