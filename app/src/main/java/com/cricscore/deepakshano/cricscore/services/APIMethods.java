package com.cricscore.deepakshano.cricscore.services;

import com.cricscore.deepakshano.cricscore.model.CreateGroupModelClass;
import com.cricscore.deepakshano.cricscore.model.LoginMobileModelClass;
import com.cricscore.deepakshano.cricscore.model.VerifyOtpModelClass;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GetAllGroupsListPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.pojo.PaginatedGroundListPoJo;
import com.cricscore.deepakshano.cricscore.pojo.TournamentListPojoClass;
import com.cricscore.deepakshano.cricscore.model.HosttournamentParametersModelClass;
import com.cricscore.deepakshano.cricscore.model.TournamentListModelClass;
import com.cricscore.deepakshano.cricscore.pojo.VerifyOtpPojo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIMethods {

    @POST("tournament/host")
    Call<GeneralPojoClass> hosttournament(@Body HosttournamentParametersModelClass hosttournamentParameters);

    @POST("tournament/list")
    Call<TournamentListPojoClass> gettournamentlist(@Body TournamentListModelClass tournamentListModelClass);

    @GET("ground/list")
    Call<PaginatedGroundListPoJo> getGroundList(@Query("lat") Double lat, @Query("lng") Double lng, @Query("page") Integer page);

    @POST("user/login")
    Call<GeneralPojoClass> attemptLogin(@Body LoginMobileModelClass loginMobile);

    @POST("user/verifyOtp")
    Call<VerifyOtpPojo> verifyOtp(@Body VerifyOtpModelClass verifyOtp);

    @GET("group/userGroups")
    Call<GetAllGroupsListPojoClass> getGroupList(@HeaderMap Map<String, String> headers);

    @POST("group/create")
    Call<GeneralPojoClass> creategroup(@HeaderMap Map<String, String> headers,@Body CreateGroupModelClass createGroupModelClass);

    @GET("group/info/{groupid}")
    Call<GroupDetailsPojo> getgroupdetails(@Path("groupid") String groupid,@HeaderMap Map<String, String> headers);






}
