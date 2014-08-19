package com.feditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MyCamera extends OrthographicCamera implements InputProcessor, GestureListener {

	private static final float VIEWPORT_WIDTH = 20;
	private Level level;
	private float initialScale = 1;// ZOOM

	public MyCamera(Level level) {
		this.level = level;
	}

	public void resize(int width, int height) {
		viewportWidth = VIEWPORT_WIDTH;
		viewportHeight = VIEWPORT_WIDTH * height / width;
		// if (viewportHeight > level.getHeight()) {
		// viewportWidth = level.getHeight() * width / height;
		// viewportHeight = level.getHeight();
		// }
		update();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		zoom += amount * .1f;

		// validateZoomAndPosition();
		update();
		return true;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (!level.isInEdition()) {
			float cameraToWorld = level.getWidth() / Gdx.graphics.getWidth();
			float camX = position.x - deltaX * cameraToWorld;
			float camY = position.y + deltaY * cameraToWorld;
			position.set(camX, camY, 0);
			update();
		}

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		float ratio = initialDistance / distance;
		zoom = initialScale * ratio;
//		validateZoomAndPosition();
		update();
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
