package retenu.flappybird.game.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import retenu.flappybird.game.actors.BaseActor;

public class StarFish extends BaseActor {

    public StarFish(){
        super();
        loadTexture("assets/starfish.png");
    }


    public StarFish(float x, float y, Stage s){
        super(x,y,s);
        loadTexture("assets/starfish.png");
    }

}
