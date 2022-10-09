package game;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.*;
import game.Capability.GameEnvironment;
import game.Capability.Gender;
import game.Capability.State;
import game.Dinosaurs.Brachiosaur;
import game.Dinosaurs.Pterodactyl;
import game.Dinosaurs.Stegosaur;
import game.Items.VendingMachine;
import game.Terrain.*;

/**
 * The main class for the Jurassic World game.
 */

public class Application {
    static State state;
    static Gender gender;
    static GameEnvironment gameEnvironment;
    public static GameMap gameMap;
    public static GameMap gameMap2;

    public static void main(String[] args) {
        World world = new World(new Display());


        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Bush(), new Lake());
//
        List<String> map = Arrays.asList(
                "...............................................................................",
                "................................................................................",
                ".....#######....................................................................",
                ".....#_____#...................................................................",
                ".....#_____#.............^...............................^......................",
                ".....###.###.......................................................^............",
                ".........................~~~~.....................^............................",
                ".........................~~~~.........+++.......................................",
                "....~~~~...............................++++.....................................",
                "....~~~~...........................+++++.................~~~~~..................",
                ".....~~................._............++++++..............~~~~~..................",
                "........................_.............+++................~~~~~..................",
                "..................^....._............+++........................................",
                "........................_................................^......................",
                "............+++........._.......................................................",
                ".............+++++..............................................................",
                "...............++........~~~~............................+++++..................",
                ".............+++..........~~.......................++++++++....................",
                "............+++.......................................+++.......................",
                ".................................^..............................................",
                "........................................................................++.....",
                ".........................................................................++.++...",
                "................^........................................................++++...",
                "...................................^......................................++....",
                "................................................................................");

        List<String> map2 = Arrays.asList(
                "...............................................................................",
                ".........................................................#######...............",
                "..............+.+...................................###_##_____#...............",
                "............+...~..+................................#__________#...............",
                "...........+..~~~~~.+...............................####_#######...............",
                "...........+.~~~~~~~.+.........................................................",
                "............+.~~~~~..+.........................................................",
                "............+..~~~..+..........................................................",
                ".............+..~~~.+..........................................................",
                "..............+..~~.+..........................................................",
                "..................+............................................................",
                "...............................................................................",
                ".........................................................++++..................",
                "........................................................+++++++................",
                "........................................................+++++++................",
                ".....................................................++++++++++................",
                "....................................................+++++++++++................",
                "......................................................+++++++++................",
                ".......................................................++++++++................",
                "........................................................+++++++................",
                "..........................................................+++++................",
                "............................................................+++................",
                "...............................................................................",
                "...............................................................................",
                "...............................................................................");

        gameMap = new GameMap(groundFactory, map);
        world.addGameMap(gameMap);

        gameMap2 = new GameMap(groundFactory, map2);
        world.addGameMap(gameMap2);

        Actor player = new Player("Player", '@', 100);
        world.addPlayer(player, gameMap.at(9, 4));

        VendingMachine vendingMachine = new VendingMachine();

        // Place a pair of stegosaurs in the middle of the map
        Stegosaur stegosaur1 = new Stegosaur("Stegosaur");
        stegosaur1.addCapability(gender.FEMALE);
        if (stegosaur1.hasCapability(gender.MALE)) {
            stegosaur1.removeCapability(gender.MALE);
        }

        gameMap.at(30, 12).addActor(stegosaur1);

        Stegosaur stegosaur2 = new Stegosaur("Stegosaur");
        stegosaur2.addCapability(gender.MALE);
        if (stegosaur2.hasCapability(gender.FEMALE)) {
            stegosaur2.removeCapability(gender.FEMALE);
        }
        gameMap.at(32, 12).addActor(stegosaur2);

        //Place a herd of Brachiosaur on the map
        Brachiosaur brachiosaur1 = new Brachiosaur("Brachiosaur");
        gameMap.at(60, 20).addActor(brachiosaur1);

        Brachiosaur brachiosaur2 = new Brachiosaur("Brachiosaur");
        gameMap.at(61, 21).addActor(brachiosaur2);

        Brachiosaur brachiosaur3 = new Brachiosaur("Brachiosaur");
        gameMap.at(61, 19).addActor(brachiosaur3);

        Brachiosaur brachiosaur4 = new Brachiosaur("Brachiosaur");
        gameMap.at(60, 18).addActor(brachiosaur4);

        gameMap.at(10, 6).addItem(new VendingMachine());

        Scanner input = new Scanner(System.in);

        System.out.println("\n+----------------*** WELCOME TO <GAME NAME> ***----------------+\n");
        System.out.println("                 +-------* Main Menu *-------+\n");
        System.out.println("1) Play Challenge");
        System.out.println("2) Play SandBox");
        System.out.println("3) Exit Game\n");
        System.out.print("Enter Game Mode: ");

        int choice = input.nextInt();

        while (choice != 3) {

            if (choice == 2) {
                world.run();
            }

            if (choice == 1) {
                System.out.print("Enter Target ecoPoints: ");
                Player.targetEcoPoints = input.nextInt();

                if (Player.targetEcoPoints != 0) {
                    System.out.print("Enter number of moves: ");
                    Player.specifiedMoves = input.nextInt();

                    if (Player.specifiedMoves != 0) {
                        System.out.println("Target ecoPoints: " + Player.targetEcoPoints + " within " + Player.specifiedMoves + " moves. Good Luck!");
                        world.run();

                    }
                } else if (Player.specifiedMoves == 0) {
                    System.exit(1);
                }
            }
        }
    }
}




