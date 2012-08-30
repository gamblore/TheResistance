package com.gamblore.theresistance.worlds;

import java.util.HashSet;
import java.util.Set;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.World;
import net.androidpunk.graphics.atlas.AtlasText;
import net.androidpunk.graphics.atlas.GraphicList;
import net.androidpunk.graphics.atlas.Image;
import net.androidpunk.utils.Input;
import android.util.Log;

import com.gamblore.theresistance.GameState;
import com.gamblore.theresistance.MainEngine;

public class ShowRoles extends World {

	private static final String TAG = "ShowRoles";
	
	private int mPlayers, mNumberSpies; 
	
	private Set<Integer> mSpyIndices;
	private Image mSpy, mResistance;
	private AtlasText mContinue, mTimeoutText;
	
	private int mCurrentDisplayedPlayer = -1;
	
	private static final float TIMEOUT = 2.0f;
	//private static final float TIMEOUT = 0.1f;
	private float mTimeout = TIMEOUT;
	
	public ShowRoles(int players) {
		mPlayers = players;
		
		Entity things = new Entity();
		
		mContinue = new AtlasText("Tap to continue", 24, MainEngine.mTypeface);
		mContinue.x = FP.halfWidth - mContinue.getWidth()/2;
		mContinue.y = FP.halfHeight - mContinue.getHeight()/2;
		mContinue.visible = false;
		
		mTimeoutText = new AtlasText(String.format("%1.2f", mTimeout), 24, MainEngine.mTypeface);
		mTimeoutText.x = FP.halfWidth - mTimeoutText.getWidth()/2;
		mTimeoutText.visible = false;
		
		mSpy = new Image(MainEngine.mAtlas.getSubTexture("spy"));
		mSpy.visible = false;
		mSpy.x = FP.halfWidth - mSpy.getWidth()/2;
		mSpy.y = FP.halfHeight - mSpy.getHeight()/2;
		
		mResistance = new Image(MainEngine.mAtlas.getSubTexture("resistance"));
		mResistance.visible = false;
		mResistance.x = FP.halfWidth - mResistance.getWidth()/2;
		mResistance.y = FP.halfHeight - mResistance.getHeight()/2;

		things.setGraphic(new GraphicList(mContinue, mTimeoutText, mSpy, mResistance));
		
		add(things);
		
		mNumberSpies = AssignRoles.SPIES_PER_PLAYERS[mPlayers-5];
		mSpyIndices = new HashSet<Integer>(mNumberSpies);
		
		int spysChoosen = 0;
		while (spysChoosen < mNumberSpies) {
			int playerIndex = FP.rand(mPlayers);
			if (mSpyIndices.contains(playerIndex)) {
				continue;
			}
			mSpyIndices.add(playerIndex);
			spysChoosen++;
		}
		
		String s = "";
		for (Integer i : mSpyIndices) {
			s += i + " ";
		}
		
		Log.d(TAG, "Spies are " + s);
		
		MainEngine.mGameState = new GameState(players);
		MainEngine.mGameState.setSpies(mSpyIndices);
		
		displayTapToContinue();
	}
	
	private void displayTapToContinue() {
		mSpy.visible = false;
		mResistance.visible = false;
		
		mContinue.visible = true;
		mTimeoutText.visible = false;
	}
	
	private void displayRole(int playerIndex) {
		mContinue.visible = false;
		mTimeoutText.visible = true;
		if (mSpyIndices.contains(playerIndex)) {
			mResistance.visible = false;
			mSpy.visible = true;
		} else {
			mResistance.visible = true;
			mSpy.visible = false;
		}
		mTimeout = TIMEOUT;
			
	}
	
	@Override
	public void update() {
		super.update();
		
		if (mTimeoutText.visible) {
			if (mTimeout > 0) {
				mTimeout -= FP.elapsed;
				if (mTimeout < 0) {
					mTimeout = 0f;
				}
				mTimeoutText.setText(String.format("%1.2f", mTimeout));
				
			} else {
				if (mCurrentDisplayedPlayer < mPlayers-1) {
					displayTapToContinue();
				} else {
					FP.setWorld(new MainMenu());
				}
			}
		}
		
		if (Input.mousePressed) {
			if (mContinue.visible) {
				 mCurrentDisplayedPlayer++;
				 
				 displayRole(mCurrentDisplayedPlayer); 
			 }
		}
	}
}
