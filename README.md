Sniffy
Ibrahiem Mohammad
Homework 1 - Java Project Setup
Readme File
===========================================================================================================================================================================================

This project contains many directories and many items, but for the purpose of this project we will focus on the following:

Sniffy Agent (Main file to be run): ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyAgent.java
Sniffy Server Tests (Test File): ibrahiem_mohammad_hw1\src\tests\java\SniffyServerTests.java
Sniffy Core Tests (Test File): ibrahiem_mohammad_hw1\src\tests\java\SniffyCoreTests.java

Running the Project
======================
The Sniffy Project is a client-server application. It starts a server, and then allows clients to establish a connection to then 'sniff out' certain elements on a page and
analayze which aspects of your web app or service are using which resources and how much. Usually the client side code is provided a GUI. However, for our sake, we will
not include a GUI but simply establish a connection to the server and run the service.

TO RUN:
Simply go to the Sniffy Agent file [ibrahiem_mohammad_hw1\sniffy\src\main\java\io\sniffy\SniffyAgent.java] and run the file.

Testing
=========

sbt: sbt clean compile test
gradle: gradle test


Visual Analysis
=================

The visualgc and jconsole snapshots are found in ibrahiem_mohammad_hw1\images


Limitations
=================

Gradle / SBT scripts are not properly configured