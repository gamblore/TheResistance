package com.gamblore.theresistance;

import java.util.HashMap;
import java.util.Map;

import net.androidpunk.Engine;
import net.androidpunk.FP;
import net.androidpunk.R;
import net.androidpunk.Sfx;
import net.androidpunk.graphics.opengl.Atlas;
import net.androidpunk.graphics.opengl.TextAtlas;
import android.graphics.Typeface;

import com.gamblore.theresistance.worlds.MainMenu;

public class MainEngine extends Engine {
	
	public static final String TAG = "MainEngine";
	public static Typeface mTypeface;
	
	public static Atlas mAtlas;
	
	public static GameState mGameState;
	
	public static final Map<String, Sfx> SFXS = new HashMap<String, Sfx>();
	
	public static final String SFX_SPY_INTRO = "spy_intro";
	public static final String SFX_WIN_SPY = "spy_win";
	public static final String SFX_WIN_RESISTANCE = "res_win";
	public static final String SFX_SUCCESS = "success";
	public static final String[] SFX_FAILURE = new String[] {"one_fail", "two_fail", "three_fail", "four_fail"};
	
	public static final String SFX_VICTORY = "victory";
	
	public MainEngine(int width, int height, float frameRate, boolean fixed) {
		super(width, height, frameRate, fixed);
		
		mAtlas = new Atlas("textures/atlas.xml");
		mTypeface = TextAtlas.getFontFromRes(R.raw.font_fixed_bold);
		
		SFXS.put(SFX_SPY_INTRO, new Sfx(R.raw.spy_intro));
		SFXS.put(SFX_WIN_SPY, new Sfx(R.raw.spy_win));
		SFXS.put(SFX_WIN_RESISTANCE, new Sfx(R.raw.res_win));
		SFXS.put(SFX_SUCCESS, new Sfx(R.raw.success));
		SFXS.put(SFX_FAILURE[0], new Sfx(R.raw.one_failure));
		SFXS.put(SFX_FAILURE[1], new Sfx(R.raw.two_failure));
		SFXS.put(SFX_FAILURE[2], new Sfx(R.raw.three_failure));
		SFXS.put(SFX_FAILURE[3], new Sfx(R.raw.four_failure));
		
		//Log.d(TAG, String.format("Won? %b", PLAYER.hasWonGame()));
		//FP.setWorld(new WinMenu(25, 19));
		//FP.setWorld(new StoryWorld(R.string.intro_story));
		FP.setWorld(new MainMenu());
		//FP.setWorld(new ShowRoles(5));
		//FP.setWorld(new AssignRoles());
		
		
		
		
	}
	 
}
