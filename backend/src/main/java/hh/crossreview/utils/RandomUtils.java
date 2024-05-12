package hh.crossreview.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

  public static <T> T getRandomElement(List<T> list) {
    int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
    return list.get(randomIndex);
  }

}
