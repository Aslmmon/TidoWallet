package com.floriaapp.vendor.common.di

import com.floriaapp.core.api.*
import com.test.utils.Common.network.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit


val retrofit: Retrofit = createNetworkClient()
private val api: OrderApi = retrofit.create(OrderApi::class.java)
private val productApi: ProductsApi = retrofit.create(ProductsApi::class.java)
private val loginApi: LoginApi= retrofit.create(LoginApi::class.java)
private val mainApi: MainApi= retrofit.create(MainApi::class.java)
private val financeApi:FinancialApi= retrofit.create(FinancialApi::class.java)

val networkModule = module {
    factory { api }
    factory { productApi }
    factory { loginApi }
    factory { mainApi }
    factory { financeApi }
}