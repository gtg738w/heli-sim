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
public class Rotor {
    private double root, bladeLength, chord, headSpeed, rho = 1.225, visc = .000014207, pitch;
    private double omega, T, Q, P, re, mass;
    private Airfoil airfoil;
    int b;
    
    public Rotor(){
        root = 0;
    }
    
    public void setRoot(double root){
        this.root = root;
    }
    
    public void setMass(double m){
        this.mass = m;
    }
    
    public void setBladeLength(double bl){
        this.bladeLength = bl;
    }
    
    public void setChord(double chord){
        this.chord = chord;
    }
    
    public void setHeadSpeed(double hs){
        this.headSpeed = hs;
        omega = 2.*Math.PI*headSpeed/60.;
    }
    
    public void setPitch(double pitch){
        this.pitch = pitch;
    }
    
    public void setAirfoil(Airfoil af){
        this.airfoil = af;
    }
    
    public void setBladeCount(int b){
        this.b = b;
    }
    
    public double getArea(){
        double area;
        area = Math.PI * Math.pow((root + bladeLength),2);
        return area;
    }
    
    public double getIdealPower(double thrust){
        double power, inflow;
        inflow = Math.sqrt(thrust/(2*rho*getArea()));
        power = thrust * inflow;
        return power;
    }
    
    public double getQ(){
        return Q;
    }
    
    public void hover(){
        double weight = 9.81 * mass;
        double error;
        setPitch(5);
        solve();
        error = (weight-T)/weight;
        while (Math.abs(error) > 0.0001){
            pitch = pitch * (1+error/10);
            System.out.println("pitch = " + pitch);
            solve();
            error = (weight-T)/weight;
        }
    }
    
    public void solve(){
        int i, numPts = 100;
        double r, dr, inflow, v, q, alpha=0, phi=0, dT=0, dQ, dA, newInflow, Ct, tipLoss;
        dr = bladeLength/numPts;
        dA = chord * dr;
        T = 0;
        Q = 0;
        for (i=0; i<numPts; i++){
            inflow = 0;
            newInflow = 1;
            r = root + i*dr + 0.5*dr;
            v = omega * r;
            re = v*chord/visc;
            q = 0.5 * rho * Math.pow(v,2);
            while (inflow<newInflow){
                inflow = inflow + 0.01 * newInflow;
                phi = Math.atan(inflow/v);
                alpha = pitch - phi*180/Math.PI;
                dT = b * q * dA * airfoil.getCl(alpha);
                newInflow = Math.sqrt(dT/(4*Math.PI*r*dr));
            }
            Ct = T/(rho*getArea()*(Math.pow(omega*(root+bladeLength),2)));
            tipLoss = 1 - Math.sqrt(2*Ct)/b;
            dQ = b * r * (q * dA * (phi*airfoil.getCl(alpha) + airfoil.getCd(alpha)));
            if (r/(root+bladeLength) < tipLoss){
                T = T + dT;
            }
            Q = Q + dQ;
        }
        //System.out.println("thrust = " + (T));
        //System.out.println("power = " + (omega*Q));
    }
}
