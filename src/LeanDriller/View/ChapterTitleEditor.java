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
public class ChapterTitleEditor extends JFrame implements ActionListener {

    private final JTextField textField;
    private String chapterTitle;
    private final JTabbedPane chaptersTab;
    private final int index;
    private final ArrayList<ChapterPanel> chapterPanels;

    public ChapterTitleEditor(JTabbedPane chaptersTab, int index, 
            ArrayList<ChapterPanel> chapterPanels) {
        super("Edit Chapter Title");
        this.chaptersTab = chaptersTab;
        this.index = index;
        this.chapterPanels = chapterPanels;
        this.textField = new JTextField(50);
        createWindow();
    }

    private void createWindow() {

        this.chapterTitle = chaptersTab.getTitleAt(this.index);

        // Define the components
        JPanel panel = new JPanel();
        textField.setText(this.chapterTitle);
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
        this.chapterTitle = textField.getText();

        /**
         * set the title of the concerned tab to the new value
         * set the chapterTitle field in the concerned chapterPanel to 
         * the new value
         * set the save flag on the base screen
         */
        this.chaptersTab.setTitleAt(index, chapterTitle);
        this.chapterPanels.get(index).setChapterTitle(chapterTitle);
        Main.getController().getBaseScreen().getMySaveButton().setWarning();
        this.setVisible(false);
    }

}
