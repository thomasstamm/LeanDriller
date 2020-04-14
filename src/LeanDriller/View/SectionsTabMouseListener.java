/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Thomas
 *
 * Add mouselistener: - left click outside the tabbed area means call add
 * section methods - left click on one of the tabs just selects the concerned
 * tab - right click on one of the tabs select the SectionsTabEditorPopUpMenu
 */
public class SectionsTabMouseListener implements MouseListener {

    private final ArrayList<SectionPanel> sectionPanels;
    private final JTabbedPane sectionsTab;
    private final ChapterPanel chapterPanel;
    private final SectionsTabEditorPopUpMenu sectionsTabEditor;

    public SectionsTabMouseListener(JTabbedPane sectionsTab,
            ArrayList<SectionPanel> sectionPanels, ChapterPanel chapterPanel) {
        this.sectionsTab = sectionsTab;
        this.sectionPanels = sectionPanels;
        this.chapterPanel = chapterPanel;
        this.sectionsTabEditor = new SectionsTabEditorPopUpMenu(sectionPanels,
                sectionsTab, chapterPanel);
    }

    private void forwardEventToButton(MouseEvent e) {
        /**
         * check if user clicked the mouse outside the tabs area
         */
        Point point = new Point(e.getX(), e.getY());
        int index = -1;
        for (int i = 0; i < sectionsTab.getTabCount(); i++) {
            Rectangle rec = sectionsTab.getBoundsAt(i);
            if (rec.contains(point)) {
                index = i;
                break;
            }
        }
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (index < 0) {
                    String s = "Default section header";
                    SectionPanel sectionPanel
                            = new SectionPanel(chapterPanel.createNewSection(s));
                    sectionPanels.add(sectionPanel);
                    sectionsTab.add(s, sectionPanel);
                    Main.getController().getBaseScreen().
                            getMySaveButton().setWarning();
                }
                break;
            case MouseEvent.BUTTON3:
                if (index >= 0) {
                    Point optimumPointPopUP = ChapterPanel.HandleMousePosition(e);
                    sectionsTabEditor.show(sectionsTab, optimumPointPopUP.x,
                            optimumPointPopUP.y);
                    sectionsTabEditor.setVisible(true);
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.sectionsTabEditor.setVisible(false);
        forwardEventToButton(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        forwardEventToButton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        forwardEventToButton(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        forwardEventToButton(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        forwardEventToButton(e);

    }

}
