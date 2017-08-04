package kr.sofac.goodtns.server.retrofit;

import kr.sofac.goodtns.Constants;
import kr.sofac.goodtns.server.type.ServerRequest;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Body;


/**
 * Created by Maxim on 03.08.2017.
 */

public interface ServiceRetrofit {

    @POST(Constants.PART_URL_APP)
    Call<ResponseBody> getData(@Body ServerRequest serverRequest);
}