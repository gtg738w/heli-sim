/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helisim;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author russ
 */
public class heliSimGUIController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfBladeLength;
    @FXML
    private TextField tfBladeRoot;
    @FXML
    private TextField tfBladeChord;
    @FXML
    private TextField tfMass;
    @FXML
    private TextField tfBladeCount;
    @FXML
    private TextField tfPitch;
    @FXML
    private TextField tfHeadSpeed;
    @FXML
    private Button btFixed;
    @FXML
    private Button btHover;
    
    Rotor rotor;
    Airfoil naca0012;
    
    @FXML
    private TextField tfMainGear;
    @FXML
    private TextField tfPinion;
    @FXML
    private TextField tfkV;
    @FXML
    private TextField tfRm;
    @FXML
    private TextField tfI0;
    @FXML
    private TextField tfCellCount;
    @FXML
    private TextField tfCellVoltage;
    @FXML
    private TextField tfPowerIn;
    @FXML
    private TextField tfThrottle;
    @FXML
    private TextField tfIrms;
    @FXML
    private TextField tfEfficiency;
    
    DecimalFormat df = new DecimalFormat("####.##");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rotor = new Rotor();
        naca0012 = new Airfoil();
        naca0012.readFromCSV("/Users/russ/naca0012.txt");
        rotor.setAirfoil(naca0012);
    }    

    @FXML
    private void updateFixed(ActionEvent event) {
    }

    @FXML
    private void updateHover(ActionEvent event) {
        rotor.setBladeLength(Double.valueOf(tfBladeLength.getText()));
        rotor.setRoot(Double.valueOf(tfBladeRoot.getText()));
        rotor.setMass(Double.valueOf(tfMass.getText()));
        rotor.setBladeCount(Integer.valueOf(tfBladeCount.getText()));
        rotor.setChord(Double.valueOf(tfBladeChord.getText()));
        rotor.setHeadSpeed(Double.valueOf(tfHeadSpeed.getText()));
        rotor.setPitch(Double.valueOf(tfPitch.getText()));
        rotor.hover();
        
        Motor m1 = new Motor();
        m1.setkV(Double.valueOf(tfkV.getText()));
        m1.setI0(Double.valueOf(tfI0.getText()));
        m1.setRm(Double.valueOf(tfRm.getText()));
        m1.setTorque(rotor.getQ()*(Double.valueOf(tfPinion.getText())/Double.valueOf(tfMainGear.getText())));
        m1.setV(Double.valueOf(tfCellCount.getText())*Double.valueOf(tfCellVoltage.getText()));
        m1.setRPM(Double.valueOf(tfHeadSpeed.getText())*(Double.valueOf(tfMainGear.getText())/Double.valueOf(tfPinion.getText())));
        tfThrottle.setText(df.format(m1.getPLA()));
        tfIrms.setText(df.format(m1.getIRMS()));
        tfPowerIn.setText(df.format(m1.getPIn()));
        tfEfficiency.setText(df.format(m1.getEfficiency()));
    }
    
}
