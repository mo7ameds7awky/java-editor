/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import form.MainForm;
import javax.swing.JFileChooser;

/**
 *
 * @author mohamed
 */
public interface Command {
    
    public void execute(MainForm mainForm, JFileChooser chooser);
    
    public void undo();
    
    public boolean isReversible();
    
}
