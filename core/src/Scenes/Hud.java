package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sprites.Player;

public class Hud {
	public Stage stage;
	public Viewport viewport;
	
	private Integer worldTimer;
	private float timeCount;
	private Integer score;
	
	Label countUpLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label playerLabel;
	Label worldLabel;
	
	public Hud(SpriteBatch sb){
		worldTimer = 0;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport (Player.V_WIDTH, Player.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setPosition(0, 90);
		table.setFillParent(true);
		
		countUpLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel =  new Label("TIME: ",new Label.LabelStyle(new BitmapFont(), Color.WHITE));		
		levelLabel =  new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));		
		worldLabel =  new Label("WORLD",new Label.LabelStyle(new BitmapFont(), Color.WHITE));		
		playerLabel = new Label("SCORE",new Label.LabelStyle(new BitmapFont(), Color.WHITE));		
		
		//table.add(playerLabel).expandX().padTop(0);
		//table.add(scoreLabel).expandX();
	//	table.add(worldLabel).expandX().padTop(10);
	//table.add(levelLabel).expandX();
		table.add(timeLabel).expandX().padTop(0);
		table.add(countUpLabel).expand();
		//table.row();
		
		
		
		
		stage.addActor(table);
	}
	
	public void update(float dt){
		timeCount+= dt;
		if(timeCount>1){
			worldTimer++;
			countUpLabel.setText(String.format("%03d",worldTimer));
			timeCount = 0;
		}
	}
}














