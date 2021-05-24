import java.math.*;

public class Patch {


    private Double temp;
    private Daisy daisy;
    private Double albedoOfSurface;

    public Patch (Double albedoOfSurface){
        this.temp = 0.0;
        this.daisy = null;
        this.albedoOfSurface = albedoOfSurface;
    }


    public void calTemp (Sun sun){

        Double absorbedLuminosity = 0.0;
        
        if (OpenGround()){
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
    
    



    // private void sproutDaisyByNeighbor (Patch neigh){

    //     Daisy neighDaisy = neigh.getDaisy();
    //     Daisy d = new Daisy();
    //     if (neighDaisy.getColor() == Daisy.DaisyColor.BLACK){ 
    //         d.setBlack();
    //     }else if (neighDaisy.getColor() == Daisy.DaisyColor.WHITE){
    //         d.setWhite();
    //     }
        
    // }


    public Boolean OpenGround (){
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
