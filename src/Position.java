public class Position {

    int x;
    int y;

    public Position (int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPos (int a, int b){
        this.x = a;
        this.y = b;
    }

    public Position getPos (){
        return this;
    }
    public int getX (){
        return x;
    }
    public int getY (){
        return y;
    }
    
}
