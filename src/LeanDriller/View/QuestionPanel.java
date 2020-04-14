
package LeanDriller.View;

import LeanDriller.Model.Question;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class QuestionPanel extends JPanel implements ActionListener {
    
    private JButton showAll, clearAll;
    private final QuestionsTable QTABLE;
    private final SectionPanel sectionPanel;

    public QuestionPanel(SectionPanel sectionPanel,LinkedList<Question> questions) {
        
        this.sectionPanel = sectionPanel;
        this.QTABLE = new QuestionsTable(this);
         createLayout();
         
         for (Question question : questions) {
                    this.QTABLE.getTableDataModel().appendQuestion(question);
         }
    }

    /**
     * Method must be private, otherwise IDE gives warning Overridable method
     * call in constructor
     */
    private void createLayout() {
        /**
         * A border layout lays out a container, arranging and resizing its
         * components to fit in five regions: north, south, east, west, and
         * center. Each region may contain no more than one component, and is
         * identified by a corresponding constant: NORTH, SOUTH, EAST, WEST, and
         * CENTER
         */
        this.setLayout(new BorderLayout());

       
        /**
         * Add the questionstabel to a JScrollPane and add the scrollPane to
         * the centerPanel. Remark: BoxLayout is used here instead of the
         * default layoutmanager FlowLayout. With FlowLayout the scrollbar is
         * only visible when the screen is maximized, not when the basescreen is
         * opened with the initialsezie of 1300*700
         */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,
                BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(this.QTABLE.getJTable());
        
        centerPanel.add(scrollPane);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        /**
         * bottomPanel and load & clear buttons
         */
        JPanel bottomPanel = new JPanel();
        showAll = new JButton("Show all");
        showAll.addActionListener(this);
        clearAll = new JButton("Hide all");
        clearAll.addActionListener(this);

        bottomPanel.add(showAll);
        bottomPanel.add(clearAll);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "Show all":
                QTABLE.getTableDataModel().showAllAnswers();
                break;
            case "Hide all":
                QTABLE.getTableDataModel().claerAllAnswers();
                break;
            default:
                System.out.println("error QuestionPanel: unknown button");
        }

    }

    public QuestionsTable getQTABLE() {
        return QTABLE;
    }

    public SectionPanel getSectionPanel() {
        return sectionPanel;
    }
    
}
