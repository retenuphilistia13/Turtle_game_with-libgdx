package retenu.flappybird.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import retenu.flappybird.game.GameState;

public abstract class BaseScreen implements Screen {
    protected Stage mainStage;

    protected Stage uiStage;
    protected GameState g;

    public BaseScreen(){
        mainStage = new Stage();
        uiStage= new Stage();
g=GameState.LAUNCHED;

        initialize();
    }

    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        mainStage.act(dt);
        uiStage.act(dt);
        update(dt);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();//order are important


        uiStage.draw();

    }


    public abstract void update(float dt);
    public abstract void initialize();



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
