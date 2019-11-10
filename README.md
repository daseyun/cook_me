Find recipes for the ingredients you have


* Software needed:
* MySQL Workbench
* Java IDE (eg. IntelliJ IDEA)
* Terminal
* JDBC Driver: https://dev.mysql.com/downloads/connector/j/
* JCommander: https://github.com/cbeust/jcommander

How to make and run project:
* In Driver.java:
  * replace static variable url ‘127.0.0.1:3306/cook_me’ with your local SQL server address corresponding to the imported database schema
  * Replace static variable user with your mysql root userID
  * Replace static variable password with your mysql root password
  * Build the .jar file.
* Instructions for IntelliJIdea:
  * Go to File -> Project Structure -> Libraries and add (+) the downloaded JDBC driver as a library.
  * Download JCommander library and drag ‘com.beust.jcommander’ into src directory.
  * Go to File -> Project Structure -> Artifact and add (+) a JAR with from existing dependencies (Driver.java) as the main class, check “include in project build” box and click OK.
  * Build the JAR by clicking the build project button. (Build -> Build Project).
  * Run the jar on the command line by going into the directory of where the jar is located and inputting ‘java -jar [jar_name_here] [command] [arg(s)]’
  * (eg. java -jar dbproject.jar -i egg )
  * (eg. java -jar dbproject.jar -min)
