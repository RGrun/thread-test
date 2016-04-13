# Thread Test

I wrote this program to better understand multithreading in Java.

# Usage

Instead of making you copy my intellij project setup, just download the compiled jar 
[here](http://pub.furu.guru/ThreadTest.jar), then run `java -jar ThreadTest.jar [consumers|poll]`.

Consumers runs three threads, asking you for numeric input. Every number
you give it, it packs up and sends to Adder and Multiplier, which run in their 
own threads. Input `0` to signal the end of the input and print the results.

Poll runs a sleeping thread, constantly checking it's state in a while loop,
incrementing a counter each time. Once the sleeping thread's state changes
to signal that it's finished, it reports the number of times it polled the thread
to see if it was finished yet. Input should be in milliseconds.

Learning is fun!