/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.command;

import controller.Command;
import form.MainForm;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author mohamed
 */
public class OpenCommand implements Command{

    @Override
    public void execute(MainForm mainForm, JFileChooser chooser) {
        mainForm.getFileOpen().addActionListener((ActionEvent e) -> {
            // check user continue or cancel
            if (checkSave(mainForm, chooser)) {
                return;
            }
            // loop until user click file .txt or .java
            File fileCheck = null;
            while (true) {
                chooser.showOpenDialog(mainForm);
                fileCheck = chooser.getSelectedFile();
                if (fileCheck.exists()) {
                    break;
                }
                JOptionPane.showMessageDialog(mainForm, "File not found", "Open", JOptionPane.INFORMATION_MESSAGE);
            }
            mainForm.setFile(fileCheck);
            // clear text area
            mainForm.getTxtArea().setText("");
            // write content file to text area
            writeFileToTextArea(mainForm);
            mainForm.setSaved(true);
        });
    }
    
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isReversible() {
        return false;
    }
    
    private boolean checkSave(MainForm mainForm, JFileChooser chooser) {
        String message = "<html><div style = 'color:blue'>Do you want to save the changes to file </div><html>";
        // check user choose yes or no
        boolean checkSaved = mainForm.isSaved();
        if (checkSaved == false) {
            int x = JOptionPane.showConfirmDialog(mainForm, message, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
            if (x == JOptionPane.YES_OPTION) {
                if (mainForm.getFile() == null) {
                    saveAsToFile(mainForm, chooser);
                } else {
                    writeTextAreaToFile(mainForm, chooser);
                }
            }
            //// user click close when x == -1 
            if (x == JOptionPane.CANCEL_OPTION || x == -1) {
                return true;
            }
        }
        return false;
    }
    
    private void saveAsToFile(MainForm mainForm, JFileChooser chooser) {
        File checkFile = null;
        while (true) {
            chooser.showSaveDialog(mainForm);
            checkFile = chooser.getSelectedFile();
            if (!checkFile.exists()) {
                break;
            }
            int option = JOptionPane.showConfirmDialog(mainForm, "Do you want to replace it?", "Save As", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                break;
            }
        }
        mainForm.setFile(checkFile);
        mainForm.setTextCheckSaved("");
        writeTextAreaToFile(mainForm, chooser);
    }
    
    private void writeTextAreaToFile(MainForm mainForm, JFileChooser chooser) {
        FileWriter fout = null;
        try {
            if (mainForm.getFile() == null) {
                saveAsToFile(mainForm, chooser);
                return;
            }
            fout = new FileWriter(mainForm.getFile());
            fout.write(mainForm.getTxtArea().getText());
            mainForm.setTextCheckSaved(mainForm.getTxtArea().getText());
        } catch (IOException ex) {
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
            }
        }
    }
    
    public void writeFileToTextArea(MainForm mainForm) {
        try {
            //TODO check save file old
            FileInputStream fin = new FileInputStream(mainForm.getFile());
            BufferedReader din = new BufferedReader(new InputStreamReader(fin));
            String str = "";
            while (str != null) {
                str = din.readLine();
                if (str == null) {
                    break;
                }
                mainForm.getTxtArea().append(str + "\n");
            }
            // when user open, not change then file save
            mainForm.setTextCheckSaved(mainForm.getTxtArea().getText());
            mainForm.setSaved(true);
            mainForm.getTxtArea().setCaretPosition(0);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    
}
