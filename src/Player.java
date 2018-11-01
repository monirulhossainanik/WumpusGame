/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;

/**
 *
 * @author Anik
 */
class Player {

    private WumpusGame w;
    public Player(WumpusGame w){
        this.w = w;
    }

    int gameOn() {
        CellInfo t;
        boolean gameContinue = true, grabGold = false;
        String text = "", text1;
        int current, prev,point = -1;
        current = w.playerClicked = 16;
        prev = 15;
        
        while (gameContinue) {
            text1 = "";
            w.setButton(current);
            t = w.perceive(current % 16);
            if (!w.cell[current % 16].isPit() && !w.cell[current % 16].isWumpus()) {
                if (prev != 15) {
                    w.b[prev].setText(text);
                }
                text = w.b[current].getText();
                w.b[current].setText("U ->" + text);
                w.b[current].setBackground(Color.pink);

                if (t.isBreeze()) {
                    text1 += "B";
                }
                if (t.isStench()) {
                    text1 += "S";
                }
                if (t.isGlitter() && !grabGold) {
                    text1 += "G";
                    grabGold = true;
                    point += 1000;
                }
                w.b[current].setText(w.b[current].getText() + text1);

                w.clicked = false;
                while (!w.clicked) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }
                }

                if (w.clicked) {
                    if (w.playerClicked == 16 && grabGold) {
                        gameContinue = false;
                    }
                    prev = current;
                    current = w.playerClicked;
                    point--;
                }
            } else {
                gameContinue = false;
                point -= 1000;
            }

        }

        w.b[prev].setText(text);
        text = w.b[current].getText();
        w.b[current].setText("U ->" + text);
        w.b[current].setBackground(Color.pink);
        try {
         Thread.sleep(3000);
         } catch (InterruptedException e) {
         }
        w.b[current].setText(text);
        
        w.tf1.setText(w.tf1.getText()+ "Point: "+point);
        return point;
    }
    
}
