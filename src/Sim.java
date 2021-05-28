public class Sim {

  /**
   * This method will coordinate all components of the system.
   * @param args Command line arguments
   */
  public static void main(String[] args) {

    //Set up the environment based on the following information 
    Double albedoOfSurface = 0.4;
    Double blackPercentage = 20.0;
    Double whitePercentage = 20.0;
    Double blackAlbedo = 0.25;
    Double whiteAlbedo = 0.75;
    Double solarLuminosity = 1.0;
    Double rabbitPercentage = 20.0;

    // Use these factors to set up daisyworld, which is same as NetLogo model
    Global daisyWorld = new Global(albedoOfSurface, blackPercentage, whitePercentage,
                                   blackAlbedo, whiteAlbedo, solarLuminosity, rabbitPercentage);

    daisyWorld.initialise();
    
    for (int tick = 1; tick < Params.TICKS; tick++) {
      daisyWorld.tick();
    }
    
    Output output = new Output(daisyWorld);

    output.recordState();
  }
    
}
