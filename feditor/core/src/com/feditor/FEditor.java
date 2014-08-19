package com.feditor;

import com.badlogic.gdx.Game;

public class FEditor extends Game {

	
	@Override
	public void create() {
		setScreen(new Editor(this));
	}
}
