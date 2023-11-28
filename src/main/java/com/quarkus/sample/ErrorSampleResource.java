package com.quarkus.sample;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.CompletableFuture;

@Path("/error")
public class ErrorSampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> badSample() {
        return Uni.createFrom().completionStage(this::secoundFuture);
    }

    private CompletableFuture<String> secoundFuture() {
        return CompletableFuture.supplyAsync( () -> { System.out.println("processMessage Thread: " + Thread.currentThread().getName());
        this.firstFuture();
        System.out.println("almost done");
        return  "Success";
        }).whenCompleteAsync((result,exception) -> {
            if(exception != null) {
                exception.printStackTrace();
            }
            });
    }

    private CompletableFuture<String> firstFuture() {
        return CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("firstFuture Thread: " + Thread.currentThread().getName());
            throw new RuntimeException("Error start");
        });
    }

}
