package com.feditor;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class InputEditor extends InputAdapter {
	private static final float BODY_CORE_RADIO = 0.2f;
	public Body inTranslation;
	private Vector3 tmp = new Vector3();
	private Vector2 tmp2 = new Vector2();
	private Editor editor;
	private ShapeRenderer shapeRenderer;
	private boolean inRotation;
	private float oldAngle;
	Body bobyCallback;
	
	private QueryCallback queryCallback = new QueryCallback() {
		 

		@Override
		public boolean reportFixture(Fixture fixture) {
			if (fixture.testPoint(tmp.x, tmp.y)) {
				bobyCallback = fixture.getBody();
				return false;
			} else {
				return true;
			}
		}
	};

	public InputEditor(Editor editor) {
		this.editor = editor;
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!editor.play) {
			editor.camera.unproject(tmp.set(screenX, screenY, 0));
			bobyCallback = null;
			editor.world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
			if (bobyCallback==null) {//no hay body en touch pos
				inTranslation=null;  
			} else {
				inTranslation = bobyCallback;
				if (bobyCallback.getPosition().dst( tmp.x, tmp.y)<BODY_CORE_RADIO) {//es centro de body
					inRotation = false;				
				} else {
					inRotation = true;
					oldAngle = (tmp2.set(tmp.x,tmp.y).sub(inTranslation.getPosition())).angle();
				}
//				if (inTranslation==null || inTranslation!=bobyCallback) {
//					inTranslation = bobyCallback;
//					inRotation = false;
//				} else {
//					inRotation = true;
//					oldAngle = (tmp2.set(tmp.x,tmp.y).sub(inTranslation.getPosition())).angle();
//				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (editor.play||inTranslation == null)
			return false;
		editor.camera.unproject(tmp.set(screenX, screenY, 0));
		if (inRotation) {
			float newAngle = (tmp2.set(tmp.x,tmp.y).sub(inTranslation.getPosition())).angle();
			inTranslation.setTransform(inTranslation.getPosition(), (inTranslation.getAngle()+newAngle-oldAngle)*MathUtils.degreesToRadians);
//			oldAngle =newAngle;
		}else {
			inTranslation.setTransform(tmp.x, tmp.y, inTranslation.getAngle());
		}
		return false;
	}

	public void renderSelected(MyCamera camera) {
		if (inTranslation == null)
			return;

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);
		float w = 1.4f;
		float h = .5f;
		
		shapeRenderer.circle(inTranslation.getPosition().x , inTranslation.getPosition().y ,BODY_CORE_RADIO,12);
		if (inRotation) {
			shapeRenderer.circle(inTranslation.getPosition().x , inTranslation.getPosition().y ,Math.max(w, h)/2,12);
		} else {
//			shapeRenderer.rect(inTranslation.getPosition().x - w / 2, inTranslation.getPosition().y - h / 2, w, h, w / 2,
//					h / 2, inTranslation.getAngle() * MathUtils.radiansToDegrees);
		}

		// System.out.println(input.inEdition.getAngle());
		// shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();

	}

	public boolean isInEdition() {
		return inTranslation!=null;
	}

}
