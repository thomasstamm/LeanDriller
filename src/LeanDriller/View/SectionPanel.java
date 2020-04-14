package LeanDriller.View;

import LeanDriller.Model.Section;
import LeanDriller.Model.Question;
import java.awt.BorderLayout;
import java.util.LinkedList;
import javax.swing.JPanel;

public class SectionPanel extends JPanel {
    
    /**
     * View model fields
     */
    private final QuestionPanel questionPanel;
    private final LinkedList<Question> questions;
    
    /**
     * Attribute fields. In a later version all attribute fields should be
     * updated from the view model in the Book/Controller model via listeners in
     * order to be more in line with MVC principles.
     */
    private final String bookIdentifier;
    private final String chapterIdentifier;
//    private final String chapterTitle;
    private final String sectionIdentifier;
    private String sectionTitle;
    
    
    public SectionPanel(Section section) {
        this.bookIdentifier = section.getBookIdentifier();
        this.chapterIdentifier = section.getChapterIdentifier();
        this.sectionIdentifier = section.getSectionIdentifier();
        this.sectionTitle = section.getSectionTitle();
        this.questions = section.getQuestions();
        
        /**
             * If the section contains no questions, a default question is added
             * This must be done by the view model, since as of the moment of
             * creation of the view model, the view model is leading
             */
        if (questions.isEmpty()) {
            questions.add(this.createNewQuestion("default question text", 
                    "default answer text"));
        }
        
        questionPanel = new QuestionPanel(this, questions);
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
        this.add(questionPanel, BorderLayout.CENTER);
    }

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

    public String getBookIdentifier() {
        return bookIdentifier;
    }
    
    public String getChapterIdentifier() {
        return chapterIdentifier;
    }

    public String getSectionIdentifier() {
        return sectionIdentifier;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }
    
    public void setSectionTitle(String s) {
        this.sectionTitle = s;
    }
       
    public Question createNewQuestion(String questionText, String answerText) {
        Question newQuestion = new Question("Q", this.bookIdentifier,
                this.chapterIdentifier, this.sectionIdentifier,
                BookPanel.getUniqueIdentifier(), questionText, answerText);
        return newQuestion;
    }
    
}
