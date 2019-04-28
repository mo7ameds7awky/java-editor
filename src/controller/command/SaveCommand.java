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
public class SaveCommand implements Command{

    /**
     *
     * @param mainForm
     * @param chooser
     */
    @Override
    public void execute(MainForm mainForm, JFileChooser chooser) {
        mainForm.getFileSave().addActionListener((ActionEvent e) -> {
            writeTextAreaToFile(mainForm, chooser);
            mainForm.setSaved(true);
        });
    }
    
    @Override
    public void undo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @return 
     */
    @Override
    public boolean isReversible() {
        return false;
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
    
}
