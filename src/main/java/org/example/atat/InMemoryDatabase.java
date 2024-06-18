package org.example.atat;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryDatabase {
    private Map<String, Map<String, String>> db;
    private Map<String, Integer> operationCount;

    public InMemoryDatabase() {
        this.db = new HashMap<>();
        this.operationCount = new HashMap<>();
    }

    public String get(String key, String column) {
        return db.getOrDefault(key, Collections.emptyMap()).getOrDefault(column, "");
    }

    public String setOrInc(String key, String column, String value) {
        db.putIfAbsent(key, new HashMap<>());
        operationCount.put(key, operationCount.getOrDefault(key, 0) + 1);

        Map<String, String> columns = db.get(key);
        if (columns.containsKey(column)) {
            int newValue = Integer.parseInt(columns.get(column)) + Integer.parseInt(value);
            columns.put(column, String.valueOf(newValue));
        } else {
            columns.put(column, value);
        }
        return columns.get(column);
    }

    public boolean delete(String key, String column) {
        if (db.containsKey(key) && db.get(key).containsKey(column)) {
            db.get(key).remove(column);
            operationCount.put(key, operationCount.getOrDefault(key, 0) + 1);
            if (db.get(key).isEmpty()) {
                db.remove(key);
            }
            return true;
        }
        return false;
    }

    public String w(int count) {
        return db.keySet().stream()
                .sorted(Comparator.comparingInt(k -> operationCount.get(k)).reversed())
                .limit(count)
                .map(key -> key + "(" + operationCount.get(key) + ")")
                .collect(Collectors.joining(","));
    }

    public String executeInstructions(String instruction) {
        String result;

            String[] parts = instruction.split(" ");
            String command = parts[0];
            switch (command) {
                case "GET" -> result = get(parts[1], parts[2]);
                case "SET_OR_INC" -> result = setOrInc(parts[1], parts[2], parts[3]);
                case "DELETE" ->  result = String.valueOf(delete(parts[1], parts[2]));
                case "w" ->  result = w(Integer.parseInt(parts[1]));
                default -> result = String.format("This instruction:'%s' is not a valid instruction", instruction);
            }

        return result;
    }


}
