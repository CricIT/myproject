package com.cricscore.deepakshano.cricscore.services;

import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.LoginMobile;
import com.cricscore.deepakshano.cricscore.pojo.PaginatedGroundListPoJo;
import com.cricscore.deepakshano.cricscore.pojo.TournamentListPojoClass;
import com.cricscore.deepakshano.cricscore.model.HosttournamentParametersModelClass;
import com.cricscore.deepakshano.cricscore.model.TournamentListModelClass;
import com.cricscore.deepakshano.cricscore.pojo.VerifyOtp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIMethods {

    @POST("tournament/host")
    Call<GeneralPojoClass> hosttournament(@Body HosttournamentParametersModelClass hosttournamentParameters);


    @POST("tournament/list")
    Call<TournamentListPojoClass> gettournamentlist(@Body TournamentListModelClass tournamentListModelClass);

    @GET("ground/list")
    Call<PaginatedGroundListPoJo> getGroundList(@Query("lat") Double lat, @Query("lng") Double lng, @Query("page") Integer page);

    @POST("user/login")
    Call<GeneralPojoClass> attemptLogin(@Body LoginMobile loginMobile);

    @POST("user/verifyOtp")
    Call<GeneralPojoClass> verifyOtp(@Body VerifyOtp verifyOtp);


}
