/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab5;

import java.util.Scanner;

public class SimpleUI implements UserInterface {
    private Blackjackgame game;
    private Scanner user = new Scanner(System.in);

  @Override
    public void setGame(Blackjackgame game) {
        this.game = game;
    }

  @Override
    public void display() {
        //FIX THIS
        System.out.println("House cards are:" + this.game.getHouseCards().toString());
        System.out.println("You cards are:" + this.game.getYourCards().toString());
    }

  @Override
    public boolean hitMe() {
        //FIX THIS
        boolean hit=true;
        System.out.println("Would you like a Card? yes or no");
        String choice = user.nextLine();
        switch(choice){
            case "yes":
                hit = true;
                break;
            case "no":
                hit = false;
                break;
            }

        return (hit);
    }

  @Override
    public void gameOver() {
        //FIX THIS
         this.display();
        int yourScore = game.score(game.getYourCards());
        int houseScore = game.score(game.getHouseCards());
        System.out.println("House Scored " + houseScore + " points, Your Scored " + yourScore + " points");
        if( (yourScore > houseScore || houseScore > 21) && (yourScore <= 21)){
            System.out.println("You Win!:)");
        }else{
            System.out.println("House Won :(");
        }
    }

}
