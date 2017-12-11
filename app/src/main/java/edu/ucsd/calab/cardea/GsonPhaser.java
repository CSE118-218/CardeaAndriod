package edu.ucsd.calab.cardea;

/**
 * Created by zht on 11/22/17.
 */

public class GsonPhaser {

    public Double walking;
    public Double running;
    public Double sitting;
    public Double standing;
    public Double lying;
    // standard getters & setters...

    public GsonPhaser(Double walking, Double running, Double sitting, Double standing, Double lying){
        this.walking = walking;
        this.running = running;
        this.sitting = sitting;
        this.standing = standing;
        this.lying = lying;

    }

}
