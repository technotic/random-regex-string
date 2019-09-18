package technotic.randomstringgenerator

import dk.brics.automaton.RegExp
import io.kotlintest.data.forall
import io.kotlintest.matchers.collections.shouldBeOneOf
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class RandomStringGeneratorSpec : StringSpec({

    val randomStringGenerator = RandomStringGenerator()

    //    "should generate a random string from a regex" {
//
//        // Given
//        val randomStringGenerator = RandomStringGenerator()
//        val regex = "[a-z]*"
//
//        // When
//        val generatedString = randomStringGenerator.generateString(regex, min = 1, max = 10)
//
//        // Then
//        generatedString shouldMatch regex
//        generatedString shouldHaveMinLength 1
//        generatedString shouldHaveMaxLength 10
//    }

    "should generate random characters from a range" {
        val charRange = 'A'..'E'
        for (i in 1..10) {
            val ch: Char = randomStringGenerator.randomCharBetween(charRange.first, charRange.last)
            charRange.toList().contains(ch) shouldBe true
        }
    }

    "should choose random item from a list" {
        val items = listOf(1, 2, 3, 4, 5)
        for (i in 1..10) {
            val value = randomStringGenerator.randomChoice(items)
            items.contains(value) shouldBe true
        }
    }

    "should find min and max length of string for transitions of state" {
        val state = RegExp("A|BB|C*|D+").toAutomaton().initialState

        val transitions = randomStringGenerator.transitionsForState(state)

        transitions shouldHaveSize 4
    }

    "should calculate minimum length of string that can be generated from a regex" {

        forall(
            row("A", 1),
            row("AA", 2),
            row("AAA", 3),
            row("A*", 0),
            row("A+", 1),
            row("A+B", 2),
            row("[A]*|B", 0),
            row("A+B+", 2),
            row("[ABC]+", 1),
            row("[ABC]{5,10}", 5),
            row("[A]{5,10}|[B]{2,10}", 2)
        ) { regex, expectedMinimum ->

            val automaton = RegExp(regex).toAutomaton()
            println("$regex : $automaton")
            val minLength = randomStringGenerator.minLength(automaton.initialState)

            minLength shouldBe expectedMinimum
        }
    }

    "should calculate maximum length of string that can be generated from a regex" {

        forall(
            row("A", 1),
            row("AA", 2),
            row("AAA", 3),
            row("A*", Int.MAX_VALUE),
            row("A+", Int.MAX_VALUE),
            row("A+B", Int.MAX_VALUE),
            row("[A]*|B", Int.MAX_VALUE),
            row("A+B+", Int.MAX_VALUE),
            row("[ABC]+", Int.MAX_VALUE),
            row("[ABC]{5,10}", 10),
            row("[A]{5,10}|[B]{2,15}", 15)
        ) { regex, expectedMaximum ->

            val automaton = RegExp(regex).toAutomaton()
            println("$regex : $automaton")
            val maxLength = randomStringGenerator.maxLength(automaton.initialState)

            maxLength shouldBe expectedMaximum
        }
    }
})