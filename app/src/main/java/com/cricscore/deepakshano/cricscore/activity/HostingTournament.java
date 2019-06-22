package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Group;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.model.HosttournamentParametersModelClass;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.MessageEvent;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HostingTournament extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "myLog";
    ArrayList<String> list = new ArrayList();
    HosttournamentParametersModelClass hosttournamentParameters;
    EditText et_tour_name, et_overs, et_entry_fee, et_minplayer_count,
            et_winners, et_runners, et_mom, et_mos, et_instructions;
    TextView tv_free, tv_Premium, tv_tennis, tv_leather, tv_limited, tv_unlimited, tv_age, tv_user_type, tv_ground_list, tv_start_date, tv_end_date;
    TextView tv_ten_teams, tv_fifteen_teams, tv_twenty_teams;
    TextView tv_twelve_players, tv_fifteen_players, tv_twenty_players;
    EditText tv_custom_teams, et_custom_players;
    Button btn_save, btn_host;
    ImageView btn_add_banner;
    Context context;
    int category, tounament_type;
    Boolean match_type;
    TextView back_btn, tv_title;
    Group lyt_match_type, lyt_overs;
    Intent intent;
    String[] typeGroup = {"School", "College", "Corporate"};
    String[] ageGroup = {"Under 12", "Under 14", "Under 16", "Under 19", "Under 23", "23+"};
    int ed_date, ed_year, ed_month;
    private TextView tv_night, tv_day, tv_weekend, tv_weekday;
    private TextView tv_male, tv_female;
    private ProgressDialog progressDialog;
    private Drawable customErrorDrawable;
    private int PICK_IMAGE_REQUEST = 1;
    private View background_gender;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;
    int maxteams = 0, maxplayers = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hosttournament_fragment);
        try {
            overridePendingTransition(R.anim.slide, R.anim.slide_out);
            customErrorDrawable = getResources().getDrawable(R.drawable.ic_error);
            customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
            context = this;
            progressDialog = new ProgressDialog(HostingTournament.this);
            intent = getIntent();
            et_tour_name = findViewById(R.id.tour_name);
            et_overs = findViewById(R.id.et_overs);
            tv_start_date = findViewById(R.id.tv_start_date);
            tv_end_date = findViewById(R.id.tv_end_date);
            et_entry_fee = findViewById(R.id.et_entry_fee);
            tv_ten_teams = findViewById(R.id.tv_ten_teams);
            et_winners = findViewById(R.id.et_winners_prize);
            et_runners = findViewById(R.id.et_runners_prize);
            et_mos = findViewById(R.id.et_mos);
            et_mom = findViewById(R.id.et_mom);
            et_instructions = findViewById(R.id.et_instructions);
            tv_free = findViewById(R.id.tv_free);
            tv_Premium = findViewById(R.id.tv_premium);
            tv_leather = findViewById(R.id.tv_leather);
            tv_tennis = findViewById(R.id.tv_tennis);
            tv_limited = findViewById(R.id.tv_limited);
            tv_unlimited = findViewById(R.id.tv_unlimited);
            btn_host = findViewById(R.id.btn_host);
            btn_save = findViewById(R.id.btn_save);
            tv_age = findViewById(R.id.tv_age);
            tv_user_type = findViewById(R.id.tv_user_type);
            tv_female = findViewById(R.id.tv_female);
            tv_male = findViewById(R.id.tv_male);
            tv_day = findViewById(R.id.tv_day);
            tv_night = findViewById(R.id.tv_night);
            tv_weekday = findViewById(R.id.tv_weekday);
            tv_weekend = findViewById(R.id.tv_weekend);
            back_btn = findViewById(R.id.btn_back);
            tv_title = findViewById(R.id.title_name);
            lyt_match_type = findViewById(R.id.group_match_type);
            lyt_overs = findViewById(R.id.group_overs);
            btn_add_banner = findViewById(R.id.btn_add_banner);
            tv_ground_list = findViewById(R.id.tv_ground_list);
            background_gender = findViewById(R.id.background);
            tv_fifteen_teams = findViewById(R.id.tv_fifteen_teams);
            tv_twenty_teams = findViewById(R.id.tv_twenty_teams);
            tv_ten_teams = findViewById(R.id.tv_ten_teams);


            tv_twelve_players = findViewById(R.id.tv_twelve_players);
            tv_fifteen_players = findViewById(R.id.tv_fifteen_players);
            tv_twenty_players = findViewById(R.id.tv_twenty_players);
            et_custom_players = findViewById(R.id.et_custom_players);

            tv_custom_teams = findViewById(R.id.tv_custom_teams);
            constraintLayout = findViewById(R.id.main_constraint);

            constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.connect(background_gender.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.TOP, 0);
            constraintSet.constrainDefaultHeight(background_gender.getId(), 200);
            constraintSet.applyTo(constraintLayout);

            hosttournamentParameters = new HosttournamentParametersModelClass();

            tv_title.setText("Create Tournament");

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tv_free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_free.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_free.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_Premium.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_Premium.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        GlobalClass.setMargins(tv_free, 4, 2, 0, 4);
                        category = 1;
                        tv_Premium.setError(null);
                        tv_free.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            tv_Premium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_Premium.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_Premium.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_free.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_free.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        category = 2;
                        tv_Premium.setError(null);
                        tv_free.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_male.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_male.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_female.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_female.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_female.setError(null);
                        tv_male.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_female.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_female.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_male.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_male.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_female.setError(null);
                        tv_male.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_weekend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_weekend.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_weekend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_weekday.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_weekday.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_weekend.setError(null);
                        tv_weekday.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }

                }
            });

            tv_weekday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_weekday.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_weekday.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_weekend.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_weekend.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_weekend.setError(null);
                        tv_weekday.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }

                }
            });

            tv_day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_day.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_day.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_night.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_night.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_night.setError(null);
                        tv_day.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_night.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_night.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_night.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_day.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_day.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tv_night.setError(null);
                        tv_day.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }

                }
            });


            tv_leather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_leather.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_leather.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_tennis.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_tennis.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tounament_type = 1;
                        tv_leather.setError(null);
                        tv_tennis.setError(null);
                        lyt_match_type.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            tv_tennis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_tennis.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_tennis.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_leather.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_leather.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        tounament_type = 2;
                        tv_leather.setError(null);
                        tv_tennis.setError(null);
                        match_type = true;
                        lyt_match_type.setVisibility(View.GONE);
                        lyt_overs.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            tv_limited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_limited.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_limited.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_unlimited.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_unlimited.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                        match_type = true;
                        lyt_overs.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            tv_unlimited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tv_unlimited.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_unlimited.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
                        tv_limited.setBackground(getResources().getDrawable(
                                R.drawable.rect_round));
                        tv_limited.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        match_type = false;
                        lyt_overs.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Toast.makeText(getApplicationContext(), "Not saving currently!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            btn_host.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (validateFormFields()) {
                            progressDialog.setMessage("Hosting tournament...");
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            savedata();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please fill all the required fields and try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_age.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ageGroupPopUp();
                }
            });

            tv_user_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tourTypePopUp();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showDateRange();
                        tv_start_date.setError(null);
                        tv_end_date.setError(null);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_end_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (tv_start_date.getText().toString().equalsIgnoreCase("Start")) {
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert", "Please Choose Tournament Starting Date", false, false);
                        } else {
                            showEndDateRange();
                            tv_end_date.setError(null);
                        }

                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });


            btn_add_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        // Show only images, no videos or anything else
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        // Always show the chooser (if there are multiple options available)
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            tv_ground_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, GroundListActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            tv_ten_teams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxteams = 10;
                        selectedstatus(tv_ten_teams, tv_fifteen_teams, tv_twenty_teams, tv_custom_teams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            tv_fifteen_teams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxteams = 15;
                        selectedstatus(tv_fifteen_teams, tv_ten_teams, tv_twenty_teams, tv_custom_teams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            tv_twenty_teams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxteams = 20;
                        selectedstatus(tv_twenty_teams, tv_ten_teams, tv_fifteen_teams, tv_custom_teams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            tv_custom_teams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String custom_age_group = tv_custom_teams.getText().toString();
                        if (!custom_age_group.equals(maxteams)) {
                            maxteams = Integer.parseInt(custom_age_group);
                            selectedstatuscustom(tv_custom_teams, tv_ten_teams, tv_fifteen_teams, tv_twenty_teams);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            tv_twelve_players.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxplayers = 12;
                        selectedstatus(tv_twelve_players, tv_fifteen_players, tv_twenty_players, et_custom_players);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            tv_fifteen_players.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxplayers = 15;
                        selectedstatus(tv_fifteen_players, tv_twelve_players, tv_twenty_players, et_custom_players);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            tv_twenty_players.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxplayers = 20;
                        selectedstatus(tv_twenty_players, tv_fifteen_players, tv_twelve_players, et_custom_players);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            et_custom_players.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        maxplayers = 20;
                        String custom_players_group = et_custom_players.getText().toString();
                        if (!custom_players_group.equals(maxplayers)) {
                            selectedstatuscustom(et_custom_players, tv_twelve_players, tv_fifteen_players, tv_twenty_players);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

    }

    public void selectedstatuscustom(EditText et_Custom, TextView Tv_one, TextView tv_two, TextView tv_three) {
        et_Custom.setBackground(getResources().getDrawable(
                R.drawable.rounded_rect_lightgreen_low));
        et_Custom.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
        Tv_one.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        Tv_one.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_two.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        tv_two.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_three.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        tv_three.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

    }

    public void selectedstatus(TextView tv_one, TextView Tv_two, TextView tv_three, EditText et_one) {
        tv_one.setBackground(getResources().getDrawable(
                R.drawable.rounded_rect_lightgreen_low));
        tv_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
        Tv_two.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        Tv_two.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_three.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        tv_three.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        et_one.setBackground(getResources().getDrawable(
                R.drawable.rect_round));
        et_one.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        et_one.setText("");
    }


    private void tourTypePopUp() {
        try {
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
            //alt_bld.setIcon(R.drawable.icon);
            alt_bld.setTitle("Select one option");

            alt_bld.setSingleChoiceItems(typeGroup, -1, new DialogInterface
                    .OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    try {
                        if (typeGroup[item].equals(typeGroup[0])) {
                            ageGroup = new String[]{"Under 12", "Under 14", "Under 16"};
                        } else {
                            ageGroup = new String[]{"Under 12", "Under 14", "Under 16", "Under 19", "Under 23", "23+"};
                        }
                        tv_user_type.setText(typeGroup[item]);
                        dialog.dismiss();// dismiss the alertbox after chose option
                        tv_user_type.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_user_type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            AlertDialog alert = alt_bld.create();
            alert.show();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView iv_tour_banner = (ImageView) findViewById(R.id.iv_group_banner);
                iv_tour_banner.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void savedata() {
        try {
            list.add("f1ad8a9c-1651-4aac-9547-8536fa6dd2c0");
            hosttournamentParameters.setTournamentName(et_tour_name.getText().toString());
            hosttournamentParameters.setDate(tv_start_date.getText().toString());
            if (!TextUtils.isEmpty(tv_end_date.getText())) {
                hosttournamentParameters.setEndDate(tv_end_date.getText().toString());
            }
            hosttournamentParameters.setEntryFee(Integer.parseInt(et_entry_fee.getText().toString()));
            hosttournamentParameters.setLimited(match_type);
            hosttournamentParameters.setOvers(Integer.parseInt(et_overs.getText().toString()));
            hosttournamentParameters.setWinnerPrize(et_winners.getText().toString());
            hosttournamentParameters.setRunnerPrize(et_runners.getText().toString());
            hosttournamentParameters.setMatchType(tounament_type);
            hosttournamentParameters.setMaxPlayers(maxplayers);
            hosttournamentParameters.setGroundId(list);
            hosttournamentParameters.setMatchInstructions(et_instructions.getText().toString());
            hosttournamentParameters.setHostId(GlobalClass.usertoken);
            hosttournamentParameters.setMaxTeams(maxteams);
            hosttournamentParameters.setMos(et_mos.getText().toString());
            hosttournamentParameters.setMom("t-shirt");
            hosttournamentParameters.setMinPlayers(2);
            hosttournamentParameters.setTime(1);

            HostTournament();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void HostTournament() {
        try {
            Log.i(TAG, "HostTournament: " + hosttournamentParameters);
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<GeneralPojoClass> call = api.hosttournament(hosttournamentParameters);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                progressDialog.dismiss();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Success!", "Tournament hosted successfully", false, true);
                            } else {
                                progressDialog.dismiss();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojoClass> call, Throwable t) {
                    try {
                        Log.d("INSIDE FAILURE", "****");
                        if (t instanceof SocketTimeoutException) {
                            progressDialog.dismiss();

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            progressDialog.dismiss();

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        e.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private boolean validateFormFields() {
        boolean flag = true;
        try {
            if (category == 0) {
                tv_Premium.setError("Please select any one", customErrorDrawable);
                tv_free.setError("Please select any one", customErrorDrawable);
                flag = false;
            }

            if (tounament_type == 0) {
                tv_leather.setError("Please select any one", customErrorDrawable);
                tv_tennis.setError("Please select any one", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(et_tour_name.getText())) {
                et_tour_name.setError("Please enter tournament name", customErrorDrawable);
                flag = false;
            }

            if (TextUtils.isEmpty(et_overs.getText()) && match_type) {
                et_overs.setError("Please enter number of over", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(tv_start_date.getText())) {
                tv_start_date.setError("Please select tournament start date", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(tv_end_date.getText())) {
                tv_end_date.setError("Please select tournament end date", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(et_entry_fee.getText())) {
                et_entry_fee.setError("Please enter entry fees", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(et_minplayer_count.getText())) {
                et_minplayer_count.setError("Please enter minimum players count ", customErrorDrawable);
                flag = false;
            } else if (Integer.parseInt(et_minplayer_count.getText().toString()) < 11) {
                et_minplayer_count.setError("minimun players cannot be less than 11", customErrorDrawable);
                flag = false;
            }

            if (TextUtils.isEmpty(et_winners.getText())) {
                et_winners.setError("Please enter winners prize", customErrorDrawable);
                flag = false;
            }
            if (TextUtils.isEmpty(et_runners.getText())) {
                et_runners.setError("Please enter runners prize", customErrorDrawable);
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        Log.i(TAG, "validateFormFields: " + flag);
        return flag;
    }

    public void ageGroupPopUp() {
        try {
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
            //alt_bld.setIcon(R.drawable.icon);
            alt_bld.setTitle("Select one option");

            alt_bld.setSingleChoiceItems(ageGroup, -1, new DialogInterface
                    .OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    try {
                        if (ageGroup[item].equals(ageGroup[4])) {
                            typeGroup = new String[]{"College", "Corporate"};
                        } else if (ageGroup[item].equals(ageGroup[5])) {
                            typeGroup = new String[]{"College", "Corporate"};
                        } else {
                            typeGroup = new String[]{"School", "College"};
                        }

                        tv_age.setText(ageGroup[item]);
                        dialog.dismiss();// dismiss the alertbox after chose option
                        tv_age.setBackground(getResources().getDrawable(
                                R.drawable.rounded_rect_lightgreen_low));
                        tv_age.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            AlertDialog alert = alt_bld.create();
            alert.show();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

    }


/*    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String[] months = {"", "Jan, Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String selected_month_start = months[monthOfYear];
        String startDate = dayOfMonth + "-" + selected_month_start + "-" + year;
        String selected_month_end = months[monthOfYearEnd];
        ed_year = year;
        ed_month = monthOfYear;
        ed_date = dayOfMonth;
        String endDate = dayOfMonthEnd + "-" + selected_month_end + "-" + yearEnd;
        tv_start_date.setText(startDate);

        tv_end_date.setText(endDate);
    }*/

    private void showDateRange() {
        try {
            Calendar now = Calendar.getInstance();
            DatePickerDialog startdate = DatePickerDialog.newInstance(
                    HostingTournament.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            startdate.setMinDate(Calendar.getInstance());

            startdate.show(getFragmentManager(), "StartDatepickerdialog");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void showEndDateRange() {
        try {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.YEAR, ed_year);
            now.set(Calendar.MONTH, ed_month);
            now.set(Calendar.DAY_OF_MONTH, ed_date);
            DatePickerDialog enddate = DatePickerDialog.newInstance(
                    HostingTournament.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            enddate.setMinDate(now);
            enddate.show(getFragmentManager(), "EndDatepickerdialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        tv_ground_list.setText(event.getGroundName());
        tv_ground_list.setBackground(getResources().getDrawable(
                R.drawable.rounded_rect_lightgreen_low));
        tv_ground_list.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        try {
            EventBus.getDefault().unregister(this);
            super.onStop();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            EventBus.getDefault().removeAllStickyEvents();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        try {
            String[] months = {"", "Jan, Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            if (view.getTag().equalsIgnoreCase("StartDatepickerdialog")) {
                String selected_month_start = months[monthOfYear];
                String startDate = dayOfMonth + "-" + selected_month_start + "-" + year;
                ed_year = year;
                ed_month = monthOfYear;
                ed_date = dayOfMonth;
                tv_start_date.setText(startDate);
                tv_start_date.setBackground(getResources().getDrawable(
                        R.drawable.rounded_rect_lightgreen_low));
                tv_start_date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
            } else if (view.getTag().equalsIgnoreCase("EndDatepickerdialog")) {
                String selected_month_start = months[monthOfYear];
                String endDate = dayOfMonth + "-" + selected_month_start + "-" + year;
                tv_end_date.setText(endDate);
                tv_end_date.setBackground(getResources().getDrawable(
                        R.drawable.rounded_rect_lightgreen_low));
                tv_end_date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right_green, 0, 0, 0);
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


}
