package com.cricscore.deepakshano.cricscore.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.activity.HostingTournament;
import com.cricscore.deepakshano.cricscore.adapter.TournamentListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.helper.PaginationScrollListener;
import com.cricscore.deepakshano.cricscore.model.TournamentListModelClass;
import com.cricscore.deepakshano.cricscore.pojo.GroundList;
import com.cricscore.deepakshano.cricscore.pojo.TournamentListPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.tournamentlist;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cricscore.deepakshano.cricscore.adapter.TournamentListAdapter.TAG;

/**
 * Created by Deepak Shano on 3/4/2019.
 */

public class TournamentFragment extends Fragment {

    private static final int PAGE_START = 0;
    public List<tournamentlist> tournamentListPojoClass;
    public List<GroundList> groundLists;
    public TournamentListModelClass tournamentListModelClass;
    public RecyclerView tournament_list_recycler;
    public TournamentListAdapter tournamentListAdapter;
    HashMap<String, String> tourmyist;
    FloatingActionButton fab;
    LinearLayoutManager linearLayoutManager;
    private Context context;
    private RelativeLayout progress;
    private LottieAnimationView animationView;
    private TextView no_internet;
    private TextView retry;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private APIMethods apiService;
    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tour_list_fragment, null);
        try {
            context = getActivity();
            tournament_list_recycler = view.findViewById(R.id.tournament_list_recycler);
            fab = view.findViewById(R.id.fab);
            progress = view.findViewById(R.id.progress_layout);
            animationView = view.findViewById(R.id.animation_view);
            no_internet = view.findViewById(R.id.error_message);
            retry = view.findViewById(R.id.retry_btn);
            tourmyist = new HashMap<>();
            groundLists = new ArrayList<>();
            tournamentListModelClass = new TournamentListModelClass();
            tournamentListPojoClass = new ArrayList<>();
            animationView.setVisibility(View.GONE);
            no_internet.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) animationView.getLayoutParams();
            params.setMargins(0, 0, 0, -70);

            // Lookup the swipe container view
            swipeRefreshLayout = view.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        currentPage = PAGE_START;
                        getuserlocationcoordinates();
                        loadFirstPage();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            // Configure the refreshing colors
            //swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, HostingTournament.class);
                        GlobalClass.Gndname = "";
                        startActivity(intent);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });


            tournamentListAdapter = new TournamentListAdapter(context, tournamentListPojoClass, tourmyist);


            // rv.setLayoutManager(new GridLayoutManager(this, 2));
            //linearLayoutManager = new GridLayoutManager(this, 2);

            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            tournament_list_recycler.setLayoutManager(linearLayoutManager);

            tournament_list_recycler.setItemAnimator(new DefaultItemAnimator());

            tournament_list_recycler.setAdapter(tournamentListAdapter);

            tournament_list_recycler.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    Log.i(TAG, "loadMoreItems: ");

                    isLoading = true;
                    currentPage += 1;

                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: ");

                            loadNextPage();
                        }
                    }, 1000);

                }

                @Override
                public int getTotalPageCount() {
                    return TOTAL_PAGES;
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            //init service and load data
            apiService = ClientServiceGenerator.getUrlClient().create(APIMethods.class);

            getuserlocationcoordinates();
            loadFirstPage();

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        animationView.setVisibility(View.GONE);
                        retry.setVisibility(View.GONE);
                        no_internet.setAnimation(null);

                        no_internet.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                        loadFirstPage();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return view;
    }


    private void getuserlocationcoordinates() {
        try {
            tournamentListModelClass.setLat(12.9166);
            tournamentListModelClass.setLng(77.6101);
            tournamentListModelClass.setPage(currentPage);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void loadFirstPage() {
        Log.d("myLog", "loadFirstPage: ");
        callGetTourList().enqueue(new Callback<TournamentListPojoClass>() {
            @Override
            public void onResponse(@NonNull Call<TournamentListPojoClass> call, @NonNull Response<TournamentListPojoClass> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getRequestStatus() == 1) {
                            try {


                                tournamentListAdapter.clear();
                                List<tournamentlist> results = fetchResults(response);

                                progress.setVisibility(View.GONE);
                                tournament_list_recycler.setVisibility(View.VISIBLE);
                                tournamentListAdapter.addAll(results);

                                swipeRefreshLayout.setRefreshing(false);

                                TournamentListPojoClass tournamentListPojoClass = response.body();
                                int nextPage = tournamentListPojoClass.getNextPage();

                                Log.i("myLog", "onResponse: " + nextPage);


                                if (nextPage < 0) {
                                    isLastPage = true;

                                } else
                                    tournamentListAdapter.addLoadingFooter();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<TournamentListPojoClass> call, Throwable t) {
                try {
                    Log.d("INSIDE FAILURE", "****");
                    if (t instanceof SocketTimeoutException) {

                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                    } else {
                        progress.setVisibility(View.GONE);
                        no_internet.setVisibility(View.VISIBLE);
                       retry.setVisibility(View.VISIBLE);
                        no_internet.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink));
                        animationView.setVisibility(View.VISIBLE);
                        animationView.playAnimation();
                        Log.d(TAG, "onFailure: "+t.getMessage());

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

    }

    private void loadNextPage() {

        Log.d("myLog", "loadNextPage: " + currentPage);
        getuserlocationcoordinates();

        callGetTourList().enqueue(new Callback<TournamentListPojoClass>() {
            @Override
            public void onResponse(Call<TournamentListPojoClass> call, Response<TournamentListPojoClass> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getRequestStatus() == 1) {
                            try {

                                tournamentListAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<tournamentlist> results = fetchResults(response);
                                tournamentListAdapter.addAll(results);

                                TournamentListPojoClass tournamentListPojoClass = response.body();
                                int nextPage = tournamentListPojoClass.getNextPage();


                                if (nextPage > 0) {
                                    tournamentListAdapter.addLoadingFooter();
                                } else isLastPage = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<TournamentListPojoClass> call, Throwable t) {
                try {
                    Log.d("INSIDE FAILURE", "****");
                    if (t instanceof SocketTimeoutException) {

                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                    } else {
                        progress.setVisibility(View.GONE);
                        no_internet.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);
                        no_internet.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink));
                        animationView.setVisibility(View.VISIBLE);
                        animationView.playAnimation();

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    private List<tournamentlist> fetchResults(Response<TournamentListPojoClass> response) {
        TournamentListPojoClass tournamentListPojoClass = response.body();
        return tournamentListPojoClass.getList();
    }

   /* private void gettounamentlist() {
        try {
            groundLists.clear();
            tourmyist.clear();
            tournamentListPojoClass.clear();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<TournamentListPojoClass> call = api.gettournamentlist(tournamentListModelClass);
            call.enqueue(new Callback<TournamentListPojoClass>() {
                @Override
                public void onResponse(Call<TournamentListPojoClass> call, final Response<TournamentListPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {

                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    tournamentListPojoClass = response.body().getList();
                                    groundLists = response.body().getGroundList();
                                    tournamentListAdapter = new TournamentListAdapter(context, tournamentListPojoClass, tourmyist);
                                    tournament_list_recycler.setHasFixedSize(true);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    tournament_list_recycler.setLayoutManager(layoutManager);
                                    tournament_list_recycler.setAdapter(tournamentListAdapter);
                                    progress.setVisibility(View.GONE);
                                    tournament_list_recycler.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

                @Override
                public void onFailure(Call<TournamentListPojoClass> call, Throwable t) {
                    try {
                        Log.d("INSIDE FAILURE", "****");
                        if (t instanceof SocketTimeoutException) {

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            progress.setVisibility(View.GONE);
                            no_internet.setVisibility(View.VISIBLE);
                            retry.setVisibility(View.VISIBLE);
                            no_internet.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink));
                            animationView.setVisibility(View.VISIBLE);
                            animationView.playAnimation();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    private Call<TournamentListPojoClass> callGetTourList() {
        return apiService.gettournamentlist(
                tournamentListModelClass
        );
    }

}
