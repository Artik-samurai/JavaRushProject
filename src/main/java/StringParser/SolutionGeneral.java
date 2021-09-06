package StringParser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SolutionGeneral {
    public static void main(String[] args) {
        SolutionGeneral solution = new SolutionGeneral();
        solution.recurse("2+8*(9/4-1.5)^(1+1)", 0); // Expected output: 0.5 6
    }

    public void recurse(final String expression, int countOperation) {
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexAnalize(expression));
        double result = (double) Math.round(expr(lexemeBuffer, countOperation)*100)/100;
        if (100*Math.ceil(result) - result*100 <= 0.0000000000000000001){
            System.out.println(Math.round(result) + " " + Count.getCount());
        } else System.out.println(result + " " + Count.getCount());
    }

//    public static void main(String [] args){
//        String str =  "cos(3 + 19*3)";
//        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexAnalize(str));
//        double result = (double) Math.round(expr(lexemeBuffer, 0)*100)/100;
//        if (100*Math.ceil(result) - result*100 <= 0.0000000000000000001){
//            System.out.println(Math.round(result) + " Count " + Count.getCount());
//        } else System.out.println(result + " Count " + Count.getCount());
//    }


    public static double expr (LexemeBuffer lexemeBuffer, int count){
        Lexeme lexeme = lexemeBuffer.next();
        if (lexeme.type.equals("EOF")){
            return 0;
        } else {
            lexemeBuffer.back();
            return PlusMinus(lexemeBuffer, Count.getCount());
        }

    }

    public static double PlusMinus (LexemeBuffer lexemeBuffer, int count){
        double value = MultDiv(lexemeBuffer, Count.getCount());
        while (true){
            Lexeme lexeme = lexemeBuffer.next();
            switch (lexeme.type){
                case "OP_PLUS":
                    Count.increment();
                    value += MultDiv(lexemeBuffer, Count.getCount());
                    break;
                case "OP_MINUS":
                    Count.increment();
                    value -= MultDiv(lexemeBuffer, Count.getCount());
                    break;
                default:
                    lexemeBuffer.back();
                    return value;
            }
        }
    }

    public static double MultDiv (LexemeBuffer lexemeBuffer, int count){
        double value = Pov(lexemeBuffer, Count.getCount());
        while (true){
            Lexeme lexeme = lexemeBuffer.next();
            switch (lexeme.type){
                case "OP_MUL":
                    Count.increment();
                    value *= Pov(lexemeBuffer, Count.getCount());
                    break;
                case "OP_DIV":
                    Count.increment();
                    value /= Pov(lexemeBuffer, Count.getCount());
                    break;
                default:
                    lexemeBuffer.back();
                    return value;
            }
        }
    }

    public static double Pov (LexemeBuffer lexemeBuffer, int count){
        double value = factor(lexemeBuffer, Count.getCount());

        while (true){
            Lexeme lexeme = lexemeBuffer.next();

            switch (lexeme.type){
                case "POV":
                    Count.increment();
                    value = Math.pow(value, factor(lexemeBuffer, Count.getCount()));
                    break;
                default:
                    lexemeBuffer.back();
                    return value;
            }
        }
    }

    public static double factor (LexemeBuffer lexemeBuffer, int count){
        Lexeme lexeme = lexemeBuffer.next();

        switch (lexeme.type){
            case "OP_MINUS":
                Count.increment();
                double value = MultDiv(lexemeBuffer, Count.getCount());
                return - value;
            case "NUMBER":
                return Double.parseDouble(lexeme.value);
            case "LEFT_BRACKET":
                value = expr(lexemeBuffer, Count.getCount());
                lexeme = lexemeBuffer.next();
                if (!lexeme.type.equals("RIGHT_BRACKET")){
                    throw new RuntimeException("Ошибка, нет правой скобки!");
                }
                return value;
            case "NAME":
                Count.increment();
                lexemeBuffer.back();
                return func(lexemeBuffer, Count.getCount());
            default:
                throw new RuntimeException("Ошибка!");
        }
    }

    public static double func (LexemeBuffer lexemeBuffer, int count){
        String name = lexemeBuffer.next().value;
        Lexeme lexeme = lexemeBuffer.next();

        if (!lexeme.type.equals("LEFT_BRACKET")) throw new RuntimeException("Ошибка в функции!");

        List <Double> list = new ArrayList<>();

        do {
            list.add(expr(lexemeBuffer, Count.getCount()));
            lexeme = lexemeBuffer.next();
        } while (!lexeme.type.equals("RIGHT_BRACKET"));

        return getFunction().get(name).calculate(list);

    }

    public SolutionGeneral() {
        //don't delete
    }

    public static List<Lexeme> lexAnalize (String expText){
        List <Lexeme> lexemes = new ArrayList<>();
        int pos = 0;

        while (pos < expText.length()){
            char c = expText.charAt(pos);

            switch (c){
                case '(':
                    lexemes.add(new Lexeme("LEFT_BRACKET", c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme("RIGHT_BRACKET", c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme("OP_PLUS", c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme("OP_MINUS", c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme("OP_MUL", c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme("OP_DIV", c));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme("POV", c));
                    pos++;
                    continue;
                case ' ':
                    pos++;
                    continue;
                default:
                    if ( c <= '9' && c >= '0'){
                        StringBuilder stringBuilder = new StringBuilder();
                        do{
                            stringBuilder.append(c);
                            pos++;
                            if (pos >= expText.length()){
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0' || c == '.');
                        lexemes.add(new Lexeme("NUMBER", stringBuilder.toString()));

                    } else if (c != ' '){
                        if ( c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' ){
                            StringBuilder str = new StringBuilder();

                            do {
                                str.append(c);
                                pos++;
                                if (pos >= expText.length()){
                                    break;
                                }
                                c = expText.charAt(pos);
                            } while ( c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' );

                            if (getFunction().containsKey(str.toString())){
                                lexemes.add(new Lexeme("NAME", str.toString()));
                            } else throw new RuntimeException("Неизвестный символ функций!");

                        } else throw new RuntimeException("Неизвестный символ!");
                    }
            }
        }
        lexemes.add(new Lexeme("EOF", ""));
        return lexemes;
    }

    public static class LexemeBuffer {
        private int pos;
        public List <Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next(){
            Lexeme lexeme = lexemes.get(pos);
            pos++;
            return lexeme;
        }

        public void back(){
            pos--;
        }

        public int getPos(){
            return pos;
        }
    }

    public static class Lexeme {
        String type;
        String value;

        public Lexeme(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(String type, Character value) {
            this.type = type;
            this.value = value.toString();
        }


        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class Count {
        public static int count;

        public Count(int count) {
            this.count = count;
        }

        public static int getCount() {
            return count;
        }

        public static void increment(){
            count++;
        }
    }

    public static HashMap<String, Function> getFunction (){
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

    public interface Function {
        double calculate(List<Double> args);
    }
}