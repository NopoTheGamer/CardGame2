package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();
    Rectangle startButton;
    Rectangle optionsButton;
    Rectangle pauseButton;
    Texture startTexture = new Texture(Gdx.files.internal("start_button.png"));
    Texture optionsTexture = new Texture(Gdx.files.internal("options_button.png"));
    Texture pauseTexture = new Texture(Gdx.files.internal("pause_button.png"));

    public MainMenuScreen(final Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 800);

        startButton = new Rectangle(200, 400, 623, 186);
        optionsButton = new Rectangle(200, 200, 623, 186);
        pauseButton = new Rectangle(200, 0, 623, 186);

    }

    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);

        //System.out.println(CardGame.pointer.x + " " + CardGame.pointer.y);

        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(startTexture, startButton.x, startButton.y, startButton.width, startButton.height);
        game.batch.draw(optionsTexture, optionsButton.x, optionsButton.y, optionsButton.width, optionsButton.height);
        game.batch.draw(pauseTexture, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
        game.fontLarge.draw(game.batch, "Card Game", 300, 750);
        game.batch.end();

        if (Gdx.input.isTouched() && startButton.overlaps(Game.pointer)) {
            game.setScreen(new LevelSelectScreen(game));
        }

        if (Gdx.input.isTouched() && optionsButton.overlaps(Game.pointer)) {
            game.setScreen(new OptionsScreen(game));
        }

        if (Gdx.input.isTouched() && pauseButton.overlaps(Game.pointer)) {
            game.saveConfig();
            throw new Error("Quit game");
        }

    }

    @Override
    public void show() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
