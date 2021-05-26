import java.util.Scanner;
import java.util.ArrayList;


public class Sim {

    public static void main(String[] args) {

        // Set up the environment based on the following information 
        // Scanner in = new Scanner(System.in);

        // System.out.println("Please Input albedoOfSurface between 0 and 1");
        // Double albedoOfSurface = in.nextDouble();

        // System.out.println("Please Input blackPercentage between 0 and 100");
        // Double blackPercentage = in.nextDouble();

        // System.out.println("Please Input whitePercentage between 0 and 100");
        // Double whitePercentage = in.nextDouble();

        // System.out.println("Please Input blackAlbedo between 0 and 1");
        // Double blackAlbedo = in.nextDouble();

        // System.out.println("Please Input whiteAlbedo between 0 and 1");
        // Double whiteAlbedo = in.nextDouble();

        // System.out.println("Please Input solarLuminosity between 0 and 3");
        // Double solarLuminosity = in.nextDouble();

        // Global daisyWorld = new Global(albedoOfSurface, blackPercentage, whitePercentage,
        //                                blackAlbedo, whiteAlbedo, solarLuminosity);

        Global daisyWorld = new Global (0.4, 10.0, 25.0, 0.35, 0.75, 1.0);
        daisyWorld.printDaisyWorld();

        

        for(int tick = 1; tick < Params.TICKS; tick ++){
            daisyWorld.tick();
        }


        System.out.println("***********************************");

        ArrayList<Integer> blackPop = daisyWorld.getBlackPopulation();
        ArrayList<Integer> whitePop = daisyWorld.getWhitePopulation();
        ArrayList<Double> temp = daisyWorld.getTemp();

        System.out.println("Black: ");
        System.out.println(blackPop);

        System.out.println("White: ");
        System.out.println(whitePop);

        System.out.println("Temp: ");
        System.out.println(temp);


        daisyWorld.printDaisyWorld();

        


        
    }
    
}
