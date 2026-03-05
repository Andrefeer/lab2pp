package org.example;
import org.graalvm.polyglot.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Main {
    private static String PythonToUpper(String token){/// in litere mari
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        Value result = polyglot.eval("python", "'"+token+"'.upper()");///apel func python
        String resultString = result.asString();
        polyglot.close();

        return resultString;
    }

    private static String SumCRC(String token){

        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        String temp = token.substring(1, token.length()-1);
        String formula = "sum(1*ord(ch)**4 + 2*ord(ch)**3 + 3*ord(ch)**2 + 4*ord(ch) + 5 for ch in '" + temp + "')";
        Value result = polyglot.eval("python", formula);
        String resultString = result.toString();
        polyglot.close();

        return resultString;
    }

    public static void main(String[] args) {
        Context polyglot = Context.create();
        HashMap<String, List<String>> valori = new HashMap<String, List<String>>();///lista de stringuri
        Value array = polyglot.eval("js", "[\"If\",\"we\",\"run\",\"the\",\"java\",\"command\"];");
        for (int i = 0; i < array.getArraySize();i++){
            String element = array.getArrayElement(i).asString();
            String upper = PythonToUpper(element);
            String crc = SumCRC(upper);
            valori.computeIfAbsent(crc, k -> new ArrayList<>()).add(upper);
            System.out.println(upper + " -> " + crc);
        }
        System.out.println("Rezultate grupate după CRC:");
        for( String key : valori.keySet() ){
            System.out.println( key + " -> " + valori.get(key));
        }
        polyglot.close();
    }
}
