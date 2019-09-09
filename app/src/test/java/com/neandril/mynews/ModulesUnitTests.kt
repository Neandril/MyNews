package com.neandril.mynews

import com.neandril.mynews.utils.applicationModule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class ModulesUnitTests : KoinTest {

    @Test
    fun checkAppModule() {
        startKoin {
            modules(applicationModule)
        }.checkModules()

        stopKoin()
    }
}