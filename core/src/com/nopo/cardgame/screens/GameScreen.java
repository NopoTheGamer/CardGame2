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
import com.nopo.cardgame.cards.FrenzyCard;
import com.nopo.cardgame.cards.WaterCard;

import java.util.ArrayList;
import java.util.List;

import static com.nopo.cardgame.screens.Game.black;
import static com.nopo.cardgame.utils.RenderUtilsKt.renderCards;

public class GameScreen implements Screen {

    public final Game game;
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

    Card cardTheir = new Card("cardTheir", 1, 9, 3, new ArrayList<>(List.of(Card.Ability.FRENZY, Card.Ability.VAMP.setValue(.25f))));
    Card cardYours = new Card("cardYours", 1, 3, 3, new ArrayList<>(List.of(Card.Ability.FRENZY)));
    Card cardDeck = new ExamplePlacementCard("cardDeck", 1, 3, 5);
    Card cardDeck2 = new WaterCard("cardDeck2", 1, 3, 1, new ArrayList<>(List.of(Card.Ability.STRIKETHROUGH)));
    Card cardDeck3 = new FrenzyCard("cardDeck3", 3, 4, 2);

    public GameScreen(final Game game) {
        this.game = game;
        INSTANCE = this;
        camera = new OrthographicCamera(viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.setToOrtho(false, viewportWidth, viewportHeight * (screenHeight / screenWidth));
        camera.update();

        background = new Texture(Gdx.files.internal("background.png"));
        white = new Texture(Gdx.files.internal("white.png"));
        rec = new Rectangle(800, 80, white.getWidth(), white.getHeight());
        ArrayList<GameField.LaneTypes> laneList = new ArrayList<>() {{
            add(GameField.LaneTypes.HEIGHTS);
            add(GameField.LaneTypes.NORMAL);
            add(GameField.LaneTypes.NORMAL);
            add(GameField.LaneTypes.NORMAL);
            add(GameField.LaneTypes.WATER);
        }};
        GameField.setLanes(laneList);
        GameField.placeCard(3, cardTheir, GameField.Type.THEIR_CARDS);
        //GameField.placeCard(1, cardYours, GameField.Type.YOUR_CARDS);
        //GameField.addToDeck(cardDeck);
        //GameField.addToDeck(cardDeck2);
        //GameField.addToDeck(cardDeck3);
        GameField.createPile();
    }

    @Override
    public void render(float delta) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);

        ScreenUtils.clear(0, .2f, .7f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        placing();
        enemyPlacing();
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
        if (GameField.INSTANCE.getTheirHealth() == 0) {
            game.batch.draw(black, 0, 0, 960, 960);
            game.fontLarge.draw(game.batch, "You Win!", 400, 500);
            game.batch.draw(new Texture(Gdx.files.internal("pause_button.png")), 100, 200, 623, 186);
            if (Game.pointer.overlaps(new Rectangle(100, 200, 623, 186)) && Gdx.input.justTouched()) {
                System.exit(0);
            }
        }
        if (GameField.INSTANCE.getYourHealth() == 0) {
            game.batch.draw(black, 0, 0, 960, 960);
            game.fontLarge.draw(game.batch, "You Lose!", 400, 500);
            game.batch.draw(new Texture(Gdx.files.internal("pause_button.png")), 100, 200, 623, 186);
            if (Game.pointer.overlaps(new Rectangle(100, 200, 623, 186)) && Gdx.input.justTouched()) {
                System.exit(0);
            }
        }
        game.batch.end();
        //LanePicker.setChance(1, Random.Default.nextFloat());
    }

    void placing() {
        if (gameState != GAME_STATE.PLACING) return;
    }

    void enemyPlacing() {
        if (gameState != GAME_STATE.ENEMY_PLACING) return;
        GameField.doEnemyPlacing();
        gameState = gameState.next();
    }

    void startCombat() {
        if (gameState != GAME_STATE.START_COMBAT) return;
        GameField.startCombat();
        gameState = gameState.next();
    }

    void combat() {
        if (gameState != GAME_STATE.COMBAT) return;
        GameField.combat();
        gameState = gameState.next();
    }

    void endCombat() {
        if (gameState != GAME_STATE.END_COMBAT) return;
        GameField.nextTurn();
        gameState = gameState.next();
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
        PLACING, ENEMY_PLACING, START_COMBAT, COMBAT, END_COMBAT;
        GAME_STATE next() {
            return switch (this) {
                case PLACING -> ENEMY_PLACING;
                case ENEMY_PLACING -> START_COMBAT;
                case START_COMBAT -> COMBAT;
                case COMBAT -> END_COMBAT;
                case END_COMBAT -> PLACING;
            };
        }
    }
}
