package stonetree.com.meals.core.provider

import com.stonetree.corerepository.CoreRepositoryConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreRepository {

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(CoreRepositoryConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
