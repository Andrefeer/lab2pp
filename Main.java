package org.example;
import org.graalvm.polyglot.*;

class Main {
    private static String Pythonproblema() {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        String formula = """
                a = int(input("Nr de aruncari:"))
                b = int(input("Nr de pajuri:"))
                a,b""";
        Value result = polyglot.eval("python", formula);
        String resultString = result.toString();
        polyglot.close();
        return resultString;
    }

    private static void PythonBinomial(Integer n, Integer x) {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        polyglot.getBindings("python").putMember("n", n);
        polyglot.getBindings("python").putMember("x", x);
        String formula = """
                import math
                p = 0.5
                distributie = []
                
                for k in range(n + 1):
                    #probabilitate binomiala
                    prob = math.comb(n, k) * (p**k) * ((1-p)**(n-k))
                    distributie.append(prob)
                    print(f"P(X = {k}) = {prob}")
                
                # Suma probabilitatilor
                prob_cel_mult_x = sum(distributie[:x + 1])
                print(f"Probabilitatea de a obține cel mult {x} pajuri din {n} aruncări este: {prob_cel_mult_x}")
                """;
        Value result = polyglot.eval("python", formula);
        polyglot.close();
    }

    //functia MAIN
    public static void main(String[] args) {
        Context polyglot = Context.create();
        String nr = Pythonproblema();
        System.out.println(nr);
        nr = nr.replaceAll("[(,)]", "");
        String[] a = nr.split(" ");
        PythonBinomial(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        polyglot.close();
    }
}
