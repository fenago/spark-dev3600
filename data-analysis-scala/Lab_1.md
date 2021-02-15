

Chapter 1. Scala Overview
----------------------------------


The following topics will be covered in this chapter:


- Installing and getting started with Scala
- Object-oriented and functional programming overview
- Scala case classes and the collection API
- Overview of Scala libraries for data analysis




#### Java and SBT

A successfully installed JDK should output something similar to the
following code:

```
$ java -version
openjdk version "1.8.0_242"
OpenJDK Runtime Environment (build 1.8.0_242-8u242-b08-0ubuntu3~18.04-b08)
OpenJDK 64-Bit Server VM (build 25.242-b08, mixed mode)

$ javac -version
javac 1.8.0_242
```



The [**Scala Build Tool**] ([**SBT**]) is a
command-line tool that is very popular for
building Scala projects. It also provides a
Scala console that can be used for exploring
Scala language features and its API.


To verify that SBT and Scala are installed correctly on your machine, go
through the following steps:


1.  Run the `sbt` command and then run the `console`
    command inside `sbt` to get access to the Scala console,
    as follows:


```
$ sbt
[info] Loading project definition from /home/jovyan/project
[info] Set current project to jovyan (in build file:/home/jovyan/)
[info] sbt server started at local:///root/.sbt/1.0/server/0f3384ff9655b6db4a75/sock

sbt:jovyan> console
[info] Starting scala interpreter...
Welcome to Scala 2.12.10 (OpenJDK 64-Bit Server VM, Java 1.8.0_242).
Type in expressions for evaluation. Or try :help.

scala>
```

2.  Run `:quit` to exit the Scala console. To exit
    `sbt`, run the `exit` command:


```
scala> :quit

[success] Total time: 6 s, completed Sep 16, 2018 11:29:24 AM

sbt:jovyan> exit
[info] shutting down server
$
```

Explore Scala from SBT by performing some of the popular Scala
`List` operations. To do this, go through the following steps:


1.  Start `sbt` and get access to the Scala console, as
    follows:


```
$ sbt

[info] Loading project definition from /home/jovyan/project
[info] Set current project to jovyan (in build file:/home/jovyan/)
[info] sbt server started at local:///root/.sbt/1.0/server/0f3384ff9655b6db4a75/sock


scala> 
```

2.  Create a Scala `List` of US states using the following
    code:


```
scala> val someStates = List("NJ", "CA", "IN", "MA", "NY", "AZ",       
                             "PA")
someStates: List[String] = List(NJ, CA, IN, MA, NY, AZ, PA)
```

3.  Examine the size of the `List` as follows:


```
scala> someStates.size
res0: Int = 7
```

4.  Sort the `List` in ascending order as follows:


```
scala> someStates.sorted
res1: List[String] = List(AZ, CA, IN, MA, NJ, NY, PA)
```

5.  Reverse the `List` as follows:


```
scala> someStates.reverse
res2: List[String] = List(PA, AZ, NY, MA, IN, CA, NJ)
```

6.  Join the elements of the list using a comma (`,`) as a
    separator, as shown in the following code:


```
scala> someStates.mkString(",")
res3: String = NJ,CA,IN,MA,NY,AZ,PA
```

7.  Perform sort and join operations as a chain, as shown in the
    following code:


```
scala> someStates.sorted.mkString(",")
res4: String = AZ,CA,IN,MA,NJ,NY,PA
```

8.  Finally, exit the Scala console and quit `sbt` once you
    are done exploring, as shown in the following code:


```
scala> :quit

[success] Total time: 17 s, completed Sep 16, 2018 11:22:41 AM
sbt:someuser> exit
[info] shutting down server
```




Overview of object-oriented and functional programming 
------------------------------------------------------------------------



Scala supports object-oriented and functional
styles of programming. Both of these 
programming paradigms have been proven to help model and solve
real-world problems. In this section, we will explore both of these
styles of programming using Scala.



### Object-oriented programming using Scala



In the object-oriented paradigm, you think in
terms of objects and classes. A class can be
thought of as a template that acts as a basis for creating objects of
that type. For example, a `Vehicle` class can represent
real-world automobiles with the following attributes:


- `vin` (a unique vehicle identification number)
- `manufacturer`
- `model`
- `modelYear`
- `finalAssemblyCountry`


A concrete instance of `Vehicle`, representing a real-world
vehicle, could be:


- `vin`: `WAUZZZ8K6AA123456`
- `manufacturer`: `Audi`
- `model`: `A4`
- `modelYear`: `2009`
- `finalAssemblyCountry`:[** **]`Germany`


Let\'s put these attributes in action in Scala.

Go to the Scala/SBT console and write the following lines of code:


1.  Define `Vehicle` Scala class as per the preceding
    specifications:


```
scala> class Vehicle(vin: String, manufacturer: String, model:     
                     String, 
                     modelYear: Int, finalAssemblyCountry: String)
defined class Vehicle
```

2.  Create an instance of `Vehicle` class:


