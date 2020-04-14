package LeanDriller.View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextEditFrame extends JFrame {

    private final String textToBeEdited;
    private final int row, column;
    private JTextArea textArea;
    private final QuestionsTableDataModel qTable;
    private String appTitle;
    private final Dimension initialFrameSize, initialTextAreaSize;
    private final int initialFrameWidth;
    private final int initialFrameHeight;
    private final MyButton mySaveTextButton, myCloseFrameButton;

    /**
     * Iniialization block
     */
    {
        this.appTitle = "Edit ";
        this.initialFrameWidth = 500;
        this.initialFrameHeight = 300;
        this.initialFrameSize = new Dimension(initialFrameWidth, initialFrameHeight);
        this.initialTextAreaSize = new Dimension(400, 80);
        this.mySaveTextButton = new MyButton("Save");
        this.myCloseFrameButton = new MyButton("Close");
        this.setTitle(appTitle);
    }

    public TextEditFrame(String textToBeEdited, int row, int column,
            QuestionsTableDataModel qTable) {
        this.textToBeEdited = textToBeEdited;
        this.row = row;
        this.column = column;
        this.qTable = qTable;
        createLayout();
        setFrameAttributes();
    }

    private void createLayout() {

        /**
         * Create Title Panel at top. FlowLayout is the default layoutmanager of
         * JPanel objects. The deafult FlowLayout constructor results in a
         * centered alignment and a default 5-unit horizontal and vertical gap.
         */
        switch (this.column) {
            case 0:
                appTitle = appTitle.concat("Question");
                break;
            case 3:
                appTitle = appTitle.concat("Answer");
                break;
        }

        JPanel titlePanel = new JPanel();
        JLabel appLabel = new JLabel(appTitle, JLabel.CENTER);
        String currentFont = appLabel.getFont().getName();
        appLabel.setFont(new Font(currentFont, Font.BOLD, 16));
        titlePanel.add(appLabel);

        /**
         * Create a panel to display the taxt area to be edited in the center
         * panel
         */
        JPanel textAreaPanel = new JPanel();
        this.textArea = new JTextArea();
        textArea.setText(textToBeEdited);
        textArea.setLineWrap(true);
        textArea.setPreferredSize(initialTextAreaSize);
        textAreaPanel.add(textArea);

        /**
         * Create ioPanel at bottom of screen FlowLayout is the default
         * layoutmanager of JPanel Objects To specify that the row is to aligned
         * either to the left or right, use a FlowLayout constructor that takes
         * an alignment argument with a default 5-unit horizontal and vertical
         * gap
         */
        JPanel ioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mySaveTextButton.addActionListener(new SaveTextActionListener(this.row,
                this.column, this.qTable, this));
        ioPanel.add(mySaveTextButton);
        
        myCloseFrameButton.addActionListener(new CloseTextActionListener(this));
        ioPanel.add(myCloseFrameButton);
        
        /**
         * Remark: the Oracle tutorial warns that the getContentPane method
         * returns a Container object, not a JComponent object. If you need the
         * JComponent features you can either typecast the return value or you
         * can create your own component, for instance a JPanel, to be the
         * content pane. Here we do neither as at this moment we do not need the
         * JComponent features for the ContentPane.
         */

        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(textAreaPanel, BorderLayout.CENTER);
        mainContainer.add(ioPanel, BorderLayout.PAGE_END); //equivalant to SOUTH
    }

    private void setFrameAttributes() {
        this.setPreferredSize(initialFrameSize);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public MyButton getMySaveTextButton() {
        return mySaveTextButton;
    }

    public String getEditedText() {
        return this.textArea.getText();
    }

}

class SaveTextActionListener implements ActionListener {

    private final int row, column;
    private final QuestionsTableDataModel qTable;
    private final Object source;

    public SaveTextActionListener(int row, int column,
            QuestionsTableDataModel qTable, Object source) {

        this.row = row;
        this.column = column;
        this.qTable = qTable;
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String editedText = ((TextEditFrame) source).getEditedText();
        ((TextEditFrame) source).dispose();
        qTable.updateText(editedText, row, column);

    }
   
}

class CloseTextActionListener implements ActionListener {

    private final Object source;

    public CloseTextActionListener(Object source) {
        
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        ((TextEditFrame) source).dispose();

    }
   
}
