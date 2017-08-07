package kr.sofac.goodtns.server.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.server.Server;
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

//    private AsyncAnswerServerResponse answerServerResponse = null;
//    private AsyncAnswerServerResponseString answerServerResponseString = null;
    private AsyncAnswerString answerString = null;


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
     * Интерфейсы обработки событий в потоке
     */
    public interface AsyncAnswerString {
        void processFinish(Boolean isSuccess, String answerString);
    }

//    public interface AsyncAnswerServerResponse {
//        void processFinish(Boolean isSuccess, ServerResponse answerServerResponse);
//    }
//
//    public interface AsyncAnswerServerResponseString {
//        void processFinish(Boolean isSuccess, ServerResponse<String> answerServerResponse);
//    }

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
    public void sendRequest(T object, String requestType, AsyncAnswerString asyncAnswer) {
        answerString = asyncAnswer;

        sendRequest(object, requestType, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    serverResponse = logServerResponse(response.body().string());
                    if (!serverResponseError.equals(getServerResponseFromStringJSON(serverResponse).getResponseStatus())) {
                        answerString.processFinish(true, serverResponse);
                    } else {
                        answerString.processFinish(false, null);
                    }
                } catch (IOException e) {
                    answerString.processFinish(false, null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                answerString.processFinish(false, null);
            }
        });
    }

//    /**
//     * Get AsyncAnswer with True(SUCCESS) or False(ERROR) and (AsyncAnswerServerResponse asyncAnswer <= body response) for this Request;
//     */
//    public void sendRequest(T object, String requestType, AsyncAnswerServerResponse asyncAnswer) {
//        answerServerResponse = asyncAnswer;
//
//        sendRequest(object, requestType, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    serverResponse = logServerResponse(response.body().string());
//                    if (!serverResponseError.equals(getServerResponseFromStringJSON(serverResponse).getResponseStatus())) {
//                        answerServerResponse.processFinish(true, getServerResponseFromStringJSON(serverResponse));
//                    } else {
//                        answerServerResponse.processFinish(false, null);
//                    }
//                } catch (IOException e) {
//                    answerServerResponse.processFinish(false, null);
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                answerServerResponse.processFinish(false, null);
//            }
//        });
//    }
//
//    /**
//     * Get AsyncAnswer with True(SUCCESS) or False(ERROR) and (AsyncAnswerServerResponse asyncAnswer <= body response) for this Request;
//     */
//    public void sendRequest(T object, String requestType, AsyncAnswerServerResponseString asyncAnswer) {
//        answerServerResponseString = asyncAnswer;
//
//        sendRequest(object, requestType, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    serverResponse = logServerResponse(response.body().string());
//                    if (!serverResponseError.equals(getServerResponseFromStringJSON(serverResponse).getResponseStatus())) {
//                        answerServerResponseString.processFinish(true, getServerResponseFromStringJSON(serverResponse));
//                    } else {
//                        answerServerResponseString.processFinish(false, null);
//                    }
//                } catch (IOException e) {
//                    answerServerResponseString.processFinish(false, null);
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                answerServerResponseString.processFinish(false, null);
//            }
//        });
//    }


    /**
     * //////// Вспомогательные методы ////////
     */

    /**
     * Получение ServerResponse из String JSON
     */
    private ServerResponse<String> getServerResponseFromStringJSON(String stringJSON) {
        Type typeServerResponse = new TypeToken<ServerResponse>() {
        }.getType();
        return new Gson().fromJson(stringJSON, typeServerResponse);
    }

    /**
     * Логирование данных передачи
     */
    private ServerRequest logServerRequest(ServerRequest serverRequest) {
        Timber.e(">>>>> class:ManagerRetrofit; object:serverRequest : \n" + serverRequest);
        return serverRequest;
    }

    /**
     * Логирование данных приема
     */
    private String logServerResponse(String serverResponse) {
        Timber.e("<<<<< class:ManagerRetrofit; object:serverResponse : \n" + serverResponse);
        return serverResponse;
    }

}
