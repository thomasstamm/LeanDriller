/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thomas
 */
public class QuestionsTableCustomRenderer implements TableCellRenderer {

    private final TableCellRenderer defaultRenderer;

    public QuestionsTableCustomRenderer(TableCellRenderer renderer) {
        defaultRenderer = renderer;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (value instanceof JButton) {
            return (JButton) value;
        }
        
        if (value instanceof JTextArea) {
            return (JTextArea) value;
        }
        
        if (value instanceof JTextPane) {
            return (JTextPane) value;
        }

        return defaultRenderer.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

    }

}
