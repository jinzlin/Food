package com.zhtx.mindlib.di.module;

import com.zhtx.mindlib.network.ApiService;
import com.zhtx.mindlib.network.JsonConverterFactory;
import com.zhtx.mindlib.network.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 作者: ljz.
 * @date 2017/11/15.
 * 描述：网络请求模型
 */
@Module
public class ApiModule {

    private String url;

    public ApiModule(String url) {
        this.url = url;
    }

    private Retrofit createRetrofit(Retrofit.Builder builder) {
        return builder
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit.Builder builder) {
        return createRetrofit(builder).create(ApiService.class);
    }

    @Singleton
    @Provides
    public RetrofitHelper provideRetrofitHelper(ApiService apiService) {
        return new RetrofitHelper(apiService);
    }

}
