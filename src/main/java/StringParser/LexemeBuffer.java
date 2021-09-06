package StringParser;

import java.util.List;

public class LexemeBuffer {
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
