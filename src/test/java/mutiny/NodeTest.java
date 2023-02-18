package mutiny;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

  @Test
  void test_item_subscribe() {
    Uni<String> uni = Uni.createFrom().item("ramon"); // Create from String Object
    uni.subscribe().with(System.out::println);

    Uni<Integer> uni2 = Uni.createFrom().item(22); // Create from Integer Object
    uni2.subscribe().with(System.out::println);

    Node node = new Node("node_1", null);

    Uni<Node> uniNode = Uni.createFrom().item(node); // Create from Node Object
    uniNode.subscribe().with(System.out::println);

    node.setData("Some random data"); // setting data
    System.out.println("node object: " + node);

    uniNode.subscribe().with(System.out::println);
  }

  @Test
  void test_simple_chain() {
    // The node_1 result must be set to data of node_2 ...
    Node node = new Node("node_1", null);
    Uni<String> nodeResp = runNode(node);

    nodeResp.subscribe().with(item -> System.out.println("nodeResp: " + item));

    Uni<Node> node2 = nodeResp.onItem().transform(item -> new Node("node_2", item));
    Uni<String> node2Resp = node2.onItem().transformToUni(item -> runNode(item));

    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));

    Uni<Node> node3 = node2Resp.onItem().transform(item -> new Node("node_3", item));
    Uni<String> node3Resp = node3.onItem().transformToUni(item -> runNode(item));

    node3Resp.subscribe().with(item -> System.out.println("node3Resp: " + item));

    Uni<Node> node4 = node3Resp.onItem().transform(item -> new Node("node_4", item));
    Uni<String> node4Resp = node4.onItem().transformToUni(item -> runNode(item));

    node4Resp.subscribe().with(item -> System.out.println("node4Resp: " + item));
  }

  @Test
  void test_simple_chain_fix() {
    // The node_1 result must be set to data of node_2
    Node node = new Node("node_1", null);
    Uni<String> nodeResp = runNode(node);

    nodeResp.subscribe().with(item -> System.out.println("nodeResp: " + item));

    Uni<Node> node2 = nodeResp.onItem().transform(item -> new Node("node_2", item));
    Uni<String> node2Resp =
        node2.onItem().transformToUni(item -> runNode(item)).memoize().indefinitely();

    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));

    Uni<Node> node3 = node2Resp.onItem().transform(item -> new Node("node_3", item));
    Uni<String> node3Resp = node3.onItem().transformToUni(item -> runNode(item));

    node3Resp.subscribe().with(item -> System.out.println("node3Resp: " + item));

    Uni<Node> node4 = node3Resp.onItem().transform(item -> new Node("node_4", item));
    Uni<String> node4Resp = node4.onItem().transformToUni(item -> runNode(item));

    node4Resp.subscribe().with(item -> System.out.println("node4Resp: " + item));

    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));
    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));
    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));
    node2Resp.subscribe().with(item -> System.out.println("node2Resp: " + item));
  }

  public Uni<String> runNode(Node node) {
    System.out.println("running: " + node.Name + " with: " + node.Data);
    return Uni.createFrom().item("resp_from_" + node.Name + "_" + node.Data);
  }

  private Uni<Node> buildNode(String name, String data) {
    return Uni.createFrom().item(new Node(name, data));
  }
}
