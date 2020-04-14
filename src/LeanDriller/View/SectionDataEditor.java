/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author thoma
 */
public class SectionDataEditor extends JFrame implements ActionListener {

    private final JTextField textField;
    private String sectionTitle;
    private final JTabbedPane sectionsTab;
    private final int index;
    private final ArrayList<SectionPanel> sectionPanels;

    public SectionDataEditor(JTabbedPane sectionsTab, int index, 
            ArrayList<SectionPanel> sectionPanels) {
        super("Edit Section Title");
        this.sectionsTab = sectionsTab;
        this.index = index;
        this.sectionPanels = sectionPanels;
        this.textField = new JTextField(50);
        createWindow();
    }

    private void createWindow() {

        this.sectionTitle = sectionsTab.getTitleAt(this.index);

        // Define the components
        JPanel panel = new JPanel();
        textField.setText(this.sectionTitle);
        textField.addActionListener(this);
        panel.add(textField);
        this.add(panel);

        //Display the window.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
//        this.setSize(300, 200); // or pack() the components
        /**
         * setLocation should be called after setSize() or pack()
         */
        this.setLocationRelativeTo(panel); // setLocation should be called after setSize()
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * get the new chapter title
         */
        this.sectionTitle = textField.getText();

        /**
         * set the title of the concerned tab to the new value
         * set the chapterTitle field in the concerned chapterPanel to 
         * the new value
         * set the save flag on the base screen
         */
        this.sectionsTab.setTitleAt(index, sectionTitle);
        this.sectionPanels.get(index).setSectionTitle(sectionTitle);
        Main.getController().getBaseScreen().getMySaveButton().setWarning();
        this.setVisible(false);
    }

}
