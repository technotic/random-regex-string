package technotic.generex;

//import com.mifmif.common.regex.Generex;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
//import io.vavr.control.Option;
//import org.junit.jupiter.api.Test;

//import static io.vavr.collection.Stream.continually;

public class GenerexTest {

//    @Test
//    void generex() {
//
//        Generex generex = new Generex("[A-Z]+");
//        generex.setSeed(System.currentTimeMillis());
//
////        // Generate random String
////        String randomStr = generex.random();
////        System.out.println(randomStr);// a random value from the previous String list
////
////        // generate the second String in lexicographical order that match the given Regex.
////        String secondString = generex.getMatchedString(2);
////        System.out.println(secondString);// it print '0b'
////
////        // Generate all String that matches the given Regex.
////        generex.getAllMatchedStrings();
//
//        Option<Double> average = continually(() -> generex.random(1, 50))
//                .take(1000)
//                .map(String::length)
//                .average();
//        System.out.println("average.get() = " + average.get());
//    }
//
//    @Test
//    void automaton() {
//
////        Automaton automaton = new RegExp("[A-Za-z]{6}[A-Za-z0-9]{2}|[A-Za-z]{6}[A-Za-z0-9]{2}[A-Za-z0-9]{3}").toAutomaton();
//        Automaton automaton = new RegExp("A").toAutomaton();
//        System.out.println("automaton.getStates() = " + automaton.getStates());
//
//        System.out.println("digraph Regex {");
//
//        State initialState = automaton.getInitialState();
//        automaton.getStates().forEach(state -> {
//            System.out.print(WpState.id(state));
//
//            String shape = isTerminal(state) ? "doublecircle" : "circle";
//            System.out.print(" [");
//            System.out.print("label=\"\",shape=" + shape + ",width=1,height=1");
//            if (state.equals(initialState)) {
//                System.out.print(" style=bold");
//            }
//            System.out.print("]");
//
//            System.out.println();
//        });
//
//        System.out.println();
//
//        automaton.getStates().forEach(state -> {
//            state.getTransitions().forEach(transition -> {
//                System.out.println(WpState.id(state) + " -> " + WpState.id(transition.getDest()) + " [label=" + minMax(transition) + "]");
//            });
//        });
//
//        System.out.println("}");
//    }
//
//    private boolean isTerminal(State state) {
//        return state.getTransitions().stream().allMatch(transition -> transition.getDest().equals(state));
//    }
//
//    private String minMax(Transition transition) {
//        return "\"" + (transition.getMax() == transition.getMin() ? transition.getMin() : ("" + transition.getMin() + "-" + transition.getMax())) + "\"";
//    }
}
