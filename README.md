## DU3 - Exercise 10 - Multi threaded client-server app - UDP - TCP - Funds raising

We want to raise funds using a client-server application.

The client must send to the server a command entered by the user. The only correct commands are:

ADD <amount>: Adds the specified amount to the funds.
SHOW: Returns the current fundraising total.
QUIT: Ends the communication with the server.
If the user does not specify the amount, or if the user does not use correctly the command, the server sends an Invalid request message to the client.

The server must display information about all requests it receives.

The server must also display information about all information it sends to clients, including the client's port. When the client adds an amount to the funds, the server must display the new amount.

UDP:

Create a FundraisingClientUDP class to launch the Client.

Create a FundraisingServerUDP class to launch the Server.

Create a FundraisingServerUDPWorker class for each thread on the Server.

TCP:

Create a FundraisingClientTCP class to launch the Client.

Create a FundraisingServerTCP class to launch the Server.

Create a FundraisingServerTCPWorker class for each thread on the Server.