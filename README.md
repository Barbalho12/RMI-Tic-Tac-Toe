# Ultimate-Tic-Tac-Toe
A tic-tac-toe that can be played by to users in different machines and a ultimate Tic-Tac-Toe that can be played agains a AI.

## Information

By default, when it is not specified: `IP` and `Port` is:

`IP=localhost`  
`PORT=8080` 

Access releases to download the `jar` and `war` files in [Releases]


## Server

Download a servlet container, we recommend [Apache Tomcat]:

```bash
wget http://ftp.unicamp.br/pub/apache/tomcat/tomcat-8/v8.0.52/bin/apache-tomcat-8.0.52.zip 
unzip apache-tomcat-8.0.52.zip 
mv apache-tomcat-8.0.52.zip tomcat
```

Move the `tictactoews.war` file to `tomcat/webapps/`

```bash
mv tictactoews.war tomcat/webapps/
```

Execute Tomcat with logs view:

```bash
./tomcat/bin/startup.sh; tail -f tomcat/logs/catalina.out
```

To end Tomcat run:

```bash
./tomcat/bin/shutdown.sh
```

## Client

Client with graphical interface

```bash
java -jar ultimate-tictactoe-client-fx.jar <IP> <PORT>
```
  
  
   [Releases]: <https://github.com/Barbalho12/RMI-Tic-Tac-Toe/releases>
   [Apache Tomcat]: <https://tomcat.apache.org/download-80.cgi>
   
