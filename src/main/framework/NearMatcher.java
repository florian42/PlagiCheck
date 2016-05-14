package framework;

/**
 * Created by Matthias on 07.05.2016.
 */
public class NearMatcher implements INearMatcher {
    //@Fixme: Implement NearMatcher to check objects (Compare tokens and collect near-match pairs)
    private final ILexer lexer;

    public NearMatcher(ILexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean isNearMatch(IToken tk1, IToken tk2) {
        String tok1 = lexer.decode(tk1);
        String tok2 = lexer.decode(tk2);
        if ((tok1.equals("B") && tok2.equals("P"))
                || (tok1.equals("P") && tok2.equals("B"))) {
            return true;
        }
        return false;
    }
}
