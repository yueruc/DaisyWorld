import java.util.Scanner;



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

        for(int tick = 0; tick < Params.TICKS; tick ++){
            daisyWorld.tick();
            tick ++;
        }


        System.out.println("***********************************");


        daisyWorld.printDaisyWorld();

        


        
    }
    
}
