package com.quarkus.sample;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.Duration;

@Path("/mutiny")
public class MutinySampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> normalSample() {
        return Uni.createFrom().item(() -> {
            System.out.println("normalSample Thread: " + Thread.currentThread().getName());
           firstSample().subscribe().with(result -> {});
           return  "Success";
        });
    }

    private Uni<Void> firstSample() {
        return Uni.createFrom().voidItem()
                .onItem().transformToUni(ignored -> Uni.createFrom().item(()-> {
                    System.out.println("normalSample Thread: " + Thread.currentThread().getName());
                    return null;}))
                .onItem().delayIt().by(Duration.ofSeconds(3))
                .onItem().transformToUni(ignored -> Uni.createFrom().failure(new RuntimeException("Error Test")));
    }

}
