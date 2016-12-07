/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helisim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author russ
 */
public class HeliSim extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("heliSimGUI.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        Airfoil naca0012 = new Airfoil();
        naca0012.readFromCSV("/Users/russ/naca0012.txt");
        
        Rotor oxy3 = new Rotor();
        oxy3.setBladeLength(0.25);
        oxy3.setRoot(0.03);
        oxy3.setMass(0.55);
        oxy3.setAirfoil(naca0012);
        oxy3.setBladeCount(2);
        oxy3.setChord(.0254);
        oxy3.setHeadSpeed(3000);
        oxy3.setPitch(12);
        oxy3.hover();
        
        Motor m1 = new Motor();
        m1.setkV(3590);
        m1.setI0(1.21);
        m1.setRm(0.061);
        m1.setTorque(oxy3.getQ()*(13./140.));
        m1.setV(3*3.7);
        m1.setRPM(3000*(140./13.));
        
        //System.out.println(m1.getPLA());
        
        
    }
    
}
