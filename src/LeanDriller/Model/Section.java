package LeanDriller.Model;

import java.util.LinkedList;

public class Section {

    private final String recordType;         //"S"
    private final String bookIdentifier;     // L0: overlay DataRecord.recordIdentifier
    private final String chapterIdentifier;  // L1: overlay DataRecord.recordIdentifier
    private final String sectionIdentifier;  // L2: overlay DataRecord.recordIdentifier 
    private String sectionTitle;             // overlay DataRecord.recordData
    private final LinkedList<Question> questions;

    public Section(String recordType, String bookIdentifier, 
            String chapterIdentifier, String sectionIdentifier, 
            String sectionTitle) {
        this.recordType = recordType;
        this.bookIdentifier = bookIdentifier;
        this.chapterIdentifier = chapterIdentifier;
        this.sectionIdentifier = sectionIdentifier;
        this.sectionTitle = sectionTitle;
        this.questions = new LinkedList<>();
    }
    
     public Question getQuestionFromQuestionIdentification(String id) {
        Question foundQuestion = null;
        for (Question question : this.questions) {
            if (question.getQuestionIdentifier().equals(id)) {
                foundQuestion = question;
                break;
            }
        }
        return foundQuestion;
    }

    public boolean sectionContainsQuestion(String id) {
        return this.getQuestionFromQuestionIdentification(id) != null;
    }

    public String getRecordType() {
        return recordType;
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

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }
    
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public LinkedList<Question> getQuestions() {
        return questions;
    }
    
    
    
}
