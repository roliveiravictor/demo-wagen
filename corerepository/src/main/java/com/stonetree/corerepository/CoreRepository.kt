package stonetree.com.meals.core.provider

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.corerepository.CoreRepositoryConstant.TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoreRepository {

    lateinit var retrofit: Retrofit

    companion object {

        private var instance: CoreRepository? = null
        fun getInstance() =
            instance ?: CoreRepository().also {
                    instance = it
                    startRetrofit()
            }

        private fun startRetrofit() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

            instance?.retrofit = Retrofit.Builder()
                .baseUrl(CoreRepositoryConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }
    }
}

