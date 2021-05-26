import java.util.ArrayList;
import java.util.Random;


public class Global {
    
    /* The ground of daisy world */
    private Patch[][] ground;

    private ArrayList<Double> globalTemperature;
    private ArrayList<Integer> whitePopulation;
    private ArrayList<Integer> blackPopulation;


    /* Need to get from command */
    private Double albedoOfSurface;
    private Double blackPercentage;
    private Double whitePercentage;
    private Double blackAlbedo;
    private Double whiteAlbedo;
    private Double solarLuminosity;

    private Sun sun;
   

    public Global(Double albedoOfSurface, Double blackPercentage,
                Double whitePercentage, Double blackAlbedo,
                Double whiteAlbedo, Double solarLuminosity){

        this.albedoOfSurface = albedoOfSurface;
        this.blackPercentage = blackPercentage;
        this.whitePercentage = whitePercentage;
        this.whiteAlbedo = whiteAlbedo;
        this.blackAlbedo = blackAlbedo;
        this.solarLuminosity = solarLuminosity;

        sun = new Sun(solarLuminosity);

        ground = new Patch[Params.LENGTH][Params.LENGTH];

        // Initialize daisy world
        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                ground[i][j] = new Patch(albedoOfSurface);
            }
        }

        globalTemperature = new ArrayList<>();
        whitePopulation = new ArrayList<>();
        blackPopulation = new ArrayList<>();


        // Seed daisies
        seedDaisies(ground, blackPercentage * Params.SIZE / 100, whitePercentage * Params.SIZE / 100);

        

        // record global temperature
        globalTempRecord();

        // record population of black daisies & white daisies
        populationRecord();
    }

    /* Calculate temperature after absorbing from sun */
    private void absorbFromSun() {

        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                ground[i][j].absorb(sun);
            }
        }
    }

    /* Record global temperature at each tick */
    private void globalTempRecord(){

        // Absort from sun
        absorbFromSun();

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
    private void populationRecord(){

        int black = 0;
        int white = 0;

        for (int i = 0; i < Params.LENGTH; i ++){
            for (int j = 0; j < Params.LENGTH; j++){
                Patch p = ground[i][j];
                if (!p.openGround()){
                    Daisy d = p.getDaisy();
                    if (d.getColor() == Daisy.DaisyColor.BLACK){
                        black += 1;
                    }else{
                        white += 1;
                    }
                }
            }
        }

        blackPopulation.add(black);
        whitePopulation.add(white);

    }

    /* Seed Daisies with particular numbers of black daisies and white daisies */
    private void seedDaisies(Patch[][] ground, Double blackNum, Double whiteNum ){

        randomSeed(ground, blackNum, Daisy.DaisyColor.BLACK);
        randomSeed(ground, whiteNum, Daisy.DaisyColor.WHITE);
       
    }


    /* Seed daisies at random position with random age */
    private void randomSeed(Patch[][] ground, Double num, Daisy.DaisyColor color){

        Random random = new Random();

        int n = 0;

        while (n < num){


            int x = random.nextInt(Params.LENGTH);
            int y = random.nextInt(Params.LENGTH);
            int age = random.nextInt(Params.MAXAGE);

            if (ground[x][y].openGround()){
                Daisy d = new Daisy();
                if (color == Daisy.DaisyColor.BLACK){
                    d.setBlack(blackAlbedo, age);
                    ground[x][y].setDaisy(d);
                }else if (color == Daisy.DaisyColor.WHITE){
                    d.setWhite(whiteAlbedo, age);
                    ground[x][y].setDaisy(d);
                }    
               n ++;
            }

        }
    }

    /* Display the daisy world */
    public void printDaisyWorld(){

        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                
                Patch patch = ground[i][j];
                if (patch.openGround()){
                    System.out.printf(" ");
                }else {
                    Daisy daisy = patch.getDaisy();
                    if(daisy.getColor() == Daisy.DaisyColor.BLACK){
                        System.out.print("\u25CF");
                    }else if(daisy.getColor() == Daisy.DaisyColor.WHITE){
                        System.out.print("\u25CB");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    /* Daisy grows up at die when age reach max */
    public void growAndDie(){

        for (int i = 0; i < Params.LENGTH; i++){
            for(int j = 0; j < Params.LENGTH; j++){
                Patch p = ground[i][j];
                if(!p.openGround()){
                    Daisy d = p.getDaisy();
                    if (d.getAge() >= Params.MAXAGE){
                        p.setDaisy(null);
                    }
                    d.grow();     
                }
            }
                
        }

    }
    

  

    public void reproduce(){

        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                Patch p = ground[i][j];
                ArrayList<Patch> neighbor = p.neighbourPatchs(i, j, ground);
                p.sproutDaisyByNeighbour(i, j, neighbor);
            }
        }

    }
    
    public void diffuseToNeighbor(){



    }
    
    
    
    /* At each tick, 
       1. each patch absorb energy from sun
       2. diffuse temperature to neighbours
       4. sprout out daisies on neighbour patch
       3. each daisy grow up / die  */

    public void tick(){
        //absorbFromSun();

        
        reproduce();
        diffuseToNeighbor();
        growAndDie();

        // record global temperature
        globalTempRecord();
       
        // record population of black daisies & white daisies
        populationRecord();

        
    }


    public ArrayList<Integer> getWhitePopulation(){
        return whitePopulation;
    }

    public ArrayList<Integer> getBlackPopulation(){
        return blackPopulation;
    }

    public ArrayList<Double> getTemp(){
        return globalTemperature;
    }









    



    
}
