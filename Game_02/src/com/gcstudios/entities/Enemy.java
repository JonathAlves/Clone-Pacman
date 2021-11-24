package com.gcstudios.entities;

import com.gcstudios.main.Game;
import com.gcstudios.world.AStar;
import com.gcstudios.world.Vector2i;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Enemy extends Entity{
	

	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*3;

	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}

	public void tick(){
		depth = 0;
		if (ghostMode == false) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}

			if(new Random().nextInt(100) < 80)
				followPath(path);

			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 10) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}

		}

			ghostFrames++;
			if(ghostFrames == nextTime){

				nextTime = Entity.rand.nextInt(60*3 - 60*1) + 60*3;
				ghostFrames = 0;
				if(ghostMode == false){
					this.setSprite(Entity.ENEMYGHOST);
					ghostMode = true;
				}else if(new Random().nextInt(100) < 20){
					this.setSprite(Entity.ENEMY1);
					ghostMode = false;

				}else if(new Random().nextInt(100) < 20){
					this.setSprite(Entity.ENEMY2);
					ghostMode = false;
				}
			}

	}

	public void render(Graphics g) {
		super.render(g);
	}

	
	
}
