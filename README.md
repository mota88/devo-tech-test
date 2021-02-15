# devo-tech-test

This is a set of three programs that implement the following:

 1. Check if a string is a palindrome. A string is a palindrome if the string matches the reverse of string.
 2. Find k-complementary pairs in a given array of integers. Given an array A, the pair (i, j) is k-complementary if K = A[i] + A[j];
 3. Tf-idf (term frequency-inverse document frequency) is an statistic that reflects the importance of a term T in a document D (or the relevance of a document for a searched term) relative to a document set S. Tf-idf can be extended to a set of terms TT adding the tf-idf for each term. Assume that we have a directory D containing a document set S, with one file per document. Documents will be added to that directory by external agents, but they will never be removed or overwritten. We are given a set of terms TT, and asked to compute the tf-idf of TT for each document in D, and report the N top documents sorted by relevance. The program must run as a daemon/service that is watching for new documents, and dynamically updates the computed tf-idf for each document and the inferred ranking.

## Requirements

 - Java 1.8+
 - Maven 3.6.3.
 - Apache Commons IO (JARs included in this repository)
 
 ## Building

This is a Maven application. It's configured to be assembled into a _single jar file_.

To build the application, run the following instruction in the application's directory:

```bash
> mvn -B package --file pom.xml
```
## Usage

Once compiled and packaged, a `devo-0.0.1-SNAPSHOT.jar` file containing the application is generated in the `target` folder. This is an executable JAR file that can be run from the command line.

To run the Palindrome program, write the following:

```bash
> java -cp devo-0.0.1-SNAPSHOT.jar devo.Palindrome string1 string2 string3 ...
```
  - `string1` `string2` `string3` ...
      Words to be checked.

To run the k-complementary program, write the following:

```bash
> java -cp devo-0.0.1-SNAPSHOT.jar devo.Kcomplementary k int1 int2 int3 ...
```
  - `k`
      Number for the sums of pairs to be complementary.
  - `int1` `int2` `int3` ...
      Numbers in the list be checked.
      
To run the tf-idf calculator, write the following:

```bash
> java -cp "devo-0.0.1-SNAPSHOT.jar;../apache-commons-io/*" devo.DirectoryWatcher dir n p term1 term2 ...
```
  - `dir`
      Path to the directory to be checked for '.txt' files.
  - `n`
      Number of elements to be displayed in the results screen.
  - `p`
      Refresh interval period (in seconds).
  - `term1` `term2` ...
      Terms to be checked in the document.

Once started the application will stay running until interrupted, showing the most current results.

It processes new files as they appear in the watched directory, but results are only updated respecting the `p` refresh interval option.

## Samples included

The `samples` folder contains a number of ipsum text samples that can be used for testing purposes. For instance, try:

```bash
> java -cp "devo-0.0.1-SNAPSHOT.jar;../apache-commons-io/*" devo.DirectoryWatcher /devo-tech-test/samples 3 5 et quam lorem sed cursus
```

## Notes on the tf-idf program

  - It uses the general formula for its calculation, as explained in https://es.wikipedia.org/wiki/Tf-idf. However, using this formula, if one of the terms is not found in at least one document of the set, a division by zero occurs. This can be easily solved, as explained in the article, using a different formula. For the sake of generality, in this program we use the most extended one, therefore examples should always be "words that appear in at least one document of the collection".
  - Although it runs correctly and does what it's meant to, it's far from perfect. With more time I could organize, clean up and refactor a few things.
  - A program like this needs more extensive testing, but unfortunately this task would require much more time than what is beeng given.
