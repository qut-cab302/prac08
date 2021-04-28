CAB302 Software Development
===========================

# Week 8: Serialisation and Network Connectivity

This practical involves working with the Address Book application from last week, but this time, you will be using it to store and retrieve details from:
* A file
* A network server

The original Address Book application was written in a very generic way, with a standard interface for different data sources. We will take
advantage of this in this practical to create two Address Book applications, one of which stores address
data in a file, while the other stores it and receives it from a server on a network.

Most of the address book application is found in classes in the `common` package. You do not need to modify
any of these to complete these exercises.

## Exercise 1 – Serialisation

Edit the class FileDataSource in the package serialisationExercise. You may need to add a constructor, private helper methods etc.

Your task is to implement the FileDataSource to store Person objects serialised inside a file (name the file something like `addressbook.dat`). You will likely need, at a minimum, the following classes:
* FileInputStream
* ObjectInputStream
* FileOutputStream
* ObjectOutputStream

It may be useful to have the JDK documentation open on these classes as you write code to work with them.

When the program is first run the address book data file will not be present. Your program should handle this
by creating a blank address book.

You might find it useful to refer back to Practical 7 if you are having trouble understanding what the methods of
FileDataSource need to do.

Once you are done, test the program. Open it up, add some entries to the address book, then close it down, start it up
again and check that they persist. Look for the `addressbook.dat` file that is created.

## Exercise 2 - Network Connectivity

For this exercise you will need to work on the address book implementation in the `networkExercise` package.
There are two classes you will need to modify; `NetworkDataSource` and `server.NetworkServer`. There is
also a NetworkServerGUI class that you can run to launch the server. What you will need to complete is
the code in `NetworkDataSource` to connect to the server and send/retrieve address information, and the
code in NetworkServer to store address information and provide it to the client on request. Initially just
use something like a `HashMap<String, Person>` to store the data on the server. This means that the
address book details will only persist as long as the server is running.

You will need to determine the *protocol* of the network connection; that is, the shared language that
the server and client will "speak" in order to be able to communicate. Think about what information needs
to be sent back and forth to enable the client to both retrieve address information and send updates to it
to the server.

There is a type called AtomicBoolean used in the NetworkServer code you are provided. Atomic types are
discussed in Lecture 9, but essentially, they are shared values that can be safely read and modified from
multiple threads. This is important because the `start()` method is invoked from the main thread, while the
`shutdown()` method is invoked from the AWT event dispatch thread.

Once you are done, test this program out in the same way as you tested out the last one. Run the server,
start the address book application up, save some entries, close the address book down (leaving the server
running), then open the address book back up and see that those entries are still there.

### A warning about interleaved network access

Something you should know when writing network applications, especially when dealing with buffered streams
(which the streams you retrieve from Socket usually are), is that it can be dangerous to interleave
writing and reading. Say you have a client that does this:

    socket.getOutputStream().write(1);
    socket.getInputStream().read();

...and a server that does this:

    socket.getInputStream().read();
    socket.getOutputStream().write(1);

This is a fairly common model; having a client send the server a request for something, then wait for the
reply. However, buffering causes problems here. The client's initial write() may not go through at all
(with the data still waiting in the stream's buffer), which means the client will be waiting for the
server to respond while the server is itself waiting for the client's message.

The solution to this is the `flush()` method. Once we modify the client to work like this:

    socket.getOutputStream().write(1);
    socket.getOutputStream().flush();
    socket.getInputStream().read();

...it will immediately flush the contents of the output stream's buffer before reading anything. This
means that the server will eventually receive the data and therefore send a response. As a general rule,
when you interleave reads and writes, flush when your writes turn into reads.

## Bonus exercise! Combine the two

As an extra task, modify your server to store data in a file like you did in the serialisation exercise.
This way the server data will persist even if you close the server and start it up again. Alternatively,
modify the server to store this data in a database.
