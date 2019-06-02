package com.cricscore.deepakshano.cricscore.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;

public class CustomMessageHelper {

    Dialog lDialogresume;
    Context context;

    public CustomMessageHelper(Context context) {
        this.context = context;
        lDialogresume = new Dialog(context);
    }

    public void showCustomMessage(final Activity activity, String title, String msg, final Boolean bFlag, final Boolean activityfinishflag) {
        // TODO Auto-generated method stub
        try {
            if (lDialogresume != null && lDialogresume.isShowing()) {
                if (!((Activity) context).isFinishing()) {
                    lDialogresume.dismiss();
                }

            }
            //if (lDialogresume != null && lDialogresume.isShowing())return;

            lDialogresume = new Dialog(context);
            lDialogresume.requestWindowFeature(Window.FEATURE_NO_TITLE);
            lDialogresume.setCanceledOnTouchOutside(false);
            lDialogresume.setCancelable(false);
            lDialogresume.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            lDialogresume.setContentView(R.layout.errordisplaydialog);
            ((TextView) lDialogresume.findViewById(R.id.dialog_title))
                    .setText(title);
            ((TextView) lDialogresume.findViewById(R.id.dialog_message))
                    .setText(msg);
            ((Button) lDialogresume.findViewById(R.id.ok)).setText("OK");

            ((Button) lDialogresume.findViewById(R.id.ok))
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            try {
                                if (bFlag) {
                                    if (lDialogresume != null && lDialogresume.isShowing()) {
                                        if (!((Activity) context).isFinishing()) {
                                            lDialogresume.dismiss();
                                            dismissMessage(activity, activityfinishflag);
                                        }
                                    }
                          /*  Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);*/
                                    //((Activity)context).finish();
                                } else {
                                    if (lDialogresume != null && lDialogresume.isShowing()) {
                                        if (!((Activity) context).isFinishing()) {
                                            lDialogresume.dismiss();
                                            dismissMessage(activity, activityfinishflag);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });
            if (!((Activity) context).isFinishing()) {
                lDialogresume.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dismissMessage(final Activity activity, Boolean activityfinishflag) {
        // TODO Auto-generated method stub
        if (activityfinishflag) {
            activity.finish();
        }
    }


}
