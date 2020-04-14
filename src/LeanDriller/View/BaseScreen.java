package LeanDriller.View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseScreen extends JFrame {

    private final String AppTitle;

    ArrayList<ChapterPanel> chapterPanels;
    ArrayList<String> chapterTitles;
    private final Dimension INITIALSIZE;
    private final int INITIALWIDTH;
    private final int INITIALHEIGHT;
    private final MyButton myLoadButton, mySaveButton, myInitializeNewBookButton;
    private JPanel titlePanel;
    Container mainContainer;

    /**
     * Initialization block
     */
    {
        this.AppTitle = "Lean Driller";

        /**
         * Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
         * Screensize height = 768 Screensize width = 1366
         *
         */
        this.INITIALWIDTH = 1300;
        this.INITIALHEIGHT = 700;
        this.INITIALSIZE = new Dimension(INITIALWIDTH, INITIALHEIGHT);
        this.myLoadButton = new MyButton("Load");
        this.mySaveButton = new MyButton("Save");
        this.myInitializeNewBookButton = new MyButton("Initialize New Book");

    }

    public BaseScreen() {
        createBaseScreen();
        setFrameAttributes();
    }

    private void createBaseScreen() {

        /*
         * Create Title Panel at top. FlowLayout is the default layoutmanager of
         * JPanel objects. The deafult FlowLayout constructor results in a
         * centered alignment and a default 5-unit horizontal and vertical gap.
         */
        titlePanel = new JPanel();
        JLabel appLabel = new JLabel(AppTitle, JLabel.CENTER);
        String currentFont = appLabel.getFont().getName();
        appLabel.setFont(new Font(currentFont, Font.BOLD, 26));
        titlePanel.add(appLabel);

        /* 
         * The center of Basescreen is used for de Book panel or the initial
         * load panel.
         * 
         * Create ioPanel at bottom of screen. FlowLayout is the default
         * layoutmanager of JPanel Objects To specify that the row is to aligned
         * either to the left or right, use a FlowLayout constructor that takes
         * an alignment argument with a default 5-unit horizontal and vertical
         * gap
         */
        JPanel ioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        myLoadButton.addActionListener(new LoadActionListener());
        ioPanel.add(myLoadButton);
        mySaveButton.addActionListener(new SaveActionListener());
        ioPanel.add(mySaveButton);
        myInitializeNewBookButton.
                addActionListener(new InitializeNewBookActionListener());
        ioPanel.add(myInitializeNewBookButton);

        /*
         * Remark: the Oracle tutorial warns that the getContentPane method
         * returns a Container object, not a JComponent object. If you need the
         * JComponent features you can either typecast the return value or you
         * can create your own component, for instance a JPanel, to be the
         * content pane. Here we do neither as at this moment we do not need the
         * JComponent features for the ContentPane.
         */
        mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(ioPanel, BorderLayout.PAGE_END); //equivalant to SOUTH

    }

    private void setFrameAttributes() {
        this.setPreferredSize(INITIALSIZE);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public MyButton getMyLoadButton() {
        return myLoadButton;
    }

    public MyButton getMySaveButton() {
        return mySaveButton;
    }


}
