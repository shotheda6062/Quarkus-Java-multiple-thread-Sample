package com.quarkus.sample;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.CompletableFuture;

@Path("/purejava")
public class PureJavaSampleResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> pureJava() {
        return Uni.createFrom().item(() -> {

                    System.out.println("pureJava Thread: " + Thread.currentThread().getName());

                    Uni.createFrom().completionStage(secoundFuture()).subscribe().with(
                            igon -> {}
                    );
           return "Success";
        });
    }

    private CompletableFuture<Void>  secoundFuture() {
        return CompletableFuture.runAsync( () -> {
            try {
                System.out.println("secoundFuture Thread: " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            firstFuture().join();
        });
    }


    private CompletableFuture<Void> firstFuture() {
        return  CompletableFuture.runAsync(() -> {
            System.out.println("firstFuture Thread: " + Thread.currentThread().getName());
            throw new RuntimeException("Test");
        });
    }
}
