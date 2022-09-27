package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();
    Rectangle test;

    public MainMenuScreen(final Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 800);

        test = new Rectangle(50, 50, 100, 100);

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
        game.batch.draw(Game.black, test.x, test.y, test.width, test.height);
        game.batch.end();

        if (Gdx.input.isTouched() && test.overlaps(Game.pointer)){
            game.setScreen(new GameScreen(game));
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
