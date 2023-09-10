package com.nopo.cardgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen {

    final Game game;

    OrthographicCamera camera;
    Vector3 touchPos = new Vector3();

    Rectangle nextButton;

    Texture deck;
    Texture field;
    int page = 0;

    public TutorialScreen(final Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 800);

        nextButton = new Rectangle(850, -28, 128, 128);
        deck = new Texture(Gdx.files.internal("deck.png"));
        field = new Texture(Gdx.files.internal("field.png"));

    }

    @Override
    public void render(float v) {
        Game.setUpTouchPos(touchPos, camera);
        Game.cursorPos(Game.pointer, touchPos);


        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.fontLarge.draw(game.batch, "Tutorial", 250, 750);
        game.fontLarge.draw(game.batch, ">", nextButton.x, nextButton.y + 128);
        if (page == 0) {
            game.font.draw(game.batch, "In this game you control your sides cards and the field to place them on.", 50, 650);
            game.batch.draw(deck, 0, 370, 962, 245);
            game.font.draw(game.batch, "The image above is your deck", 50, 365);
            game.font.draw(game.batch, "Circled in red you have a troop, you drag these onto the field above and they attack for you", 50, 340);
            game.font.draw(game.batch, "Circled in green you have the next round button", 50, 325);
            game.font.draw(game.batch, "Circled in black you have your current mana amount, this goes up every round by 1", 50, 310);
            game.font.draw(game.batch, "Circled in yellow you have your current health points", 50, 295);
            game.font.draw(game.batch, "Circled in orange you have a troops mana cost", 50, 280);
            game.font.draw(game.batch, "Circled in blue you have a troops health points", 50, 265);
            game.font.draw(game.batch, "Circled in white you have a troops attack points", 50, 250);
        } else if (page == 1) {
            game.batch.draw(field, 0, 370, 958, 268);
            game.font.draw(game.batch, "The image above is the game field", 50, 365);
            game.font.draw(game.batch, "Circled in red you have a troops health points, the text colour is red showing that the troop has been hurt", 50, 340);
            game.font.draw(game.batch, "Circled in pink you have a troops health points, the text colour is green showing that the troop has gained health", 50, 325);
            game.font.draw(game.batch, "Circled in blue you have your opponents troop", 50, 310);
            game.font.draw(game.batch, "Circled in black you have your opponents health points", 50, 295);
        } else if (page == 2) {
            game.fontLarge.draw(game.batch, "Tips", 250, 660);
            game.font.draw(game.batch, "Hover over a card in your deck and click spacebar to see what that card does", 50, 550);
        }
        game.batch.end();

        if (Gdx.input.justTouched() && nextButton.overlaps(Game.pointer)) {
            page++;
            if (page == 3) {
                game.config.hasPlayedBefore = true;
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
