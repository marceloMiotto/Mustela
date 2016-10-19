package udacitynano.com.br.mustela.util;


import java.util.Random;

public class Util {


    public int generateRandomNumber(){
        int min = 0;
        int max = 8;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        return i1;

    }

}
