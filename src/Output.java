import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Output {

  private Global daisyWorld;
  private String fileName;

  /**
   * Construct the Output to record the state of the system.
   * @param daisyWorld The state to be recorded
   */
  public Output(Global daisyWorld) {
    this.daisyWorld = daisyWorld;
    LocalDateTime currTime = LocalDateTime.now();
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    String formattedTime = dtFormatter.format(currTime);
    fileName = formattedTime + "-simulation.csv";

    try {
      File outputFile = new File(fileName);
      if (outputFile.createNewFile()) {
        System.out.println(outputFile + " created");
      } else {
        System.err.println("Failed to create file");
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }

  public void recordState() {
    daisyWorld.printDaisyWorld();
  }

}
