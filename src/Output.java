import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

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
      writeCsv(tempFilename, null, daisyWorld.getTemp());
      
      File blackPop = new File(blackPopFilename);
      blackPop.createNewFile();
      writeCsv(blackPopFilename, daisyWorld.getBlackPopulation(), null);
      
      File whitePop = new File(whitePopFilename);
      whitePop.createNewFile();
      writeCsv(whitePopFilename, daisyWorld.getWhitePopulation(),null);

      
      File rabbitPop = new File(rabbitPopFilename);
      rabbitPop.createNewFile();
      writeCsv(rabbitPopFilename, daisyWorld.getRabbitPopulation(), null);
            
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }


  public void writeCsv(String filename, ArrayList<Integer> dataInt, ArrayList<Double> dataDouble){

    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      for (Integer i = 1; i <= Params.TICKS; i++) {
        if (i < Params.TICKS) {
            writer.write(i.toString() + ',');
        } else {
            writer.write(i.toString());
        }
      }
      writer.newLine();
      if (dataInt == null){
        for (Integer i = 0; i < dataDouble.size(); i++) {
          if (i < dataDouble.size() - 1) {
              writer.write(dataDouble.get(i).toString() + ',');
          } else {
              writer.write(dataDouble.get(i).toString());
          }
        }
      }else if (dataDouble == null){
        for (Integer i = 0; i < dataInt.size(); i++) {
          if (i < dataInt.size() - 1) {
              writer.write(dataInt.get(i).toString() + ',');
          } else {
              writer.write(dataInt.get(i).toString());
          }
        }
      }
      writer.close();

     

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    
  }

  public void recordState() {
    daisyWorld.printDaisyWorld();
  }

}
