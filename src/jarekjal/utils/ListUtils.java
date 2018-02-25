package jarekjal.utils;

/**
 * Created by ejarjal on 2018-02-22.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    /**
     * Returns given number of random indexes for given List in form of
     * List<Integer>
     *
     *
     */

    public static List<Integer> randomIndexesOf(List<?> list, int number) {

        if (number > list.size() || number < 1)
            throw new IllegalArgumentException("Number can't be higher than list's size or less than 1");

        List<Integer> resultTmp = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            resultTmp.add(j);
        }
        List<Integer> result = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < number; i++) {
            int num = rnd.nextInt(resultTmp.size());
            result.add(resultTmp.get(num));
            resultTmp.remove(num);
        }
        return result;
    }

    /**
     * Returns new List<T> made of number of random elements chosen from given
     * List<T>
     *
     *
     */

    public static <T> List<T> randomSublistOf(List<T> list, int number) {

        List<Integer> indexes = randomIndexesOf(list, number);
        // System.out.println(indexes);
        List<T> result = new ArrayList<>();
        for (int i : indexes) {
            result.add(list.get(i));
        }
        return result;
    }



    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        list.add("gg");
        list.add("hh");
        list.add("ii");
        System.out.println(randomIndexesOf(list, 5));
        System.out.println(randomSublistOf(list, 5));

    }

}