```
scala> val theAuto = new Vehicle("WAUZZZ8K6AA123456", "Audi", "A4",  
                                  2009, "Germany")
theAuto: Vehicle = Vehicle@7c6c2822
```

Following is the IntelliJ Scala worksheet:


![](./images/50259238-77b4-4a6d-ba58-c6957caae926.png)



Let\'s look at encapsulation and abstraction in Scala
[**Read-Evaluate-Print-Loop**] ([**REPL**]). We\'ll
use Scala\'s `construct` class to define a template for a
real-world`Vehicle`, as shown in the following code:


1.  Let us define `Vehicle` class, this is an example of
    abstraction because we are taking real-world complex entities and
    defining a simple model to represent them. When internals of
    implementations is hidden then it is an example of encapsulation.
    Publicly visible methods define behavior:


```
scala> class Vehicle(vin: String, manufacturer: String, model: String, modelYear: Int, finalAssemblyCountry: String) { // class is an example of abstraction
     | private val createTs = System.currentTimeMillis() // example of encapsulation (hiding internals)
     | def start(): Unit = { println("Starting...") } // behavior
     | def stop(): Unit = { println("Stopping...") } // behavior
     | }
defined class Vehicle
```

2.  Now let create an instance of `Vehicle`. This is also an
    abstraction because `Vehicle` class is a template
    representing a simplified model of real-world vehicles. An instance
    of `Vehicle` represents a very specific vehicle but it is
    still a model:


```
scala> val theAuto = new Vehicle("WAUZZZ8K6AA123456", "Audi", "A4", 
     2009, "Germany") // object creation is an example of abstraction
theAuto: Vehicle = Vehicle@2688b2be
```

3.  Perform `start` behavior on the object:


```
scala> theAuto.start()
Starting...
```

4.  Perform `stop` behavior on the object:


```
scala> theAuto.stop()
Stopping...
```

To reiterate the main points aforementioned, the ability to define a
class is an example of abstraction. Inside the class, we have an
attribute called `createTs` (creation timestamp). The scope of
this attribute is private and this attribute cannot be accessed from
outside the class. The ability to hide internal details is an example of
[**encapsulation**].

Now let\'s look at inheritance and polymorphism in Scala REPL. We\'ll
define a new class called `SportsUtilityVehicle` by extending
the `Vehicle` class, as shown in the following code:


1.  Define `SportsUtilityVehicle` class that provides an
    extension to `Vehicle` class:


```
scala> class SportsUtilityVehicle(vin: String, manufacturer: String, model: String, modelYear: Int, finalAssemblyCountry: String, fourWheelDrive: Boolean) extends Vehicle(vin, manufacturer, model, modelYear, finalAssemblyCountry) { // inheritance example
     | def enableFourWheelDrive(): Unit = { if (fourWheelDrive) println("Enabling 4 wheel drive") }
     | override def start(): Unit = {
     | enableFourWheelDrive()
     | println("Starting SUV...")
     | }
     | }
defined class SportsUtilityVehicle
```

2.  Create an instance of `SUV` object but assign to
    `Vehicle` type object, this is permissible because every
    SUV object is also a `Vehicle`:


```
scala> val anotherAuto: Vehicle = new SportsUtilityVehicle("WAUZZZ8K6A654321", "Audi", "Q7", 2019, 
                     "Germany", true)
anotherAuto: Vehicle = SportsUtilityVehicle@3c2406dd
```

3.  Perform start behavior on the object, on doing so the object
    exhibits the behavior of an SUV class. This is the polymorphism
    property facilitated by the object-oriented paradigm:


```
scala> anotherAuto.start() // polymorphism example
Enabling 4 wheel drive
Starting SUV...
```

Inheritance is a powerful construct that allows us to reuse code. We
created an instance of`SportsUtilityVehicle`and assigned it to
a type of vehicle. When we invoke the`start` method on this
object, the runtime system automatically determines the actual type of
object and calls the`start`method defined
in`SportsUtilityVehicle`. This is an example of polymorphism,
where we can treat objects as base types; however, at runtime, the
appropriate behavior is applied depending upon thetrue type of the
object.

The following is a UML diagram with a more formal representation of the
inheritance relationship:


![](./images/95727c62-4440-495a-8973-dbe228e88db2.png)


 It captures the following important properties:


- The [**Vehicle**] is a super-class or base-class 
- [**SportsUtilityVehicle**] is a sub-class that extends the
    [**Vehicle**] base-class
- This relationship can be envisioned as a parent-child relationship

The following is a screenshot of the same example in IDE:


![](./images/5f3aa033-1735-41fa-889e-216f27ef6e6a.png)


IDEs such IntelliJ help us to visualize many of the properties of
classes and objects in a user-friendly way. A good IDE certainly acts as
a great productivity tool. In the preceding example, the IDE screen is
divided into the following three parts:


- [**Structure**]: Structural properties of classes and
    objects, such as methods and attributes
- [**Source code**]: Source code in the context
- [**Runtime**]: Output from the execution of the program



### Functional programming using Scala



