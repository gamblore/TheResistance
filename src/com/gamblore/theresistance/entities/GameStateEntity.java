package com.gamblore.theresistance.entities;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.graphics.atlas.AtlasText;
import net.androidpunk.graphics.atlas.GraphicList;
import net.androidpunk.graphics.atlas.Image;

import com.gamblore.theresistance.GameState;
import com.gamblore.theresistance.MainEngine;
import com.gamblore.theresistance.worlds.RunMission;

public class GameStateEntity extends Entity {

	private Image[] mSpies = new Image[5];
	private Image[] mResistance = new Image[5];
	
	//Displays how many on next mission.
	private AtlasText mInfoText;
	
	public GameStateEntity() {
		super();
		
		for (int i = 0; i < mSpies.length; i++) {
			int x = i*(FP.width/mSpies.length);
			mSpies[i] = new Image(MainEngine.mAtlas.getSubTexture("failure"));
			mSpies[i].x = x;
			mSpies[i].visible = false;
			
			mResistance[i] = new Image(MainEngine.mAtlas.getSubTexture("success"));
			mResistance[i].x = x;
			mResistance[i].visible = false;
		}
		
		mInfoText = new AtlasText("a", 16, MainEngine.mTypeface);
		
		mInfoText.x = 0;
		mInfoText.y = FP.screen.getHeight() - mInfoText.getHeight();
		GraphicList gl = new GraphicList(mInfoText);
		for (int i = 0; i < mSpies.length; i++) {
			gl.add(mSpies[i]);
			gl.add(mResistance[i]);
		}
		setGraphic(gl);
		
		refreshInfo();
	}
	
	public void refreshInfo() {
		if (MainEngine.mGameState != null) {
			GameState gs = MainEngine.mGameState;	
			int[] state = gs.getRounds();
			for (int i = 0; i < state.length; i++) {
				switch(state[i]) {
				case GameState.WIN_SPY:
					mSpies[i].visible = true;
					mResistance[i].visible = false;
					break;
					
				case GameState.WIN_RESISTANCE:
					mSpies[i].visible = false;
					mResistance[i].visible = true;
					break;
					
				default:
					mSpies[i].visible = false;
					mResistance[i].visible = false;
				}
			}
			if (gs.isGameOver()) {
				if (gs.doesResistanceWin()) {
					mInfoText.setText("Resistance Win");
				} else {
					mInfoText.setText("Spies Win");
				}
			} else {
				if (gs.getPlayers() > 6 && gs.getCurrentRound() == 3) {
					mInfoText.setText(String.format("%d members (2 failures)", RunMission.MEMBERS_PER_STAGE[gs.getPlayers()-5][gs.getCurrentRound()]));
				} else {
					mInfoText.setText(String.format("%d members on next mission", RunMission.MEMBERS_PER_STAGE[gs.getPlayers()-5][gs.getCurrentRound()]));
				}
			}
			mInfoText.x = FP.halfWidth - mInfoText.getWidth()/2;
		} else {
			for (int i = 0; i < mSpies.length; i++) {
				mSpies[i].visible = false;
				mResistance[i].visible = false;
				mInfoText.visible = false;
			}
		}
	}
}
