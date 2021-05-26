import java.math.*;
import java.util.ArrayList;
import java.util.Random;

public class Patch {


    private Double temp;
    private Daisy daisy;
    private Double albedoOfSurface;

    public Patch (Double albedoOfSurface){
        this.temp = 0.0;
        this.daisy = null;
        this.albedoOfSurface = albedoOfSurface;
    }

    // absorb from sun, which each patch's temparature.
    public void absorb (Sun sun){

        Double absorbedLuminosity = 0.0;
        
        if (openGround()){
            /* the percentage of absorbed energy is calculated (1 - albedoOfSurface) 
               and then multiplied by the solar-luminosity give a scaled absorbedLuminosity. */
            absorbedLuminosity = ((1 - albedoOfSurface) * sun.getLuminosity());
        }else {
            /* the percentage of absorbed energy is calculated (1 - albedo) 
               and then multiplied by the solar-luminosity give a scaled absorbedLuminosity. */
            absorbedLuminosity = ((1 - daisy.getAlbedo()) * sun.getLuminosity());
        }

        Double localHeating = 0.0;
       
        
      /*
        localHeating is calculated as logarithmic function of solar-luminosity
        where a absorbed-luminosity of 1 yields a local-heating of 80 degrees C
        and an absorbed-luminosity of .5 yields a local-heating of approximately 30 C
        and a absorbed-luminosity of 0.01 yields a local-heating of approximately -273 C */
        if (absorbedLuminosity > 0){
            localHeating = 72.0 * Math.log10(absorbedLuminosity) + 80.0;
        }else{
            localHeating = 80.0;

        }
        /* Set the temperature at this patch to be the average of 
           the current temperature and the local-heating effect */
        temp = (temp + localHeating) / 2;
    }

    /* Find all neighbours based on current position */
    public ArrayList<Patch> neighbourPatchs(int x, int y, Patch[][] ground){

       
        
        ArrayList<Position> neighPos = new ArrayList<>(); 
        ArrayList<Patch> neighs = new ArrayList<>(); 

        

            neighPos.add(new Position(x, y-1));
            neighPos.add(new Position(x, y+1));
            neighPos.add(new Position(x+1, y));
            neighPos.add(new Position(x-1, y));
            neighPos.add(new Position(x-1, y-1));
            neighPos.add(new Position(x+1, y-1));
            neighPos.add(new Position(x-1, y+1));
            neighPos.add(new Position(x+1, y+1));

            for (int i = 0; i < 8; i++){
                int a = neighPos.get(i).getX();
                int b = neighPos.get(i).getY();
                if (0 <= a && a < Params.LENGTH && 0 <= b && b < Params.LENGTH ){
                    if(!ground[a][b].openGround()){
                        neighs.add(ground[a][b]);
                    }
                }
                
            }


        

        
        return neighs;
    }
    
    

    /*  
     *  This function is to randomly choose a neighbour and inherits daisy if it is openGround
     */
    public void sproutDaisyByNeighbour (int x, int y, ArrayList<Patch> neighbors){

    
        double seedThreshold = (0.1457 * temp) - (0.0032 * (temp * temp)) - 0.6443;
        Random random = new Random();
        double survivability = random.nextDouble();

        if (survivability < seedThreshold){
            
            /* randomly choose a neighbour and inherits daisy from it */
            if(openGround() && neighbors.size() > 0){

                /* randomly find a parentDaisy from neighbours */
                int index = random.nextInt(neighbors.size());
                Daisy parentDaisy = neighbors.get(index).getDaisy();

                /* inherits daisy */
                this.daisy = new Daisy();
                if (parentDaisy.getColor() == Daisy.DaisyColor.BLACK){
                    daisy.setBlack(parentDaisy.getAlbedo(), 0);
                }else if (parentDaisy.getColor() == Daisy.DaisyColor.WHITE){
                    daisy.setWhite(parentDaisy.getAlbedo(), 0);
                }


            }

        }
        
    }


    public Boolean openGround (){
        return daisy == null;
    }

    public Double getTemp(){
        return temp;
    }

    public Daisy getDaisy(){
        return daisy;
    }

    public void setDaisy(Daisy d){
        daisy = d;
    }




    
}