In the functional programming paradigm, functions become the primary tool for modeling solutions to a problem. In the
simplest form, we can think of a function as
something that accepts one or more input and produces an output. 

To illustrate this concept, let\'s define a function in Scala that
accepts two sets of integer input and returns the sum of two integers
plus one, as shown in the following code:

```
scala> val addAndInc = (a: Int, b: Int) => a + b + 1
addAndInc: (Int, Int) => Int = <function2>

scala> addAndInc(5, 10)
res0: Int = 16
```

In the preceding example, we have created an anonymous function that
takes two sets of integer input and returns an integer output. This
function increments the sum of two input numbers and returns the result.
There is another way of defining a function in Scala as a named method.
Let\'s look at that in the Scala REPL:

```
scala> def addAndIncMethod(a: Int, b: Int) = a + b + 1
addAndIncMethod: (a: Int, b: Int)Int

scala> addAndIncMethod(5, 10)
res1: Int = 16

scala> val methodAsFunc = addAndIncMethod
<console>:12: error: missing argument list for method addAndIncMethod
Unapplied methods are only converted to functions when a function type is expected.
You can make this conversion explicit by writing `addAndIncMethod _` or `addAndIncMethod(_,_)` instead of `addAndIncMethod`.
       val methodAsFunc = addAndIncMethod
```

In the preceding example, we have defined a method that is bound to a
name. The usage of the anonymous function and the named method is
identical; however, there are some subtle differences between the two,
as shown in the following list:


- The signature of the anonymous function
    is `(Int, Int) => Int` and the signature of the named
    method is `(a: Int, b: Int)Int`
- When we try to assign a method to a variable, we get an error


A method can be easily converted into an anonymous function in Scala by
doing the following:

```
scala> val methodAsFunc = addAndIncMethod _ // turns method into function
methodAsFunc: (Int, Int) => Int = <function2>

scala> methodAsFunc(5, 10)
res2: Int = 16
```

As can be seen, after conversion, the signature changes
to `(Int, Int) => Int`. 

Anonymous functions and named methods are both useful in the context of
Scala programming. In the previous section on Scala\'s object-oriented
programming, we defined a `scala` class with some methods.
These were all named methods, and would not be of much value from an
object-oriented point of view if these were anonymous methods.


Functions can be defined within a function. Let\'s look at the following
concrete example using Scala REPL to see why this is useful:

```
scala> def factorial(n: Int): Long = if (n <= 1) 1 else n * factorial(n-1)
factorial: (n: Int)Int

scala> factorial(5)
res0: Long = 120
```

The preceding code is an example of the `factorial` function,
and it uses recursion to compute this value. We know that classic
recursion requires a stack size proportional to the number of
iterations. Scala provides tail recursion optimization to address this
problem, and we can make use of an inner function to optimize this
problem. In the following code, we\'ll define a function inside a
function:

```
scala> import scala.annotation.tailrec
import scala.annotation.tailrec

scala> def optimizedFactorial(n: Int): Long = {
     | @tailrec
     | def factorialAcc(acc: Long, i: Int): Long = {
     | if (i <= 1) acc else factorialAcc(acc * i, i -1)
     | }
     | factorialAcc(1, n)
     | }
optimizedFactorial: (n: Int)Long

scala> optimizedFactorial(5)
res1: Long = 120
```


Recursion is a very useful programming construct and tail recursion is a
special type of recursion that can be optimized at compile time. Let us
first try to understand what recursion really is and what type of
recursion is considered tail recursive. In simple terms, any function
that calls or invokes itself one or more times is considered recursive.
Our factorial examples in both forms are recursive. Let us look at the
execution of the first version for a value of `5`:

```
factorial(5)
5 * factorial(4)
5 * 4 * factorial(3)
5 * 4 * 3 * factorial(2)
5 * 4 * 3 * 2 * factorial(1)
5 * 4 * 3 * 2 * 1
120
```

For the second version:

```
optimizedFactorial(5)
factorialAcc(1, 5)
factorialAcc(1*5, 4)
factorialAcc(1*5*4, 3)
factorialAcc(1*5*4*3, 2)
factorialAcc(1*5*4*3*2, 1)
1*5*4*3*2
120
```

The most important difference between the two versions is in the last
return statement of the function:

```
// output of next invocation must be multiplied to current number, so // the state (current number) has to preserved on stack frame
n * factorial(n-1)

// function being called already knows the current state being passed // as the first argument so it does not need preserved on stack frame
factorialAcc(acc * i, i -1) 
```


The following is a screenshot of the preceding recursion examples in
IntelliJ IDE. The IDE helps us clearly see which functions or methods
are purely [**Recursive**] and which ones
are [**T**][**ail Recursive**]:


![](./images/672ddd00-92cc-40b7-bed9-32a59659e4ab.png)


Please note the specific symbols next to `factorial` and
`optimizedFactorial`. The two symbols are different, and if
you hover over them, you can see the full description, listed as
follows:


- Method factorial is recursive
- Method factorial is tail recursive


Let\'s use the following code to see whether we are able to apply tail
recursion optimization to the original factorial function in Scala REPL:

