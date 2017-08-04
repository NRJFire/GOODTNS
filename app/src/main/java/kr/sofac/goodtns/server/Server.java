package kr.sofac.goodtns.server;

import android.content.Context;
import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.server.retrofit.ManagerRetrofit;


/**
 * Created by Maxim on 03.08.2017.
 */

public class Server {


    public void authorizationUser(Context context, AuthorizationDTO authorizationDTO, ManagerRetrofit.AsyncAnswer asyncAnswer) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(context, authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), asyncAnswer);
    }


    public void authorizationManager(Context context, AuthorizationDTO authorizationDTO, ManagerRetrofit.AsyncAnswer asyncAnswer) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(context, authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), asyncAnswer);
    }

}
