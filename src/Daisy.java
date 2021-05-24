
public class Daisy {

    public enum DaisyColor {BLACK, WHITE};

    private int age;
    private DaisyColor color;
    private Double albedo;

    


    public Daisy (int age, DaisyColor color, Double albedo){
        this.age = age;
        this.color = color;
        this.albedo = albedo;
    }

    public void grow() {
        age ++;
    }

    // public void setBlack(){
    //     albedo = blackAlbedo;
    //     age = 0;
    //     color = DaisyColor.BLACK;
    // }

    // public void setWhite(){
    //     albedo = whiteAlbedo;
    //     age = 0;
    //     color = DaisyColor.WHITE;
    // }

    public DaisyColor getColor(){
        return color;
    }

    public Double getAlbedo(){
        return albedo;
    }


}
