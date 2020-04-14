package LeanDriller.Control;

import LeanDriller.View.BaseScreen;
import LeanDriller.View.BookPanel;
import LeanDriller.View.ChapterPanel;
import LeanDriller.View.SectionPanel;
import LeanDriller.View.QuestionPanel;
import LeanDriller.Model.DataRecord;
import LeanDriller.Model.IOManagerExtended;
import LeanDriller.Model.Book;
import LeanDriller.Model.Chapter;
import static LeanDriller.Model.IOManagerExtended.SUBTOKENSEPARATOR;
import LeanDriller.Model.Section;
import LeanDriller.Model.Question;
import java.io.File;
import java.util.ArrayList;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LeanDrillerController {

    private BaseScreen baseScreen;
    public BookPanel bookPanel;

    /**
     * *
     * This application uses an hierarchical data model: a book consists of one
     * or more chapters. Each chapter has one or more sections and each section
     * holds one or more questions. A question is an entity with ons question
     * text and one answer text.
     *
     * The key on each level consists of the foreign keys of the higher levels
     * and an unique identifier for the concerned object. For instance: a
     * question has an unique question identifier and a foreign keys refering to
     * the concerned section, chapter and bookidentifiers.
     *
     * The book is stored in a flat file. The flat file is read into a
     * datastream from which a temporary internal book model is built. This
     * internal book model is only used to build the view model. All user
     * interaction is kept in the view model. When the user saves the book, all
     * data in the view model is read into a datastream which is then saved into
     * a flat file.
     *
     * In this version of the application are no MCV principles implemented. The
     * implmentation of the MVC model will be done in an next version of the
     * application when dhe dat wil be stored in a database.
     *
     */
    private Book book;
    private ArrayList<DataRecord> dataStream;
    private String bookPath;

    public void startAppliction() {

        /*
         * This flag must be set before any frame is initialized. It is a static
         * method of class JFrame. It sets for all frames:
         * frame.setUndecorated(true);
         * frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
         *
         */
        setDefaultLookAndFeelDecorated(false);

        SwingUtilities.invokeLater(() -> {
            baseScreen = new BaseScreen();
        });
    }

    public void initializeNewBook(File SelectedFile) {

        this.bookPath = SelectedFile.getPath();
        this.dataStream = new ArrayList<>();
        dataStream.add(new DataRecord("B", BookPanel.getUniqueIdentifier(),
                "Default Book Title"));
        startBook();

    }

    public void loadFile(File SelectedFile) {

        /**
         * *
         * Read the datastrean from the selected file into the internal buffer
         * the ArrayList<DataRecord> dataStream. Presumably the old dataStream
         * object will garbaged by GC, unless there is somewhere still a
         * reference to the old dataStream that we are not aware of. To be
         * checked later.
         */
        this.bookPath = SelectedFile.getPath();
        this.dataStream = IOManagerExtended.LoadFile(this.bookPath);
        startBook();
    }

    public void startBook() {
        /**
         * *
         * Creating a new book from the current dataStream by calling the method
         * createBookFromDataStream. This method creates a new Book object from
         * dataStream. Presumably the old Book object will be garbaged by the
         * unless there is somewhere still a reference to the old Book object
         * that we are not aware of. To be checked later.
         */
        createBookFromDataStream();
        this.dataStream = null; // dereferencing the obsolete dataStream object
        /**
         *
         * Start building the book, chapter, section and question panels, taking
         * the Book object as input. Presumably the old bookpanel and all
         * underlying panels will be garbaged by the GC. To be checked later.
         */

        this.bookPanel = new BookPanel(this.book);
        this.baseScreen.setVisible(true);
        /**
         * *
         * derefencing the obsolete Book object. It's only function was to
         * facilitate builing the view. From now on until saving, the view is
         * leading and the Book object should be GCed in order to avoid
         * inconsistencies between the 'old' Book object and the 'new' view
         * model. This means that all the view components must also hold the
         * unique identifiers of Book, Chapter, Section and Questions, which is
         * against the MVC principles. To be corrected later.
         */
        this.book = null;

    }

    public void saveBook() {

        String bookIdentifier = this.bookPanel.getBookIdentifier();
        String bookTitle = this.bookPanel.getBookTitle();

        JOptionPane.showMessageDialog(null, "Saving: " + bookTitle
                + " in file: " + this.bookPath,
                "alert",
                JOptionPane.ERROR_MESSAGE);

        createDataStreamFromView(bookIdentifier, bookTitle);

        if (IOManagerExtended.SaveFile(this.bookPath, this.dataStream)) {
            Main.getController().getBaseScreen().getMySaveButton().setNeutral();
        } else {
            System.out.println("Fatal error Saving File -- "
                    + this.getClass().getName());
            System.exit(9);
        }
    }

    public Book getBook() {
        return book;
    }

    public BaseScreen getBaseScreen() {
        return baseScreen;
    }

    private void createBookFromDataStream() {

        dataStream.forEach((dr) -> {

            String recordType = dr.getRecordType();
            String recordIdentifier = dr.getRecordIdentifier();
            String recordData = dr.getRecordData();
            String L0 = "", L1 = "", L2 = "", L3 = "";
            String foreignKeys[]
                    = subString(recordIdentifier, SUBTOKENSEPARATOR);
            String dataFields[] = subString(recordData, SUBTOKENSEPARATOR);

            switch (recordType) {
                
                /**
                 * Book data record lay-out: 
                 * String recordType = 'B' 
                 * String recordIdentifier : 
                 * foreignKeys[0] = bookIdentifier
                 * String dataFields[0] = bookTitle
                 * Remark: the bookPath is not stored in the file, but is input
                 * from the selectFile dialog.
                 */
                case "B":
                    book = new Book(recordType, foreignKeys[0], dataFields[0]);
                    book.setBookPath(this.bookPath);
                    break;

                /**
                 * Chapter data record lay-out: 
                 * String recordType = 'C' 
                 * String recordIdentifier : 
                 * foreignKeys[0] = bookIdentifier
                 * foreignKeys[1] = chapterIdentifier 
                 * String dataFields[0] = chapterTitle
                 *
                 */
                case "C":
                    if (!book.getBookIdentifier().equals(foreignKeys[0])) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Chapter refers to a book that "
                                + "is not equal to the current book");
                        System.err.println("Current Book id : " + book.getBookIdentifier());
                        System.err.println("Chapter refers to Book id : " + foreignKeys[0]);
                        System.err.println("Chapter id : " + foreignKeys[1]);
                        System.err.println("Chapter title : " + dataFields[0]);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    /**
                     * Check home key for duplicity in the chapter list of the
                     * current book
                     */
                    if (book.bookContainsChapter(foreignKeys[1])) {
                        System.err.println("----------------");
                        System.err.println("Duplicate chapter id");
                        System.err.println("Book id : " + book.getBookIdentifier());
                        System.err.println("Book title : " + book.getBookTitle());
                        System.err.println("Chapter id : " + foreignKeys[1]);
                        System.err.println("Chapter title : " + dataFields[0]);
                        System.err.println("----------------");
                        System.exit(9);
                    }

                    Chapter chapter = new Chapter(recordType, foreignKeys[0],
                            foreignKeys[1], dataFields[0]);
                    book.addChapter(chapter);
                    break;
                    
                 /**
                 * Section data record lay-out: 
                 * String recordType = 'S' 
                 * String recordIdentifier : 
                 * foreignKeys[0] = bookIdentifier
                 * foreignKeys[1] = chapterIdentifier
                 * foreignKeys[2] = sectionIdentifier
                 * String dataFields[0] = sectionTitle
                 */
                case "S":
                    /**
                     * Substring RecordIdentifier
                     */
                    L0 = foreignKeys[0];
                    L1 = foreignKeys[1];
                    L2 = foreignKeys[2];
                    /**
                     * *
                     * Check foreign keys for referential integrity
                     */
                    if (!book.getBookIdentifier().equals(foreignKeys[0])) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Section refers to a book that "
                                + "is not equal to the current book");
                        System.err.println("Current Book id : " + book.getBookIdentifier());
                        System.err.println("Section refers to Book id : " + foreignKeys[0]);
                        System.err.println("Section id : " + L2);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    chapter = book.getChapterFromChapterIdentification(foreignKeys[1]);
                    if (chapter == null) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Section refers to a chapter "
                                + "that could not be found in the current book");
                        System.err.println("Section refers to Chapter id : "
                                + foreignKeys[1]);
                        System.err.println("Section id : " + L2);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    /**
                     * Check home key for duplicity in the section list of the
                     * current section
                     */
                    if (chapter.chapterContainsSection(L2)) {
                        System.err.println("----------------");
                        System.err.println("Duplicate section id");
                        System.err.println("Chapter id : " + chapter.getChapterIdentifier());
                        System.err.println("Chapter title : " + chapter.getChapterTitle());
                        System.err.println("Section id : " + L2);
                        System.err.println("Section title : " + dataFields[0]);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    /**
                     * add current record
                     */
                    Section section = new Section(recordType, foreignKeys[0],
                            L1, L2, dataFields[0]);
                    chapter.addSection(section);
                    break;
                case "Q":
                    /**
                     * Substring RecordIdentifier
                     */
                    L0 = foreignKeys[0];
                    L1 = foreignKeys[1];
                    L2 = foreignKeys[2];
                    L3 = foreignKeys[3];
                    String questionText = dataFields[0];
                    String answerText = dataFields[1];

                    /**
                     * *
                     * Check foreign keys for referential integrity
                     */
                    if (!book.getBookIdentifier().equals(L0)) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Question refers to a book that "
                                + "is not equal to the current book");
                        System.err.println("Current Book id : " + book.getBookIdentifier());
                        System.err.println("Question refers to Book id : " + L0);
                        System.err.println("Question id : " + L2);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    chapter = book.getChapterFromChapterIdentification(L1);
                    if (chapter == null) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Question refers to a chapter "
                                + "that could not be found in the current book");
                        System.err.println("Question refers to Chapter id : " + L1);
                        System.err.println("Question id : " + L3);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    section = chapter.getSectionFromSectionIdentification(L2);
                    if (section == null) {
                        System.err.println("----------------");
                        System.err.println("Invalid foreign key reference: "
                                + "Question refers to a section "
                                + "that could not be found in the current chapter");
                        System.err.println("Question refers to Chapter id : " + L1);
                        System.err.println("Question refers to Section id : " + L2);
                        System.err.println("Question id : " + L3);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    /**
                     * Check home key of the question to be adeed for duplicity
                     * in the section list of the current section
                     */
                    if (section.sectionContainsQuestion(L3)) {
                        System.err.println("----------------");
                        System.err.println("Adding duplicate question in section");
                        System.err.println("Chapter id : " + chapter.getChapterIdentifier());
                        System.err.println("Chapter title : " + chapter.getChapterTitle());
                        System.err.println("Section id : " + section.getSectionIdentifier());
                        System.err.println("Section title : " + section.getSectionTitle());
                        System.err.println("Question id : " + L3);
                        System.err.println("Question questiontext : " + dataFields[0]);
                        System.err.println("Question answertext : " + dataFields[1]);
                        System.err.println("----------------");
                        System.exit(9);
                    }
                    /**
                     * *
                     * add current record
                     */
                    Question question = new Question(recordType, L0, L1, L2, L3,
                            questionText, answerText);
                    section.addQuestion(question);
                    break;

                default:
                    System.err.println("----------------");
                    System.err.println("Unknown recordtype reading file");
                    System.err.println("or file is not a valid Lean Driller format");
                    System.err.println("----------------");
                    System.exit(9);
            }
        });

    }

    private void createDataStreamFromView(String bookIdentifier, String bookTitle) {
        /**
         * *
         * Create record for Book
         */
        dataStream = new ArrayList<>();
        dataStream.add(new DataRecord("B", bookIdentifier, bookTitle));

        ArrayList<ChapterPanel> chapterPanelsList
                = Main.getController().bookPanel.getChapterPanels();

        for (ChapterPanel currentChapterPanel : chapterPanelsList) {
            dataStream.add(new DataRecord("C", bookIdentifier
                    + SUBTOKENSEPARATOR
                    + currentChapterPanel.getChapterIdentification(),
                    currentChapterPanel.getChapterTitle()));
            ArrayList<SectionPanel> sectionPanels = currentChapterPanel.sectionPanels;
            for (SectionPanel currentSectionPanel : sectionPanels) {
                /**
                 * Each section contains 1 questionpanel
                 */
                dataStream.add(new DataRecord("S", bookIdentifier
                        + SUBTOKENSEPARATOR
                        + currentSectionPanel.getChapterIdentifier()
                        + SUBTOKENSEPARATOR
                        + currentSectionPanel.getSectionIdentifier(),
                        currentSectionPanel.getSectionTitle()));
                QuestionPanel currentQuestionPanel = currentSectionPanel.getQuestionPanel();
                ArrayList<Question> questions = currentQuestionPanel.
                        getQTABLE().getTableDataModel().getQuestionStream();
                for (Question question : questions) {
                    dataStream.add(new DataRecord("Q", bookIdentifier
                            + SUBTOKENSEPARATOR
                            + currentSectionPanel.getChapterIdentifier()
                            + SUBTOKENSEPARATOR
                            + currentSectionPanel.getSectionIdentifier()
                            + SUBTOKENSEPARATOR
                            + question.getQuestionIdentifier(),
                            question.getQuestionText()
                            + SUBTOKENSEPARATOR
                            + question.getAnswerText()));
                }

            }
        }
    }

    private static String[] subString(String field, Character delimiter) {

        String[] s = field.split(delimiter.toString());
        return s;
    }

}
