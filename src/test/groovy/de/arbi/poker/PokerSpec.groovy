package de.arbi.poker

import de.arbi.poker.com.PokerCom
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Unroll

class PokerSpec extends Specification {

    @AutoCleanup
    MainClassApplicationUnderTest aut = new MainClassApplicationUnderTest(PokerCom.class);

    @Delegate
    TestHttpClient client = TestHttpClient.testHttpClient(aut)

    @Unroll
    def "Test that '#url' returns '#result'"() {
        expect:
        get(url).body.text == result

        where:
        url                  | result
        '/foo'               | 'from the foo handler'
        '/bar'               | 'from the bar handler'
        '/nested'            | 'root handler!'
        '/nested/one'        | 'from the nested handler, var1: one, var2: null'
        '/nested/one/two'    | 'from the nested handler, var1: one, var2: two'
        '/nested/one:two'    | 'from the nested handler, var1: one:two, var2: null'
        '/injected?text=one' | 'service value: one'
        '/injected/hallo'    | 'root handler!'
        '/injected'          | 'service value: none'
    }
}
