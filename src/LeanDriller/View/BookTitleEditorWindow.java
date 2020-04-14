/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.View;

import LeanDriller.Control.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author thoma
 */
public class BookTitleEditorWindow extends JFrame implements ActionListener {

    private final JTextField bookTitleTextField;
    private final JTextField bookPathTextField;
    private String bookTitle;
    private final String bookPath;

    public BookTitleEditorWindow(String bookTitle, String bookPath) {
        super("Edit Book Title");
        super.setLayout(new BorderLayout());
        bookTitleTextField = new JTextField(50);
        bookPathTextField = new JTextField(50);
        this.bookTitle = bookTitle;
        this.bookPath = bookPath;
        createWindow();
    }

    private void createWindow() {

        // Define the components
        JPanel bookTitlePanel = new JPanel();
        bookTitleTextField.setText(this.bookTitle);
        bookTitleTextField.addActionListener(this);
        bookTitlePanel.add(bookTitleTextField);
        this.add(bookTitlePanel, BorderLayout.NORTH);

        JPanel bookPathPanel = new JPanel();
        this.bookPathTextField.setText(this.bookPath);
        bookPathPanel.add(bookPathTextField);
        this.add(bookPathPanel, BorderLayout.SOUTH);

        //Display the window.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
//        this.setSize(300, 200); // or pack() the components
        /**
         * setLocation should be called after setSize() or pack()
         */
        this.setLocationRelativeTo(bookTitlePanel); // setLocation should be called after setSize()
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.bookTitle = bookTitleTextField.getText();
        Main.getController().bookPanel.setTextBookTitleLabel(bookTitle);
        Main.getController().bookPanel.setBookTitle(bookTitle);
        Main.getController().getBaseScreen().getMySaveButton().setWarning();
        this.setVisible(false);
    }

}
