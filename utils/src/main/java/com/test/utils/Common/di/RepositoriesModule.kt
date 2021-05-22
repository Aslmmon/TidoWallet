package com.test.utils.Common.di

import com.floriaapp.core.data.repositories.financial.FinancialRepo
import com.floriaapp.core.data.repositories.login.LoginRepo
import com.floriaapp.core.data.repositories.order.OrdersRepo
import com.floriaapp.core.data.repositories.products.ProductRepo
import com.floriaapp.vendor.common.Repo.MainRepo.IMain
import com.floriaapp.vendor.common.Repo.MainRepo.MainRepo
import org.koin.dsl.module

val repositoriesModule = module {
    factory { OrdersRepo(get()) }
    factory { ProductRepo(get()) }
    factory { LoginRepo(get()) }
    factory { MainRepo(get()) as IMain }
    factory { FinancialRepo(get()) }

}