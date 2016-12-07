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
public class PolarPt implements Comparable<PolarPt>{
    double alpha, cl, cd;
    
    public PolarPt(double alpha, double cl, double cd){
        this.alpha = alpha;
        this.cl = cl;
        this.cd = cd;
    }

    @Override
    public int compareTo(PolarPt o) {
        return Double.compare(alpha, o.alpha);
    }
}
