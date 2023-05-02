/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

/**
 *
 * @author ahmad
 */
public class Customers {
    private final String username;
    private final String password;
    private int points;
    private String status; //just testing have to use status abstract class and implement state design pattern

    Customers(String username, String password) {
        this.username = username;
        this.password = password;
        points = 0;
        setStatus(points);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = this.points + points;
        setStatus(this.points);
    }

    public String getStatus() {
        return status;
    }
    private void setStatus(int points) {
        if(points>1000){
            status = "Gold";
        }
        else status = "Silver";
    }
}
