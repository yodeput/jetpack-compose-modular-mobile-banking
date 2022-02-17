package yodeput.mobile.banking.core.di

import yodeput.mobile.banking.common.base.Repository
import yodeput.mobile.banking.core.repository.PayeeRepository
import yodeput.mobile.banking.core.repository.TransactionRepository
import yodeput.mobile.banking.core.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideUserRepository(repository: UserRepository): Repository

    @Singleton
    @Binds
    abstract fun provideTransactionRepository(repository: TransactionRepository): Repository

    @Singleton
    @Binds
    abstract fun providePayeeRepository(repository: PayeeRepository): Repository
}