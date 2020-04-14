/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import LeanDriller.Model.Question;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * @author Thomas
 */
public class QuestionsTableDataModel extends DefaultTableModel {

    private final Object[] COLUMNNAMES;
    private final ArrayList<Question> questions;
    private final QuestionPanel questionPanel;

    {
        COLUMNNAMES = new Object[]{"Questions", "Show", "Hide", "Answer"};
        this.addColumn(COLUMNNAMES[0]);
        this.addColumn(COLUMNNAMES[1]);
        this.addColumn(COLUMNNAMES[2]);
        this.addColumn(COLUMNNAMES[3]);
        this.questions = new ArrayList<>();
    }


    public QuestionsTableDataModel(QuestionPanel questionPanel) {
        this.questionPanel = questionPanel;
    }    
       
    /**
     * @param column
     * @return Class
     *
     * The override of getColumnClass method is necessary. The TabelModel
     * associated witha given JTable does not only keep track of the contents of
     * each cell, but it also keeps track of the class of data stored in eacht
     * column. The DefaultTableModel is designed to work with The TableModel
     * associated with a given JTable does not only keep track of the contents
     * of each cell, but it also keeps track of the class of data stored in each
     * column. DefaultTableModel is designed to work with
     * DefaultTableCellRenderer and will return java.lang.String.class for
     * columns containing data types that it does not specifically handle. The
     * exact method that does this is getColumnClass(int column). Your second
     * step is to create a TableModel implementation that returns JButton.class
     * for cells that contain JButtons. JTableButtonModel shows one way to do
     * this. It just returns the result of getClass() for each piece of cell
     * data.
     */
    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    /**
     * @param row
     * @param column
     * @return This override is also necessary. Otherwise, the cell containing
     * the JButton switches to an editing mode after a double click! Presumably
     * this also applies to the JTextArea and other components of the table
     * (integer for chapternumbers and String for the answers)
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void appendQuestion(Question q) {
        
        int row = this.getRowCount();
        this.insertQuestion(row, q); //equivalent to append

    }
    
    public void insertQuestion(int row, Question q) {
        
        this.insertRow(row, new Object[]{
            new QuestionsTableTextArea(q.getQuestionText()).getTextArea(),
            new JButton("Show"),
            new JButton("Hide"),
            new QuestionsTableTextArea().getTextArea()});

        this.questions.add(row, q);

    }

    public void deleteQuestion(int row) {
        this.removeRow(row);
        this.questions.remove(row);
    }

    public Question getQuestion(int row) {
        return questions.get(row);
    }

    public void updateAnswerTextArea(String answer, int row) {
        JTextArea textArea = (JTextArea) this.getValueAt(row, 3);
        textArea.setText(answer);
        this.setValueAt(textArea, row, 3);
    }

    public void updateQuestionTextArea(String answer, int row) {
        JTextArea textArea = (JTextArea) this.getValueAt(row, 0);
        textArea.setText(answer);
        this.setValueAt(textArea, row, 0);
    }

    public void updateText(String text, int row, int column) {

        Question q = this.questions.get(row);

        switch (column) {
            case 0:
                q.setQuestionText(text);
                updateQuestionTextArea(text, row);
                break;
            case 3:
                q.setAnswerText(text);
                updateAnswerTextArea(text, row);
                break;
        }

        this.questions.set(row, q);
        Main.getController().getBaseScreen().getMySaveButton().setWarning();
      
    }

    public void clearAnswer(int row) {
        JTextArea textArea = (JTextArea) this.getValueAt(row, 3);
        textArea.setText("");
        this.setValueAt(textArea, row, 3);
    }

    public void showAllAnswers() {

        for (int row = 0; row < this.getRowCount(); row++) {
            this.updateAnswerTextArea(this.getAnswer(row), row);
        }
    }

    public void claerAllAnswers() {

        for (int row = 0; row < this.getRowCount(); row++) {
            this.clearAnswer(row);
        }

    }

    private String getAnswer(int row) { // only internal use
        return this.questions.get(row).getAnswerText();
    }

    public ArrayList<Question> getQuestionStream() {
        return questions;
    }
    
    public QuestionPanel getQuestionPanel() {
        return this.questionPanel;
    }
    
    public void insertNewQuestion(int row) {
        this.insertQuestion(row, this.createNewQuestion());
    }
    
    public Question createNewQuestion() {
        
        /**
         * this temporary construction does not guarantee absolutely that
         * a unique questionIdentifier is generated, but is used now only to be
         * able to quickly implement this insert above function.
         * To be corrected later
         */
        /***
         * As opposed to the situation with the chapters and sectiontab (where 
         * we only have one method for a new chapter or section), we now have 
         * two different methods: one during the building of the view model in 
         * the section panel class and one during the execution stage, when the
         * user requests a new question.
         */
        
        String bookIdentifier = 
                this.questionPanel.getSectionPanel().getBookIdentifier();
        String chapterIdentifier = 
                this.questionPanel.getSectionPanel().getChapterIdentifier();
        String sectionIdentifier = 
                this.questionPanel.getSectionPanel().getSectionIdentifier();
        Question newQuestion = new Question("Q", bookIdentifier,
                chapterIdentifier, sectionIdentifier,
                BookPanel.getUniqueIdentifier(), 
                "inserted question text", 
                        "inserted answer text");
        return newQuestion;
    }

}
