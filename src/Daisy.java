public class Daisy {

  public enum DaisyColor { BLACK, WHITE }

  private int age;
  private DaisyColor color;
  private Double albedo;

  public void grow() {
    age++;
  }

  /**
   * Set the daisy to be a black daisy.
   * @param blackAlbedo The albedo of the black daisy
   * @param age The initial age of the daisy
   */
  public void setBlack(Double blackAlbedo, int age) {
    albedo = blackAlbedo;
    this.age = age;
    color = DaisyColor.BLACK;
  }

  /**
   * Set the daisy to be a white daisy.
   * @param whiteAlbedo The albedo of the white daisy
   * @param age The initial age of the daisy
   */
  public void setWhite(Double whiteAlbedo, int age) {
    albedo = whiteAlbedo;
    this.age = age;
    color = DaisyColor.WHITE;
  }

  public DaisyColor getColor() {
    return color;
  }

  public int getAge() {
    return age;
  }

  public Double getAlbedo() {
    return albedo;
  }

  /**
   * Convert the daisy to a string.
   */
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
