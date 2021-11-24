package com.gcstudios.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

import static com.gcstudios.main.Game.*;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int lastDir = 1;
	public BufferedImage sprite_left;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		sprite_left = Game.spritesheet.getSprite(47, 1, 15, 15);
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = -1;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
		}
		verificaPegaFruta();
		verificaColisaoInimigo();

		if(Game.frutas_contagem == Game.frutas_atual){
			Game.gamestate = Gamestate.WIN;
			Sound.WinEffect.play();
			Sound.musicBackground.clip.stop();
		}

	}

	public void render(Graphics g){
		if(lastDir == 1){
			super.render(g);
		}else{
			g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(gamestate.equals(Gamestate.WIN)){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,255));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 14));
			g.setColor(Color.white);
			g.drawString("Parabens! Voce venceu!", (WIDTH*SCALE)/450+40, (HEIGHT*SCALE)/5);
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.drawString("> Pressione Enter para reiniciar <", (WIDTH*SCALE)/10, (HEIGHT*SCALE)/4);
		}else if(gamestate.equals(Gamestate.GAMEOVER)){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,255));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 14));
			g.setColor(Color.white);
			g.drawString("GAME OVER!", (WIDTH*SCALE)/450+73, (HEIGHT*SCALE)/5);
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.drawString("> Pressione Enter para reiniciar <", (WIDTH*SCALE)/10, (HEIGHT*SCALE)/4);
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.drawString("> Pressione Backspace para ir ao menu <", (WIDTH*SCALE)/14, 140);
			Game.gamestate = Gamestate.GAMEOVER;
		}
	}

	public void verificaPegaFruta(){
		for(int i = 0;i < Game.entities.size();i++){
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta ){
				if(Entity.isColidding(this, current)){
					Game.frutas_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void verificaColisaoInimigo(){
		for(int i = 0;i < Game.entities.size();i++){
			Entity current = Game.entities.get(i);
			if(current instanceof Enemy && !((Enemy) current).ghostMode){
				if(Entity.isColidding(this, current)){
					Sound.hurtEffect.play();
					Sound.musicBackground.clip.stop();
					Game.gamestate = Gamestate.GAMEOVER;
					return;
				}
			}
		}
	}



}
