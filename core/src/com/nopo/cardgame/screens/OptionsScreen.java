package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class OptionsScreen implements Screen {

    final Game game;
    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();
    Rectangle backButton;

    public OptionsScreen(final Game game) {

        //https://libgdx.com/wiki/utils/reading-and-writing-json
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 800);

        backButton = new Rectangle(20, 20, 150, 153);
    }

    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);

        ScreenUtils.clear(0.2f, 0.76f, 0.41f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("back_button.png")), backButton.x, backButton.y, backButton.width, backButton.height); //TODO: change file thingy
        game.batch.end();

        if (Gdx.input.isTouched() && backButton.overlaps(Game.pointer)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void show() {

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

    @Override
    public void dispose() {

    }
}
