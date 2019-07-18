package stonetree.com.meals.core.provider

import com.stonetree.corerepository.CoreRepositoryConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoreRepository {

    lateinit var retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    companion object {
        @Volatile private var instance: CoreRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CoreRepository().also {
                    instance = it
                    instance?.retrofit = Retrofit.Builder()
                        .baseUrl(CoreRepositoryConstant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
    }
}
