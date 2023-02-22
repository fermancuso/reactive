package mutiny;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

class NodeTest {

  @Test
  void test_item_subscribe() {
    Uni<String> uni = Uni
        .createFrom()
        .item("ramon"); // Create from String Object
    uni
        .subscribe()
        .with(System.out::println);

    Uni<Integer> uni2 = Uni
        .createFrom()
        .item(22); // Create from Integer Object
    uni2
        .subscribe()
        .with(System.out::println);

    Node node = new Node("node_1", null);

    Uni<Node> uniNode = Uni
        .createFrom()
        .item(node); // Create from Node Object
    uniNode
        .subscribe()
        .with(System.out::println);

    node.setData("Some random data"); // setting data
    System.out.println("node object: " + node);

    uniNode
        .subscribe()
        .with(System.out::println);
  }

  @Test
  void test_simple_chain() {
    // The node_1 result must be set to data of node_2 ...
    // node_1 create +  run
    Node n1 = new Node("node_1", null);
    Uni<String> n1Res = runNode(n1);

    n1Res
        .subscribe()
        .with(item -> System.out.println("## n1Res: " + item));

    // node_2 subscribe to node_1 + run
    Uni<Node> n2 = n1Res
        .onItem()
        .transform(item -> new Node("node_2", item));
    Uni<String> n2Res = n2
        .onItem()
        .transformToUni(item -> runNode(item)); // should use call? why?

    n2Res
        .subscribe()
        .with(item -> System.out.println("## n2Res: " + item));

    // node_3 subscribe to node_2 + run
    Uni<Node> n3 = n2Res
        .onItem()
        .transform(item -> new Node("node_3", item));
    Uni<String> n3Res = n3
        .onItem()
        .transformToUni(item -> runNode(item));

    n3Res
        .subscribe()
        .with(item -> System.out.println("## n3Res: " + item));

    // node_4 subscribe to node_3 + run
    Uni<Node> n4 = n3Res
        .onItem()
        .transform(item -> new Node("node_4", item));
    Uni<String> n4Res = n4
        .onItem()
        .transformToUni(item -> runNode(item));

    n4Res
        .subscribe()
        .with(item -> System.out.println("## n4Res: " + item));
  }

  @Test
  void test_simple_chain_fix() {
    // The node_1 result must be set to data of node_2
    // node_1 create +  run
    Node n1 = new Node("node_1", null);
    Uni<String> n1Res = runNode(n1);

    n1Res
        .subscribe()
        .with(item -> System.out.println("## n1Res: " + item));

    // node_2 subscribe to node_1 + run
    Uni<Node> n2 = n1Res
        .onItem()
        .transform(item -> new Node("node_2", item));

    Uni<String> n2Res = n2
        .onItem()
        .transformToUni(item -> runNode(item))
        .memoize()
        .indefinitely();

    n2Res
        .subscribe()
        .with(item -> System.out.println("## n2Res: " + item));

    // node_3 subscribe to node_2 + run
    Uni<Node> n3 = n2Res
        .onItem()
        .transform(item -> new Node("node_3", item));

    Uni<String> n3Res = n3
        .onItem()
        .transformToUni(item -> runNode(item))
        .memoize()
        .indefinitely();

    n3Res
        .subscribe()
        .with(item -> System.out.println("## n3Res: " + item));

    // node_4 subscribe to node_3 + run
    Uni<Node> n4 = n3Res
        .onItem()
        .transform(item -> new Node("node_4", item));

    Uni<String> n4Res = n4
        .onItem()
        .transformToUni(item -> runNode(item))
        .memoize()
        .indefinitely();

    n4Res
        .subscribe()
        .with(item -> System.out.println("## n4Res: " + item));
  }

  @Test
  void test_simple_chain_generator(){
    // TODO: write this test.
    // given
    // n = numbers of nodes  generate the complete chain and run it.

    // when
    // the pipeline is running

    // then
    // . all nodes must be executed once
    // . the result of node=n must be the result of n + before n's results.
  }
  public Uni<String> runNode(Node node) {
    System.out.println("... running: " + node.Name + " data: " + node.Data);
    return Uni
        .createFrom()
        .item("result_" + node.Name + "_" + node.Data);
  }

  private Uni<Node> buildNode(String name, String data) {
    return Uni
        .createFrom()
        .item(new Node(name, data));
  }
}
