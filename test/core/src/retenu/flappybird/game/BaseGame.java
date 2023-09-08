package retenu.flappybird.game;

import com.badlogic.gdx.Game;
import retenu.flappybird.game.screen.BaseScreen;

public abstract class BaseGame extends Game {

private static BaseGame game;

public BaseGame(){
    game = this;
}
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }

//    }
//    public abstract void update(float dt);
//    public abstract void initialize();
    @Override
    public void create()
    {



    }
}
