Socket socket = serverSocket.accept(); in this method the client would request from the server to connect. And the server would accept it, otherwise it would show the connection failed.
The graphics interface i created two windows that create and receive the messages from both clients (I wasn’t able to do it as a send button, it couldn't read the connection between both clients. )
Based on the book the thread method would be able to create the server connection and be able to read the clients and handle any errors. After that I created the sockets to accept the connection, the input and output streams will be able to connect to the socket as well. For the read and receive text it was done by creating a loop to wait to receive the message and display it.
The event handler was to send the message by pressing enter. (it didn’t work for me when i created ( btnSend.setOnAction(event -> { instead of tClient.setOnKeyPressed( e -> { even though I set private Button btSend = new Button(“Send”); it didn’t get to send the message and it kept the message from not displaying..
