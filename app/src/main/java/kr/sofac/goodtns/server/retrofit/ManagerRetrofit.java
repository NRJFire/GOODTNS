package kr.sofac.goodtns.server.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.server.type.ServerRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static kr.sofac.goodtns.Constants.SERVER_RESPONSE_ERROR;

/**
 * Created by Maxim on 03.08.2017.
 */

public class ManagerRetrofit<T> {

    private static ServiceRetrofit serviceRetrofit;
    private String responseServer = SERVER_RESPONSE_ERROR;

    public interface AsyncResponseWithAnswer {
        void processFinish(Boolean isSuccess, String answer);
    }


    private AsyncResponseWithAnswer asyncResponseWithAnswer = null;

    public ManagerRetrofit() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        serviceRetrofit = retrofit.create(ServiceRetrofit.class);
    }

    @SuppressWarnings("unchecked")
    public void sendRequest(T object, String requestType, Callback<ResponseBody> responseBodyCallback) {
        ServerRequest serverRequest = new ServerRequest(requestType, object);
        Timber.e("serverRequest !!!!!!!!!! : "+serverRequest);
        getServiceRetrofit().getData(new ServerRequest(requestType, object)).enqueue(responseBodyCallback);
    }

    @SuppressWarnings("unchecked")
    public void sendRequest(T object, String requestType) {
        ServerRequest serverRequest = new ServerRequest(requestType, object);
        Timber.e("serverRequest !!!!!!!!!! : "+serverRequest);

        getServiceRetrofit().getData(serverRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseServer = response.body().string();
                    asyncResponseWithAnswer.processFinish(true, responseServer);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {

                    if (!response.body().string().contains(SERVER_RESPONSE_ERROR)) {
//                        Timber.e("!!!!!!!!!" + req);
//                        startMainActivity();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                asyncResponseWithAnswer.processFinish(false, null);
            }
        });

    }

    public ServiceRetrofit getServiceRetrofit() {
        return serviceRetrofit;
    }

}
