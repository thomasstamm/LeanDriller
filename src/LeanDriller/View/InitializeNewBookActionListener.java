package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class InitializeNewBookActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        
        File SelectedFile = SelectFileforOpenDialog();
        /**
         * check if user did not choose Cancel or Terminate the open file dialog
         */

        if (SelectedFile == null) {
            JOptionPane.showMessageDialog(null, "Selection cancelled or "
                    + "terminated, "
                    + "no file is loaded", "alert",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        /**
         * *
         * Inform controller to process a new book by passing the file object
         * obtained from JFileChooser
         */
        
        Main.getController().initializeNewBook(SelectedFile);
        Main.getController().getBaseScreen().getMySaveButton().setWarning();
 
    }

    public File SelectFileforOpenDialog() {

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showDialog(null, "Choose a file "
                + "name and directory for your new book");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

}
