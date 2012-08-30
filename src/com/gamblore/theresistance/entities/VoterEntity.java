package com.gamblore.theresistance.entities;

import com.gamblore.theresistance.MainEngine;

import net.androidpunk.Entity;
import net.androidpunk.FP;
import net.androidpunk.graphics.atlas.Image;

public class VoterEntity extends Entity {

	public static final String TYPE = "VoterEntity";
	
	private Image mSuccessImage, mFailureImage;
	private boolean mSuccess;
	
	public VoterEntity(boolean success) {
		super();
		
		mSuccess = success;
		
		mSuccessImage = new Image(MainEngine.mAtlas.getSubTexture("success"));
		mFailureImage = new Image(MainEngine.mAtlas.getSubTexture("failure"));
		
		if (mSuccess) {
			setGraphic(mSuccessImage);
		} else {
			setGraphic(mFailureImage);
		}
		
		setHitbox(mSuccessImage.getWidth(), mSuccessImage.getHeight());
		setType(TYPE);
	}
	
	public void setSuccess(boolean success) {
		mSuccess = success;
		if (mSuccess) {
			setGraphic(mSuccessImage);
		} else {
			setGraphic(mFailureImage);
		}
	}
	
	public boolean isSuccess() {
		return mSuccess;
	}
	
}
