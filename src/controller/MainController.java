package controller;

import form.MainForm;

/**
 *
 * @author Sharp team
 */
public class MainController {
    public static void main(String[] args) {
        MainForm mainForm = MainForm.getInstance();
        mainForm.setVisible(true);
        
        Factory factory = new Factory();
        
        FactoryController file = factory.getInstance("filecontroller");
        file.controller(mainForm);
        
        FactoryController edit = factory.getInstance("editcontroller");
        edit.controller(mainForm);
    }
}
