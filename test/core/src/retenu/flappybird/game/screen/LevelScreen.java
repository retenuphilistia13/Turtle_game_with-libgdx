package retenu.flappybird.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import retenu.flappybird.game.GameState;
import retenu.flappybird.game.actors.BaseActor;
//import retenu.flappybird.game.actors.Controller;
import retenu.flappybird.game.actors.StarFish;
import retenu.flappybird.game.actors.Turtle;

import java.util.ArrayList;
import java.util.Iterator;

import static retenu.flappybird.game.BaseGame.setActiveScreen;

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

///check winning/////
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
        if(g==GameState.AFTERWIN){
            if(!winMessage.hasActions())
                setActiveScreen( new LevelScreen() );
        }
// clear screen
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//// draw graphics



        mainStage.draw();

        uiStage.draw();


    }
//    Controller touchpad;
    @Override
    public void initialize() {

        winPoints=0;
        userPoints=0;

        ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture( "assets/ocean.png" );


//        touchpad=new Controller(20.0f,);
//
//        touchpad.setBounds(Gdx.graphics.getWidth()/22,Gdx.graphics.getWidth()/22,Gdx.graphics.getWidth()/5,Gdx.graphics.getWidth()/5);
//        touchpad.getColor().a=0.70f;
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



        Gdx.input.setInputProcessor(turtle);//mouse input

        turtle.setSize(70,70);

        starFishList=new ArrayList<>();

        starPlacement();

    }

    private void starPlacement(){
        int minX =0;
        int maxX = screenWidth;

        int minY= (int) (turtle.getY()+(turtle.getHeight()*1.1));
        int maxY = screenHeight ;

        int starFishNumber=20;

        int startIndex=0;
//east south
        starFishNumber=5;

        minX = (int) (turtle.getX()+(turtle.getWidth()))*2;
        maxX = screenWidth;
        maxY = (int) (turtle.getY()+turtle.getHeight()) ;
        minY=0;
        startIndex=starFishList.size();//0

        generateStars(minX,maxX,minY,maxY,starFishNumber,startIndex);

        //everywhere else
        minX =0;
        maxX = screenWidth;

        minY= (int) (turtle.getY()+(turtle.getHeight()*1.1));
        maxY = screenHeight ;

        startIndex=starFishList.size();
        starFishNumber=20;

        generateStars(minX,maxX,minY,maxY,starFishNumber,startIndex);


    }

    private void generateStars(int minX,int maxX,int minY,int maxY,int starFishNumber,int startIndex){

        Action spin ;


        int randomInt ;
        boolean loop;

        int loopCount=0;


        for (int i = startIndex; i < starFishNumber; i++) {

            StarFish starFish=new StarFish(); // Create a new StarFish object in each iteration
            float factor=0.4f;
            float width=starFish.getWidth()*factor;
            float height=starFish.getHeight()*factor;

            starFish.setBounds(0,0,width,height);
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

                starFish.setBounds(x,y,width,height);

                //starFish.setOrigin(starFish.getWidth()/2,starFish.getHeight()/2);//take the origin of the modified size starfish
                starFish.updateOrigin();
                if(isStarTurtleCollide(starFish,turtle)||isStarPlace(starFish)==false) {//isStarPlace(starFish)==false||
                    loop=true;
                    starFish.remove();
                }else {
                    loop=false;
                }

            }while(loop);
            starFishList.add(starFish);

            ///spining action for star
            randomInt = 6 + (int) (Math.random() * (12 - 6));

            int spinAmount=50 + (int) (Math.random() * (60 - 50));
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
