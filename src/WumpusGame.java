/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

/**
 *
 * @author Anik
 */
public class WumpusGame extends JFrame implements ActionListener {

    private JFrame f;
    private JMenuBar mb;
    private JMenu m;
    private JMenuItem m1, m2;
    JTextField tf1, tf2, tf3;
    public MyButton[] b;
    private JPanel p1, p2, p3, p4;

    public boolean clicked, start;
    public int playerClicked;
    public CellInfo[] cell;
    public int[][] adjacent;

    public WumpusGame() {

        cell = new CellInfo[4 * 4];
        adjacent = new int[4 * 4][];

        f = new JFrame("Wumpus Game");
        mb = new JMenuBar();
        m = new JMenu("Options");
        m1 = new JMenuItem("Start");
        m2 = new JMenuItem("Exit");
        tf1 = new JTextField(35);
        tf1.setText(" Player : ");
        tf1.setEditable(false);
        tf2 = new JTextField(35);
        tf2.setText(" Computer : ");
        tf2.setEditable(false);
        tf3 = new JTextField(20);
        tf3.setEditable(false);
        b = new MyButton[32];
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        for (int i = 0; i < 32; i++) {
            b[i] = new MyButton("");

        }
    }

    public void launchFrame() {

        int i, j;

        m1.addActionListener(this);
        m2.addActionListener(this);
        m1.setActionCommand("Start");
        m2.setActionCommand("Exit");
        m.add(m1);
        m.add(m2);
        mb.add(m);
        for (i = 16; i < 32; i++) {
            String temp = Integer.toString(i % 16);
            b[i].setActionCommand(temp);
        }

        p1.setSize(400, 400);
        p2.setSize(400, 400);
        p3.setSize(900, 150);
        p4.setSize(900, 450);

        p1.setLayout(new GridLayout(4, 4));
        for (j = 12; j >= 0; j -= 4) {
            for (i = j; i < j + 4; i++) {
                //b[i].setText(""+i);
                p1.add(b[i]);
            }
        }

        p2.setLayout(new GridLayout(4, 4));
        for (j = 28; j >= 16; j -= 4) {
            for (i = j; i < j + 4; i++) {
                //b[i].setText(""+i);
                p2.add(b[i]);
            }
        }

        p3.setLayout(new GridLayout(1, 2));
        p4.setLayout(new FlowLayout());
        p3.add(p2);
        p3.add(p1);

        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        tf1.setFont(font);
        tf2.setFont(font);
        tf3.setFont(font);
        tf3.setBorder(BorderFactory.createCompoundBorder(
                tf3.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        p4.add(tf1);
        p4.add(tf2);

        JPanel p5 = new JPanel(new GridLayout(8, 1));
        JLabel userLabel = new JLabel("U - User", JLabel.CENTER);
        JLabel computerLabel = new JLabel("C - Computer", JLabel.CENTER);
        JLabel wumpusLabel = new JLabel("W - Wumpus", JLabel.CENTER);
        JLabel breezeLabel = new JLabel("B - Breeze", JLabel.CENTER);
        JLabel pitLabel = new JLabel("P - Pit", JLabel.CENTER);
        JLabel stenchLabel = new JLabel("S - Stench", JLabel.CENTER);
        JLabel goldLabel = new JLabel("G - Gold", JLabel.CENTER);

        p5.add(tf3);

        p5.add(userLabel);
        p5.add(computerLabel);
        p5.add(wumpusLabel);
        p5.add(breezeLabel);
        p5.add(pitLabel);
        p5.add(stenchLabel);
        p5.add(goldLabel);
        p4.add(p5);
        f.setSize(900, 600);
        f.setLayout(new GridLayout(2, 1));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setJMenuBar(mb);
        f.add(p3);
        f.add(p4);
    }

    public int[] adjacentCell(int i) {

        Stack s = new Stack();
        int j = 0;
        int[] a;
        if (i % 4 == 0) {
            s.push(i + 1);
            j++;
        } else if ((i + 1) % 4 == 0) {
            s.push(i - 1);
            j++;
        } else {
            s.push(i - 1);
            s.push(i + 1);
            j += 2;
        }
        if ((i - 4) >= 0) {
            s.push(i - 4);
            j++;
        }
        if ((i + 4) < 16) {
            s.push(i + 4);
            j++;
        }

        a = new int[j];
        j = 0;
        while (!s.empty()) {
            a[j++] = (int) s.pop();
        }

        return a;
    }

    public void setBoard() {

        int[] bs = {2, 5, 6, 15};
        int i, rnd;
        Random r = new Random();
        rnd = r.nextInt(4);
        System.out.println(bs[rnd]);

        for (i = 0; i < 16; i++) {
            cell[i] = new CellInfo();
            int[] copy = adjacentCell(i);
            adjacent[i] = new int[copy.length];
            System.arraycopy(copy, 0, adjacent[i], 0, copy.length);
        }
        for (i = 0; i < 16; i++) {
            System.out.print(i + " -> ");
            for (int j = 0; j < adjacent[i].length; j++) {
                System.out.print(adjacent[i][j] + ",");
            }
            System.out.println();
        }
        cell[(bs[rnd]) % 16].setPit(true);
        for (i = 0; i < adjacent[(bs[rnd]) % 16].length; i++) {
            cell[adjacent[(bs[rnd]) % 16][i]].setBreeze(true);
        }
        cell[(bs[rnd] + 6) % 16].setWumpus(true);
        for (i = 0; i < adjacent[(bs[rnd] + 6) % 16].length; i++) {
            cell[adjacent[(bs[rnd] + 6) % 16][i]].setStench(true);
        }
        cell[(bs[rnd] + 6 + 1) % 16].setGlitter(true);
        cell[(bs[rnd] + 6 + 1 + 1) % 16].setPit(true);
        for (i = 0; i < adjacent[(bs[rnd] + 6 + 1 + 1) % 16].length; i++) {
            cell[adjacent[(bs[rnd] + 6 + 1 + 1) % 16][i]].setBreeze(true);
        }
        cell[(bs[rnd] + 6 + 1 + 1 + 5) % 16].setPit(true);
        for (i = 0; i < adjacent[(bs[rnd] + 6 + 1 + 1 + 5) % 16].length; i++) {
            cell[adjacent[(bs[rnd] + 6 + 1 + 1 + 5) % 16][i]].setBreeze(true);
        }
    }

    public void showBoard() {
        for (int i = 0; i < 32; i++) {
            String temp = "";
            if (cell[i % 16].isBreeze()) {
                temp = temp + "B";
            }
            if (cell[i % 16].isGlitter()) {
                temp = temp + "G";
            }
            if (cell[i % 16].isPit()) {
                temp = temp + "P";
            }
            if (cell[i % 16].isStench()) {
                temp = temp + "S";
            }
            if (cell[i % 16].isWumpus()) {
                temp = temp + "W";
            }
            b[i].setText(temp);
        }

    }

    public CellInfo perceive(int i) {
        CellInfo t = new CellInfo();

        t.setBreeze(cell[i].isBreeze());
        t.setStench(cell[i].isStench());
        t.setGlitter(cell[i].isGlitter());

        return t;
    }

    void setButton(int c) {
        int i, j, k;
        for (i = 16; i < 32; i++) {
            for (j = 0, k = 0; j < adjacent[c % 16].length && k == 0; j++) {
                if (i == (adjacent[c % 16][j] + 16)) {
                    b[i].addActionListener(this);
                    k = 1;
                }
            }
            if (k == 0) {
                b[i].removeActionListener(this);
            }
        }
    }

    void removeButtonAction() {
        for (int i = 16; i < 32; i++) {
            b[i].removeActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Start":
                start = true;
                break;
            case "Exit":
                System.exit(0);
            default:
                playerClicked = 16 + Integer.parseInt(e.getActionCommand());
                clicked = true;
                break;
        }
    }

    public static void main(String[] args) {
        
        long startTime, playerTime, computerTime;
        int playerPoint, computerPoint;
        
        WumpusGame w = new WumpusGame();
        Player player = new Player(w);
        Computer computer = new Computer(w);

        w.launchFrame();
        w.setBoard();
        
        while (!w.start) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        startTime = System.nanoTime();
        playerPoint = player.gameOn();
        playerTime = (System.nanoTime() - startTime)/1000000;
        w.tf1.setText(w.tf1.getText() + " Time Elapsed (milli seconds) : "+playerTime);

        w.removeButtonAction();
        w.showBoard();

        startTime = System.nanoTime();
        computerPoint = computer.gameOn();
        computerTime = (System.nanoTime() - startTime)/1000000;
        w.tf2.setText(w.tf2.getText() + " Time Elapsed (milli seconds) : "+computerTime);
        
        if (computerPoint > playerPoint)
            w.tf3.setText("     Winner: Computer");
        else if (computerPoint == playerPoint && computerTime < playerTime)
            w.tf3.setText("     Winner: Computer ");
        else w.tf3.setText(" Congratulation! You won the game");
        
    }

}
