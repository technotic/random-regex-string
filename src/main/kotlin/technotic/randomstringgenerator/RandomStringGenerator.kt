package technotic.randomstringgenerator

import com.mifmif.common.regex.Generex
import dk.brics.automaton.RegExp
import dk.brics.automaton.State
import dk.brics.automaton.Transition
import java.util.*
import kotlin.random.Random.Default.nextInt

class RandomStringGenerator {

    // TODO how to find all routes through a graph?
    // TODO for each route find the terminal state and unwind to add all values
    // Algo:
    // For a state:
    // if accept state then {min: 0, max: 0} (and visited all transitions?)
    // else for each { minOf(all transitions) + 1, maxOf(all transitions) + 1}
    // Simplify to just minOf
    // if accept state then 0 else 1+min(

    fun generateString(regex: String, max: Int = 10, min: Int = 1) =
        stringFromStateMachine(RegExp(regex).toAutomaton().initialState, max, min)

    fun generateStringGenerex(regex: String, max: Int = 10, min: Int = 1): String {

        val automaton = RegExp("B[C]+A").toAutomaton()
        println(automaton.states)

        return Generex(regex, Random()).random(min, max)
    }

    fun stringFromStateMachine(state: State, min: Int, max: Int) = stringFromStateMachine(state, min, max, "")

    fun stringFromStateMachine(state: State, min: Int, max: Int, generated: String): String =
        if (state.isAccept && generated.length >= min && generated.length <= max) {
            generated
        } else {
            val possibleTransitions =
                transitionsForState(state).filter { transition -> transition.first <= min || transition.second >= max }
            if (possibleTransitions.isEmpty()) throw java.lang.IllegalStateException("No possible transition from: $state can satisfy $min $max")
            val transition = randomChoice(possibleTransitions)
            stringFromStateMachine(
                transition.third.dest,
                min - 1,
                max - 1,
                generated + randomCharBetween(transition.third.min, transition.third.max)
            )
        }

    private fun randomIntBetweenInclusive(min: Int, max: Int): Int = nextInt(min, max + 1)
    fun randomCharBetween(min: Char, max: Char): Char = randomIntBetweenInclusive(min.toInt(), max.toInt()).toChar()
    fun <T> randomChoice(items: List<T>) = items[randomIntBetweenInclusive(0, items.size - 1)]

    // TODO return type should be Map<Transition, Pair<Int, Int>>
    fun transitionsForState(state: State): List<Triple<Int, Int, Transition>> =
        state.transitions.map { transition ->
            Triple(
                minLength(transition),
                maxLength(transition),
                transition
            )
        }

    fun minLength(transition: Transition): Int = inc(minLength(transition.dest))
    fun maxLength(transition: Transition): Int = inc(maxLength(transition.dest))

    fun inc(a: Int) = if (a == Int.MAX_VALUE) a else a + 1

    fun minLength(state: State) = minLength(state, emptySet())

    fun minLength(state: State, visited: Set<State>): Int {

        return if (state.isAccept) {
            0
        } else {
            val possibleTransitions = state.transitions.filter { t -> t.dest != state && !visited.contains(t.dest) }
            possibleTransitions.map { t -> inc(minLength(t.dest, visited.plus(state))) }.min()
                ?: throw IllegalStateException("No transition found from state: $state")
        }
    }

    fun maxLength(state: State) = maxLength(state, emptySet())

    fun maxLength(state: State, visited: Set<State>): Int {

        val containsCycle = state.transitions.any() { t -> t.dest == state || visited.contains(t.dest) }
        return when {
            containsCycle -> Int.MAX_VALUE
            state.isAccept && state.transitions.isEmpty() -> 0
            else -> state.transitions.map { t ->
                inc(maxLength(t.dest, visited.plus(state)))
            }.max()
                ?: throw IllegalStateException("No transition found from state: $state")
        }
    }
}