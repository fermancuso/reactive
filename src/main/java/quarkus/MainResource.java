package quarkus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.Random;

@Path("/data")
public class MainResource {
  @GET
  @Produces
  public Multi<Integer> data() {
    Random random = new Random();
    return Multi.createFrom()
        .items(1, 2, 3, 4, 5)
        .onItem()
        .call(
            i -> {
              Duration delay = Duration.ofMillis(random.nextInt(1000) + 1);
              return Uni.createFrom().nullItem().onItem().delayIt().by(delay);
            });
  }
}
