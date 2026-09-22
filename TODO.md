# TODO

## General Code-keeping

- [ ] Remove excess code
- [X] Fix bug 2 (github)
- [X] Fix bug 3 (github)
- [X] Fix bug 4 (github)
- [X] Fix bug 5 (github)
- [ ] Put more code comments around the program. (got tired of not knowing why I wrote something)
- [X] Allow "" as input when offering default value
- [ ] Rework the main menu

## Networking

- [ ] Rebuild the P2P client networking See note 1.
- [ ] make the frame of the network connection
- [ ] Finish making the connection manager menu
- [ ] Figure out how the structure of P2P connections should go

## Threadding

- [ ] Finish ThreadController start/stop
- [ ] Remove unnecisary worker null checks in ThreadControler

### Notes

1) The client should be able to accept, decline, make, and break connections. See plan section 1.

### planing

#### 1

The general plan is as follows:

(Starting the connection)

1) On startup, one thread is started. This thread purely listens for an incoming connection attempt.
2) On connection attempt, the listening thread will take over with a Connection Menu. It will list the following information from the packet: IP, (T/P)UUID, and Username
3) While this is happening, a heartbeat will automatically begin between the two devices, to check if anyone canceled/declined.
4) If accepted, the sender will be sent to the chat menu, and so will the receiver. The old heartbeat will be reused. (Optionally, a new heartbeat will be created and the old one closed.)

(During the connection)

1) Both clients will send pings to eachother, and expect to receive a ping in return.
2) the clients will communicate as normal.

(Before and after ending connection)

1) Before the connection is terminated, the client disconnecting will send a `DISCONNECT` packet.
2) Once `DISCONNECT` is received, the chat window will be replaced with a "Chat ended, cleaning up" screen, which allows the user to know that the chat has ended, and allows time for the server to clean up and deactive threads and variables.
3) Once cleanup is complete, the user will be sent to the main menu to continue the cycle.
