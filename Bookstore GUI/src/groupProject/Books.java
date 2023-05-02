/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import javafx.scene.control.CheckBox;

/**
 *
 * @author ahmad
 */
public class Books {
    private final String bookName;
    private final double bookPrice;
    public CheckBox select;

    public Books(String bookName, double bookPrice) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        select = new CheckBox();
    }

    public String getBookName() {
        return this.bookName;
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
