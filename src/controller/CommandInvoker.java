/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import form.MainForm;
import java.util.Stack;
import javax.swing.JFileChooser;

/**
 *
 * @author mohamed
 */
public class CommandInvoker {

    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    public CommandInvoker() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void execute(Command cmd, MainForm mainForm, JFileChooser chooser) {

        cmd.execute(mainForm, chooser);
        if (cmd.isReversible()) {
            undoStack.push(cmd);
        }

    }

    public void undo() {

        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            redoStack.push(cmd);
            cmd.undo();
        }

    }

    public void redo(MainForm mainForm, JFileChooser chooser) {

        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            undoStack.push(cmd);
            cmd.execute(mainForm, chooser);
        }

    }

}
