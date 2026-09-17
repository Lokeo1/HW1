package simplelexersubset;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class SimpleLexer {
  private static final Dfa dfa = Dfa.convertNfaToDfa(Nfa.construct());

  private final String source;
  private int position;

  public SimpleLexer(final String source) {
    if (source == null) {
      throw new IllegalArgumentException("source must not be null");
    }
    this.source = source;
  }

  /** Returns the next token, or {@link Token.Type#EOF} after the input ends. */
  public Token nextToken() {
    skipWhitespace();
    if (position == source.length()) {
      return new Token(Token.Type.EOF, "", position);
    }

    final int start = position;
    Dfa.State state = dfa.start;
    Dfa.State lastAccepting = null;
    int end = start;
    while (this.position < source.length()) {
      final char c = this.source.charAt(this.position);
      state = transition(state, c);      
      if (state == null) {
        break;
      }
      this.position++;
      if (state.type != null) {
        lastAccepting = state;
        end = this.position;
      }
    }

    if (lastAccepting == null) {
      throw new LexicalException(source.charAt(start), start);
    }
    position = end;
    return new Token(lastAccepting.type, source.substring(start, end), start);
  }

  /** DFA transition function. A null result means that no transition exists. */
  private Dfa.State transition(final Dfa.State state, final char c) {
    return state.nextState(c);
  }

  private void skipWhitespace() {
    while (position < source.length() && Character.isWhitespace(source.charAt(position))) {
      position++;
    }
  }

  /** Thompson NFA, including token labels on accepting states. */
  private static final class Nfa {
    private final State start;
    private final Set<Character> alphabet = new LinkedHashSet<Character>();

    private Nfa() {
      start = state();
    }

    /* Constructs an NFA for the given set of tokens. */
    static Nfa construct() {
      final Nfa nfa = new Nfa();
      // IF | [A-Za-z_][A-Za-z0-9_]* | 0 | [1-9][0-9]*
      nfa.add(nfa.word("if"), Token.Type.IF);
      nfa.add(nfa.concat(nfa.chars(identifierStart()), nfa.star(nfa.chars(identifierPart()))),
          Token.Type.ID);
      nfa.add(nfa.chars("0"), Token.Type.INT);
      nfa.add(nfa.concat(nfa.chars("123456789"), nfa.star(nfa.chars("0123456789"))),
          Token.Type.INT);
      return nfa;
    }

    private static String identifierStart() {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    }

    private static String identifierPart() {
      return identifierStart() + "0123456789";
    }

    private void add(final Fragment fragment, final Token.Type type) {
      start.epsilon.add(fragment.start);
      fragment.end.type = type;
    }

    private Fragment word(final String text) {
      Fragment result = null;
      for (int i = 0; i < text.length(); i++) {
        final Fragment character = chars(String.valueOf(text.charAt(i)));
        result = result == null ? character : concat(result, character);
      }
      return result;
    }

    private Fragment chars(final String characters) {
      final State from = state();
      final State to = state();
      for (int i = 0; i < characters.length(); i++) {
        final Character character = Character.valueOf(characters.charAt(i));
        from.nextStateSet(character).add(to);
        alphabet.add(character);
      }
      return new Fragment(from, to);
    }

    private Fragment concat(final Fragment left, final Fragment right) {
      left.end.epsilon.add(right.start);
      return new Fragment(left.start, right.end);
    }

    private Fragment star(final Fragment expression) {
      final State from = state();
      final State to = state();
      from.epsilon.add(expression.start);
      from.epsilon.add(to);
      expression.end.epsilon.add(expression.start);
      expression.end.epsilon.add(to);
      return new Fragment(from, to);
    }

    private State state() {
      return new State();
    }
    
    private static final class Fragment {
      private final State start;
      private final State end;

      Fragment(final State start, final State end) {
        this.start = start;
        this.end = end;
      }
    }

    /* Represents a state in the NFA. */
    private static final class State {
      private final Map<Character, Set<State>> transitions = new HashMap<Character, Set<State>>();
      private final Set<State> epsilon = new HashSet<State>();
      private Token.Type type;

      State() { }

      Set<State> nextStateSet(final Character character) {
        Set<State> targets = transitions.get(character);
        if (targets == null) {
          targets = new HashSet<State>();
          transitions.put(character, targets);
        }
        return targets;
      }
    }
  }

  /** DFA produced by epsilon closure and subset construction. */
  private static final class Dfa {
    private final State start;

    private Dfa(final State start) {
      this.start = start;
    }

    /* Converts an NFA to a DFA using the subset construction algorithm. */
    static Dfa convertNfaToDfa(final Nfa nfa) {
      // TODO: Implement the subset construction algorithm to convert the NFA to a DFA.
      return null; // TODO: Replace it with the actual DFA instance.
    }

    private static Set<Nfa.State> move(final Set<Nfa.State> states, final Character character) {
      final Set<Nfa.State> result = new HashSet<Nfa.State>();
      for (final Nfa.State state : states) {
        final Set<Nfa.State> targets = state.transitions.get(character);
        if (targets != null) {
          result.addAll(targets);
        }
      }
      return result;
    }

    /* Computes the epsilon closure of the given set of NFA states. */
    private static Set<Nfa.State> closure(final Set<Nfa.State> seeds) {
      final Set<Nfa.State> result = new HashSet<Nfa.State>(seeds);
      final Queue<Nfa.State> pending = new ArrayDeque<Nfa.State>(seeds);
      while (!pending.isEmpty()) {
        for (final Nfa.State target : pending.remove().epsilon) {
          if (result.add(target)) {
            pending.add(target);
          }
        }
      }
      return result;
    }

    /* Determine whether the given set of NFA states contains an accepting state and return its token type, if any. */
    private static Token.Type acceptingType(final Set<Nfa.State> states) {
      Token.Type result = null;
      for (final Nfa.State state : states) {
        if (state.type != null && (result == null || priority(state.type) < priority(result))) {
          result = state.type;
        }
      }
      return result;
    }

    // When two token regexes accept the same longest text, earlier rules win.
    private static int priority(final Token.Type type) {
      if (type == Token.Type.IF) {
        return 0;
      }
      if (type == Token.Type.ID) {
        return 1;
      }
      return 2;
    }

    private static final class State {
      private final Map<Character, State> transitions = new HashMap<Character, State>();
      private final Token.Type type; // The token type if this is an accepting state, null otherwise.

      State(final Token.Type type) {
        this.type = type;
      }

      /* Returns the next state for the given character, or null if there is no transition. */
      State nextState(final char c) {
        return this.transitions.get(c);
      }
    }    
  }

  public static final class LexicalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LexicalException(final char character, final int position) {
      super("Unexpected character '" + character + "' at offset " + position);
    }
  }
}
