package com.ardinata.shared_core

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

class AppContext(val context: Context)

actual class KtorClientEngine constructor(private val appContext: AppContext) {
    actual fun getClientEngine(config: HttpClientConfig<*>.() -> Unit) : HttpClient = HttpClient(OkHttp) {
        config.invoke(this)
        engine {
//            requestTimeout = 0
            addInterceptor(ChuckerInterceptor.Builder(appContext.context).build())
        }
    }
    actual companion object Factory {
        lateinit var context: Context
        actual fun build(): KtorClientEngine = KtorClientEngine(AppContext(context))
    }
}

