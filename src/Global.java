import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class Global {
  
  /* The ground of daisy world */
  private Patch[][] ground;

  /* Record global temperature, population of white, black and rabbit */

  private ArrayList<Double> globalTemperature;
  private ArrayList<Integer> whitePopulation;
  private ArrayList<Integer> blackPopulation;
  private ArrayList<Integer> rabbitPopulation;


  /* Need to get from command */
  private Double albedoOfSurface;
  private Double blackPercentage;
  private Double whitePercentage;
  private Double blackAlbedo;
  private Double whiteAlbedo;
  private Double solarLuminosity;
  private Double rabbitPercentage;

  private Sun sun;
   
  /**
   * TBW.
   * @param albedoOfSurface TBW
   * @param blackPercentage TBW
   * @param whitePercentage TBW
   * @param blackAlbedo TBW
   * @param whiteAlbedo TBW
   * @param solarLuminosity TBW
   * @param rabbitPercentage TBW
   */
  public Global(Double albedoOfSurface, Double blackPercentage,
        Double whitePercentage, Double blackAlbedo,
        Double whiteAlbedo, Double solarLuminosity, Double rabbitPercentage) {

    this.albedoOfSurface = albedoOfSurface;
    this.blackPercentage = blackPercentage;
    this.whitePercentage = whitePercentage;
    this.whiteAlbedo = whiteAlbedo;
    this.blackAlbedo = blackAlbedo;
    this.solarLuminosity = solarLuminosity;
    this.rabbitPercentage = rabbitPercentage;
       
    globalTemperature = new ArrayList<>();
    whitePopulation = new ArrayList<>();
    blackPopulation = new ArrayList<>();
    rabbitPopulation = new ArrayList<>();    
  }
  
  /**
   *  TBW.
   */
  public void initialise() {      
    sun = new Sun(solarLuminosity);
  
    ground = new Patch[Params.LENGTH][Params.LENGTH];
  
    // Initialize daisy world
    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        ground[i][j] = new Patch(albedoOfSurface);
      }
    }
  
    // Seed daisies
    seedDaisies(ground, (int) Math.round(blackPercentage * Params.SIZE / 100), 
              (int) Math.floor(whitePercentage * Params.SIZE / 100));   
              
     
    generateRabbits(ground, (int) Math.round(rabbitPercentage * Params.SIZE / 100));  
   
    // Absort from sun
    absorbFromSun();
  
    // record global temperature
    globalTempRecord();
  
    // record population of black daisies & white daisies
    populationRecord();
  }

  /* Calculate temperature after absorbing from sun */
  private void absorbFromSun() {

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        ground[i][j].absorb(sun,i);
      }
    }
  }

  /* Record global temperature at each tick */
  private void globalTempRecord() {

    Double totalTemp = 0.0;

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        totalTemp += ground[i][j].getTemp();
      }
    }

    /* The average temperature of the patches in the world */
    Double currTemp = totalTemp / Params.SIZE;
    globalTemperature.add(currTemp);

  }


  /* Record black & white population on the daisyworld at each tick */
  private void populationRecord() {

    int black = 0;
    int white = 0;
    int rabbit = 0;

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        Patch p = ground[i][j];
        if (!p.openGround()) {
          Daisy d = p.getDaisy();
          if (d.getColor() == Daisy.DaisyColor.BLACK) {
            black += 1;
          } else {
            white += 1;
          }
        }
        if (!p.noRabbitYet()) {
          rabbit += 1;
        }
      }
    }

    blackPopulation.add(black);
    whitePopulation.add(white);
    rabbitPopulation.add(rabbit);

  }

  /* Seed Daisies with particular numbers of black daisies and white daisies */
  private void seedDaisies(Patch[][] ground, int blackNum, int whiteNum) {
    randomSeed(ground, blackNum, Daisy.DaisyColor.BLACK);
    randomSeed(ground, whiteNum, Daisy.DaisyColor.WHITE);       
  }
  
  /* Generate with particular numbers of rabbits */
  private void generateRabbits(Patch[][] ground, int rabbitNum) {
    randomGenerate(ground, rabbitNum);     
  }


  /* Seed daisies at random position with random age */
  private void randomSeed(Patch[][] ground, int num, Daisy.DaisyColor color) {

    Random random = new Random();
    int n = 0;

    while (n < num) {

      int x = random.nextInt(Params.LENGTH);
      int y = random.nextInt(Params.LENGTH);
      int age = random.nextInt(Params.MAXAGE);

      if (ground[x][y].openGround()) {
        Daisy d = new Daisy();
        
        if (color == Daisy.DaisyColor.BLACK) {
          d.setBlack(blackAlbedo, age);
          ground[x][y].setDaisy(d);
        } else if (color == Daisy.DaisyColor.WHITE) {
          d.setWhite(whiteAlbedo, age);
          ground[x][y].setDaisy(d);
        }    
        n++;
      }
    }
  }

  /* Generate Rabbits at random position with random age */
  private void randomGenerate(Patch[][] ground, int num) {

    Random random = new Random();
    int n = 0;

    while (n < num) {

      int x = random.nextInt(Params.LENGTH);
      int y = random.nextInt(Params.LENGTH);
      int age = random.nextInt(Params.MAXAGE);

      if (ground[x][y].noRabbitYet()) {
        Rabbit rb = new Rabbit();
        rb.setRabbitAge(age);
        ground[x][y].setRabbit(rb);  
        n++;
      }
    }
  }
  
  
  /**
   * Display the daisy world.
   */
  public void printDaisyWorld() {

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        
        Patch patch = ground[i][j];
        if (patch.openGround()) {
          System.out.printf(" ");
        } else {
          Daisy daisy = patch.getDaisy();
          System.out.print(daisy);
        }
      }
      System.out.println();
    }
    System.out.println();
  }


  /**
   * Daisy grows up at die when age reach max.
   */
  public void growAndDie() {

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        Patch p = ground[i][j];
        if (!p.openGround()) {
          Daisy d = p.getDaisy();
          d.grow();     
          if (d.getAge() >= Params.MAXAGE) {
            p.setDaisy(null);
          }
        }
        if (!p.noRabbitYet()) {
          Rabbit rb = p.getRabbit();
          rb.grow();
          if (rb.getAge() >= Params.MAXAGE) {
            p.setRabbit(null);
          }
        }
      }
        
    }

  } 

  /**
   *  TBW.
   */
  public void reproduce() {

    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        Patch p = ground[i][j];
        if (!p.openGround()) {
          ArrayList<Patch> neighbors = p.neighbourPatchs(i, j, ground);
          p.sproutDaisyToNeighbour(i, j, neighbors);
        }
        if (!p.noRabbitYet()) {
          ArrayList<Patch> neighbors = p.neighbourPatchs(i, j, ground);
          p.rabbitReproduceToNeighbour(i, j, neighbors);
        }
        
        
      }
    }

  }
  
  /**
   *  TBW.
   */
  public void diffuseToNeighbor() {
    for (int i = 0; i < Params.LENGTH; i++) {
      for (int j = 0; j < Params.LENGTH; j++) {
        Patch p = ground[i][j];
        ArrayList<Patch> neighbors = p.neighbourPatchs(i, j, ground);
        p.diffuse(neighbors);
      }
    }
  }
  
  
  
  /**
   * At each tick, 
   * 1. each patch absorb energy from sun
   * 2. diffuse temperature to neighbours
   * 3. each daisy grow up / die
   * 4. sprout out daisies on neighbour patch
   */
  public void tick() {
    absorbFromSun();
  
    diffuseToNeighbor();
    growAndDie();
    reproduce();

    // record global temperature
    globalTempRecord();
     
    // record population of black daisies & white daisies
    populationRecord();

    
  }


  public ArrayList<Integer> getWhitePopulation() {
    return whitePopulation;
  }

  public ArrayList<Integer> getBlackPopulation() {
    return blackPopulation;
  }
  
  public ArrayList<Integer> getRabbitPopulation() {
    return rabbitPopulation;
  }
  
  public ArrayList<Double> getTemp() {
    return globalTemperature;
  }   
}
