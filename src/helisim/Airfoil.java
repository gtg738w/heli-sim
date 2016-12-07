/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helisim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author russ
 */
public class Airfoil {
    public ArrayList<PolarPt> polar;

    public Airfoil(){
        polar = new ArrayList<>();
    }
    
    public void addPt(PolarPt pt){
        polar.add(pt);
    }
    
    public double getCl(double alpha){
        int ind;
        ind = getAlphaIndex(alpha);
        double alpha1, alpha2, cl1, cl2;
        alpha1 = polar.get(ind).alpha;
        alpha2 = polar.get(ind+1).alpha;
        cl1 = polar.get(ind).cl;
        cl2 = polar.get(ind+1).cl;
        
        double slope = (cl2-cl1)/(alpha2-alpha1);
        double cl = slope * (alpha - alpha1) + cl1;
        
        return cl;
        
    }
    
    public double getCd(double alpha){
        int ind;
        ind = getAlphaIndex(alpha);
        double alpha1, alpha2, cd1, cd2;
        alpha1 = polar.get(ind).alpha;
        alpha2 = polar.get(ind+1).alpha;
        cd1 = polar.get(ind).cd;
        cd2 = polar.get(ind+1).cd;
        
        double slope = (cd2-cd1)/(alpha2-alpha1);
        double cd = slope * (alpha - alpha1) + cd1;
        
        return cd;
    }
    
    public int getAlphaIndex(double alpha){
        int i;
        for (i=0; i<(polar.size()-2); i++){
            if ((polar.get(i).alpha <= alpha) && (alpha < polar.get(i+1).alpha)){
                break;
            }
        }
        return i;
    }
    
    public void readFromCSV(String file){
        try {
            BufferedReader input =  new BufferedReader(new FileReader(file));
            String line = "";
            while (( line = input.readLine()) != null) {
                String[] data = line.split(" +");
                this.addPt(new PolarPt(Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3])));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }
}
