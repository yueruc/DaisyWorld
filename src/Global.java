import java.util.ArrayList;
import java.util.Random;


public class Global {
    
    /* The ground of daisy world */
    private Patch[][] ground;

    private ArrayList<Double> globalTemperature;
    private ArrayList<Double> whitePopulation;
    private ArrayList<Double> blackPopulation;
    


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

        // Absort from sun
        absorbFromSun();

        // calculate global temperature
        calGlobalTemp();
    }


    private void absorbFromSun() {
        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                ground[i][j].calTemp(sun);
            }
        }
    }

    private void calGlobalTemp(){

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


    private void seedDaisies(Patch[][] ground, Double blackNum, Double whiteNum ){

        randomSeed(ground, blackNum, Daisy.DaisyColor.BLACK);
        randomSeed(ground, whiteNum, Daisy.DaisyColor.WHITE);
       
    }

    private void randomSeed(Patch[][] ground, Double num, Daisy.DaisyColor color){

        
        Random random = new Random();

        int n = 0;

        while (n < num){


            int x = random.nextInt(Params.LENGTH);
            int y = random.nextInt(Params.LENGTH);
            int age = random.nextInt(Params.MAXAGE);

            if (ground[x][y].OpenGround()){
                if (color == Daisy.DaisyColor.BLACK){
                    ground[x][y].setDaisy(new Daisy(age, color, blackAlbedo));
                }else if (color == Daisy.DaisyColor.WHITE){
                    ground[x][y].setDaisy(new Daisy(age, color, whiteAlbedo));
                }    
               n ++;
            }

        }
    }


    public void printDaisyWorld(){

        for (int i = 0; i < Params.LENGTH; i++) {
            for (int j = 0; j < Params.LENGTH; j++) {
                
                Patch patch = ground[i][j];
                if (patch.OpenGround()){
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


    public Patch[][] getGround(){
        return ground;
    }






    



    
}
