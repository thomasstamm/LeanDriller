package LeanDriller.Model;

import java.util.LinkedList;

public class Chapter {

    private final String recordType;         //"C"
    private final String bookIdentifier;     // L0: overlay DataRecord.recordIdentifier
    private final String chapterIdentifier;  // L1: overlay DataRecord.recordIdentifier
    private String chapterTitle;       // overlay DataRecord.recordData    
    private final LinkedList<Section> sections;

    public Chapter(String recordType, String bookIdentifier,
            String chapterIdentifier, String chapterTitle) {
        this.recordType = recordType;
        this.bookIdentifier = bookIdentifier;
        this.chapterIdentifier = chapterIdentifier;
        this.chapterTitle = chapterTitle;
        this.sections = new LinkedList<>();
    }

    public Section getSectionFromSectionIdentification(String id) {
        Section foundSection = null;
        for (Section section : this.sections) {
            if (section.getSectionIdentifier().equals(id)) {
                foundSection = section;
                break;
            }
        }
        return foundSection;
    }

    public boolean chapterContainsSection(String id) {
        return this.getSectionFromSectionIdentification(id) != null;
    }

    public boolean validateChapterReference(String id) {
        return this.chapterIdentifier.equals(id);
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

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public LinkedList<Section> getSections() {
        return sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }

    public Section createNewSection(String sectionTitle) {
        Integer newSectionIdentifier = this.sections.size() + 1;
        String sectionIdentifier = newSectionIdentifier.toString();
        Section newSection = new Section("S", this.bookIdentifier,
                this.chapterIdentifier, sectionIdentifier, sectionTitle);
        return newSection;
    }

}
