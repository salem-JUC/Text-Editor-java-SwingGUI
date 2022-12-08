/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package textEditor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author salem
 */
class appFrame extends JFrame {

    private JTextArea taNote;
    private Operations pnlOperations;
    private JPanel pnlText;
    private JScrollPane pan;
    File SelectedFile;

    public appFrame() {
        super("Notes Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500);
        pnlText = new JPanel();
        pnlText.setLayout(new BorderLayout(5, 5));
        taNote = new JTextArea();
        pan = new JScrollPane(taNote, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlOperations = new Operations();
        add(pnlOperations, BorderLayout.EAST);

        pnlText.add(taNote);
        add(pnlText, BorderLayout.CENTER);

    }

    public class Operations extends JPanel {

        PrintWriter file;
        Scanner inputStream = null;
        private JButton btnSave, btnSaveAs, btnLoad, btnInfo;
        FileFilter filter;

        public Operations() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(4, 4, 4, 4);

            btnSave = new JButton("Save");
            btnSave.setEnabled(false);
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Selected File btnSave action" + SelectedFile);
                    try {
                        file = new PrintWriter(new FileOutputStream(SelectedFile.getPath()));
                        String text = taNote.getText();
                        file.print(text);

                    } catch (FileNotFoundException ex) {

                    }
                    file.close();

                }
            });
            btnSaveAs = new JButton("Save As");

            btnSaveAs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String Text;
                    if (!taNote.getText().equals("")) {

                        Text = taNote.getText();

                        JFileChooser choser = new JFileChooser();
                        choser.showSaveDialog(null);
                        System.out.println(choser.getSelectedFile());
                        try {
                            if (choser.getSelectedFile().isFile()) {
                                file = new PrintWriter(new FileOutputStream(choser.getSelectedFile() + ".txt"));

                                file.print(Text);
                            }

                        } catch (FileNotFoundException ex) {

                        }
                        file.close();
                    } else {
                        System.out.println("Text Filed is empty");
                    }

                }
            });
            btnLoad = new JButton("Load");

            btnLoad.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser choser = new JFileChooser();

                    choser.addChoosableFileFilter(filter);

                    choser.showOpenDialog(null);

                    try {
                        if (choser.getSelectedFile().isFile()) {
                            inputStream = new Scanner(new FileInputStream(choser.getSelectedFile()));
                        }
                    } catch (Exception ex) {

                    }

                    String Text = null;
                    while (inputStream.hasNextLine()) {
                        Text += inputStream.nextLine() + '\n';
                    }
                    taNote.setText(Text);
                    EnableSave(choser.getSelectedFile().getPath());
                    inputStream.close();
                }
            });

            btnInfo = new JButton("Info");
            btnInfo.setEnabled(false);

            add(btnSave, gbc);
            gbc.gridy++;
            add(btnSaveAs, gbc);
            gbc.gridy++;
            add(btnLoad, gbc);
            gbc.gridy++;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.SOUTH;

            add(btnInfo, gbc);
        }

        void EnableSave(String FilePath) {
            btnSave.setEnabled(true);
            btnInfo.setEnabled(true);
            SelectedFile = new File(FilePath);

            Date lastMod = new Date(SelectedFile.lastModified());
            System.out.println(lastMod);

        }
    }

}
