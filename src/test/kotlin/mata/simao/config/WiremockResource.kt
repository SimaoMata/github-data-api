package mata.simao.config

import com.github.tomakehurst.wiremock.WireMockServer
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager.TestInjector


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectWireMock

class WiremockResource : QuarkusTestResourceLifecycleManager {

    companion object {
        const val WIREMOCK_PORT = 7777
    }

    lateinit var wiremockServer: WireMockServer

    override fun start(): MutableMap<String, String> {
        wiremockServer = WireMockServer(WIREMOCK_PORT)
        return wiremockServer.start().run {
            mutableMapOf(
                "quarkus.rest-client.repository-api.url" to wiremockServer.baseUrl()
            )
        }
    }

    override fun stop() {
        if (this::wiremockServer.isInitialized) {
            wiremockServer.stop()
        }
    }

    override fun inject(testInjector: TestInjector) {
        testInjector.injectIntoFields(
            wiremockServer,
            TestInjector.AnnotatedAndMatchesType(InjectWireMock::class.java, WireMockServer::class.java)
        )
    }

}