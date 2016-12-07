/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helisim;

/**
 *
 * @author russ
 */
public class Motor {
    private double I0, kV, Rm, kt, km, J, RPM, V, IRMS, PLA, pIn, pMech;
    
    public void setI0(double I0){
        this.I0 = I0;
    }
    
    public void setkV(double kV){
        this.kV = kV;
        this.kt = 60/(2*Math.PI*kV);
        //System.out.println("kt = " + kt);
    }
    
    public void setRm(double Rm){
        this.Rm = Rm;
        if (kV != 0){
            
        }
    }
    
    public void setTorque(double J){
        this.J = J/0.85;
    }
    
    public void setRPM(double RPM){
        this.RPM = RPM;
    }
    
    public double getI(){
        return J/kt + I0;
    }
    
    public void setV(double V){
        this.V = V;
    }
    
    public double getPLA(){
        double dV, I, pCond, pIron;
        dV = V - RPM/kV;
        I = dV/Rm;
        PLA = (J + I0*kt)/(I*kt);
        pIn = I*V*PLA;
        pMech = RPM*(2.*Math.PI/60.)*J;
        IRMS = I*Math.sqrt(PLA);
        pCond = IRMS*Rm;
        pIron = I0*kt*RPM*(2.*Math.PI/60.);
        //System.out.println("power = " + pIn + "  Irms = " + IRMS + "  dc = " + PLA + "  eff = " + pMech/pIn);
        return PLA;
    }
    
    public double getPIn(){
        return pIn;
    }
    
    public double getIRMS(){
        return IRMS;
    }
    
    public double getEfficiency(){
        return (pMech/pIn);
    }
    
    
    
}
