package edu.ucsd.calab.usingextrasensorylabels;

/**
 * Created by zht on 11/22/17.
 */

public class GsonPhaser {

    private Double walking;
    private Double running;
    private Double sitting;
    private Double standing;
    private Double lying;
    // standard getters & setters...

    public GsonPhaser(Double walking, Double running, Double sitting, Double standing, Double lying){
        this.walking = walking;
        this.running = running;
        this.sitting = sitting;
        this.standing = standing;
        this.lying = lying;

    }

}
