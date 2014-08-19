package com.feditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.feditor.bodys.Background;

public class Editor implements Screen, Level {

	private FEditor game;
	private SpriteBatch batch;
	public MyCamera camera;
	public World world;
	private Box2DDebugRenderer renderer;
	private Stage stage;
	InputEditor inputEditor = new InputEditor(this);
	private Skin skin;
	boolean play;
	private Window winElemSelection;


	public Editor(FEditor game) {
		this.game = game;
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -9.8f), true);
		renderer = new Box2DDebugRenderer(true,true,true,true,true,true);
		batch = new SpriteBatch();
		camera = new MyCamera(this);
		new Background(world);
		createUI();
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, camera, new GestureDetector(camera), inputEditor));
	}

	private void createUI() {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Table table = new Table();
		table.debug();
		stage.addActor(table);
		table.top().left();
		table.setFillParent(true);
		TextButton button = new TextButton(">", skin);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (play) {
					play = false;
					((TextButton) actor).setText(">");
				} else {
					play = true;
					((TextButton) actor).setText("||");
				}
			}
		});
		table.add(button).size(60, 60).pad(5, 5, 5, 5).align(Align.left).expandX();
		button = new TextButton("+", skin);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				winElemSelection.setVisible(true);
			}
		});
		table.row();
		table.add(button).size(60, 60).pad(5, 5, 5, 5).align(Align.left);
		stage.addActor(buildOptionsWindowLayer());
	}
	
	private Table buildOptionsWindowLayer () {
		winElemSelection = new Window("Select item to add", skin);
		addElementButon(winElemSelection, EntitiFactory.FIX);
		addElementButon(winElemSelection, EntitiFactory.BALL);
		addElementButon(winElemSelection, EntitiFactory.STICK);
		winElemSelection.setVisible(false);
//		winElemSelection.debug();
		winElemSelection.pack();
		winElemSelection.setPosition(Gdx.graphics.getWidth()/2-winElemSelection.getWidth()/2, Gdx.graphics.getHeight()/2);
		return winElemSelection;
	}

	private void addElementButon(final Window winElemSelection, final String elementType) {
		TextButton button = new TextButton(elementType, skin);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				EntitiFactory.create(world, elementType);
				winElemSelection.setVisible(false);
			}
		});
		winElemSelection.add(button).size(60, 60).pad(5, 5, 5, 5).align(Align.left);
	}

	@Override
	public void render(float delta) {
		if (play)
			world.step(delta, 8, 3);
		stage.act(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(world, camera.combined);
		inputEditor.renderSelected(camera);
		stage.draw();
		// Table.drawDebug(stage);
	}


	@Override
	public void resize(int width, int height) {
		camera.resize(width, height);
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public float getHeight() {
		return 10;
	}

	@Override
	public float getWidth() {
		return 10;
	}

	@Override
	public boolean isInEdition() {
		return inputEditor.isInEdition();
	}

}
