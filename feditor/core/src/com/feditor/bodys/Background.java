package com.feditor.bodys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Background {
	private Body b2Body;

	public Background(World world) {

		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		b2Body = world.createBody(bd);	
		PolygonShape shape = new PolygonShape();
			
		FixtureDef fd = new FixtureDef();
//		fd.density = 1;
		fd.friction = 1f;
//		fd.restitution = 0.5f;
		fd.shape = shape;

		shape.setAsBox(10, 1,new Vector2(0, -8),0);	
		b2Body.createFixture(fd);
		shape.setAsBox(10, 1,new Vector2(0, 8),0);	
		b2Body.createFixture(fd);
		shape.setAsBox(1, 10,new Vector2(-10,0),0);	
		b2Body.createFixture(fd);
		shape.setAsBox(1, 10,new Vector2(10,0),0);	
		b2Body.createFixture(fd);
		
		
		shape.dispose();
	}
}
