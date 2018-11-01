/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.util.Stack;

/**
 *
 * @author Anik
 */
class Computer {

    private WumpusGame w;

    public Computer(WumpusGame w) {
        this.w = w;
    }

    int gameOn() {

        boolean grabGold = false;
        Stack p = new Stack();
        CellInfo[] gc = new CellInfo[16];
        CellInfo t, t1;
        String text = "";
        boolean[] visit = new boolean[16];
        boolean wumpus = false;
        int point = -1;
        int current, prev, i, j, k, lc = -1;

        for (i = 0; i < 16; i++) {
            gc[i] = new CellInfo();
            visit[i] = false;
        }
        
        current = 0;
        prev = -1;
        while (!grabGold) {
            if (prev != -1) {
                w.b[prev].setText(text);
            }
            text = w.b[current].getText();
            w.b[current].setText("C ->" + text);
            w.b[current].setBackground(Color.LIGHT_GRAY);
            visit[current] = true;
            t = w.perceive(current);

            if (!t.isGlitter()) {
                if (!p.empty()) {
                    lc = (int) p.peek();
                }
                if (t.isBreeze() && (lc != current)) {

                    for (i = 0; i < w.adjacent[current].length; i++) {
                        if (!visit[w.adjacent[current][i]]) {
                            for (j = 0, k = 1; j < w.adjacent[w.adjacent[current][i]].length && k == 1; j++) {
                                if (current != w.adjacent[w.adjacent[current][i]][j] && visit[w.adjacent[w.adjacent[current][i]][j]]) {
                                    t1 = w.perceive(w.adjacent[w.adjacent[current][i]][j]);
                                    if (t1.isBreeze() && t.isBreeze()) {
                                        p.push(w.adjacent[w.adjacent[current][i]][j]);
                                        for (int i1 = 0; i1 < w.adjacent[w.adjacent[w.adjacent[current][i]][j]].length; i1++) {
                                            gc[w.adjacent[w.adjacent[w.adjacent[current][i]][j]][i1]].setPit(false);
                                            gc[w.adjacent[w.adjacent[w.adjacent[current][i]][j]][i1]].setWumpus(false);
                                        }
                                        gc[w.adjacent[current][i]].setPit(true);
                                        k = 0;
                                    } else {
                                        gc[w.adjacent[current][i]].setPit(false);
                                        gc[w.adjacent[current][i]].setWumpus(false);
                                    }

                                } else {
                                    gc[w.adjacent[current][i]].setPit(true);
                                }
                            }
                        }
                    }

                } else {
                    if (lc == current) {
                        p.pop();
                    }
                }

                if (t.isStench() && !wumpus) {
                    for (i = 0; i < w.adjacent[current].length && !wumpus; i++) {
                        if (!visit[w.adjacent[current][i]]) {
                            for (j = 0, k = 1; j < w.adjacent[w.adjacent[current][i]].length && k == 1; j++) {
                                if (current != w.adjacent[w.adjacent[current][i]][j] && visit[w.adjacent[w.adjacent[current][i]][j]]) {
                                    t1 = w.perceive(w.adjacent[w.adjacent[current][i]][j]);
                                    if (t1.isStench() && t.isStench()) {
                                        for (int i1 = 0; i1 < w.adjacent[w.adjacent[w.adjacent[current][i]][j]].length; i1++) {
                                            gc[w.adjacent[w.adjacent[w.adjacent[current][i]][j]][i1]].setPit(false);
                                            gc[w.adjacent[w.adjacent[w.adjacent[current][i]][j]][i1]].setWumpus(false);
                                        }
                                        gc[w.adjacent[current][i]].setWumpus(true);
                                        wumpus = true;
                                        k = 0;
                                    } else {
                                        gc[w.adjacent[current][i]].setPit(false);
                                        gc[w.adjacent[current][i]].setWumpus(false);
                                    }

                                } else {
                                    gc[w.adjacent[current][i]].setWumpus(true);
                                }
                            }
                        }
                    }
                }

                ////
                for (i = 0, k = 0; i < w.adjacent[current].length && k == 0; i++) {
                    if (prev != w.adjacent[current][i] && !gc[w.adjacent[current][i]].isPit() && !gc[w.adjacent[current][i]].isWumpus()) {
                        prev = current;
                        current = w.adjacent[current][i];
                        k = 1;
                    }
                }
                if (k == 0) {
                    for (i = 0, k = 0; i < w.adjacent[current].length && k == 0; i++) {
                        if (!gc[w.adjacent[current][i]].isPit() && !gc[w.adjacent[current][i]].isWumpus()) {
                            prev = current;
                            current = w.adjacent[current][i];
                            k = 1;
                        }
                    }
                }
                
            } else {
                grabGold = true;
                point += 1000;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
            point--;
        }
        point++;
        while (current != 0) {

            w.b[current].setText("C ->" + text);
            w.b[current].setBackground(Color.LIGHT_GRAY);
            for (i = 0, k = 0; i < w.adjacent[current].length && k == 0; i++) {
                if (prev != w.adjacent[current][i] && visit[w.adjacent[current][i]]) {
                    prev = current;
                    current = w.adjacent[current][i];
                    k = 1;
                }
            }
            if (k == 0) {
                for (i = 0, k = 0; i < w.adjacent[current].length && k == 0; i++) {
                    if (visit[w.adjacent[current][i]]) {
                        prev = current;
                        current = w.adjacent[current][i];
                        k = 1;
                    }
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }

            if (prev != -1) {
                w.b[prev].setText(text);
            }
            text = w.b[current].getText();
            
            point--;

        }
        w.b[current].setText("C ->" + text);
        w.b[current].setBackground(Color.LIGHT_GRAY);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
        w.b[current].setText(text);
        
        w.tf2.setText(w.tf2.getText()+ "Point: "+point);
        
        return point;
    }

}
