package com.irem.urlshortener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BitlyApiService {

    private static final String API_KEY = "API_KEY bilgisini buraya giriniz"; // Bitly API anahtarınızı buraya ekleyin
    private static final String BASE_URL = "https://api-ssl.bitly.com/v4/";

    private static Retrofit retrofit = null;

    // Retrofit instance'ını döndürmek için kullanılan metod
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + API_KEY)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // URL kısaltma isteğini göndermek için kullanılan metod
    public Call<ShortenResponse> shortenUrl(ShortenRequest request) {
        return getRetrofitInstance().create(ApiServiceInterface.class).shortenUrl(request);
    }
}