```
scala> @tailrec
     | def factorial(n: Int): Long = if (n <= 1) 1 else n * factorial(n-1)
<console>:14: error: could not optimize @tailrec annotated method factorial: it contains a recursive call not in tail position
       def factorial(n: Int): Long = if (n <= 1) 1 else n * factorial(n-1)
```




Scala case classes and the collection API 
-----------------------------------------------------------



Scala case classes and its collection API play a significant role in data 
analysis using Scala. This section will give you insight into these
topics and an understanding of their relevance in the context of data
analysis.

During the data analysis process, we will frequently encounter data that
consists of a collection of records. These records often need to
be transformed, cleaned, or filtered.



### Scala case classes



Scala case classes provide a convenient mechanism to work with objects that hold values. Let\'s look at an example in
Scala REPL. The `case` class defined in the following code
will be used in other example codes in this chapter:

```
scala> case class Person(fname: String, lname: String, age: Int)
defined class Person

scala> val jon = Person("Jon", "Doe", 21)
jon: Person = Person(Jon,Doe,21)
```

In the preceding example, we have defined a Scala case class
called `Person` with three attributes, namely
`fname`, `lname`, and `age`. We created an
instance, `jon`, of the `Person` class without using
the new keyword. Also, note that the `jon` object\'s
attributes are printed out in a easy-to-use form. There are several such
convenient features associated with Scala case classes that are
extremely beneficial for programmers in general, particularly someone
who deals with data.

Let\'s look at another convenient feature of Scala case classes, namely
the `copy `object. We\'ll copy a Scala `case` class
object by updating only the `fname` attribute using the
following code:

```
scala> case class Person(fname: String, lname: String, age: Int)
defined class Person

scala> val jon = Person("Jon", "Doe", 21)
jon: Person = Person(Jon,Doe,21)

scala> val jonNew = jon.copy(fname="John")
jonNew: Person = Person(John,Doe,21)
```

This feature comes in really handy during data processing when we work
with a template representation and generate specific instances from a
template by updating a subset of attributes.

Another great feature of case classes is pattern matching, which helps
in writing flexible code that is easier to work with. Let\'s look at an
example of pattern matching in Scala REPL, as shown in the following
code:

```
scala> def isJon(p: Person) = {
     | p match {
     | case Person("Jon", _, _) => {println("I am Jon"); true}
     | case Person(n,_,_) => {println(s"I am not Jon but I am ${n}"); false}
     | case _ => {println("I am not Jon but I am something other than Person"); false}
     | }
     | }
isJon: (p: Person)Boolean

scala> val jon = Person("Jon", "Doe", 25)
jon: Person = Person(Jon,Doe,25)

scala> isJon(jon)
I am Jon
res13: Boolean = true

scala> val bob = Person("Bob", "Crew", 27)
bob: Person = Person(Bob,Crew,27)

scala> isJon(bob)
I am not Jon but I am Bob
res14: Boolean = false

scala> isJon(null)
I am not Jon but I am something other than Person
res16: Boolean = false
```

We can explore the same example in the IDE, as shown in the
following screenshot:


![](./images/0bb78541-dbb7-4d3f-a531-08f37a5dca5c.png)


Using the IDE, we can clearly see the properties of the `case`
class. Another great option is to use the Scala worksheet feature in IDE
to explore this example, as shown in the following screenshot:


![](./images/40b02cdc-7e66-4e22-83a0-bdd6864a5d75.png)


Let\'s look at the preceding example in a bit more detail, looking at
the following lines:


