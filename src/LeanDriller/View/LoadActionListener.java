package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LoadActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        File SelectedFile = SelectFileforOpenDialog();
        /**
         * check if user did not choose Cancel or Terminate the open file dialog
         */
        if (SelectedFile != null) {
            Main.getController().loadFile(SelectedFile);
        } else {
            JOptionPane.showMessageDialog(null, "Selection cancelled or "
                    + "terminated, "
                    + "no file is loaded", "alert",
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public File SelectFileforOpenDialog() {
        
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }
    
}
