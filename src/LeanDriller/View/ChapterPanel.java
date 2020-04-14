package LeanDriller.View;

import LeanDriller.Model.Chapter;
import LeanDriller.Model.Section;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ChapterPanel extends JPanel {

    /**
     * View model fields
     */
    private final JTabbedPane sectionsTab;
    public ArrayList<SectionPanel> sectionPanels;
    private final LinkedList<Section> sections;
    /**
     * Attribute fields. In a later version all attribute fields should be
     * updated from the view model in the Book/Controller model via listeners in
     * order to be more in line with MVC principles.
     */
    private final String bookIdentifier;
    private final String chapterIdentifier;
    private String chapterTitle;
    /***
     * Constants for the static method for positioning the popup menu
     * for both chaptersTab and sectionsTab
     */
    private static final int POPUPWIDTHDISPLACEMENT = 20;
    private static final int POPUPHEIGHTDISPLACEMENT = 20;

    public ChapterPanel(Chapter chapter) {
        
        sectionsTab = new JTabbedPane(JTabbedPane.TOP);
        this.sectionPanels = new ArrayList<>();
        this.bookIdentifier = chapter.getBookIdentifier();
        this.chapterIdentifier = chapter.getChapterIdentifier();
        this.chapterTitle = chapter.getChapterTitle();

        this.sections = chapter.getSections();

        if (sections.isEmpty()) {
            /**
             * If the chapter contains no sections, a default section is added
             * This must be done by the view model, since as of the moment of
             * creation of the view model, the view model is leading
             */
            sections.add(this.createNewSection("Default section header"));
        }

        for (Section section : sections) {
            String sectionTitle = section.getSectionTitle();
            SectionPanel sectionPanel = new SectionPanel(section);
            sectionsTab.add(sectionTitle, sectionPanel);
            sectionPanels.add(sectionPanel);
        }

        sectionsTab.setSelectedIndex(0);

        /**
         * Add mouselistener: - left click outside the tabbed area mens call add
         * chapter method - left click on one of the tabs just selects the
         * concerned tab - right click on one of the tabs select the change
         * chapterTitle method
         */
        sectionsTab.addMouseListener(
                new SectionsTabMouseListener(sectionsTab, sectionPanels, this));

        createLayout();

    }

    private void createLayout() {

        /**
         * A border layout lays out a container, arranging and resizing its
         * components to fit in five regions: north, south, east, west, and
         * center. Each region may contain no more than one component, and is
         * identified by a corresponding constant: NORTH, SOUTH, EAST, WEST, and
         * CENTER
         */
        this.setLayout(new BorderLayout());
        this.add(sectionsTab, BorderLayout.CENTER);

    }

    public String getChapterIdentification() {
        return chapterIdentifier;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    /**
     * *
     * Remark: this method is used both during the initial building of the view
     * model (when the user request the initialisation of a new book) and,
     * later, when the user requests a new chapter.The static method
     * BookPanel.getUniqueIdentifier is used to get an unique identifier for the
     * new section.
     *
     * @param sectionTitle
     * @return newSection
     */
    protected Section createNewSection(String sectionTitle) {

        Section newSection = new Section("S", this.bookIdentifier,
                this.chapterIdentifier, BookPanel.getUniqueIdentifier(),
                sectionTitle);
        return newSection;
    }

    /**
     * This method determines optimal x- and y-coordinates for the upper-left
     * corner of the popup menus for the chapters and sections tabs.
     * @param e
     * @return Point
     */
    public static Point HandleMousePosition(MouseEvent e) {

        if (!(e.getSource() instanceof JTabbedPane)) {
            System.err.println("----------------");
            System.err.println("Source object is not a JTabbedPane");
            System.err.println("----------------");
            System.exit(9);
        }
        
        JTabbedPane myTabbedPane = (JTabbedPane) e.getSource();
            int tabbedPaneWidth
                    = myTabbedPane.getBoundsAt(myTabbedPane.getSelectedIndex()).width;
            int tabbedPaneHeight
                    = myTabbedPane.getBoundsAt(myTabbedPane.getSelectedIndex()).height;
            int tabbedPaneGetX
                    = myTabbedPane.getBoundsAt(myTabbedPane.getSelectedIndex()).x;
            int tabbedPaneGetY
                    = myTabbedPane.getBoundsAt(myTabbedPane.getSelectedIndex()).y;

            int optimumWidth = tabbedPaneGetX + tabbedPaneWidth + POPUPWIDTHDISPLACEMENT;
            int optimumHeight = tabbedPaneGetY + tabbedPaneHeight + POPUPHEIGHTDISPLACEMENT;
            return new Point(optimumWidth, optimumHeight);
            
    }

}
