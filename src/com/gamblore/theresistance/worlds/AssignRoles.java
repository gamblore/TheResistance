package com.gamblore.theresistance.worlds;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.World;
import net.androidpunk.graphics.atlas.AtlasText;
import net.androidpunk.utils.Input;
import android.graphics.Point;

import com.gamblore.theresistance.entities.TextButton;

public class AssignRoles extends World {

	public static final int[] SPIES_PER_PLAYERS = new int[] { 2, 2, 3, 3, 3, 4 };
	
	public static final int MIN_PLAYERS = 5;
	public static final int MAX_PLAYERS = 10;
	
	private int mPlayers;
	
	private TextButton goButton, backButton, infoButton;
	
	public AssignRoles() {
		super();
		
		mPlayers = 5;
		
		infoButton = new TextButton(TextButton.POSITION_CENTER, TextButton.POSITION_CENTER, String.format("%d Players", mPlayers), new Runnable() {
			
			@Override
			public void run() {
				mPlayers++;
				if (mPlayers > MAX_PLAYERS) {
					mPlayers = MIN_PLAYERS;
				}
				infoButton.setText(String.format("%d Players", mPlayers));
			}
		});
		
		add(infoButton);
		
		goButton = new TextButton(TextButton.POSITION_RIGHT, TextButton.POSITION_BOTTOM, "Go", new Runnable() {
			
			@Override
			public void run() {
				FP.setWorld(new ShowRoles(mPlayers));
			}
		});
		goButton.y -= 15;
		add(goButton);
		
		
		backButton = new TextButton(TextButton.POSITION_LEFT, TextButton.POSITION_BOTTOM, "Back", new Runnable() {
			
			@Override
			public void run() {
				FP.setWorld(new MainMenu());
				
			}
		});
		backButton.y -= 15;
		add(backButton);
		
	}
	
	@Override
	public void update() {
		super.update();
		
		if (Input.mousePressed) {
			Point p = Input.getTouches()[0];
			
			Entity e;
			if ((e = collidePoint(TextButton.TYPE, p.x, p.y)) != null) {
				TextButton tb = (TextButton)e;
				tb.onClick();
			}
		}
	}
}
