Start the server
 mvn -q exec:java -Dexec.mainClass=app.DerbyScgDbServer
 
 To dump to console
 mvn -q exec:java -Dexec.mainClass=app.DumpDerbyScgDb
 
In a new window use to build and execute code
mvn clean compile
mvn exec:java -Dexec.mainClass=app.InitDb

To generate the invoice from db
mvn exec:java -Dexec.mainClass=app.Assignment07