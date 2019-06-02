package com.cricscore.deepakshano.cricscore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;

import java.text.ParseException;

public class TournamentDetails extends AppCompatActivity {

    private TextView tv_mos, tv_tournament_type, tv_mom, tv_runner_prize, tv_winner_prize, tv_min_player, tv_max_teams,
            tv_entry_fees, et_instructions, tv_tour_name, tv_overs, tv_match_type,
            back_btn, tv_title, tv_tour_date,tv_tour_time;
    int max_teams, min_players, max_players, amt_entry_fees, tour_type, overs;
    String winner_prize, runner_prize, mom, mos, time, tour_name, date,Special_instruction;
    boolean isLimited;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_tour_details);
        try{
        overridePendingTransition(R.anim.slide, R.anim.slide_out);

        initializeViews();

        getValuesFromIntent();

        assignValues();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

    }

    private void assignValues() {
        try {
            if (tour_type == 2) {
                tv_tournament_type.setText("Tennis");
            } else {
                tv_tournament_type.setText("Leather");
            }

            if (isLimited) {
                tv_match_type.setText("Limited");
            } else {
                tv_match_type.setText("Unlimited");
            }

            tv_overs.setText(overs + "");

            tv_entry_fees.setText(amt_entry_fees + "");

            tv_tour_name.setText(tour_name);

            tv_min_player.setText(min_players + "");

            tv_max_teams.setText(max_teams + "");

            tv_mom.setText(mom);

            tv_mos.setText(mos);

            tv_runner_prize.setText(runner_prize);

            tv_winner_prize.setText(winner_prize);

            tv_title.setText("Tournament Details");

            tv_tour_date.setText(GlobalClass.outputdateformat.format(GlobalClass.inputdateformat.parse(date)));


            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            et_instructions.setText(Special_instruction);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getValuesFromIntent() {
        try{
        Intent intent = getIntent();
        tour_type = intent.getIntExtra("tour_type", 0);
        isLimited = intent.getBooleanExtra("match_type", false);
        overs = intent.getIntExtra("overs", 0);
        amt_entry_fees = intent.getIntExtra("entry_fees", 0);
        max_players = intent.getIntExtra("max_players", 0);
        min_players = intent.getIntExtra("min_players", 0);
        max_teams = intent.getIntExtra("max_teams", 0);
        winner_prize = intent.getStringExtra("winner_prize");
        runner_prize = intent.getStringExtra("runner_prize");
        mom = intent.getStringExtra("mom");
        mos = intent.getStringExtra("mos");
        time = intent.getStringExtra("time");
        tour_name = intent.getStringExtra("tour_name");
        date = intent.getStringExtra("tour_date");
        Special_instruction=intent.getStringExtra("Special_instruction");
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void initializeViews() {
        try{
        tv_tournament_type = findViewById(R.id.tv_ball);
        tv_match_type = findViewById(R.id.tv_match_type);
        tv_overs = findViewById(R.id.tv_overs);
        tv_tour_date = findViewById(R.id.tv_start_date);
        tv_winner_prize = findViewById(R.id.tv_winners_prize);
        tv_runner_prize = findViewById(R.id.tv_runners_prize);
        tv_mom = findViewById(R.id.tv_mom);
        tv_mos = findViewById(R.id.tv_mos);
        tv_tour_name = findViewById(R.id.tour_name);
        tv_max_teams = findViewById(R.id.tv_max_teams);
        tv_min_player = findViewById(R.id.tv_minplayer_count);
        tv_tour_time = findViewById(R.id.tv_day_night);
        tv_entry_fees = findViewById(R.id.tv_entry_fee);
        back_btn = findViewById(R.id.btn_back);
        tv_title = findViewById(R.id.title_name);
        et_instructions = findViewById(R.id.et_instructions);
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }
}
