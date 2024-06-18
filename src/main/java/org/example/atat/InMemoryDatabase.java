package org.example.atat;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDatabase implements InMemoryDatabaseInterface {
    private final Map<String, Map<String, String>> db;
    private final Map<String, Integer> operationCount;

    public InMemoryDatabase(){
        this.db = new HashMap<>();
        this.operationCount = new HashMap<>();
    }
    @Override
    public String get(String key, String column) {
        return db.getOrDefault(key, Collections.emptyMap()).getOrDefault(column, "");
    }
    @Override
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
    @Override
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
    @Override
    public String w(int count) {
        return db.keySet().stream()
                .sorted(Comparator.comparingInt(operationCount::get).reversed())
                .limit(count)
                .map(key -> key + "(" + operationCount.get(key) + ")")
                .collect(Collectors.joining(", "));
    }
    @Override
    public String executeInstructions(String instruction) {
        String result;

        String[] parts = instruction.split(" ");
        String command = parts[0];
        switch (command) {
            case "GET" -> result = get(parts[1], parts[2]);
            case "SET_OR_INC" -> result = setOrInc(parts[1], parts[2], parts[3]);
            case "DELETE" -> result = String.valueOf(delete(parts[1], parts[2]));
            case "w", "W" -> result = w(Integer.parseInt(parts[1]));
            case "SHOW_DB" -> {
                showDb();
                result = "Database displayed";
            }
            default -> result = String.format("This instruction:'%s' is not a valid instruction", instruction);
        }

        return result;
    }

    @Override
    public void showDb() {
        final int size = 4;
        System.out.println();
        if (db.isEmpty()) {
            System.out.println("Database is empty");
        } else {
            int maxKeyLength = db.keySet().stream()
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);

            List<String> headers = new ArrayList<>();
            headers.add("Key");
            db.values().forEach(columns -> columns.keySet().forEach(column -> {
                if (!headers.contains(column)) {
                    headers.add(column);
                }
            }));

            System.out.printf("%-" + (maxKeyLength + size) + "s", headers.getFirst());
            for (int i = 1; i < headers.size(); i++) {
                System.out.printf("| %-10s", headers.get(i));
            }
            System.out.println();

            System.out.print("-".repeat(maxKeyLength + size));
            for (int i = 1; i < headers.size(); i++) {
                System.out.print("+".repeat(maxKeyLength * size));
            }
            System.out.println();

            db.forEach((key, columns) -> {
                System.out.printf("%-" + (maxKeyLength + size) + "s", key);
                headers.stream().skip(1).forEach(column -> System.out.printf("| %-10s", columns.getOrDefault(column, "")));
                System.out.println();
            });
        }
        System.out.println();
    }


}
