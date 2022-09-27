package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nopo.cardgame.GameField;
import com.nopo.cardgame.cards.Card;

import static com.nopo.cardgame.utils.RenderUtilsKt.renderCards;

public class GameScreen implements Screen {

    final Game game;
    public static GameScreen INSTANCE;
    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();
    static float viewportWidth = 960;
    static float viewportHeight = 960;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    Texture background;

    Card card = new Card("name", 3, 3,3, new Texture(Gdx.files.internal("player.png")));

    public GameScreen(final Game game) {
        this.game = game;
        INSTANCE = this;
        camera = new OrthographicCamera(viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.setToOrtho(false, viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.update();

        background = new Texture(Gdx.files.internal("background.png"));
    }

    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);

        ScreenUtils.clear(0, .2f, .7f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        GameField.placeCard(0, card, GameField.Type.THEIR_CARDS);
        GameField.placeCard(4, card, GameField.Type.YOUR_CARDS);
        game.batch.begin();
        game.batch.draw(background, 0, 250, screenWidth, screenHeight - 250);
        renderCards(game);
        game.batch.end();
        GameField.moveCard(0, 2, GameField.Type.THEIR_CARDS);
        GameField.killCard(2, GameField.Type.THEIR_CARDS);
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
