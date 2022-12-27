/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupProject;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Ahmad El-Gohary
 */

public class Books {

    private final String title;
    private final double bookPrice;
    public CheckBox select;

    public Books(String title, double bookPrice) {
        this.title = title;
        this.bookPrice = bookPrice;
        select = new CheckBox();
    }

    public String getTitle() {
        return this.title;
    }

    public double getBookPrice() {
        return this.bookPrice;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
}
