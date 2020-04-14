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
 * chapter method - left click on one of the tabs just selects the concerned tab
 * - right click on one of the tabs select the change chapterTitle method
 */
public class ChaptersTabMouseListener implements MouseListener {

    private final JTabbedPane chaptersTab;
    private final ArrayList<ChapterPanel> chapterPanels;
    private final BookPanel bookPanel;
    private final ChaptersTabEditorPopUpMenu chaptersTabEditor;

    public ChaptersTabMouseListener(JTabbedPane chaptersTab,
            ArrayList<ChapterPanel> chapterPanels, BookPanel bookPanel) {
        this.chaptersTab = chaptersTab;
        this.chapterPanels = chapterPanels;
        this.bookPanel = bookPanel;
        this.chaptersTabEditor = new ChaptersTabEditorPopUpMenu(chapterPanels,
                                    chaptersTab, bookPanel);
    }

    private void forwardEventToButton(MouseEvent e) {
        /**
         * check if user clicked the mouse outside the tabs area
         */

        Point point = new Point(e.getX(), e.getY());
        int index = -1;
        for (int i = 0; i < chaptersTab.getTabCount(); i++) {
            Rectangle rec = chaptersTab.getBoundsAt(i);
            if (rec.contains(point)) {
                index = i;
                break;
            }
        }
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (index < 0) {
                    String s = "Default chapter header";
                    ChapterPanel chapterPanel
                            = new ChapterPanel(bookPanel.createNewChapter(s));
                    chapterPanels.add(chapterPanel);
                    chaptersTab.add(s, chapterPanel);
                    Main.getController().getBaseScreen().
                            getMySaveButton().setWarning();
                }
                break;
            case MouseEvent.BUTTON3:
                if (index >= 0) {
                    Point optimumPointPopUP = ChapterPanel.HandleMousePosition(e);
                    chaptersTabEditor.show(chaptersTab, optimumPointPopUP.x,
                            optimumPointPopUP.y);
                    chaptersTabEditor.setVisible(true);                        
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.chaptersTabEditor.setVisible(false);       
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
