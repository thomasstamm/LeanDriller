/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Thomas
 */
public class QuestionsTableMouseListener implements MouseListener {

    private final JTable qTable;
    private final QuestionsTableDataModel questionsTableDataModel;
    private final ArrayList<QuestionEditPopUpMenu> registeredPopUpMenus;

    public QuestionsTableMouseListener(QuestionsTable qTable) {
        this.qTable = qTable.getJTable();
        this.questionsTableDataModel = qTable.getTableDataModel();
        this.registeredPopUpMenus = new ArrayList<>();
    }

    private void forwardEventToButton(MouseEvent e) {
        Object value;
        JButton button;
        MouseEvent buttonEvent;

        /**
         * *
         * Check first if the table is empty
         */
        if (qTable.getRowCount() == 0) {
            this.questionsTableDataModel.
                    insertNewQuestion(0);
            Main.getController().getBaseScreen().getMySaveButton().setWarning();
            return;
        }

        /**
         * Retrieving row and column where the mouse event occurred
         */
        TableColumnModel columnModel = qTable.getColumnModel();
        int column = columnModel.getColumnIndexAtX(e.getX());
        int row = e.getY() / qTable.getRowHeight();

        /**
         * Test if row or column values are within range Remark: column and row
         * are zero based. This means that if column or row are equal to
         * columncount or rowcount, they are already out of range.
         */
        if (row >= qTable.getRowCount() || row < 0) {
            this.questionsTableDataModel.
                    insertNewQuestion(qTable.getRowCount());
            Main.getController().getBaseScreen().getMySaveButton().setWarning();
            return;
        }
        /**
         * This situation should not be possible, because the number of columns
         * is fixed (4) and the culumns fille the whole questionpane, so there
         * is no room for clicking to the right of the table
         */
        if (column >= qTable.getColumnCount() || column < 0) {
            System.err.println("----------------");
            System.err.println("Fatal error. User clicked in an area "
                    + "that should not be reachable for the user");
            System.err.println("----------------");
            System.exit(9);
            return;
        }

        /**
         * Check if mouseclick ocurred in column 0 or 3 column = 0: edit answer
         * column = 3: edit question On left click: process edit question text
         * or answer text On right mouse click: activitate QuestionPopUpMen
         */
        if (column == 0 || column == 3) {

            switch (e.getButton()) {

                case MouseEvent.BUTTON1:
                    switch (column) {
                        case 0:
                            processQuestionClick(row, column);
                            break;
                        case 3:
                            processAnswerClick(row, column);
                            break;
                    }
                    break;

                case MouseEvent.BUTTON3: {
                    QuestionEditPopUpMenu questionEditPopUpMenu
                            = new QuestionEditPopUpMenu(this, this.questionsTableDataModel,
                                    row, column);

                    this.registerPopUpForThisTable(questionEditPopUpMenu);
                    questionEditPopUpMenu.setLocation(optimizeX(e), optimizeY(e));
                    questionEditPopUpMenu.setVisible(true);
                }
                break;
            }
            return;
        }

        /**
         * row and column are now in bounds for the JButtons row = 1: show row =
         * 2: hide ) or value 2 (hide) react only on left click, ignore right
         * click it is safe to retrieve the value of the cell and cast to
         * JButton
         */
        value = qTable.getValueAt(row, column);

        /**
         * Moudeclicks in columns 1 and 2 should be oa type JButton
         */
        if (!(value instanceof JButton)) {
            System.out.println("error: columns 1 and 2 of the table s"
                    + "hould only contain Jbuttons");
            return;
        }

        processButtonClick(row, column);
        button = (JButton) value;
        buttonEvent = (MouseEvent) SwingUtilities.convertMouseEvent(qTable, e, button);
//        button.dispatchEvent(buttonEvent);
        qTable.repaint();

    }

    private void processButtonClick(int row, int column) {

        String answer = this.questionsTableDataModel.getQuestion(row).getAnswerText();

        switch (column) {
            case 1:
                this.questionsTableDataModel.updateAnswerTextArea(answer, row);
                break;
            case 2:
                this.questionsTableDataModel.clearAnswer(row);
                break;
            default:
                System.out.println("Error unknown column");
                break;
        }
    }

    private void processQuestionClick(int row, int column) {

        String questionText = questionsTableDataModel.getQuestion(row).getQuestionText();
        TextEditFrame questionEdit
                = new TextEditFrame(questionText, row, column, this.questionsTableDataModel);
        questionEdit.setLocationRelativeTo(qTable);

    }

    private void processAnswerClick(int row, int column) {
        String answerText = questionsTableDataModel.getQuestion(row).getAnswerText();
        TextEditFrame answerEdit
                = new TextEditFrame(answerText, row, column, this.questionsTableDataModel);
        answerEdit.setLocationRelativeTo(qTable);
    }

    public void closeAllPopUpsForThisTable() {
        for (QuestionEditPopUpMenu registeredPopUpMenu : registeredPopUpMenus) {
            registeredPopUpMenu.setVisible(false);
        }
        this.registeredPopUpMenus.clear();
    }

    private void registerPopUpForThisTable(QuestionEditPopUpMenu popUpToBeRegisterd) {
        this.registeredPopUpMenus.add(popUpToBeRegisterd);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        closeAllPopUpsForThisTable();
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

    int optimizeX(MouseEvent e) {
        return e.getX() + 100;
    }

    int optimizeY(MouseEvent e) {

        int rowHeight = this.qTable.getRowHeight();

        int indentY = e.getYOnScreen() - 1 * rowHeight;
        if (indentY < 50) {
            indentY = 50;
        }

        return indentY;
    }

}
