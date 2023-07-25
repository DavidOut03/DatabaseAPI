package com.davidout.api.minecraft.database;

import java.util.*;
import java.util.stream.Collectors;

public class Table {

    private HashMap<String, List<Object>> values;

    public Table() {
        this.values = new HashMap<>();
    }

    public Table(HashMap<String, List<Object>> newValues) {
        this.values = (newValues == null) ? new HashMap<>() : newValues;
    }

    public void setValues(HashMap<String, List<Object>> values) {
        this.values = values;
    }


    public int getColumnNumber(String columnName) {
        for (int i = 0; i < getTotalColumns(); i++) {
            String currentColumn = getColumn(i);

            if(!columnName.equalsIgnoreCase(currentColumn)) continue;
            return i;
        }

        return -1;
    }

    public String getColumn(int i) {
        String columnName = (getColumns().size() <= i) ? null : getColumns().get(i);
        if(columnName == null) values.put("Column " + i, new ArrayList<>());
        return columnName;
    }

    public String getColumn(String name) {
        for (int i = 0; i < getColumns().size(); i++) {
            String cName = getColumn(i);
            if(!cName.equalsIgnoreCase(name)) continue;
            return cName;
        }

        values.put(name, new ArrayList<>());
        return name;
    }


    public void addRowValues(Object... newValues) {
        for (int i = 0; i < newValues.length; i++) {
            String columnName = getColumn(i);

            if(columnName == null) {
                values.put("Column " + getColumns().size(), Collections.singletonList(newValues[i]));
            } else {
                values.get(columnName).add(newValues[i]);
            }
        }
    }


    public void addColumnValues(String column, List<Object> newValues) {
        values.get(getColumn(column)).addAll(newValues);
    }

    public void addColumnValues(String column, Object... newValues) {
        values.get(getColumn(column)).addAll(Arrays.asList(newValues));
    }

    public List<Object> getRowValues(int row) {
        List<Object> returned = new ArrayList<>();

        for (Map.Entry<String, List<Object>> stringListEntry : values.entrySet()) {
            if(stringListEntry.getValue().size() <= row) continue;
            returned.add( stringListEntry.getValue().get(row) );
        }

        return returned;
    }


    public List<String> getColumns() {return new ArrayList<>(values.keySet());}
    public HashMap<String, List<Object>> getTableValues() {return this.values;}

    public int getTotalColumns() {return values.keySet().size();}
    public int getTotalRows() {
        return getTableValues().values().stream().findFirst().orElse(new ArrayList<>()).size();
    }

    public List<Object> getColumnValues(String column) {
        return values.get(column);
    }

    public List<Object> getColumnValues(int col) {
        return values.get(getColumns().get(col));
    }

    @Override
    public String toString() {
        return getTableValues().toString();
    }

}
