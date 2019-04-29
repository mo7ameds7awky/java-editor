package controller;

import controller.command.OpenCommand;
import controller.command.SaveCommand;
import controller.command.ExitCommand;
import controller.command.SaveAsCommand;
import controller.command.NewCommand;
import form.MainForm;
import javax.swing.JFileChooser;

/**
 *
 * @author Sharp team
 */
public class FileController extends Controller implements FactoryController{
    CommandInvoker commandInvoker;

    // controller file
    public void controller(MainForm mainForm) {
        JFileChooser chooser = new JFileChooser();
        commandInvoker = new CommandInvoker();
        setupFileChoose(chooser);
        newFile(mainForm, chooser);
        openFile(mainForm, chooser);
        saveFile(mainForm, chooser);
        saveAsFile(mainForm, chooser);
        exitNote(mainForm, chooser);
        checkClose(mainForm, chooser);
        checkSaved(mainForm);
    }

    // allow user save file
    private void saveFile(MainForm mainForm, JFileChooser chooser) {
        System.out.println("Save Command Triggered");
        SaveCommand savecommand = new SaveCommand();
        this.commandInvoker.execute(savecommand, mainForm, chooser);
    }

    // allow user save file as
    private void saveAsFile(MainForm mainForm, JFileChooser chooser) {
        System.out.println("Save As Command Triggered");
        SaveAsCommand saveAsCommand = new SaveAsCommand();
        this.commandInvoker.execute(saveAsCommand, mainForm, chooser);
    }

    // allow user openfile
    private void openFile(MainForm mainForm, JFileChooser chooser) {
        System.out.println("Open Command Triggered");
        OpenCommand openCommand = new OpenCommand();
        this.commandInvoker.execute(openCommand, mainForm, chooser);
    }

    // create new file
    private void newFile(MainForm mainForm, JFileChooser chooser) {
        System.out.println("New Command Triggered");
        NewCommand newCommand = new NewCommand();
        this.commandInvoker.execute(newCommand, mainForm, chooser);
    }

    // exit app
    private void exitNote(MainForm mainForm, JFileChooser chooser) {
        System.out.println("Exit Command Triggered");
        ExitCommand exitCommand = new ExitCommand();
        this.commandInvoker.execute(exitCommand, mainForm, chooser);
    }

}
