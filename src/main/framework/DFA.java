package framework;

import com.sun.org.apache.bcel.internal.generic.FALOAD;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static framework.DFAStates.*;

/**
 * Deterministic state automate.
 */
public class DFA implements IDFA {
    private static final Set<Character> pm = new HashSet<Character>(Arrays.asList('.', ',', ':', ';', '!', '?'));

    @Override
    public DFAStates initial() {
        return DFAStates.START;
    }

    @Override
    public DFAStates trans(DFAStates state, int nextChar) {
        switch (state) {
            case EOF:
                break;
            case START:
                if (Character.isWhitespace(nextChar)) {
                    return WS;
                }
                else if (pm.contains((char) nextChar)) {
                    return PM;
                }
                else if (Character.isDigit(nextChar)) {
                    return FIRST_OF_DAY;
                }
                else if (Character.isAlphabetic(nextChar)) {
                    return IDENTIFIER;
                }
                else if (nextChar == -1) {
                    return EOF;
                }
                else {
                    return FAILURE;
                }
            case FAILURE:
                break;
            case WS:
                if (Character.isWhitespace(nextChar)) {
                    return WS;
                }
                else {
                    return FAILURE;
                }
            case PM:
                return FAILURE;
            case IDENTIFIER:
                if (Character.isAlphabetic(nextChar)) {
                    return IDENTIFIER;
                }
                else {
                    return FAILURE;
                }
            case INTCONS:
                if (Character.isDigit(nextChar)) {
                    return INTCONS;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_DAY:
                if (Character.isDigit(nextChar)) {
                    return SECOND_OF_DAY;
                }
                else {
                    return FAILURE;
                }
            case SECOND_OF_DAY:
                if (nextChar == '.') {
                    return DAY_STATE;
                }
                else if (Character.isDigit(nextChar)) {
                    return INTCONS;
                }
                else {
                    return FAILURE;
                }
            case DAY_STATE:
                if (Character.isDigit(nextChar)) {
                    return FIRST_OF_MONTH;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_MONTH:
                if (Character.isDigit(nextChar)) {
                    return SECOND_OF_MONTH;
                }
                else {
                    return FAILURE;
                }
            case SECOND_OF_MONTH:
                if (nextChar == '.') {
                    return MONTH_STATE;
                }
                else {
                    return FAILURE;
                }
            case MONTH_STATE:
                if (Character.isDigit(nextChar)) {
                    return FIRST_OF_YEAR;
                }
                else {
                    return FAILURE;
                }
            case FIRST_OF_YEAR:
                if (Character.isDigit(nextChar)) {
                    return SECOND_OF_YEAR;
                }
                else {
                    return FAILURE;
                }
            case SECOND_OF_YEAR:
                if (Character.isDigit(nextChar)) {
                    return DATE_STATE;
                }
                else {
                    return FAILURE;
                }
            case DATE_STATE:
                return FAILURE;
        }
        return null;
    }

    @Override
    public boolean isFinal(DFAStates state) {
        if (state == WS
                || state == PM
                || state == DFAStates.IDENTIFIER
                || state == DFAStates.INTCONS
                || state == DFAStates.DATE_STATE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isStop(DFAStates state) {
        if (state == DFAStates.EOF || state == DFAStates.FAILURE) {
            return true;
        }
        return false;
    }

    @Override
    public Set getTokenClasses() {
        return null;
    }
}
