package com.nopo.cardgame.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static Rectangle pointer;
	static Texture black;
	public BitmapFont font;
	public BitmapFont fontLarge;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontGeneratorParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontGeneratorParam.size = 90;
		fontLarge = fontGenerator.generateFont(fontGeneratorParam);
		fontGenerator.dispose();
		pointer = new Rectangle(-10, -10, 32, 32);
		black = new Texture(Gdx.files.internal("black.png"));
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public static void setUpTouchPos(Vector3 touchPos, OrthographicCamera camera) {
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
	}

	public static void cursorPos(Rectangle pointer, Vector3 touchPos) {
		pointer.x = touchPos.x - 15;
		pointer.y = touchPos.y - 15;
	}

}
