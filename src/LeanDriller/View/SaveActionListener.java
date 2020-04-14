package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Main.getController().saveBook();
    }

}
