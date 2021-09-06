package StringParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFunction {

    public static HashMap <String, Function > getFunction (){
        HashMap <String, Function> map = new HashMap<>();

        map.put("cos", args -> {
            if (args.isEmpty() ){
                throw new RuntimeException("Нет аргументов!");
            }
            return (double) Math.cos(args.get(0) * Math.PI/180);
        });

        map.put("sin", args -> {
            if (args.isEmpty() ){
                throw new RuntimeException("Нет аргументов!");
            }
            return (double) Math.sin(args.get(0) * Math.PI/180);
        });

        map.put("tan", args -> {
            if (args.isEmpty() ){
                throw new RuntimeException("Нет аргументов!");
            }
            return (double) Math.tan(args.get(0) * Math.PI/180);
        });


        return map;
    }
}
