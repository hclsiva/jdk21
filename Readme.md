# Git Init commands
    
    git init
    git remote add origin https://github.com/hclsiva/jdk21
    git add .
    git commit -m 
    git push origin master

# How to Run from the prompt.

## in linux/mac enviornment
java -classpath "D:\dev\studies\java\jdk21\target\classes;C:\Users\user\.m2\repository\org\slf4j\slf4j-api\1.7.36\slf4j-api-1.7.36.jar;C:\Users\user\.m2\repository\org\slf4j\slf4j-simple\1.7.36\slf4j-simple-1.7.36.jar" \
-Djdk.virtualThreadScheduler.parallelism=1 \
-Djdk.virtualThreadScheduler.maxPoolSize=1 \
-Djdk.virtualThreadScheduler.minRunnable=1 \
study.jdk21.virtualthreads.VirtualThreadStudy

## in windows enviornment
java  "-Djdk.virtualThreadScheduler.parallelism=1" "-Djdk.virtualThreadScheduler.maxPoolSize=1" "-Djdk.virtualThreadScheduler.minRunnable=1" "-Djdk.tracePinnedThreads=full/short" -classpath "D:\\dev\\studies\\java\\jdk21\\target\\classes;C:\\Users\\user\\.m2\\repository\\org\\slf4j\\slf4j-api\\1.7.36\\slf4j-api-1.7.36.jar;C:\\Users\\user\\.m2\\repository\\org\\slf4j\\slf4j-simple\\1.7.36\\slf4j-simple-1.7.36.jar" study.jdk21.virtualthreads.VirtualThreadStudy

