package retenu.flappybird.game.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controller extends Touchpad {

    public Controller(float deadzoneRadius, Skin skin) {
        super(deadzoneRadius, skin);
    }

    public Controller(float deadzoneRadius, Skin skin, String styleName) {
        super(deadzoneRadius, skin, styleName);
    }

    public Controller(float deadzoneRadius, TouchpadStyle style) {
        super(deadzoneRadius, style);
    }


}
