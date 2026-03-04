package br.com.androidtest.di

import br.com.androidtest.data.remote.RetrofitProvider
import br.com.androidtest.data.repository.MyDataRepository
import br.com.androidtest.data.repository.MyPlanRepository
import br.com.androidtest.data.repository.NpMyDataRepository
import br.com.androidtest.data.repository.NpMyPlanRepository
import br.com.androidtest.data.repository.RwMyDataRepository
import br.com.androidtest.data.repository.RwMyPlanRepository
import br.com.androidtest.data.service.ApiService
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.feature.mydata.NPMyDataViewModel
import br.com.androidtest.presentation.feature.mydata.RWMyDataViewModel
import br.com.androidtest.presentation.feature.myplan.NPMyPlanViewModel
import br.com.androidtest.presentation.feature.myplan.RWMyPlanViewModel
import br.com.androidtest.utils.Platform
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val npQualifier = named(Platform.NP.code)
val rwQualifier = named(Platform.RW.code)

val dataModule = module {
    single<Retrofit> { RetrofitProvider.create(androidContext()) }

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }

    single<MyDataRepository>(npQualifier) { NpMyDataRepository(get()) }
    single<MyDataRepository>(rwQualifier) { RwMyDataRepository(get()) }

    single<MyPlanRepository>(npQualifier) { NpMyPlanRepository(get()) }
    single<MyPlanRepository>(rwQualifier) { RwMyPlanRepository(get()) }
}

val domainModule = module {
    factory(npQualifier) { GetMyDataUseCase(get(npQualifier)) }
    factory(rwQualifier) { GetMyDataUseCase(get(rwQualifier)) }

    factory(npQualifier) { GetMyPlanUseCase(get(npQualifier)) }
    factory(rwQualifier) { GetMyPlanUseCase(get(rwQualifier)) }
}

val presentationModule = module {
    viewModel(qualifier = npQualifier) { NPMyDataViewModel(get(npQualifier)) }
    viewModel(qualifier = rwQualifier) {
        RWMyDataViewModel(
            getMyDataUseCase = get(rwQualifier),
            privacyUrl = getProperty("privacy_url"),
        )
    }
    viewModel(qualifier = npQualifier) { NPMyPlanViewModel(get(npQualifier)) }
    viewModel(qualifier = rwQualifier) { RWMyPlanViewModel(get(rwQualifier)) }
}

val appModules = listOf(
    dataModule,
    domainModule,
    presentationModule,
)