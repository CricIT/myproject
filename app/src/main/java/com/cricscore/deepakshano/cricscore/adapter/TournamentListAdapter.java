package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.activity.TournamentDetails;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.tournamentlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TournamentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<tournamentlist> TournamentList;
    HashMap<String, String> tourmyist;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static String TAG = "myLog";


    public TournamentListAdapter(Context context, List<tournamentlist> TournamentList, HashMap<String, String> tourmyist) {
        Log.i(TAG, "TournamentListAdapter: ");
        this.context = context;
        this.TournamentList = TournamentList;
        this.tourmyist = tourmyist;

    }


    @Override
    public int getItemViewType(int position) {
        Log.i(TAG, "getItemViewType: ");
        return (position == TournamentList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.load_more_progress, parent, false);
                viewHolder = new LoadingTournaments(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        Log.i(TAG, "getViewHolder: ");
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.tournament_listing_content, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final tournamentlist result = TournamentList.get(position);

        Log.i("myLog", "onBindViewHolder: " + result.getMom());

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder viewHolder = (ViewHolder) holder;
                try {
                    viewHolder.dash.setVisibility(View.GONE);
                    viewHolder.tournament_end_date.setVisibility(View.GONE);
                    viewHolder.tv_tournament_name.setText(TournamentList.get(position).getTournamentName());
                    viewHolder.tournament_ground_name.setText("Location: " + result.getLocation());
                    viewHolder.tour_entry_fees.setText("Entry Fees: " + result.getEntryFee());
                    viewHolder.tour_prize.setText("Prize Money: " + result.getWinnerPrize() + "");
                    viewHolder.tournament_start_date.setText(GlobalClass.outputdateformat.format(GlobalClass.inputdateformat.parse(TournamentList.get(position).getDate())));
                    if (TournamentList.get(position).getEndDate() != null) {
                        viewHolder.dash.setVisibility(View.VISIBLE);
                        viewHolder.tournament_end_date.setVisibility(View.VISIBLE);
                        viewHolder.tournament_end_date.setText(GlobalClass.outputdateformat.format(GlobalClass.inputdateformat.parse(TournamentList.get(position).getEndDate())));
                    }
                    viewHolder.layout_tournament_reycler_content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = new Intent(context, TournamentDetails.class);
                                intent.putExtra("Special_instruction", result.getMatchInstructions());
                                intent.putExtra("tour_date", result.getDate());
                                intent.putExtra("tour_type", result.getMatchType());
                                intent.putExtra("match_type", result.getIsLimited());
                                intent.putExtra("overs", result.getOvers());
                                intent.putExtra("tour_name", result.getTournamentName());
                                intent.putExtra("time", result.getTime());
                                intent.putExtra("entry_fees", result.getEntryFee());
                                intent.putExtra("max_players", result.getMaxPlayers());
                                intent.putExtra("min_players", result.getMinPlayers());
                                intent.putExtra("max_teams", result.getMaxTeams());
                                intent.putExtra("winner_prize", result.getWinnerPrize());
                                intent.putExtra("runner_prize", result.getRunnerPrize());
                                intent.putExtra("mom", result.getMom());
                                intent.putExtra("mos", result.getMos());
                                context.startActivity(intent);
                            }catch (Exception e){
                                e.getMessage();
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
        }

    }

    public void setitem(List<tournamentlist> TournamentList) {
        this.TournamentList = TournamentList;
    }

    @Override
    public int getItemCount() {
        return TournamentList == null ? 0 : TournamentList.size();
    }

    public void add(tournamentlist r) {
        TournamentList.add(r);
        notifyItemInserted(TournamentList.size() - 1);
    }
    public void addAll(List<tournamentlist> tourResults) {
        Log.i(TAG, "addAll: ");
        for (tournamentlist result : tourResults) {
            add(result);
        }
    }

    public void remove(tournamentlist r) {
        int position = TournamentList.indexOf(r);
        if (position > -1) {
            TournamentList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new tournamentlist());
    }

    public void removeLoadingFooter() {
        Log.i(TAG, "removeLoadingFooter: ");
        isLoadingAdded = false;

        int position = TournamentList.size() - 1;
        tournamentlist result = getItem(position);

        if (result != null) {
            TournamentList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public tournamentlist getItem(int position) {
        return TournamentList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tv_tournament_name, tournament_start_date, tournament_end_date, tournament_ground_name, tournament_time, tour_prize, tour_entry_fees;
        public LinearLayout layout_tournament_reycler_content;
        private CardView tour_details;
        private View dash;


        public ViewHolder(View view) {

            super(view);
            Log.i(TAG, "ViewHolder: ");
            layout_tournament_reycler_content = itemView.findViewById(R.id.layout_tournament_reycler_content);
            dash=itemView.findViewById(R.id.dash);
            tv_tournament_name = itemView.findViewById(R.id.tv_tournament_name);
            tournament_start_date = itemView.findViewById(R.id.tournament_start_date);
            tournament_end_date = itemView.findViewById(R.id.tournament_end_date);
            tournament_ground_name = itemView.findViewById(R.id.tournament_ground_name);
            tour_prize = itemView.findViewById(R.id.prize_money);
            tour_entry_fees = itemView.findViewById(R.id.entry_fees);
            tour_details = itemView.findViewById(R.id.card_tour_details);
        }
    }

    protected class LoadingTournaments extends RecyclerView.ViewHolder {
        public LoadingTournaments(View view) {
            super(view);
            Log.i(TAG, "LoadingTournaments: ");
        }
    }
}

