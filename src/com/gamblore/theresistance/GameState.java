package com.gamblore.theresistance;

import java.util.Set;

public class GameState {

	public static final int WIN_RESISTANCE = 1;
	public static final int WIN_SPY = 2;
	
	private int mPlayers;
	private Set<Integer> mSpies;
	
	private int[] mRounds = new int[5];
	
	private int mCurrentRound;
	
	public GameState(int players) {
		mPlayers = players;
		for (int i = 0; i < 5; i++) {
			mRounds[i] = 0;
		}
		mCurrentRound = 0;
	}
	
	public void setSpies(Set<Integer> spies) {
		mSpies = spies;
	}
	
	public Set<Integer> getSpies() {
		return mSpies;
	}
	
	public int getPlayers() {
		return mPlayers;
	}
	
	public int getCurrentRound() {
		return mCurrentRound;
	}
	
	public int[] getRounds() {
		return mRounds;
	}
	
	public void addWin(int whoWon) {
		mRounds[mCurrentRound++] = whoWon;
	}
	
	public boolean isGameOver() {
		int spys = 0, resistance = 0;
		for (int i = 0; i < mRounds.length; i++) {
			switch(mRounds[i]) {
			case WIN_RESISTANCE:
				resistance++;
				break;
				
			case WIN_SPY:
				spys++;
				break;
				
			default:
				
			}
			
		}
		if (spys >= 3 || resistance >= 3) {
			return true;
		}
		return false;
	}
	
	public boolean doesResistanceWin() {
		int count = 0;
		for (int i = 0; i < mRounds.length; i++) {
			if (mRounds[i] == WIN_RESISTANCE) {
				count++;
			}
		}
		return count >= 3;
	}
}
