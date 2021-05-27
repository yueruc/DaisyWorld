import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Output {

  private Global daisyWorld;

  private String tempFilename;
  private String blackPopFilename;
  private String whitePopFilename;
  private String rabbitPopFilename;

  /**
   * Construct the Output to record the state of the system.
   * @param daisyWorld The state to be recorded
   */
  public Output(Global daisyWorld) {
    this.daisyWorld = daisyWorld;
    LocalDateTime currTime = LocalDateTime.now();
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    String formattedTime = dtFormatter.format(currTime);
    String simFolder = formattedTime + "-simulation";

    String outputFolder = System.getProperty("user.dir") + "/output/" + simFolder;

    try {
      Path path = Paths.get(outputFolder);

      Files.createDirectories(path);

      tempFilename = String.format("%s/temp.csv", outputFolder);
      blackPopFilename = String.format("%s/blackPopulation.csv", outputFolder);
      whitePopFilename = String.format("%s/whitePopulation.csv", outputFolder);
      rabbitPopFilename = String.format("%s/rabbitPopulation.csv", outputFolder);

      File temp = new File(tempFilename);
      temp.createNewFile();
      
      File blackPop = new File(blackPopFilename);
      blackPop.createNewFile();
      
      File whitePop = new File(whitePopFilename);
      whitePop.createNewFile();
      
      File rabbitPop = new File(rabbitPopFilename);
      rabbitPop.createNewFile();
      
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }

  public void recordState() {
    daisyWorld.printDaisyWorld();
  }

}
