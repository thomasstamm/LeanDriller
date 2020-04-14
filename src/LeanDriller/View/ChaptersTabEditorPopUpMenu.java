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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author Thomas Stamm
 */
public class ChaptersTabEditorPopUpMenu extends JPopupMenu {

    private final ArrayList<ChapterPanel> chapterPanels;
    private final JTabbedPane chaptersTab;
    private final BookPanel bookPanel;
    private ChaptersTabEditorPopUpMenu captureThisChaptersTabEditorPopUpMenu;

    public ChaptersTabEditorPopUpMenu(ArrayList<ChapterPanel> chapterPanels,
            JTabbedPane chaptersTab, BookPanel bookPanel) {

        this.chapterPanels = chapterPanels;
        this.chaptersTab = chaptersTab;
        this.bookPanel = bookPanel;

        createPopup();

    }

    private void createPopup() {
        
        /***
         * Capture the link to this SectionsTabEditorPopUp
         */
        
        this.captureThisChaptersTabEditorPopUpMenu = this;
        
        JMenuItem menuItem01 = this.add(new JMenuItem("Insert New Chapter Above"));
        menuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chaptersTab.getSelectedIndex() >= 0) {
                    insertNewChapter(chaptersTab.getSelectedIndex());
                    Main.getController().getBaseScreen().
                            getMySaveButton().setWarning();
                    captureThisChaptersTabEditorPopUpMenu.setVisible(false);
                }
            }
        });

        JMenuItem menuItem02 = this.add(new JMenuItem("Insert New Chapter Below"));
        menuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chaptersTab.getSelectedIndex() >= 0) {
                    insertNewChapter(chaptersTab.getSelectedIndex() + 1);
                    Main.getController().getBaseScreen().
                            getMySaveButton().setWarning();
                    captureThisChaptersTabEditorPopUpMenu.setVisible(false);
                }
            }
        });

        JMenuItem menuItem03 = this.add(new JMenuItem("Delete Selected Chapter"));
        menuItem03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chapterPanels.remove(chaptersTab.getSelectedIndex());
                chaptersTab.remove(chaptersTab.getSelectedIndex());
                Main.getController().getBaseScreen().
                        getMySaveButton().setWarning();
                captureThisChaptersTabEditorPopUpMenu.setVisible(false);
            }
        });

        JMenuItem menuItem04 = this.add(new JMenuItem("Edit Chapter Title"));
        menuItem04.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChapterTitleEditor chapterTitleEditor
                        = new ChapterTitleEditor(chaptersTab,
                                chaptersTab.getSelectedIndex(), chapterPanels);
                captureThisChaptersTabEditorPopUpMenu.setVisible(false);
            }
        });
    }

    private void insertNewChapter(int index) {
        String s = "Default chapter header";
        ChapterPanel chapterPanel
                = new ChapterPanel(bookPanel.createNewChapter(s));
        chapterPanels.add(index, chapterPanel);
        chaptersTab.insertTab(s, null, chapterPanel, s, index);
    }
    
}
