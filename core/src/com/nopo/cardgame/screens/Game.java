package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.nopo.cardgame.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public static Rectangle pointer;
	static Texture black;
	public BitmapFont font;
	public BitmapFont fontLarge;

	Config config;
	File configFile;
	Json json = new Json();

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

		json.setUsePrototypes(false);
		json.setOutputType(JsonWriter.OutputType.json);

		configFile = Gdx.files.internal("config.json").file();

		if (configFile.exists()) {
			try (
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							new FileInputStream(configFile),
							StandardCharsets.UTF_8
					))
			) {
				config = json.fromJson(Config.class, reader);
			} catch (Exception exc) {
				new RuntimeException("Invalid config file. This will reset the config to default", exc).printStackTrace();
			}
		}

		if (config == null) {
			config = new Config();
		}

		try {
			configFile.createNewFile();

			try (
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(configFile),
							StandardCharsets.UTF_8
					))
			) {
				writer.write(json.prettyPrint(config));
			}
		} catch (Exception ignored) {
		}

		saveConfig();

		this.setScreen(new MainMenuScreen(this));
	}

	public void saveConfig() {
		try {
			configFile.createNewFile();

			try (
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(configFile),
							StandardCharsets.UTF_8
					))
			) {
				writer.write(json.prettyPrint(config));
			}
		} catch (Exception ignored) {
		}
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
