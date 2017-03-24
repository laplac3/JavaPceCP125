Start the server
 mvn -q exec:java -Dexec.mainClass=app.DerbyScgDbServer
 
 To dump to console
 mvn -q exec:java -Dexec.mainClass=app.DumpDerbyScgDb
 
In a new window use to build and execute code
mvn clean compile
 
mvn -q exec:java -Dexec.mainClass=app.Assignment08Server

mvn -q exec:java -Dexec.mainClass=app.Assignment08