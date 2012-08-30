package com.gamblore.theresistance.entities;

import javax.crypto.spec.PSource;

import net.androidpunk.FP;
import net.androidpunk.graphics.atlas.AtlasText;

import com.gamblore.theresistance.MainEngine;

public class TextButton extends Button {

	public static final String TYPE = "TextButton";
	
	private AtlasText mTextGraphic;
	
	public static final int POSITION_LEFT = 1;
	public static final int POSITION_CENTER = 2;
	public static final int POSITION_RIGHT = 3;
	public static final int POSITION_TOP = 1;
	public static final int POSITION_BOTTOM = 3;
	
	private int mPosX, mPosY; 
	
	public TextButton(int positionX, int positionY, String text, Runnable onClick) {
		this(positionX, positionY, text, onClick, true);
	}
	
	public TextButton(int positionX, int positionY, String text, Runnable onClick, boolean useConstantPositions) {
		super();
		
		mPosX = positionX;
		mPosY = positionY;
		
		mTextGraphic = new AtlasText(text, 24, MainEngine.mTypeface); 
		mTextGraphic.x = 5;
		mTextGraphic.y = 5;
		setGraphic(mTextGraphic);
		
		if (useConstantPositions) {
			positionEntity();
		} else {
			x = positionX - mTextGraphic.getWidth()/2;
			
			y = positionY - mTextGraphic.getHeight()/2;
		}
		
		
		setHitbox(mTextGraphic.getWidth() + 10, mTextGraphic.getHeight() + 10);
		setType(TYPE);
		
		setOnClick(onClick);
	}
	
	private void positionEntity() {
		switch(mPosX) {
		case POSITION_LEFT:
			x = 5;
			break;
		case POSITION_CENTER:
			x = FP.screen.getWidth()/2 - mTextGraphic.getWidth()/2;
			break;
		case POSITION_RIGHT:
			x = FP.screen.getWidth() - mTextGraphic.getWidth() - 5;
			break;
		}
		
		switch(mPosY) {
		case POSITION_TOP:
			y = 5;
			break;
		case POSITION_CENTER:
			y =  FP.screen.getHeight()/2 - mTextGraphic.getHeight()/2;
			break;
		case POSITION_BOTTOM:
			y = FP.screen.getHeight() - mTextGraphic.getHeight() - 5;
			break;
		}
	}
	
	public void setColor(int color) {
		mTextGraphic.setColor(color);
	}
	
	public void setText(String text) {
		mTextGraphic.setText(text);
		
		positionEntity();
		setHitbox(mTextGraphic.getWidth() + 10, mTextGraphic.getHeight() + 10);
	}
}
