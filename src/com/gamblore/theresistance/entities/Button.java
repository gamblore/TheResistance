package com.gamblore.theresistance.entities;

import net.androidpunk.Entity;

public class Button extends Entity {

	private Runnable mOnClick;
	
	public void setOnClick(Runnable onClick) {
		mOnClick = onClick;
	}
	
	public void onClick() {
		if (mOnClick != null) {
			mOnClick.run();
		}
	}
}
