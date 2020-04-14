/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import LeanDriller.Model.Book;
import LeanDriller.Model.Chapter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Thomas
 */
public class BookPanel extends JPanel {

    /**
     * *
     * View model fields
     */
    private final ArrayList<ChapterPanel> chapterPanels;
    private final JTabbedPane chaptersTab;
    private final JPanel centerPanel;
    private final LinkedList<Chapter> chapters;
    private final JLabel appLabel;
    /**
     * *
     * Attribute fields. In a later version all attribute fields should be
     * updated from the view model in the Book/Controller model via listeners in
     * order to be more in line with MVC principles.
     */
    private final Book book;
    private final String bookIdentifier;
    private String bookTitle;
    private String bookPath;
    private final BookPanel bookPanel;

    public BookPanel(Book book) {

        setLayoutManager();
        this.book = book;
        this.bookIdentifier = book.getBookIdentifier();
        this.bookTitle = book.getBookTitle();
        this.bookPath = book.getBookPath();
        this.chapterPanels = new ArrayList<>();
        this.chaptersTab = new JTabbedPane(JTabbedPane.LEFT);
        this.bookPanel = this;

        /**
         * Create title panel on top
         */
        JPanel titlePanel = new JPanel();
        appLabel = new JLabel(bookTitle, JLabel.CENTER);
        String currentFont = appLabel.getFont().getName();
        appLabel.setFont(new Font(currentFont, Font.BOLD, 26));
        titlePanel.add(appLabel);
        /**
         * Create MouseListener for changing the BookTitle
         */
        titlePanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                BookTitleEditorWindow bookTitleEditorWindow
                        = new BookTitleEditorWindow(bookTitle, bookPath);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /**
         * Create Tabbed Pane in Center. For each chapter a panel is created.
         */
        this.chapters = this.book.getChapters();

        /**
         * If the book contains no chapters during the stage of the initial
         * building of the view model, a default chapter is added.
         */
        if (chapters.isEmpty()) {
            chapters.add(this.createNewChapter("Default chapter header"));
        }

        /**
         * *
         * At the moment of creation of the view model, the Book object is
         * leading and contains the latest information. The information is
         * provided by passing the concerned Object to the lower level.
         */
        for (Chapter chapter : chapters) {
            String s = chapter.getChapterTitle();
            ChapterPanel chapterPanel = new ChapterPanel(chapter);
            chapterPanels.add(chapterPanel);
            chaptersTab.add(s, chapterPanel);
        }
        chaptersTab.setSelectedIndex(0);

        /**
         * Add mouselistener: - left click outside the tabbed area mens call add
         * chapter method - left click on one of the tabs just selects the
         * concerned tab - right click on one of the tabs select the change
         * chapterTitle method
         */
        chaptersTab.addMouseListener(new ChaptersTabMouseListener(chaptersTab,
                chapterPanels, bookPanel));

        /**
         * create a centerPanel to hold the titlePanel and the chaptersTab,
         * otherwise the lay-out manager of chaptersTab will display the
         * titlePanel on the left side of the BookPanel
         */
        this.centerPanel = new JPanel(new BorderLayout());
        this.centerPanel.add(titlePanel, BorderLayout.NORTH);
        this.centerPanel.add(chaptersTab, BorderLayout.CENTER);

        /**
         * Remove the last bookPanel, otherwise alle bookPanel will be displayed
         * on top of each other and the application will block. Remark: during
         * the first pass through there is no bookPanel yet, so we cannot just
         * remove the last component. Therefore we must test if the component is
         * a bookPanel object
         */
        Component[] componentList = Main.getController().
                getBaseScreen().mainContainer.getComponents();

        for (Component c : componentList) {
            if (c instanceof BookPanel) {
                Main.getController().getBaseScreen().mainContainer.remove(c);
            }
        }

        /**
         * Add all panels to bookPanel
         */
        addPanelstoBookPanel();
    }

    /**
     * *
     * The code in the following methods is not placed in the constructor but
     * instead in private methods in order to avoid the IDE warning 'Leaking
     * this in constructor' or 'Overridable method call in constructor'.
     */
    private void setLayoutManager() {
        this.setLayout(new BorderLayout());
    }

    private void addPanelstoBookPanel() {

        /**
         * Add the centerPanel to bookPanel
         */
        this.add(centerPanel, BorderLayout.CENTER);

        /**
         * Add the bookPanel to the baseScreen en revalidate and repaint
         */
        Main.getController().getBaseScreen().mainContainer.add(this, BorderLayout.CENTER);
        Main.getController().getBaseScreen().mainContainer.revalidate();
        Main.getController().getBaseScreen().mainContainer.repaint();
    }

    public String getBookIdentifier() {
        return bookIdentifier;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setTextBookTitleLabel(String bookTitle) {
        this.appLabel.setText(bookTitle);
    }

    public ArrayList<ChapterPanel> getChapterPanels() {
        return chapterPanels;
    }

    protected Chapter createNewChapter(String chapterTitle) {
        /**
         * Remark: this method is used both during the initial building of the
         * view model (when the user request the initialisation of a new book)
         * and, later, when the user requests a new chapter. 
         */

        Chapter newChapter = new Chapter("C", this.bookIdentifier,
                getUniqueIdentifier(), chapterTitle);
        return newChapter;
    }

    /**
     * This functions returns an unique identifier by calling the
     * static randomUUID method of the UUID class in Java. 
     * This method generates an unique key using UUID version 4 
     * (random generated) and variant 2.
     *
     * @return String unique Identifier
     */
    public static String getUniqueIdentifier() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
