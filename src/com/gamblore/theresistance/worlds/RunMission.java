package com.gamblore.theresistance.worlds;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.World;
import net.androidpunk.graphics.atlas.AtlasText;
import net.androidpunk.graphics.atlas.GraphicList;
import net.androidpunk.utils.Input;
import android.graphics.Point;
import android.util.Log;

import com.gamblore.theresistance.GameState;
import com.gamblore.theresistance.MainEngine;
import com.gamblore.theresistance.entities.GameStateEntity;
import com.gamblore.theresistance.entities.VoterEntity;

public class RunMission extends World {

	private static final String TAG = "RunMission";
	
	public static final int[][] MEMBERS_PER_STAGE = new int[][] { new int[] { 2, 3, 2, 3, 3 }, // 5
		new int[] { 2, 3, 4, 3, 4 }, // 6
		new int[] { 2, 3, 3, 4, 4 }, // 7
		new int[] { 3, 4, 4, 5, 5 }, // 8
		new int[] { 3, 4, 4, 5, 5 }, // 9
		new int[] { 3, 4, 4, 5, 5 } // 10 
	};
	
	private AtlasText mResultText, mTapToContinue;
	
	private GameStateEntity mGameStateEntity;
	
	private VoterEntity[] mVoterEntities = new VoterEntity[4];
	
	private int mFailVotes;
	private int mCurrentVote;
	private int mTotalVotes;
	
	public RunMission() {
		super();
		
		GameState gs = MainEngine.mGameState;
		
		mGameStateEntity = new GameStateEntity();
		
		add(mGameStateEntity);
		
		mTotalVotes = MEMBERS_PER_STAGE[gs.getPlayers()-5][gs.getCurrentRound()];
		mCurrentVote = 0;
		mFailVotes = 0;
		
		Entity e = new Entity();
		
		mTapToContinue = new AtlasText("Tap to Continue", 24, MainEngine.mTypeface);
		mTapToContinue.x = FP.halfWidth - mTapToContinue.getWidth()/2;
		mTapToContinue.y = FP.halfHeight - mTapToContinue.getHeight()/2;
		
		
		mResultText = new AtlasText("", 16, MainEngine.mTypeface);
		mResultText.visible = false;
		
		e.setGraphic(new GraphicList(mResultText, mTapToContinue));
		
		add(e);
		
		for (int i = 0; i < mVoterEntities.length; i++ ) {
			mVoterEntities[i] = new VoterEntity(true);
			mVoterEntities[i].x = (int)((FP.halfWidth/2 + (i % 2) * FP.halfWidth) - mVoterEntities[i].width/2);
			mVoterEntities[i].y = (int)((FP.halfHeight/2 + (i / 2) * FP.halfHeight) - mVoterEntities[i].height/2);
			mVoterEntities[i].visible = false;
			mVoterEntities[i].collidable = false;
			add(mVoterEntities[i]);
		}
		
		shuffle();
	}
	
	private void showTapToContinue() {
		mTapToContinue.visible = true;
		for (int i = 0; i < mVoterEntities.length; i++) {
			mVoterEntities[i].visible = false;
			mVoterEntities[i].collidable = false;
		}
	}
	
	private void showVoteOptions() {
		mTapToContinue.visible = false;
		shuffle();
		for (int i = 0; i < mVoterEntities.length; i++ ) {
			mVoterEntities[i].visible = true;
			mVoterEntities[i].collidable = true;
		}
	}
	
	private void showFailures(int fails) {
		GameState gs = MainEngine.mGameState;
		boolean spiesWin = false;

		if (gs.getPlayers() < 7 && fails > 0) {
			spiesWin = true;
		} else if (gs.getPlayers() >= 7) {
			if (gs.getCurrentRound() == 3 && fails > 1) {
				spiesWin = true;
			} else if (gs.getCurrentRound() != 3 && fails > 0) {
				spiesWin = true;
			}
		}
		if (fails > 0) {
			MainEngine.SFXS.get(MainEngine.SFX_FAILURE[fails-1]).play();
		} else {
			MainEngine.SFXS.get(MainEngine.SFX_SUCCESS).play();
		}
		if (spiesWin) {
			mResultText.setText(String.format("%d failure Spies win a round.", fails));
		} else {
			mResultText.setText(String.format("Resistance win a round."));
		}
		mResultText.x = FP.halfWidth - mResultText.getWidth()/2;
		mResultText.y = FP.halfHeight/2 - mResultText.getHeight()/2;
		mResultText.visible = true;
		
		mTapToContinue.visible = true;
		
		gs.addWin((spiesWin) ? GameState.WIN_SPY : GameState.WIN_RESISTANCE);
	}

	private void shuffle() {
		int failPos = FP.rand(4);
		
		for (int i = 0; i < mVoterEntities.length; i++) {
			mVoterEntities[i].setSuccess(i != failPos);
		}
	}

	
	@Override
	public void update() {
		super.update();
		
		if (Input.mousePressed) {
			if (mResultText.visible) {
				FP.setWorld(new MainMenu());
				return;
			}
			
			if (mTapToContinue.visible) {
				if (mCurrentVote == mTotalVotes) {
					showFailures(mFailVotes);
				} else {
					showVoteOptions();
				}
			} else {
				Point p = Input.getTouches()[0];
				Entity e;
				if (( e = collidePoint(VoterEntity.TYPE, p.x, p.y)) != null) {
					VoterEntity ve = (VoterEntity)e;
					if (!ve.isSuccess()) {
						mFailVotes++;
					}
					mCurrentVote++;
					Log.d(TAG, "On to " + mCurrentVote);
					showTapToContinue();
				}
				
			}
		}
	}
	
	
}
