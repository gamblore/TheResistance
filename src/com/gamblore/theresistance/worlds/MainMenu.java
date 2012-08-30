package com.gamblore.theresistance.worlds;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.World;
import net.androidpunk.utils.Input;

import android.graphics.Point;

import com.gamblore.theresistance.MainEngine;
import com.gamblore.theresistance.entities.GameStateEntity;
import com.gamblore.theresistance.entities.TextButton;

public class MainMenu extends World {

	private TextButton mAssignRolesButton, mPlaySoundClipButton, mRunMissionButton, mResetGameButton;
	
	private GameStateEntity mGameStateEntity;
	
	public MainMenu() {
		super();
		
		mGameStateEntity = new GameStateEntity();
		
		add(mGameStateEntity);
		
		mAssignRolesButton = new TextButton((int)FP.halfWidth, 100 , "Assign Roles", new Runnable() {
			
			@Override
			public void run() {
				FP.setWorld(new AssignRoles());
			}
		}, false);
		
		add(mAssignRolesButton);
		
		mRunMissionButton = new TextButton((int)FP.halfWidth, 300 , "Run Mission", new Runnable() {
			
			@Override
			public void run() {
				FP.setWorld(new RunMission());
			}
		}, false);
		
		add(mRunMissionButton);
		
		mPlaySoundClipButton = new TextButton((int)FP.halfWidth, 500 , "Play Spy Intro", new Runnable() {
			
			@Override
			public void run() {
				MainEngine.SFXS.get(MainEngine.SFX_SPY_INTRO).play();
			}
		}, false);
		
		add(mPlaySoundClipButton);
		
		mResetGameButton = new TextButton((int)FP.halfWidth, 700 , "Reset Game", new Runnable() {
			
			@Override
			public void run() {
				remove(mGameStateEntity);
				MainEngine.mGameState = null;
				mAssignRolesButton.setColor(0xffffffff);
				mAssignRolesButton.collidable = true;
				mResetGameButton.visible = false;
			}
		}, false);
		mResetGameButton.visible = false;
		
		add(mResetGameButton);
		
		if (MainEngine.mGameState == null) {
			mAssignRolesButton.setColor(0xffffffff);
			mAssignRolesButton.collidable = true;
			mRunMissionButton.setColor(0x44ffffff);
			mRunMissionButton.collidable = false;
		} else {
			mAssignRolesButton.setColor(0x44ffffff);
			mAssignRolesButton.collidable = false;
			mResetGameButton.visible = true;
			if (MainEngine.mGameState.isGameOver()) {
				if (MainEngine.mGameState.doesResistanceWin()) {
					MainEngine.SFXS.get(MainEngine.SFX_WIN_RESISTANCE).play();
				} else {
					MainEngine.SFXS.get(MainEngine.SFX_WIN_SPY).play();
				}
				mRunMissionButton.setColor(0x44ffffff);
				mRunMissionButton.collidable = false;
			}
		}
		
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
