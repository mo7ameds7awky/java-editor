/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import form.MainForm;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author mohamed
 */
public class Controller {
    
    // setup file chooser
    protected void setupFileChoose(JFileChooser chooser) {
        // allow user choose file .java 
        chooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().endsWith(".java");
                }
            }

            @Override
            public String getDescription() {
                return "Java Source File(*.java)";
            }
        });

        // allow user choose file .txt
        chooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().endsWith(".txt");
                }
            }

            @Override
            public String getDescription() {
                return "Text Files(*.txt)";
            }
        });

        // set current directory
        chooser.setCurrentDirectory(new File("."));

    }
    
    // write file from file to text area
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

    // write text area to file
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

    // save as write to file
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

    // check change saved
    protected void checkSaved(MainForm mainForm) {
        mainForm.getTxtArea().addCaretListener((CaretEvent e) -> {
            if (!mainForm.getTxtArea().getText().equals(mainForm.getTextCheckSaved())) {
                mainForm.setSaved(false);
            }
        });
    }

    // check user choose yes or no or cancel in dialog
    protected boolean checkSave(MainForm mainForm, JFileChooser chooser) {
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

    // check user want to close or not
    protected void checkClose(MainForm mainForm, JFileChooser chooser) {
        mainForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
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
                    // user click close
                    if (x == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                    // user click no
                    if (x == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
    }
    
}
