package retenu.flappybird.game;

import retenu.flappybird.game.screen.LevelScreen;

public class MyGdxGame extends BaseGame {
	public void create()
	{
		setActiveScreen( new LevelScreen() );
	}

}
