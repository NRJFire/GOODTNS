package kr.sofac.goodtns.server;
import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.server.retrofit.ManagerRetrofit;


/**
 * Created by Maxim on 03.08.2017.
 */

public class Server {

    // Type authorizationType = new TypeToken<ServerResponse<ManagerInfoDTO>>() {}.getType();

    public void authorizationUser(AuthorizationDTO authorizationDTO, ManagerRetrofit.AsyncAnswerString asyncAnswerString) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), asyncAnswerString);
    }


    public void authorizationManager(AuthorizationDTO authorizationDTO, ManagerRetrofit.AsyncAnswerString asyncAnswerString) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        managerRetrofit.sendRequest(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName(), asyncAnswerString);
    }

}
