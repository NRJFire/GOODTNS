package kr.sofac.goodtns.server.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.server.type.ServerRequest;
import kr.sofac.goodtns.server.type.ServerResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Maxim on 03.08.2017.
 */

public class ManagerRetrofit<T> {

    private ServiceRetrofit serviceRetrofit;
    private String serverResponseError = Constants.SERVER_RESPONSE_ERROR;
    private String serverResponse = serverResponseError;
    private ServerRequest serverRequest;
    private AsyncAnswer answer = null;

    /**
     * Иницалиазация сервиса передачи
     * */ {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        serviceRetrofit = retrofit.create(ServiceRetrofit.class);
    }

    /**
     * Интерфейс обработки событий в потоке
     */
    public interface AsyncAnswer {
        void processFinish(Boolean isSuccess, String answer);
    }

    /**
     * Get String <= json (body response);
     */
    @SuppressWarnings("unchecked")
    public String sendRequest_String(T object, String requestType) {
        serverRequest = new ServerRequest(requestType, object);
        try {
            return logServerResponse(serviceRetrofit.getData(logServerRequest(serverRequest)).execute().body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return serverResponse;
        }
    }

    /**
     * Get True(SUCCESS) or False(ERROR) result for this Request;
     */
    public Boolean sendRequest_Boolean(T object, String requestType) {
        Type typeServerResponse = new TypeToken<ServerResponse<String>>() {
        }.getType();
        ServerResponse<String> serverResponse = new Gson().fromJson(sendRequest_String(object, requestType), typeServerResponse);
        return !serverResponseError.equals(serverResponse.getResponseStatus());

    }

    /**
     * Get ServerResponse<String> for this Request;
     */
    public ServerResponse<String> sendRequest_ServerResponse(T object, String requestType) {
        return getServerResponseFromStringJSON(sendRequest_String(object, requestType));
    }


    /**
     * Get Callback<ResponseBody> for this Request;
     */
    @SuppressWarnings("unchecked")
    public void sendRequest(T object, String requestType, Callback<ResponseBody> responseBodyCallback) {
        serverRequest = new ServerRequest(requestType, object);
        serviceRetrofit.getData(logServerRequest(serverRequest)).enqueue(responseBodyCallback);
    }

    /**
     * Get AsyncAnswer with True(SUCCESS) or False(ERROR) and String <= json (body response) for this Request;
     */
    public void sendRequest(T object, String requestType, AsyncAnswer asyncAnswer) {
        answer = asyncAnswer;

        sendRequest(object, requestType, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serverResponse = logServerResponse(response.body().string());
                    if (!serverResponseError.equals(getServerResponseFromStringJSON(serverResponse).getResponseStatus())) {
                        answer.processFinish(true, serverResponse);
                    } else {
                        answer.processFinish(false, null);
                    }
                } catch (IOException e) {
                    answer.processFinish(false, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                answer.processFinish(false, null);
            }
        });
    }

    //TODO написать метод и интерфейс который случает поток, и отдает пример ServerResponse<ManagerDTO>

    /**
     * //////// Вспомогательные методы ////////
     */

    /**
     * Получение ServerResponse из String JSON
     */
    private ServerResponse<String> getServerResponseFromStringJSON(String stringJSON) {
        Type typeServerResponse = new TypeToken<ServerResponse<String>>() {
        }.getType();
        return new Gson().fromJson(stringJSON, typeServerResponse);
    }

    /**
     * Логирование данных передачи
     */
    private ServerRequest logServerRequest(ServerRequest serverRequest) {
        Timber.e(">>>>> class:ManagerRetrofit; object:serverRequest : " + serverRequest);
        return serverRequest;
    }

    /**
     * Логирование данных приема
     */
    private String logServerResponse(String serverResponse) {
        Timber.e("<<<<< class:ManagerRetrofit; object:serverResponse : " + serverResponse);
        return serverResponse;
    }

}
