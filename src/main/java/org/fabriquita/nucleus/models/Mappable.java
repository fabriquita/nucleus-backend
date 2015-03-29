package org.fabriquita.nucleus.models;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Mappable {

    public abstract Object mappableId();

    public String[] mappableFields() {
        List<String> list = new LinkedList<>();
        Class<?> c = this.getClass();
        for (Field field : c.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                list.add(field.getName());
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    public Map<String, Object> toMap() {
        return toMap(this.mappableFields());
    }

    public Map<String, Object> toMap(String[] fieldsForMap) {
        Map<String, Object> map = new HashMap<>();
        Class<?> c = this.getClass();
        for (String fieldName : fieldsForMap) {
            String getterName = generateGetterForField(fieldName);
            Method getter = null;
            try {
                getter = c.getMethod(getterName);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            String result = null;
            try {
                Object getterResult = getter.invoke(this);
                if (getterResult != null) {
                    if (getterResult instanceof String) {
                        result = (String)getterResult;
                    } else if (getterResult instanceof Number) {
                        result = getterResult.toString();
                    } else {
                        result = getterResult.toString();
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
            map.put(fieldName, result);
        }
        return map;
    }

    public String generateGetterForField(String fieldName) {
        char firstChar = (char)(fieldName.charAt(0) - 'a' + 'A');
        String getter = "get" + firstChar + fieldName.substring(1);
        return getter;
    }

    public void print() {
        this.print(System.out);
    }

    public void print(PrintStream out) {
        Map<String, Object> map = this.toMap();
        for (String key : map.keySet()) {
            out.println(key + ": " + map.get(key));
        }
    }

}
