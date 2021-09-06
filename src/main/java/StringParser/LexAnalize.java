package StringParser;

import java.util.ArrayList;
import java.util.List;

public class LexAnalize {

    public static List<Lexeme> lexAnalize (String expText){
        List <Lexeme> lexemes = new ArrayList<>();
        int pos = 0;

        while (pos < expText.length()){
            char c = expText.charAt(pos);

            switch (c){
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.POV, c));
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
                        lexemes.add(new Lexeme(LexemeType.NUMBER, stringBuilder.toString()));

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

                            if (GetFunction.getFunction().containsKey(str.toString())){
                                lexemes.add(new Lexeme(LexemeType.NAME, str.toString()));
                            } else throw new RuntimeException("Неизвестный символ функций!");

                        } else throw new RuntimeException("Неизвестный символ!");
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }
}
