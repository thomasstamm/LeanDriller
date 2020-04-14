/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Thomas Stamm
 */
public class QuestionEditPopUpMenu extends JPopupMenu {

    private final QuestionsTableMouseListener questionsTableMouseListener;
    private final QuestionsTableDataModel questionsTableDataModel;
    private final int row;
    private final int column;

    public QuestionEditPopUpMenu(QuestionsTableMouseListener questionsTableMouseListener,
            QuestionsTableDataModel questionsTableDataModel,
            int row, int column) {

        this.questionsTableMouseListener = questionsTableMouseListener;
        this.questionsTableDataModel = questionsTableDataModel;
        this.row = row;
        this.column = column;

        createPopup();

    }

    private void createPopup() {
        JMenuItem menuItem01 = this.add(new JMenuItem("Insert Line Above"));
        menuItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionsTableDataModel.insertNewQuestion(row);
                questionsTableMouseListener.closeAllPopUpsForThisTable();
                Main.getController().getBaseScreen().getMySaveButton().setWarning();
            }
        });

        JMenuItem menuItem02 = this.add(new JMenuItem("Insert Line Below"));
        menuItem02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionsTableDataModel.insertNewQuestion(row + 1);
                questionsTableMouseListener.closeAllPopUpsForThisTable();
                Main.getController().getBaseScreen().getMySaveButton().setWarning();
            }
        });

        JMenuItem menuItem03 = this.add(new JMenuItem("Delete line"));
        menuItem03.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionsTableDataModel.deleteQuestion(row);
                questionsTableMouseListener.closeAllPopUpsForThisTable();
                Main.getController().getBaseScreen().getMySaveButton().setWarning();
            }
        });
    }

}
