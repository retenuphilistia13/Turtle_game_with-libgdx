package retenu.flappybird.game;

public enum GameState {

    WIN,LOSE,LAUNCHED,AFTERWIN;

    public boolean isWin() {
        return this == WIN;
    }

}
