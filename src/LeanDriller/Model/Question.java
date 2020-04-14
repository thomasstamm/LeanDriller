/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.Model;

/**
 *
 * @author Thomas
 */
public class Question {

    private final String recordType;         //"Q"
    private final String bookIdentifier;     // L0: overlay DataRecord.recordIdentifier
    private final String chapterIdentifier;  // L1: overlay DataRecord.recordIdentifier
    private final String sectionIdentifier;  // L2: overlay DataRecord.recordIdentifier 
    private final String questionIdentifier; // L3: overlay DataRecord.recordIdentifier
    private String questionText;             // overlay DataRecord.recordData
    private String answerText;               // overlay DataRecord.recordData
    
    public Question(String recordType, String bookIdentifier,
            String chapterIdentifier, String sectionIdentifier,
            String questionIdentifier, String questionText, String answerText) {
        this.recordType = recordType;
        this.bookIdentifier = bookIdentifier;
        this.chapterIdentifier = chapterIdentifier;
        this.sectionIdentifier = sectionIdentifier;
        this.questionIdentifier = questionIdentifier;
        this.questionText = questionText;
        this.answerText = answerText;
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

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}
