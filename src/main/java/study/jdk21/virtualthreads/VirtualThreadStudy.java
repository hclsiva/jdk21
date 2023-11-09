package study.jdk21.virtualthreads;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

@Slf4j
public class VirtualThreadStudy {
    static class Bathroom {
        private final Lock lock = new ReentrantLock();

        @SneakyThrows
        void useTheToiletWithLock() {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                try {
                    log("I'm going to use the toilet");
                    sleep(Duration.ofSeconds(1L));
                    log("I'm done with the toilet");
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    static class SynchronizedBathroom {
        synchronized void useTheToilet() {
            log("I'm going to use the toilet");
            sleep(Duration.ofSeconds(1L));
            log("I'm done with the toilet");
        }
    }
    static void log(String message){
        log.info("{} | " + message,Thread.currentThread());
    }

    private static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
    }
    static Thread bathTime() {
        return virtualThread(
                "Bath time",
                () -> {
                    log("I'm going to take a bath");
                    sleep(Duration.ofMillis(500L));
                    log("I'm done with the bath");
                });
    }
    static Thread boilingWater() {
        return virtualThread(
                "Boil some water",
                () -> {
                    log("I'm going to boil some water");
                    sleep(Duration.ofSeconds(1L));
                    log("I'm done with the water");
                });
    }
    @SneakyThrows
    static void concurrentMorningRoutine() {
        var bathTime = bathTime();
        var boilingWater = boilingWater();
        bathTime.join();
        boilingWater.join();
    }
    public void runVirtualThread(){
        try{
            Thread vt = Thread.ofVirtual()
                        .name("Sample Virtual Thread")
                        .start(() -> log(" I am running a Virtual Thread"));
            log("Is This a Virtual Thread = "+vt.isVirtual());
            Thread.sleep(100);
        }   catch(Exception e)  {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    static void concurrentMorningRoutineUsingExecutors() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var bathTime =
                    executor.submit(
                            () -> {
                                log("I'm going to take a bath");
                                sleep(Duration.ofMillis(500L));
                                log("I'm done with the bath");
                            });
            var boilingWater =
                    executor.submit(
                            () -> {
                                log("I'm going to boil some water");
                                sleep(Duration.ofSeconds(1L));
                                log("I'm done with the water");
                            });
            bathTime.get();
            boilingWater.get();
        }
    }
    @SneakyThrows
    static void concurrentMorningRoutineUsingExecutorsWithName() {
        final ThreadFactory factory = Thread.ofVirtual().name("routine-", 0).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            var bathTime =
                    executor.submit(
                            () -> {
                                log("I'm going to take a bath");
                                sleep(Duration.ofMillis(500L));
                                log("I'm done with the bath");
                            });
            var boilingWater =
                    executor.submit(
                            () -> {
                                log("I'm going to boil some water");
                                sleep(Duration.ofSeconds(1L));
                                log("I'm done with the water");
                            });
            bathTime.get();
            boilingWater.get();
        }
    }
    static int numberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }
    static void viewCarrierThreadPoolSize() {
        final ThreadFactory factory = Thread.ofVirtual().name("routine-", 0).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            IntStream.range(0, numberOfCores() + 1)
                    .forEach(i -> executor.submit(() -> {
                        log("Hello, I'm a virtual thread number " + i);
                        sleep(Duration.ofSeconds(1L));
                    }));
        }
    }
    private static void sleep(Duration d){
        try {
            Thread.sleep(d);
        }catch(Exception e){
            log(e.getMessage());
        }
    }
    static Thread workingHard() {
        return virtualThread(
                "Working hard",
                () -> {
                    log("I'm working hard");
                    while (alwaysTrue()) {
                        // Do nothing
                        log("I'm working hard");
                        sleep(Duration.ofMillis(5000L));
                    }
                    sleep(Duration.ofMillis(100L));
                    log("I'm done with working hard");
                });
    }
    static boolean alwaysTrue(){
        return true;
    }
    static Thread takeABreak() {
        return virtualThread(
                "Take a break",
                () -> {
                    //while (alwaysTrue()) {
                        log("I'm going to take a break");
                        sleep(Duration.ofSeconds(1L));
                        log("I'm done with the break");
                    //}
                });
    }
    @SneakyThrows
    static void workingHardRoutine() {
        var workingHard = workingHard();
        var takeABreak = takeABreak();
        workingHard.join();
        takeABreak.join();
    }
    static Thread goToTheToiletWithLock() {
        Bathroom bathroom = new Bathroom();
        return virtualThread("Go to the toilet", bathroom::useTheToiletWithLock);
    }
    static Thread goToTheSynchronizedToilet() {
        SynchronizedBathroom bathroom = new SynchronizedBathroom();
        return virtualThread("Go to the toilet", bathroom::useTheToilet);
    }
    @SneakyThrows
    static void twoEmployeesInTheOfficeWithLock() {
        var riccardo = goToTheToiletWithLock();
        var daniel = takeABreak();
        riccardo.join();
        daniel.join();
    }
    @SneakyThrows
    static void twoEmployeesInTheSynchronizedOffice() {
        var riccardo = goToTheSynchronizedToilet();
        var daniel = takeABreak();
        riccardo.join();
        daniel.join();
    }
    public static void main(String ... args){
        log("Running sample Virtual Thread");
        VirtualThreadStudy vts = new VirtualThreadStudy();
        //sampleVTCalls();
        //singleThreadedCall();
          twoEmployeesInTheSynchronizedOffice();
        // twoEmployeesInTheOfficeWithLock();
    }
    private static void singleThreadedCall(){
        workingHardRoutine();
    }
    private static void sampleVTCalls(VirtualThreadStudy vts){
        vts.runVirtualThread();
        log("");
        log("");
        log("");
        log("Morning Routines");
        concurrentMorningRoutine();
        log("");
        log("");
        log("");
        log("Morning Routines using Executors");
        concurrentMorningRoutineUsingExecutors();
        log("");
        log("");
        log("");
        log("Morning Routines using Executors with Name ");
        concurrentMorningRoutineUsingExecutorsWithName();
        log("");
        log("");
        log("");
        log("Available Runtime Cores :: " + numberOfCores());
        log("");
        log("");
        log("");
        log("View Carrier Pools ");
        viewCarrierThreadPoolSize();
    }
}
