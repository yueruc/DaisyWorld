import java.util.Scanner;

public class Sim {

  /**
   * This method will coordinate all components of the system.
   * @param args Command line arguments
   */
  public static void main(String[] args) {

    //Set up the environment based on the following information 
    Scanner in = new Scanner(System.in);

    System.out.println("Please Input albedoOfSurface between 0 and 1");
    Double albedoOfSurface = in.nextDouble();

    System.out.println("Please Input blackPercentage between 0 and 100");
    Double blackPercentage = in.nextDouble();

    System.out.println("Please Input whitePercentage between 0 and 100");
    Double whitePercentage = in.nextDouble();

    System.out.println("Please Input blackAlbedo between 0 and 1");
    Double blackAlbedo = in.nextDouble();

    System.out.println("Please Input whiteAlbedo between 0 and 1");
    Double whiteAlbedo = in.nextDouble();

    System.out.println("Please Input solarLuminosity between 0 and 3");
    Double solarLuminosity = in.nextDouble();

    System.out.println("Please Input solarLuminosity between 0 and 100");
    Double rabbitPercentage = in.nextDouble();

    // Use these factors to set up daisyworld, which is same as NetLogo model
    Global daisyWorld = new Global(albedoOfSurface, blackPercentage, whitePercentage,
                                   blackAlbedo, whiteAlbedo, solarLuminosity, rabbitPercentage);

    //Global daisyWorld = new Global(0.4, 20.0, 20.0, 0.25, 0.75, 1.1, 20.0);
    daisyWorld.initialise();
    
    for (int tick = 1; tick < Params.TICKS; tick++) {
      daisyWorld.tick();
    }
    
    Output output = new Output(daisyWorld);

    output.recordState();
    in.close();
  }
    
}
