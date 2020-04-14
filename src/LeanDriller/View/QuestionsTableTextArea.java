/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Thomas
 */
public class QuestionsTableTextArea {


    private static final Font DEFAULTFONT;
    private static final Float DEFAULTFONTSIZE = 20f; 
    
    static {
        Font systemFont = UIManager.getDefaults().getFont("TextPane.font");
        DEFAULTFONT = systemFont.deriveFont(DEFAULTFONTSIZE);
    }
    
    private final JTextArea textArea;

    public QuestionsTableTextArea() {
        this.textArea = new JTextArea();
        this.textArea.setLineWrap(true);
        this.textArea.setBackground(Color.lightGray);
        this.textArea.setFont(DEFAULTFONT);
    }

    public QuestionsTableTextArea(String text) {
        this();
        this.textArea.setText(text);
    }

    public JTextArea getTextArea() {
        return this.textArea;
    }

}
