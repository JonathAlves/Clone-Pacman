package com.gcstudios.main;

import com.gcstudios.entities.Gamestate;

import java.awt.*;

import static com.gcstudios.main.Game.*;
import static com.gcstudios.main.Game.SCALE;

public class Menu {
    public String[] opt = {"Novo jogo", "Carregar jogo", "Sair"};
    public int optAtual = 0;
    public int maxOption = opt.length -1;
    public boolean up, down, enter;
    public static boolean pause = false;

    public void tick(){
        if(up){
            up = false;
            optAtual--;
            if(optAtual < 0){
                optAtual = maxOption;
            }
        }

        if(down){
            down = false;
            optAtual++;
            if(optAtual > maxOption){
                optAtual = 0;
            }
        }

        if(enter){
            enter = false;
            if(opt[optAtual].equalsIgnoreCase("Novo jogo") || opt[optAtual].equalsIgnoreCase("Continuar")){
                Game.gamestate = Gamestate.NORMAL;
                pause = false;
            }else if(opt[optAtual].equalsIgnoreCase("Sair")){
                System.exit(1);
            }
        }

    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 255));
        g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("arial", Font.BOLD, 15));
        g.drawString("::PACMAN CLONE::", (Game.WIDTH*Game.SCALE)/2 - 185, (Game.HEIGHT*Game.SCALE)/2 - 160);
        if(pause == false){
            g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE)/2 - 160, 100);
        }
        else{
            g.drawString("Continuar", (Game.WIDTH*Game.SCALE)/2 - 160, 100);
        }
        g.drawString("Carregar jogo", (Game.WIDTH*Game.SCALE)/2 - 160, 120);
        g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 160, 140);

        if(opt[optAtual].equalsIgnoreCase("Novo jogo")){
            g.drawString("->", (Game.WIDTH*Game.SCALE)/2 - 175, 100);
        }
        else if(opt[optAtual].equalsIgnoreCase("Carregar jogo")){
            g.drawString("->", (Game.WIDTH*Game.SCALE)/2 - 175, 120);
        }
        else if(opt[optAtual].equalsIgnoreCase("Sair")){
            g.drawString("->", (Game.WIDTH*Game.SCALE)/2 - 175, 140);
        }
    }
}
