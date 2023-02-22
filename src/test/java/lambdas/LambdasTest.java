package mutiny;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LambdasTest {

  @Test
  void should_contain_a_6() {
    List<Integer> list = List<String> list = Arrays.asList(2, 4, 6, 8, 10);
    Utility utility = new Utility();
    assertTrue(utility.contains(list, 6));
  }

  @Test
  void should_return_only_pairs() {
    List<Integer> list = List<String> list = Arrays.asList(1, 2, 3, 4, 5, 6);
    Utility utility = new Utility();
    assertEquals(Arrays.asList(2, 4, 6), utility.pairs(list));
  }

  @Test
  void should_return_only_pairs() {
    List<Integer> list = List<String> list = Arrays.asList(1, 2, 3, 4, 5, 6);
    Utility utility = new Utility();
    assertEquals(Arrays.asList(2, 4, 6), utility.pairs(list));
  }

  @Test
  void should_add_5_to_all_items() {
    List<Integer> list = List<String> list = Arrays.asList(1, 2, 3);
    Utility utility = new Utility();
    assertEquals(Arrays.asList(6, 7, 8), utility.add(list, 5));
  }

  @Test
  void should_add_5_to_pair_items() {
    List<Integer> list = List<String> list = Arrays.asList(1, 2, 3, 4, 5, 6);
    Utility utility = new Utility();
    assertEquals(Arrays.asList(1, 7, 3, 9, 5, 11), utility.addToPairs(list, 5));
  }

  @Test
  void should_sum_all_elements() {
    List<Integer> list = List<String> list = Arrays.asList(1, 2, 3);
    Utility utility = new Utility();
    assertEquals(6, utility.sum(list));
  }
}
