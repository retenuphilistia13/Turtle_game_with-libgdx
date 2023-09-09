package retenu.flappybird.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import retenu.flappybird.game.Rotation;
import retenu.flappybird.game.actors.BaseActor;

public class Turtle extends BaseActor implements InputProcessor {

//    Rotation turtleRotation;

    boolean ableToMove;
    public Turtle() {
        super();
//        loadTexture("assets/turtle/turtle_1.png");

        ableToMove=true;

    }

    public Turtle(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("assets/turtle/turtle_1.png");




       // turtleRotation = Rotation.LEFT;

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(50);

        ableToMove=true;
    }



public boolean isAbleToMove(){
    return ableToMove;
}

    public void setAbleToMove(boolean able){
        ableToMove=able;
    }
    @Override
    public void act(float dt) {
       super.act(dt);

boundToWorld();
        alignCamera();
if(ableToMove){
    updateOrigin();
    if(isPressing){

        turtleMouseInput(dt);
        setAbleToMove(true);

    }
    else if(!isPressing){
        turtleInputs(dt);
    }


//dt=dt;
    //turtleMouseInput(dt);
}



    }

    float dt;


    private void turtleMouseInput(float dt){
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        // Convert screen coordinates to world coordinates
        Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
        getStage().getCamera().unproject(mousePos);

        if (this.getCollisionRectangle().contains(mousePos.x, mousePos.y)){
            ableToMove=false;
        }

        float actorX = getX();
        float actorY = getY();

        float angleRadians = MathUtils.atan2(mousePos.y - actorY, mousePos.x - actorX);
        float angleDegrees = angleRadians * MathUtils.radiansToDegrees;

//        if(ableToMove) {
            accelerateAtAngle(angleDegrees);
            applyPhysics(dt);

            // Optionally, you can set the turtle's rotation to face the mouse cursor
            setRotation(angleDegrees);

//        }
    }

    private void turtleInputs(float dt){

        if(Gdx.input.isKeyPressed(Keys.LEFT))
            accelerateAtAngle(180);
        if(Gdx.input.isKeyPressed(Keys.RIGHT))
            accelerateAtAngle(0);
        if(Gdx.input.isKeyPressed(Keys.UP))
            accelerateAtAngle(90);
        if(Gdx.input.isKeyPressed(Keys.DOWN))
            accelerateAtAngle(270);

//   if(isMoving()){//update origin point when rotating
//       setOrigin(getWidth() / 2, getHeight() / 2);
//   }

        applyPhysics(dt);
        setAnimationPaused( !isMoving() );

        if ( getSpeed() > 0 )
            setRotation( getMotionAngle() );

    }
    boolean isPressing;
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Calculate the angle between the actor and the mouse click


            isPressing=true;

        // Check if the click position is within the turtle's bounds


        return true; // Return true to indicate that the input event has been handled
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isPressing=false;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
