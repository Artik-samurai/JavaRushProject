package StringParser;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    /*------------------------------------------------------------------
     * PARSER RULES
     *------------------------------------------------------------------*/

//    expr : plusminus* EOF ;
//
//    plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
//
//    multdiv : pov ( ( '*' | '/' ) pov )* ;
//
//    pov : factor ( ( '^' ) factor )* ;
//
//    factor : func | unary | NUMBER | '(' expr ')' ;
//
//    unary : '-' multdiv
//
//    func : NAME '(' (expr (',' expr)+)? ')'
//

    public static void main(String [] args){
        String str =  "-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)";
        LexemeBuffer lexemeBuffer = new LexemeBuffer(LexAnalize.lexAnalize(str));
        double result = (double) Math.round(expr(lexemeBuffer, 0)*100)/100;
        if (100*Math.ceil(result) - result*100 <= 0.0000000000000000001){
            System.out.println(Math.round(result) + " Count " + Count.getCount());
        } else System.out.println(result + " Count " + Count.getCount());
    }


    public static double expr (LexemeBuffer lexemeBuffer, int count){
        Lexeme lexeme = lexemeBuffer.next();
        if (lexeme.type == LexemeType.EOF){
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
                case OP_PLUS:
                    Count.increment();
                    value += MultDiv(lexemeBuffer, Count.getCount());
                    break;
                case OP_MINUS:
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
                case OP_MUL:
                    Count.increment();
                    value *= Pov(lexemeBuffer, Count.getCount());
                    break;
                case OP_DIV:
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
                case POV:
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
            case OP_MINUS:
                Count.increment();
                double value = MultDiv(lexemeBuffer, Count.getCount());
                return - value;
            case NUMBER:
                return Double.parseDouble(lexeme.value);
            case LEFT_BRACKET:
                value = expr(lexemeBuffer, Count.getCount());
                lexeme = lexemeBuffer.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET){
                    throw new RuntimeException("Ошибка, нет правой скобки!");
                }
                return value;
            case NAME:
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

        if (lexeme.type != LexemeType.LEFT_BRACKET) throw new RuntimeException("Ошибка в функции!");

        List <Double> list = new ArrayList<>();

        do {
            list.add(expr(lexemeBuffer, Count.getCount()));
            lexeme = lexemeBuffer.next();
        } while (lexeme.type != LexemeType.RIGHT_BRACKET);

        return GetFunction.getFunction().get(name).calculate(list);

    }
}
