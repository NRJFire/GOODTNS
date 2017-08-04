package kr.sofac.goodtns.server;

import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.dto.ManagerDTO;
import kr.sofac.goodtns.server.retrofit.ManagerRetrofit;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import timber.log.Timber;

import static kr.sofac.goodtns.Constants.SERVER_RESPONSE_ERROR;


/**
 * Created by Maxim on 03.08.2017.
 */

public class Server {

//    public ArrayList<PushMessage> getAllPush(ManagerDTO managerDTO, Callback<ResponseBody> responseBodyCallback ) {
//
//        ManagerRetrofit<ManagerDTO> managerRetrofit = new ManagerRetrofit<>();
//
//        Type type = new TypeToken<ServerResponse<ArrayList<PushMessage>>>() {
//        }.getType();
//
//        managerRetrofit.sendRequest(managerDTO, (new Object() {}.getClass().getEnclosingMethod().getName()),responseBodyCallback);
//
//        ArrayList<PushMessage> pushMessages = new ArrayList<>();
//        if (!Constants.SERVER_RESPONSE_ERROR.equals(objectResponse)) {
//            ServerResponse serverResponse = new Gson().fromJson(objectResponse, type);
//        } else return null;
//
//        return null;
//    }


    public void authorizationUser(AuthorizationDTO authorizationDTO, Callback<ResponseBody> responseBodyCallback) {

        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), responseBodyCallback);

    }


    public void authorizationManager(AuthorizationDTO authorizationDTO, Callback<ResponseBody> responseBodyCallback) {

        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), responseBodyCallback);

    }


    public ManagerDTO getManagerDTO(ManagerDTO managerDTO) {

        return new ManagerDTO();
    }


}
