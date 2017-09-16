Sniffy
Ibrahiem Mohammad
Homework 1 - Java Project Setup
Readme File
===========================================================================================================================================================================================

This project contains many directories and many items, but for the purpose of this project we will focus on the following:

Sniffy Agent (Main file to be run): ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyAgent.java
Sniffy Server Tests (Test File): ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyServerTests.java
Sniffy Core Tests (Test File): ibrahiem_mohammad_hw1\sniffy-core\src\main\java\io\sniffy\io.sniffy.SniffyCoreTests.java

Running the Project
======================
The Sniffy Project is a client-server application. It starts a server, and then allows clients to establish a connection to then 'sniff out' certain elements on a page and
analayze which aspects of your web app or service are using which resources and how much. Usually the client side code is provided a GUI. However, for our sake, we will
not include a GUI but simply establish a connection to the server and run the service.

TO RUN:
Simply go to the Sniffy Agent file [ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyAgent.java] and run the file.

Testing
=========
The tests made for this project are included in two different files. One suite tests certain aspects of the server connection, while the other suite
tests the core functionality of the sniffy application.

TO TEST:
Go to either:
Sniffy Server Tests: ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyServerTests.java

OR

Sniffy Core Tests: ibrahiem_mohammad_hw1\sniffy-core\src\main\java\io\sniffy\io.sniffy.SniffyCoreTests.java

And simply run the test files.


Visual Analysis
=================

![alt text](images/jconsole-mem.png)
![alt text](images/jconsole-overview.png)
![alt text](images/memprofiler-server.png)
![alt text](images/memsampler-server.png)
![alt text](images/monitor-cpu.png)
![alt text](images/tests-core.png)
![alt text](images/tests-server.png)
![alt text](images/threads-server.png)
![alt text](images/visualgc.png)


