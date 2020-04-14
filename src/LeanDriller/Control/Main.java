package LeanDriller.Control;

public class Main {

    private static LeanDrillerController leanDrillerController;

    public static void main(String[] args) {
    
        leanDrillerController = new LeanDrillerController();
        leanDrillerController.startAppliction();
    }

    public static LeanDrillerController getController() {
        return leanDrillerController;
    }
    
}
