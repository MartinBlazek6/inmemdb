package org.example.atat;

import java.util.Arrays;

public class Main {
    static  InMemoryDatabase db = new InMemoryDatabase();

    public static void dbIterator(String... commands){
        Arrays.stream(commands).forEach(command -> System.out.printf("%s,",db.executeInstructions(command)));
    }
    public static void main(String[] args) {

        dbIterator("SET_OR_INC foo bar 42",
                "GET bar foo",
                "GET foo bar",
                "SET_OR_INC bar foo 10",
                "GET bar foo",
                "SET_OR_INC foo baz 1337",
                "SET_OR_INC bar foo 15",
                "SET_OR_INC bar foo 15",
                "DELETE foo bar");

//        System.out.println(db.executeInstructions("SET_OR_INC foo bar 42"));
//        System.out.println(db.executeInstructions("GET bar foo"));
//        System.out.println(db.executeInstructions("GET foo bar"));
//        System.out.println(db.executeInstructions("SET_OR_INC bar foo 10"));
//        System.out.println(db.executeInstructions("GET bar foo"));
//        System.out.println(db.executeInstructions("SET_OR_INC foo baz 1337"));
//        System.out.println(db.executeInstructions("SET_OR_INC bar foo 15"));
//        System.out.println(db.executeInstructions("DELETE foo bar"));

//Exercise: In-Memory Database with Simple Language
//
//you are tasked with creating an in-memory database that operates using a simple language composed of various instructions.
//Each instruction manipulates the database entries in a specific way.
// Your task is to implement the functionality for the database according  to the requirements below.
//
//Basic Database Operations
//
//Create the basic operations for interacting with the in-memory database.
//
//Instructions:
//
//GET <key> <column>
//Returns the (string representation of the) value stored in the record specified by <key>. <column>; "" (empty string) if <key>.<column> has not been set
//
//-SET_OR_INC <key> <column> <value>
//
//Sets the record at <key>.<column> to the specified <value> if no such record exists yet (GET <key> <column> would return "").
//Otherwise, the current value at <key>.<column> is incremented by the specified <value>
//Returns the (string representation of the) newly set value stored in the record specified by <key>.<column> ;
//should never return "" (as the value  should be set correctly)
//
//DELETE <key> <column>
//Deletes the record (column) at <key>.<column>
//Returns "true" if the column has been successfully deleted; "false" if the record does not exist in the database
//
//w <count>
//Returns a comma-separated list of length <count> containing the database record keys sorted by the number of write (SET* or DELETE*)
//operations performed at each key. If two keys have an equal number of operations, sort them alphabetically.
// = The output format of each array element should be <key>(<operations count>) - see the sample output below
//
//As the input, your application should accept a string input representing a list of instructions and their parameters.
//Each instruction will be on a new line. An Instruction keyword will be followed by space-separated list of instruction parameters;
//extra parameters are permissible and should be ignored.
// The input will be correctly formatted/valid and include a list of instructions to execute.
// Your application should return a comma-separated instruction output values in the correct order.
//
//Sample input:
//
//SET_OR_INC foo bar 42
//GET bar foo
//GET foo bar
//SET_OR_INC bar foo 10
//GET bar foo
//SET_OR_INC foo baz 1337
//SET_OR_INC bar foo 15
//DELETE foo bar
//
//Simple Output:
//
//        42, , 42, 10, 10, 1337, 25, true
    }

}