package br.com.androidtest.di

import br.com.androidtest.data.datasource.mydata.NPMyDataDataSource
import br.com.androidtest.data.datasource.mydata.NPMyDataDataSourceImpl
import br.com.androidtest.data.datasource.mydata.RWMyDataDataSource
import br.com.androidtest.data.datasource.mydata.RWMyDataDataSourceImpl
import br.com.androidtest.core.data.AssetsReader
import br.com.androidtest.data.datasource.myplan.NPMyPlanDataSource
import br.com.androidtest.data.datasource.myplan.NPMyPlanDataSourceImpl
import br.com.androidtest.data.datasource.myplan.RWMyPlanDataSource
import br.com.androidtest.data.datasource.myplan.RWMyPlanDataSourceImpl
import br.com.androidtest.data.repository.mydata.NPMyDataRepository
import br.com.androidtest.data.repository.mydata.RWMyDataRepository
import br.com.androidtest.data.repository.myplan.NPMyPlanRepository
import br.com.androidtest.data.repository.myplan.RWMyPlanRepository
import br.com.androidtest.domain.repository.MyDataRepository
import br.com.androidtest.domain.repository.MyPlanRepository
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.home.viewmodel.HomeViewModel
import br.com.androidtest.presentation.mydata.viewmodel.NPMyDataViewModel
import br.com.androidtest.presentation.mydata.viewmodel.RWMyDataViewModel
import br.com.androidtest.presentation.myplan.viewmodel.NPMyPlanViewModel
import br.com.androidtest.presentation.myplan.viewmodel.RWMyPlanViewModel
import br.com.androidtest.util.ResourceResolver
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    single { AssetsReader(androidContext()) }
    single { Gson() }
    single { ResourceResolver(androidContext()) }
}

val homeModule = module {
    viewModel { HomeViewModel() }
}

val myDataModule = module {
    // Data sources
    single<NPMyDataDataSource> { NPMyDataDataSourceImpl(get(), get()) }
    single<RWMyDataDataSource> { RWMyDataDataSourceImpl(get(), get()) }

    // Repositories
    single<MyDataRepository>(named("NP")) { NPMyDataRepository(get()) }
    single<MyDataRepository>(named("RW")) { RWMyDataRepository(get()) }

    // Use cases
    factory(named("NP")) { GetMyDataUseCase(get(named("NP"))) }
    factory(named("RW")) { GetMyDataUseCase(get(named("RW"))) }

    // ViewModels
    viewModel(named("NP")) { NPMyDataViewModel(get(named("NP")), get()) }
    viewModel(named("RW")) { RWMyDataViewModel(get(named("RW")), get()) }
}

val myPlanModule = module {
    // Data sources
    single<NPMyPlanDataSource> { NPMyPlanDataSourceImpl(get(), get()) }
    single<RWMyPlanDataSource> { RWMyPlanDataSourceImpl(get(), get()) }

    // Repositories
    single<MyPlanRepository>(named("NP_plan")) { NPMyPlanRepository(get()) }
    single<MyPlanRepository>(named("RW_plan")) { RWMyPlanRepository(get()) }

    // Use cases
    factory(named("NP_plan")) { GetMyPlanUseCase(get(named("NP_plan"))) }
    factory(named("RW_plan")) { GetMyPlanUseCase(get(named("RW_plan"))) }

    // ViewModels
    viewModel(named("NP_plan")) { NPMyPlanViewModel(get(named("NP_plan"))) }
    viewModel(named("RW_plan")) { RWMyPlanViewModel(get(named("RW_plan"))) }
}

// Combinação de todos os módulos
val appModuleClaro = listOf(
    coreModule,
    homeModule,
    myDataModule,
    myPlanModule
)