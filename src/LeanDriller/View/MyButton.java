/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Thomas
 */
public class MyButton extends JButton {

    private Color initialBackgroundButtoncolor, 
            initialForegroundButtonColor;

    public MyButton(String text) {
        super(text);
        getInitialColors();
    }

    private void getInitialColors() {
        this.initialBackgroundButtoncolor = this.getBackground();
        this.initialForegroundButtonColor = this.getForeground();
    }

    public void setWarning() {
        this.setForeground(Color.RED);
        this.setBackground(Color.ORANGE);
    }

    public void setNeutral() {
        this.setBackground(initialBackgroundButtoncolor);
        this.setForeground(initialForegroundButtonColor);
    }

}
