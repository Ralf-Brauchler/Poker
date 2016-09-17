package de.arbi.poker

import com.fasterxml.jackson.databind.JsonNode
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.embed.GroovyEmbeddedApp
import ratpack.guice.Guice
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.embed.EmbeddedApp
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Unroll

class PokerSpec extends Specification {

    @AutoCleanup
    MainClassApplicationUnderTest aut = new MainClassApplicationUnderTest(Poker.class);

    @Delegate
    TestHttpClient client = TestHttpClient.testHttpClient(aut)

    @Unroll
    def "Test that '#url' returns '#result'"() {
        expect:
        get(url).body.text == result

        where:
        url    | result
        '/foo' | 'from the foo handler'
        '/bar' | 'from the bar handler'
    }
}
