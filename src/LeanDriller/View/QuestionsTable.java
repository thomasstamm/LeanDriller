/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thomas
 */
public class QuestionsTable {

    private final QuestionsTableCustomRenderer customRenderer;
    private final JTable qTable;
    private final QuestionsTableMouseListener qMouseListener;
    private final QuestionsTableDataModel tableDataModel;
    private final QuestionPanel questionPanel;

    public QuestionsTable(QuestionPanel questionPanel) {

        this.questionPanel = questionPanel;
        tableDataModel = new QuestionsTableDataModel(questionPanel);
        qTable = new JTable(tableDataModel);
        qTable.setRowHeight(80);
        qTable.setFillsViewportHeight(true);
        qTable.setPreferredScrollableViewportSize(new Dimension(1100, 500));

        /**
         * Create the mouselistener
         */
        qMouseListener = new QuestionsTableMouseListener(this);
        qTable.addMouseListener(qMouseListener);

        /**
         * First find the default TableCellRenderer of qTable by retrieving the
         * defaultrenderer of the first column. Note: by default the
         * defaultTableModel uses the same renderer for all columns. Then
         * instantiate the QuestionsTableCustomRenderer that requires the
         * default renderer as parameter for the constructor. Finally, add this
         * customrenderer to all columns.
         */
//        Class<?> columnClass = qTable.getColumnClass(0);        
        TableCellRenderer t = qTable.getDefaultRenderer(String.class);
        customRenderer = new QuestionsTableCustomRenderer(t);

        qTable.getColumnModel().getColumn(0).setCellRenderer(customRenderer);
        qTable.getColumnModel().getColumn(1).setCellRenderer(customRenderer);
        qTable.getColumnModel().getColumn(2).setCellRenderer(customRenderer);
        qTable.getColumnModel().getColumn(3).setCellRenderer(customRenderer);

        qTable.getColumnModel().getColumn(0).setPreferredWidth(400);
        qTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        qTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        qTable.getColumnModel().getColumn(3).setPreferredWidth(400);

    }

    JTable getJTable() {
        return this.qTable;
    }

    public QuestionsTableDataModel getTableDataModel() {
        return this.tableDataModel;
    }

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }
    
}