- [**Line \#4**]: `case Person("Jon",  _,  _)`means
    any person whose first name is `Jon`
- [**Line \#7**]: `case Person(n, _, _)` means any
    person with the first name is extracted into variable `n`
- [**Line \#10**]: `case _` means anything that
    does not match line \#4 and line \#7


With classic pattern matching, it is generally necessary for you to
write a significant amount of boilerplate code with
`if-then-else` types of constructs. Scala and its case classes
provide a concise and expressive way to solve this problem.


### Scala collection API



Scala has a comprehensive API for working conveniently
with collections. A good understanding of
some of the APIs is essential for making effective use of Scala in data
analysis.

Although a full review of the Scala collection API is not part of the
scope of this book, three key data structures will be covered in this
section: the array, list, and map. The emphasis here is on their direct
relevance to data analysis. For complete details, please refer to the
official Scala resource
at <https://www.scala-lang.org/api/current/scala/collection/index.html>.

It is also important to consider the performance characteristics of a
Scala collection API and use this information in making appropriate data
structure selections for the problem being solved. Refer
to [https://docs.scala-lang.org/overviews/collections/performance-characteristics.html ](https://docs.scala-lang.org/overviews/collections/performance-characteristics.html){.ulink}for
more information.



#### Array



The following is an example run execution in Scala REPL to demonstrate
the Scala array and some of its functionality:

```
scala> val persons = Array(Person("Jon", "Doe", 21), Person("Alice", "Smith", 20), Person("Bob", "Crew", 27)) // construct a Array of Person objects
persons: Array[Person] = Array(Person(Jon,Doe,21), Person(Alice,Smith,20), Person(Bob,Crew,27))

scala> val personHead = persons.head // first person in the collection
personHead: Person = Person(Jon,Doe,21)

scala> val personAtTwo = persons(2) // person at index 2 (this is same as apply operation)
personAtTwo: Person = Person(Bob,Crew,27)

scala> val personsTail = persons.tail // collection without the first person
personsTail: Array[Person] = Array(Person(Alice,Smith,20), Person(Bob,Crew,27))

scala> val personsByAge = persons.sortBy(p => p.age) // sort persons by age
personsByAge: Array[Person] = Array(Person(Alice,Smith,20), Person(Jon,Doe,21), Person(Bob,Crew,27))

scala> val personsByFname = persons.sortBy(p => p.fname) // sort persons by first name
personsByFname: Array[Person] = Array(Person(Alice,Smith,20), Person(Bob,Crew,27), Person(Jon,Doe,21))

scala> val (below25, above25) = persons.partition(p => p.age <= 25) // split persons by age
below25: Array[Person] = Array(Person(Jon,Doe,21), Person(Alice,Smith,20))
above25: Array[Person] = Array(Person(Bob,Crew,27))

scala> val updatePersons = persons.updated(0, Person("Jon", "Doe", 20)) // update first element
updatePersons: Array[Person] = Array(Person(Jon,Doe,20), Person(Alice,Smith,20), Person(Bob,Crew,27))
```

The following is a summary of the array operations and their associated
performance characteristics:

![](./images1.PNG)
 

As can be seen in the preceding table, the `apply` operation
for getting the element at a specified index is a fast constant-time
operation for an array. Along similar lines, the `update`
operation for replacing an element at the specified index is also a fast
constant-time operation. On the other hand, the `tail`
operation for getting elements other than the `head` is a slow
linear time operation. In fact, the `prepend`,
`append`, and `insert` operations are not even
supported for an array. This might seem a limiting factor at first, but
Scala has an `ArrayBuffer` class for building an array, and
that should be used if such operations are necessary.

In data analysis, we typically create a dataset initially and use it
over and over again during different phases of the analysis. This
implies that the dataset construction is generally a one-time step, and
the construction is then used multiple times. This is precisely why a
builder such as `ArrayBuffer` is separated from the array:
because each serves a different purpose. The `ArrayBuffer` is
designed to help in the construction of the array with support for the
commonly desired build operations. Let\'s look at
the `ArrayBuffer` functionality using Scala REPL, as shown in
the following code:

```
scala> import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ArrayBuffer

scala> val personsBuf = ArrayBuffer[Person]() // create ArrayBuffer of Person
personsBuf: scala.collection.mutable.ArrayBuffer[Person] = ArrayBuffer()
scala> personsBuf.append(Person("Jon", "Doe", 21)) // append a Person object at the end
scala> personsBuf.prepend(Person("Alice", "Smith", 20)) // prepend a Person object at head
scala> personsBuf.insert(1, Person("Bob", "Crew", 27)) // insert a Person object at index 1
scala> val persons = personsBuf.toArray // materialize into an Array of Person
persons: Array[Person] = Array(Person(Alice,Smith,20), Person(Bob,Crew,27), Person(Jon,Doe,21))
scala> val personRemoved = personsBuf.remove(1) // remove Person object at index 1
personRemoved: Person = Person(Bob,Crew,27)

scala> val personsUpdated = personsBuf.toArray // materialize into an Array of Person
personsUpdated: Array[Person] = Array(Person(Alice,Smith,20), Person(Jon,Doe,21))
```

As can be seen in the preceding code, `ArrayBuffer` provides a
comprehensive set of functionalities to construct a collection and
provides a convenient mechanism to materialize it into an array once
construction is complete.


#### List



A `List` provides fast and constant time performance
for`head` and `tail`
operations in a collection. We can visualize a List as a collection of
elements that are connected by some kind of link. Let\'s look at the
Scala `List` functionality using Scala REPL, as shown in the
following code:

```
scala> val persons = List(Person("Jon", "Doe", 21), Person("Alice", "Smith", 20), Person("Bob", "Crew", 27)) // construct a List of Person objects
persons: List[Person] = List(Person(Jon,Doe,21), Person(Alice,Smith,20), Person(Bob,Crew,27))

scala> val personHead = persons.head // first person in the collection
personHead: Person = Person(Jon,Doe,21)

scala> val personAtTwo = persons(2) // person at index 2 (this is same as apply operation)
personAtTwo: Person = Person(Bob,Crew,27)

scala> val personsTail = persons.tail // collection without the first person
personsTail: List[Person] = List(Person(Alice,Smith,20), Person(Bob,Crew,27))

scala> val personsByAge = persons.sortBy(p => p.age) // sort persons by age
personsByAge: List[Person] = List(Person(Alice,Smith,20), Person(Jon,Doe,21), Person(Bob,Crew,27))

scala> val personsByFname = persons.sortBy(p => p.fname) // sort persons by first name
personsByFname: List[Person] = List(Person(Alice,Smith,20), Person(Bob,Crew,27), Person(Jon,Doe,21))

scala> val (below25, above25) = persons.partition(p => p.age <= 25) // split persons by age
below25: List[Person] = List(Person(Jon,Doe,21), Person(Alice,Smith,20))
above25: List[Person] = List(Person(Bob,Crew,27))

scala> val updatePersons = persons.updated(0, Person("Jon", "Doe", 20)) // update first element
updatePersons: List[Person] = List(Person(Jon,Doe,20), Person(Alice,Smith,20), Person(Bob,Crew,27))
```

The following is a summary of the List operations and their associated
performance characteristics:

![](./images2.PNG)

As can be seen in the preceding table, the List enables very fast
`head`, `tail`, and `prepend`
operations. For the array type described earlier, we saw that
`tail` was an expensive linear time operation. The
`apply` operation for getting an element at the specified
index is a linear time operation. This is because the desired element
can only be located by traversing the links, starting from the
`head`. This explains why an update is a slow operation for
the List.

In a real-world scenario, constant time performance is the desired
behavior and we want to avoid linear time performance, particularly for
large datasets. Performance is an important factor in determining the
most suitable data structure for the problem being solved. If constant
time performance is not practical, we generally look for data structures
and algorithms that provide [*Log Time performance O(log
n)*]{.emphasis}: time proportional to the logarithm of the collection
size. Note that there are many algorithms, such as sorting, with best
performance times of [*O(n log n)*]{.emphasis}. When dealing with large
datasets, a good understanding of the performance characteristics of the
data structures and algorithms that are used goes a long way in solving
problems effectively and efficiently.

Similar considerations hold true for memory usage, even though larger
amounts of RAM are now becoming available at a cheaper price. This is
because the growth in the size of data being produced is much higher
than the drop in prices of RAM.

Let\'s now look at `ListBuffer`, which can be used for
constructing a list more efficiently. This will be very useful, given
that the `append` operation has significant performance
overheads. As mentioned earlier, datasets are generally constructed once
but are used multiple times during data analysis processes. Let\'s look
at the following code:

```
scala> import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer

scala> val personsBuf = ListBuffer[Person]() // create ListBuffer of Person
personsBuf: scala.collection.mutable.ListBuffer[Person] = ListBuffer()

scala> personsBuf.append(Person("Jon", "Doe", 21)) // append a Person object at end

scala> personsBuf.prepend(Person("Alice", "Smith", 20)) // prepend a Person object at head

scala> personsBuf.insert(1, Person("Bob", "Crew", 27)) // insert a Person object at index 1

scala> val persons = personsBuf.toList // materialize into a List of Person
persons: List[Person] = List(Person(Alice,Smith,20), Person(Bob,Crew,27), Person(Jon,Doe,21))

scala> val personRemoved = personsBuf.remove(1) // remove Person object at index 1
personRemoved: Person = Person(Bob,Crew,27)

scala> val personsUpdated = personsBuf.toList // materialize into a List of Person
personsUpdated: List[Person] = List(Person(Alice,Smith,20), Person(Jon,Doe,21))
```

If we compare `ArrayBuffer` and `ListBuffer`, we can
see that they both offer similar APIs. Their primary use is for
constructing an array and list respectively, providing good performance
characteristics.

The decision of choosing between array and list is dependent on how the
dataset will be used. The following are some useful tips:


- An array should generally be the first choice because of its storage
    efficiency. Array operations are somewhat limited compared to list
    operations, and the usage pattern becomes the determining factor.
- If a `tail` operation is necessary, a list is the obvious
    choice. In fact, there are many recursive algorithms that make
    extensive use of this feature. Using an array instead of a list will
    result in a significant performance penalty.
- If `apply` or `update` operations are desired,
    then an array is certainly a better choice.
- If the `prepend` operation is needed or if a limited use
    of `append` is required, then a list is the only choice
    because an array does not support the `prepend` or
    `append` operations.


As you can see, there are many factors at play when it comes to
selecting the appropriate data structure. This is often the case in any
software design decision where there are conflicting needs and you need
to decide how to make trade-offs. For example, you might decide in favor
of using a list even though none of the non-array features of a list are
required based on current usage patterns. This could be because of the
list\'s fast `tail` operation, which could be beneficial for
the recursive algorithms in future usage patterns.

Recursive algorithms play a central role in functional programming. In
fact, Scala supports tail-recursion optimization out of the box, which
facilitates practical usage of recursive algorithms with large datasets.
With classic recursion, a significant amount of stack space is required,
making it impractical to use on large datasets. With tail-recursion
optimization, the Scala compiler eliminates the stack growth under the
hood. Let\'s look at a classic recursion and tail-recursion example:

```
scala> import annotation.tailrec
import annotation.tailrec

scala> @tailrec def classicFactorial(n: Int): Int = { require(n > 0); if (n == 1) n else n * classicFactorial(n-1) } // this should fail
<console>:14: error: could not optimize @tailrec annotated method classicFactorial: it contains a recursive call not in tail position
       @tailrec def classicFactorial(n: Int): Int = { require(n > 0); if (n == 1) n else n * classicFactorial(n-1) }

scala> def classicFactorial(n: Int): Int = { require(n > 0, "n must be non-zero and positive"); if (n == 1) n else n * classicFactorial(n-1) } // this should work
classicFactorial: (n: Int)Int

scala> val classicResult = classicFactorial(5)
classicResult: Int = 120
scala> def tailRecFactorial(n: Int): Int = {
     | require(n > 0, "n must be non-zero and positive")
     | @tailrec def factorial(acc: Int, m: Int): Int = if (m == 1) acc else factorial(acc * m, m-1) // this should work as this recursive algorithm meets tail recursion requirements
     | factorial(1, n)
     | }
tailRecFactorial: (n: Int)Int

scala> val tailRecResult = tailRecFactorial(5)
tailRecResult: Int = 120
```

The preceding examples provides insight into recursive functions, and in
particular demonstrates a tail-recursion variant. Let\'s look at the
following example of a tail-recursion algorithm using `List`:

```
scala> val persons = List(Person("Jon", "Doe", 21), Person("Alice", "Smith", 20), Person("Bob", "Crew", 27))
persons: List[Person] = List(Person(Jon,Doe,21), Person(Alice,Smith,20), Person(Bob,Crew,27))

scala> @tailrec def minAgePerson(acc: Option[Person], lst: List[Person]): Option[Person] = {
     | if (lst.isEmpty) acc
     | else if (acc.isEmpty) minAgePerson(Some(lst.head), lst.tail)
     | else if (acc.get.age <= lst.head.age) minAgePerson(acc, lst.tail)
     | else minAgePerson(Some(lst.head), lst.tail)
     | }
minAgePerson: (acc: Option[Person], lst: List[Person])Option[Person]

scala> val youngest = minAgePerson(None, persons) // Person with minimum age
youngest: Option[Person] = Some(Person(Alice,Smith,20))

scala> val youngestEmpty = minAgePerson(None, Nil) // Nil == List(), an empty list
youngestEmpty: Option[Person] = None
```

The preceding code is a very simple example of finding a
`Person` with the minimum age from a list of
`Person` objects. This simple example, however, illustrates
the following important and powerful points regarding Scala:


- It is fairly straightforward to write a tail-recursive algorithm
    using a list in Scala that accumulates information. This algorithm
    can traverse the entire list without incurring the overhead of stack
    growth in a classic recursion.
- Scala\'s `option` construct provides a convenient way of
    representing the presence or absence of an object.
- List\'s `head` and `tail` operations come in
    handy in writing such recursive algorithms, and provide the desired
    constant time performance for both these operations.
- The code is concise and works even on the empty list.
- Using the accumulator is a commonly used pattern in turning a
    classic recursion algorithm into a tail-recursion algorithm. 



#### Map



A Map provides a mapping from a key to the 
associated value. Lookups into a Map based on a key have a generally
constant time of [*O(1)*]{.emphasis}. A Map is an important data
structure that has many applications in the real world.

Let\'s look at some simple Map usage using Scala REPL:

```
scala> val countryToCurrency = Map(("US" -> "USD"), ("DE" -> "EUR"), ("FR" -> "EUR"), ("IN" -> "INR")) // Mapping from country code to currency code
countryToCurrency: scala.collection.immutable.Map[String,String] = Map(US -> USD, DE -> EUR, FR -> EUR, IN -> INR)

scala> countryToCurrency.keys // country codes
res4: Iterable[String] = Set(US, DE, FR, IN)

scala> countryToCurrency.values // currency codes
res5: Iterable[String] = MapLike.DefaultValuesIterable(USD, EUR, EUR, INR)

scala> countryToCurrency("US") // lookup currency code for US
res6: String = USD
```

In Scala, there are many different types of map, each with its own set
of characteristics. We will cover the following three:

![](./images3.PNG)
 

The following are some general considerations to bear in mind
regarding the performance of each of these `Map` types:


- `HashMap` is the best choice in most cases, particularly
    for lookup-centric use cases. `HashMap` does not preserve
    key insertion order or sort keys.
- `TreeMap` is suitable for use cases where keys need to be
    sorted.
- `LinkedHashMap` is most suited when the key insertion
    order needs to be preserved.


Let\'s explore some of these map types in Scala REPL using the following
code:


1.  Import `HashMap`, `TreeMap`, and
    `LinkedHashMap` from Scala\'s
    `collection.mutable` package. Each represents Map type but
    with a slightly different flavor:


```
scala> import collection.mutable.{HashMap,TreeMap,LinkedHashMap}
import collection.mutable.{HashMap, TreeMap, LinkedHashMap}
```

2.  Create a `HashMap` that maps a number to its English word
    equivalent. Notice that the order of keys is not preserved. The
    number `8` was at position `8` in our
    constructor however in the object created it is at the first
    position:


```
scala> val numHashMap = HashMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) // keys can be in any order
numHashMap: scala.collection.mutable.HashMap[Int,String] = Map(8 -> eight, 2 -> two, 5 -> five, 4 -> four, 7 -> seven, 1 -> one, 9 -> nine, 3 -> three, 6 -> six)
```

3.  Add a new entry of `0`. This got added at the very end
    however this is just a coincidence, it could have been anywhere:


```
// add new mapping, keys can be any order
scala> numHashMap += (0->"zero") 
res5: numHashMap.type = Map(8 -> eight, 2 -> two, 5 -> five, 4 -> four, 7 -> seven, 1 -> one, 9 -> nine, 3 -> three, 6 -> six, 0 -> zero)
```

4.  Create a `TreeMap` similar to `HashMap`. Note
    that the order of keys is preserved. In fact, this is due to keys
    automatically being sorted by `TreeMap`. Our object
    construction had provided the keys in ascending order:


```
// keys must be sorted
scala> val numTreeMap = TreeMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) 
numTreeMap: scala.collection.mutable.TreeMap[Int,String] = TreeMap(1 -> one, 2 -> two, 3 -> three, 4 -> four, 5 -> five, 6 -> six, 7 -> seven, 8 -> eight, 9 -> nine)
```

5.  Add a new entry to `TreeMap` with key as `0`.
    This gets added to the beginning because of key sorting in a
    `TreeMap`:


```
// add a new mapping, keys must get sorted
scala> numTreeMap += (0->"zero") 
res6: numTreeMap.type = TreeMap(0 -> zero, 1 -> one, 2 -> two, 3 -> three, 4 -> four, 5 -> five, 6 -> six, 7 -> seven, 8 -> eight, 9 -> nine)
```

6.  Create a `LinkedHashMap` similar to `HashMap`
    and `TreeMap`. Note that keys appear exactly as it was
    specified in the constructor:


```
// order must be preserved
scala> val numLinkedHMap = LinkedHashMap((1->"one"), (2->"two"), (3->"three"), (4->"four"), (5->"five"), (6->"six"), (7->"seven"), (8->"eight"), (9->"nine")) 
numLinkedHMap: scala.collection.mutable.LinkedHashMap[Int,String] = Map(1 -> one, 2 -> two, 3 -> three, 4 -> four, 5 -> five, 6 -> six, 7 -> seven, 8 -> eight, 9 -> nine)
```

7.  Add new entry to `LinkedHashMap` with key as
    `0`. This gets added at the very end because
    `LinkedHashMap` preserves the order of key insertion:


```
// this must be the last element
scala> numLinkedHMap += (0->"zero") 
res17: numLinkedHMap.type = Map(1 -> one, 2 -> two, 3 -> three, 4 -> four, 5 -> five, 6 -> six, 7 -> seven, 8 -> eight, 9 -> nine, 0 -> zero)
```

Scala offers a good number of choices in terms of Map implementation.
You can choose the best option based on the usage pattern. Similar to
the design choices between arrays and lists, at times there are
trade-offs that need to be considered in deciding the best Map
implementation.



Overview of Scala libraries for data analysis 
---------------------------------------------------------------



There are a great number of Scala libraries and
frameworks that simplify data analysis tasks.
There is a lot of innovation happening regarding the simplification of
data analysis-related tasks, from simple tasks such as data cleaning, to
more advanced tasks such as deep learning. The following sections focus
on the most popular data-centric libraries and frameworks that have
seamless Scala integration.



### Apache Spark



Apache Spark (<https://spark.apache.org/>) is a unified analytics
engine for large-scale data processing. Spark
provides APIs for batch as well as stream
data processing in a distributed computing
environment. Spark\'s API can be broadly divided into the following five
categories:


- [**Core**]: RDD
- [**SQL structured**]: DataFrames and Datasets
- [**Streaming**]: Structured streaming and DStreams
- [**MLlib**]: Machine learning
- [**GraphX**]: Graph processing

Apache Spark is a very active open source project. New features are
added and performance improvements made on a regular basis. Typically,
there is a new minor release of Apache Spark every three months with
significant performance and feature improvements. At the time of
writing, 2.4.0 is the most recent version of Spark.

The following is Spark core\'s SBT dependency:

```
scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.1"
```

Spark version 2.4.0 has introduced support for Scala version 2.12;
however, we will be using Scala version 2.11 for exploring Spark\'s
feature sets. Spark will be covered in more detail in the subsequent
chapters.



Summary 
-------------------------



This chapter provided a high-level overview of the Scala programming
language. We looked at some of the object-oriented and functional
programming aspects of Scala using applied examples. This chapter
touched upon the array, list, and map functionalities of the Scala
collection API. These data structures have numerous uses in the data
analysis life cycle. The chapter also provided the necessary information
to set up and install the Scala tools that are essential for
understanding and applying the topics covered in subsequent chapters.
Finally, a quick overview of the data-centric Scala libraries was
provided. We will be making use of these libraries in the next few
chapters to solve specific data life cycle problems.

In the next chapter, we will look at the data analysis life cycle and
associated tasks.
