/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.command;

import controller.Command;
import form.MainForm;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author mohamed
 */
public class SaveAsCommand implements Command{

    @Override
    public void execute(MainForm mainForm, JFileChooser chooser) {
        mainForm.getFileSaveAs().addActionListener((ActionEvent e) -> {
            // check user continue or cancel
            if (checkSave(mainForm, chooser)) {
                return;
            }
            saveAsToFile(mainForm, chooser);
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
    
    
}
