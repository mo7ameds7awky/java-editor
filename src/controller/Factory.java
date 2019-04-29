 
package controller;

/**
 *
 * @author Shams Gamal
 */

public class Factory {
    // use getInstance metod to get object of type controller // ( type of controller edit or file )
    public FactoryController getInstance(String str){
        /*if (str.equals("editcontroller"))
            return new EditController();
        else 
            return new FileController();*/
        
        if (str == null){
            return null;
        }
        if (str.equals("editcontroller")){
            return new EditController();
        }
        if (str.equals("filecontroller")){
            return new FileController();
        }
        return null;
        
    }
    
}
