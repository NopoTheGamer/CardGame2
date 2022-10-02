package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nopo.cardgame.GameField;
import com.nopo.cardgame.cards.Card;
import com.nopo.cardgame.cards.ExamplePlacementCard;

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
    Texture white;
    Rectangle rec;
    public GAME_STATE gameState = GAME_STATE.PLACING;

    Card cardTheir = new ExamplePlacementCard("cardTheir", 1, 3, 3, new Texture(Gdx.files.internal("player.png")));
    Card cardYours = new Card("cardYours", 3, 3, 3, new Texture(Gdx.files.internal("player.png")));
    Card cardDeck = new ExamplePlacementCard("cardDeck", 3, 3, 5, new Texture(Gdx.files.internal("player.png")));
    Card cardDeck2 = new Card("cardDeck2", 3, 3, 5, new Texture(Gdx.files.internal("player.png")));

    public GameScreen(final Game game) {
        this.game = game;
        INSTANCE = this;
        camera = new OrthographicCamera(viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.setToOrtho(false, viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.update();

        background = new Texture(Gdx.files.internal("background.png"));
        white = new Texture(Gdx.files.internal("white.png"));
        rec = new Rectangle(800, 80, white.getWidth(), white.getHeight());
        GameField.placeCard(0, cardTheir, GameField.Type.THEIR_CARDS);
        GameField.placeCard(4, cardYours, GameField.Type.YOUR_CARDS);
        GameField.addToDeck(cardDeck);
        GameField.addToDeck(cardDeck2);
    }

    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);

        ScreenUtils.clear(0, .2f, .7f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        placing();
        startCombat();
        combat();
        endCombat();

        game.batch.begin();
        game.batch.draw(background, 0, 250, screenWidth, screenHeight - 250);
        game.font.draw(game.batch, Integer.toString(GameField.INSTANCE.getEnergy()), 900, 230);
        game.font.draw(game.batch, Integer.toString(GameField.INSTANCE.getTheirHealth()), 50, 750);
        game.font.draw(game.batch, Integer.toString(GameField.INSTANCE.getYourHealth()), 50, 230);
        game.font.draw(game.batch, Integer.toString(gameState.ordinal()), 900, 60);
        nextButton();
        renderCards(game);
        game.batch.end();
    }

    void placing() {
        if (gameState != GAME_STATE.PLACING) return;
    }

    void startCombat() {
        if (gameState != GAME_STATE.START_COMBAT) return;
        GameField.startCombat();
        gameState = GAME_STATE.COMBAT;
    }

    void combat() {
        if (gameState != GAME_STATE.COMBAT) return;
        GameField.combat();
        gameState = GAME_STATE.END_COMBAT;
    }

    void endCombat() {
        if (gameState != GAME_STATE.END_COMBAT) return;
        GameField.nextTurn();
        gameState = GAME_STATE.PLACING;
    }

    void nextButton() {
        game.batch.draw(white, 800, 80, 128, 128);
        if (rec.overlaps(Game.pointer) && Gdx.input.justTouched()) {
            gameState = gameState.next();
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

    public enum GAME_STATE {
        PLACING, START_COMBAT, COMBAT, END_COMBAT;
        GAME_STATE next() {
            return switch (this) {
                case PLACING -> START_COMBAT;
                case START_COMBAT -> COMBAT;
                case COMBAT -> END_COMBAT;
                case END_COMBAT -> PLACING;
            };
        }
    }
}
