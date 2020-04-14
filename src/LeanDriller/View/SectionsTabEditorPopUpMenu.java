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
public class SectionsTabEditorPopUpMenu extends JPopupMenu {

    private final ArrayList<SectionPanel> sectionPanels;
    private final JTabbedPane sectionsTab;
    private final ChapterPanel chapterPanel;
    private SectionsTabEditorPopUpMenu captureThisSectionsTabEditorPopUpMenu;

    public SectionsTabEditorPopUpMenu(ArrayList<SectionPanel> sectionPanels,
            JTabbedPane sectionsTab, ChapterPanel chapterPanel) {

        this.sectionPanels = sectionPanels;
        this.sectionsTab = sectionsTab;
        this.chapterPanel = chapterPanel;
        createPopup();
    }

    private void createPopup() {
        
        /***
         * Capture the link to this SectionsTabEditorPopUp
         */
        
        this.captureThisSectionsTabEditorPopUpMenu = this;
        
        JMenuItem menuItem01 = this.add(new JMenuItem("Insert New Section Before"));
        menuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertNewSection(sectionsTab.getSelectedIndex());
                Main.getController().getBaseScreen().
                        getMySaveButton().setWarning();  
                captureThisSectionsTabEditorPopUpMenu.setVisible(false);
            }
        });

        JMenuItem menuItem02 = this.add(new JMenuItem("Insert New Section After"));
        menuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertNewSection(sectionsTab.getSelectedIndex() + 1);
                Main.getController().getBaseScreen().
                        getMySaveButton().setWarning();
                captureThisSectionsTabEditorPopUpMenu.setVisible(false);
            }
        });

        JMenuItem menuItem03 = this.add(new JMenuItem("Delete Selected Section"));
        menuItem03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sectionPanels.remove(sectionsTab.getSelectedIndex());
                sectionsTab.remove(sectionsTab.getSelectedIndex());
                Main.getController().getBaseScreen().
                        getMySaveButton().setWarning();
                captureThisSectionsTabEditorPopUpMenu.setVisible(false);
            }
        });

        JMenuItem menuItem04 = this.add(new JMenuItem("Edit Section Title"));
        menuItem04.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SectionDataEditor sectionDataEditor
                        = new SectionDataEditor(sectionsTab,
                                sectionsTab.getSelectedIndex(), sectionPanels);
                captureThisSectionsTabEditorPopUpMenu.setVisible(false);
            }
        });
    }

    private void insertNewSection(int index) {
        String s = "Default section header";
        SectionPanel sectionPanel
                = new SectionPanel(chapterPanel.createNewSection(s));
        sectionPanels.add(index, sectionPanel);
        sectionsTab.insertTab(s, null, sectionPanel, s, index);
    }
   
}
