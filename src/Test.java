public class Test {
    public static int randomBinaryWalk(){
        return randomBetween(0, 2) - 1;
    }

    public static int randomBetween(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static void main(String[] args) {

    }
}
