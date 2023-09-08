package retenu.flappybird.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import retenu.flappybird.game.GameState;
import retenu.flappybird.game.actors.BaseActor;
import retenu.flappybird.game.actors.StarFish;
import retenu.flappybird.game.actors.Turtle;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelScreen extends BaseScreen{

    private Turtle turtle;
    private ArrayList<BaseActor> starFishList;
    private BaseActor ocean;
    private BaseActor winMessage;
    private boolean win;

    int winPoints;

    int userPoints;

    boolean victoryDance;

    int screenWidth;

    int screenHeight;
    @Override
    public void update(float dt) {
// check user input
        mainStage.act(1/60f);


        if (g!= GameState.WIN) {
            Iterator<BaseActor> iterator = starFishList.iterator();
            while (iterator.hasNext()) {
                BaseActor starfish = iterator.next();

                if (turtle.overlaps(starfish)) {
                    starfish.remove(); // This removes the actor from the stage.
                    iterator.remove(); // Remove it from the list.

                    // If you want to remove the texture (assuming you have a removeTexture method), do it here.
                    starfish.removeTexture();

                    userPoints++;
                    System.out.println("points user: " + userPoints);

                    System.out.println("points to win: " + winPoints);
                }
            }

        }


        if(!g.isWin()&&winPoints==userPoints&&g!=GameState.AFTERWIN){
//			win=true;
            g=GameState.WIN;
            victoryDance=true;
        }


        if(g==GameState.WIN) {
            if(victoryDance) {
                Action spin = Actions.rotateBy(360, 3);
                turtle.clearActions();
                turtle.addAction(spin);
                victoryDance=false;
            }

            //make turtle stop moving
            turtle.setAbleToMove(false);

            winMessage = new BaseActor(0,0,uiStage);
            winMessage.loadTexture("assets/you_win.png");
            //winMessage.setTexture( new Texture(Gdx.files.internal("assets/you_win.png")) );
            //Rectangle world=BaseActor.getWorldBounds();

            winMessage.setPosition(180,180);
            winMessage.getColor().a = 0.6f;
            uiStage.addActor(winMessage);
            winMessage.addAction( Actions.delay(1) );
            winMessage.addAction( Actions.after( Actions.fadeIn(3) ) );
            winMessage.addAction( Actions.after( Actions.fadeOut(1) ) );
            System.out.println("you win");

            g=GameState.AFTERWIN;
        }

// clear screen
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//// draw graphics



        mainStage.draw();

        uiStage.draw();


    }

    @Override
    public void initialize() {

        winPoints=0;
        userPoints=0;

        ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture( "assets/ocean.png" );

       // System.out.println("width "+screenWidth+ " \nheight "+screenHeight);
float multi=1.0f;
int count=0;
do{
    ocean.setSize(ocean.getWidth()*multi,ocean.getHeight()*multi);
//
    ocean.setWidth(ocean.getWidth()*multi);
    ocean.setHeight(ocean.getHeight()*multi);
multi+=0.1;
count++;
}while(ocean.getWidth()<=Gdx.graphics.getWidth()||ocean.getHeight()<=Gdx.graphics.getHeight());
        System.out.println("background count "+count);

        BaseActor.setWorldBounds(ocean);//because it static we could use the class name it self

        screenWidth=(int)(ocean.getWidth());
        screenHeight=(int)(ocean.getHeight());

        turtle = new Turtle(0,70,mainStage);

        Gdx.input.setInputProcessor(turtle);
        turtle.setSize(70,70);

        starFishList=new ArrayList<>();

        generateStars();



    }

    private void generateStars(){
        Action spin ;

        int minX =0;
        int maxX = screenWidth;

        int minY= 0;
        int maxY = screenHeight ;
        int randomInt ;
        boolean loop;

        int loopCount=0;

        int starFishNumber=10;

        for (int i = 0; i < starFishNumber; i++) {

            StarFish starFish=new StarFish(); // Create a new StarFish object in each iteration

            do {

                loopCount++;
                randomInt = minX + (int) (Math.random() * (maxX - minX));

                // Calculate random x and y positions
                int x = 0 + randomInt;

                randomInt = minY + (int) (Math.random() * (maxY - minY));

                int y = 0 + randomInt;

                // Ensure the starfish stays within the screen boundaries
                if (x < 0) {
                    x = 0;
                } else if (x > screenWidth - starFish.getWidth()) {
                    x = screenWidth - (int) starFish.getWidth();
                }

                if (y < 0) {
                    y = 0;
                } else if (y > screenHeight - starFish.getHeight()) {
                    y = screenHeight - (int) starFish.getHeight();
                }

                starFish = new StarFish(x, y, mainStage);
                //scale it down
                float factor=0.4f;
                starFish.setBounds(x,y,starFish.getWidth()*factor,starFish.getHeight()*factor);
                //starFish.setOrigin(starFish.getWidth()/2,starFish.getHeight()/2);//take the origin of the modified size starfish
                starFish.updateOrigin();
                if(isStarPlace(starFish)==false||isStarTurtleCollide(starFish,turtle)) {
                    loop=true;
                    starFish.remove();
                }else {
                    loop=false;
                }

            }while(loop);
            starFishList.add(starFish);

            ///spining action for star
            randomInt = 4 + (int) (Math.random() * (8 - 4));

            int spinAmount=20 + (int) (Math.random() * (60 - 20));
            if(i%2==0)
                spin = Actions.rotateBy(spinAmount, randomInt);
            else{
                spin = Actions.rotateBy(-spinAmount, randomInt);
            }

            starFishList.get(i).addAction(Actions.forever(spin));

            winPoints++;
        }

        System.out.println("loop count" + loopCount);
    }

    private  boolean isStarPlace(BaseActor starFish){

        for(int i=0;i<starFishList.size();i++) {
            if(starFish.overlaps(starFishList.get(i))){
                return false;
            }

        }

        return true;
    }

    private boolean isStarTurtleCollide(BaseActor starFish, BaseActor turtle){

        if(starFish.overlaps(turtle)){
            return true;
        }

        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
super.render();//important to draw the stages from the BaseScreen
    }
}
