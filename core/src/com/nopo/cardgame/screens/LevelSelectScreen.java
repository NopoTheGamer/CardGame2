package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelSelectScreen implements Screen {

    final Game game;

    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();
    Rectangle level1;



    public LevelSelectScreen(final Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 800);

        level1 = new Rectangle(100, 500, 128, 128);

    }
    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);


        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.fontLarge.draw(game.batch, "Level Select", 250, 750);
        game.fontLarge.draw(game.batch, "1", level1.x, level1.y + 128);
        game.batch.end();

        if (Gdx.input.isTouched() && level1.overlaps(Game.pointer)) {
            if (!game.config.hasPlayedBefore) {
                game.setScreen(new TutorialScreen(game));
            } else {
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {

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
