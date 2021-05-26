
public class Daisy {

    public enum DaisyColor {BLACK, WHITE};

    private int age;
    private DaisyColor color;
    private Double albedo;

    public Daisy (){
        
    }

    public void grow() {
        age ++;
    }


    public void setBlack(Double blackAlbedo, int age){
        albedo = blackAlbedo;
        this.age = age;
        color = DaisyColor.BLACK;
    }

    public void setWhite(Double whiteAlbedo, int age){
        albedo = whiteAlbedo;
        this.age = age;
        color = DaisyColor.WHITE;
    }

    public DaisyColor getColor(){
        return color;
    }

    public int getAge(){
        return age;
    }

    public Double getAlbedo(){
        return albedo;
    }

    public String toString() {
        switch (color) {
            case BLACK:
                return "\u25CF";
            case WHITE:
                return "\u25CB";
            // Invalid color
            default:
                return "x";
        }
    }

}
