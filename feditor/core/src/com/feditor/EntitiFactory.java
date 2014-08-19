package com.feditor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EntitiFactory {
	protected static final String FIX = "Fix";
	protected static final String BALL = "Ball";
	protected static final String STICK = "Stick";

	public static void create(World world, String type) {
		switch (type) {
		case FIX:
			createFix(world);
			break;
		case BALL:
			createBall(world);
			break;
		case STICK:
			createStick(world);
			break;
		}
		

	}

	private static void createFix(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0,0);
		Body b2Body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(.5f, .1f);;
		FixtureDef fixt = new FixtureDef();

		fixt.shape = shape;
		
//		fixt.density = 1;
//		fixt.restitution = 1F;
//		fixt.friction = .1f;
		
		b2Body.createFixture(fixt);
		shape.dispose();
	}
	private static void createBall(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type =BodyType.DynamicBody;
		bodyDef.fixedRotation = false;
		Body b2Body = world.createBody(bodyDef);
		
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(.5f);
		FixtureDef fixt = new FixtureDef();
		fixt.shape = ballShape;		
		fixt.density = 1;
		fixt.restitution = .9F;
		fixt.friction = .1f;
		b2Body.createFixture(fixt);
		ballShape.dispose();
	}
	private static void createStick(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0,0);
		bodyDef.type =BodyType.DynamicBody;
//		bodyDef.fixedRotation = true;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(.5f, .1f);;
		Body b2Body = world.createBody(bodyDef);

		FixtureDef fixt = new FixtureDef();
		fixt.shape = shape;
		// fixt.filter.groupIndex = -10;
		fixt.density = 1;

		fixt.restitution = 1F;
		fixt.friction = .1f;
		b2Body.createFixture(fixt);
		shape.dispose();
	}
}
