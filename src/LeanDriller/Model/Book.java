package LeanDriller.Model;

import java.util.LinkedList;

public class Book {

    private final String recordType; //"B"
    private final String bookIdentifier; // overlay DataRecord.recordIdentifier
    private String bookTitle; // overlay DataRecord.recordData
    private final LinkedList<Chapter> chapters;
    private String bookPath;

    public Book(String recordType, String bookIdentifier, String bookTitle) {
        this.recordType = recordType;
        this.bookIdentifier = bookIdentifier;
        this.bookTitle = bookTitle;
        this.chapters = new LinkedList<>();
    }
    
    public Chapter getChapterFromChapterIdentification(String id) {
        Chapter foundChapter = null;
        for (Chapter chapter : this.chapters) {
            if (chapter.getChapterIdentifier().equals(id)) {
                foundChapter = chapter;
                break;
            }
        }
        return foundChapter;
    }

    public boolean bookContainsChapter(String id) {
        return this.getChapterFromChapterIdentification(id) != null;
    }
    
    public boolean validateBookreference(String id) {
        return this.bookIdentifier.equals(id);
    }

    public String getRecordType() {
        return recordType;
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

    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }

    public LinkedList<Chapter> getChapters() {
        return chapters;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }
    
    public void listBook() {
        System.out.println("BookID = " + this.bookIdentifier + 
                " Title = " + this.bookTitle);

        for (Chapter chapter : this.chapters) {
            System.out.println("BookID = " + this.bookIdentifier + 
                    " Chapter ID = " + chapter.getChapterIdentifier()
                    + " Chapter Title  = " + chapter.getChapterTitle());
            for (Section section : chapter.getSections()) {
                System.out.println("BookID = " + this.bookIdentifier + 
                        " Chapter ID = " + chapter.getChapterIdentifier() +
                        " Section id = " + section.getSectionIdentifier() 
                        + " Section title = " + section.getSectionTitle());
                for (Question question : section.getQuestions()) {
                    System.out.println("Question ID = " + question.getQuestionIdentifier());
                    System.out.println("Question = " + question.getQuestionText());
                    System.out.println("Answer = " + question.getAnswerText());
                }
            }
        }
    }

}
