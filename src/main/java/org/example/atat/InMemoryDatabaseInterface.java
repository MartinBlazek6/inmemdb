package org.example.atat;

public interface InMemoryDatabaseInterface {

    /**
     * Retrieves the value associated with the given key and column.
     *
     * @param key    the key to search for
     * @param column the column to search for
     * @return the value associated with the key and column, or an empty string if not found
     */
    String get(String key, String column);

    /**
     * Sets or increments the value for the specified key and column.
     * If the column already exists for the key, the value is incremented.
     *
     * @param key    the key to modify
     * @param column the column to modify
     * @param value  the value to set or increment
     * @return the new value after setting or incrementing
     */
    String setOrInc(String key, String column, String value);

    /**
     * Deletes the specified column for the given key.
     *
     * @param key    the key to modify
     * @param column the column to delete
     * @return true if the column was deleted, false otherwise
     */
    boolean delete(String key, String column);

    /**
     * Retrieves the top N keys sorted by the number of operations performed on them.
     *
     * @param count the number of top keys to retrieve
     * @return a string representation of the top N keys and their operation counts
     */
    String w(int count);

    /**
     * Executes a given instruction on the database.
     *
     * @param instruction the instruction to execute
     * @return the result of the instruction execution
     */
    String executeInstructions(String instruction);

    /**
     * Displays the current state of the database.
     */
    void showDb();
}
