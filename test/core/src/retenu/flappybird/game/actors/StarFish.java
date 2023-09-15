package retenu.flappybird.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import retenu.flappybird.game.actors.BaseActor;

public class StarFish extends BaseActor {

    public StarFish(){
        super();
        loadTexture("assets/starfish.png");

        setAcceleration(20);
        setMaxSpeed(2);

    }


    public StarFish(float x, float y, Stage s){
        super(x,y,s);
        loadTexture("assets/starfish.png");

        setAcceleration(10);
        setMaxSpeed(3 + (int) (Math.random() * (7 - 3)));

        //   setDeceleration(0);
        randomInt = -360 + (int) (Math.random() * (360 - -360));
    }
    int randomInt;

    @Override
    public void act(float dt) {
        super.act(dt);


    repelToWorld(dt);

        System.out.println("angle to accelarate "+getMotionAngle());
       accelerateAtAngle(randomInt);


    movingPhysics(dt);
}

//
//            accelerateAtAngle(randomInt);
//
//
//        movingPhysics(dt);




}
