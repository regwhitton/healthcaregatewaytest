# Setup and Build

### Java 8

You will need to have the Java 8 JDK installed.  Check by typing the following into the command line:

    $ java -version
    java version "1.8.0_202"
    Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
    Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
    
    $ javac -version
    javac 1.8.0_202

If these are not found or show a version other than 1.8 then download and install the latest Java 8 JDK from [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/index.html).

Once installed, the directory containing java should have been added to your PATH environment variable, so that the above `java` and `javac` commands will work from the command line. 

### Building and starting the server from the command line

    cd path/to/this/dir
    mvnw package            (./mvnw - if using bash)
    mvnw spring-boot:run    (Use Control-C or task-manager to stop)
    
Browse to <http://localhost:8080/swagger-ui.html> expand the controller and "Try it out".
