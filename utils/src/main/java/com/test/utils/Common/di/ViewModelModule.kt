package com.floriaapp.vendor.common.di

import com.floriaapp.core.ui.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { OrderViewModel(get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { FinancialViewModel(get()) }

}