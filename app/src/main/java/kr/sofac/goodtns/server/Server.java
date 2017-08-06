package kr.sofac.goodtns.server;
import kr.sofac.goodtns.dto.AuthorizationDTO;
import kr.sofac.goodtns.server.retrofit.ManagerRetrofit;


/**
 * Created by Maxim on 03.08.2017.
 */

public class Server {

    // Type authorizationType = new TypeToken<ServerResponse<ManagerInfoDTO>>() {}.getType();

    public Boolean authorizationUser(AuthorizationDTO authorizationDTO) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        return managerRetrofit.sendRequest_Boolean(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName());
    }


    public Boolean authorizationManager(AuthorizationDTO authorizationDTO) {
        ManagerRetrofit<AuthorizationDTO> managerRetrofit = new ManagerRetrofit<>();
        return managerRetrofit.sendRequest_Boolean(authorizationDTO, new Object() {}.getClass().getEnclosingMethod().getName());
    }

}
