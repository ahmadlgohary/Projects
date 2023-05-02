/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupProject;

/**
 *
 * @author Ahmad El-Gohary
 */
public class Customers {
    private String username;
    private String password;
    private int points;
    private String Status;

    public Customers(String username, String password, int points ) {
        this.username = username;
        this.password = password;
        this.points   = points;
        setStatus(points); 
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
        setStatus(this.points);
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(int points) {
        if(points<1000){
            this.Status="Sliver";
        }
        else{
            this.Status="Gold";

        }
    }
}