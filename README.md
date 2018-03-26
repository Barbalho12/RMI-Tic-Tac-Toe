# RMI-Tic-Tac-Toe
A tic-tac-toe that can be played by to users in different machines and a ultimate Tic-Tac-Toe that can be played agains a AI.

## Information

By default, when it is not specified: IP * and * Port * is:

`IP=localhost`  
`PORT=1099` 

## Server


```bash
java -jar -Djava.rmi.server.hostname=<IP> ultimate-tictactoe-server.jar <IP> <PORT>
```

## Client

Client with graphical interface

```bash
java -jar ultimate-tictactoe-client-fx.jar <IP> <PORT>
```

Client using terminal

```bash
java -jar ultimate-tictactoe-client.jar <IP> <PORT>
```
