package in.mynewcar.trivzcalender;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class BlockCalendar extends AppCompatActivity {

    CalendarPickerView calendar_view;
    String dataSet = "";
    boolean isConnected;
    TextView time_block_text;
    BetterSpinner block_type;
    Switch time_block_switch;
    EditText from_time, till_time;
    LinearLayout time_block_linear;
    String[] dates = new String[20];
    int k = 0;
    //    private static final String[] blockTypeString = new String[]{"Block multiple dates", "Block consecutive dates", "Block single date"};
    Context context;
    String[] blockTypeString;
//    Animation animZoomIn, animZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'><small>Block Calendar</small></font>"));
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_block_calendar);
        getSupportActionBar().hide();

        context = BlockCalendar.this;
        blockTypeString = context.getResources().getStringArray(R.array.blockTypeString);

     final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        calendar_view = (CalendarPickerView) findViewById(R.id.calendar_view);
        block_type = (BetterSpinner) findViewById(R.id.block_type);
        time_block_switch = (Switch) findViewById(R.id.time_block_switch);
        time_block_linear = (LinearLayout) findViewById(R.id.time_block_linear);
        till_time = (EditText) findViewById(R.id.till_time);
        from_time = (EditText) findViewById(R.id.from_time);
        time_block_text = (TextView) findViewById(R.id.time_block_text);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);
        Date today = new Date();

//        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoom_in);
//        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoom_out);

        time_block_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (time_block_switch.isChecked()) {
                    time_block_linear.setVisibility(View.VISIBLE);
//                    time_block_linear.startAnimation(animZoomIn);
                } else {
                    time_block_linear.setVisibility(View.GONE);
//                    time_block_linear.startAnimation(animZoomOut);
                }

            }
        });
//        String CAL_URL = "http://images.mynewcar.in/MNC-TESTDRIVE/api_2/confirmed_data/format/json";

        HashMap<String, String> confirmedDataParams = new HashMap<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        String formatMonth = df.format(c.getTime());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
        String formatYear = df1.format(c.getTime());




        calendar_view.setOnInvalidDateSelectedListener(null);
        calendar_view.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                boolean isSelectable = true;
                String dateBlocked = formatter.format(date);
                for (String date1 : dates) {
                    if (dateBlocked.equalsIgnoreCase(date1)) {
                        isSelectable = false;
                    }
                }
                return isSelectable;
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date tomorrow = calendar.getTime();

        calendar_view.init(today, nextYear.getTime()).withConfirmedDate(tomorrow).inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        block_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                Calendar nextYear = Calendar.getInstance();
                nextYear.add(Calendar.MONTH, 1);
                Date today = new Date();
                if (type.equalsIgnoreCase(context.getResources().getString(R.string.block_multiple_dates))) {
                    calendar_view.setOnInvalidDateSelectedListener(null);
                    calendar_view.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
                        @Override
                        public boolean isDateSelectable(Date date) {
                            boolean isSelectable = true;
                            String dateBlocked = formatter.format(date);
                            for (String date1 : dates) {
                                if (dateBlocked.equalsIgnoreCase(date1)) {
                                    isSelectable = false;
                                }
                            }
                            return isSelectable;
                        }
                    });
                    calendar_view.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.MULTIPLE);
                } else if (type.equalsIgnoreCase(context.getResources().getString(R.string.block_single_date))) {
                    calendar_view.setOnInvalidDateSelectedListener(null);
                    calendar_view.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
                        @Override
                        public boolean isDateSelectable(Date date) {
                            boolean isSelectable = true;
                            String dateBlocked = formatter.format(date);
                            for (String date1 : dates) {
                                if (dateBlocked.equalsIgnoreCase(date1)) {
                                    isSelectable = false;
                                }
                            }
                            return isSelectable;
                        }
                    });
                    calendar_view.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.SINGLE);
                } else if (type.equalsIgnoreCase(context.getResources().getString(R.string.block_consecutive_dates))) {
                    calendar_view.setOnInvalidDateSelectedListener(null);
                    calendar_view.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
                        @Override
                        public boolean isDateSelectable(Date date) {
                            boolean isSelectable = true;
                            String dateBlocked = formatter.format(date);
                            for (String date1 : dates) {
                                if (dateBlocked.equalsIgnoreCase(date1)) {
                                    isSelectable = false;
                                }
                            }
                            return isSelectable;
                        }
                    });
                    calendar_view.init(today, nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);
                }
            }
        });
        //getting current


        //action while clicking on a date
        calendar_view.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                String dateBlocked = formatter.format(date);
                for (String date1 : dates) {
                    if (dateBlocked.equalsIgnoreCase(date1)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BlockCalendar.this);
                        builder.setCancelable(true);

                        builder.setMessage(R.string.date_is_blocked_message);

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        final AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {
//                Toast.makeText(getApplicationContext(), "UnSelected Date is : " + date.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_show_dates = (Button) findViewById(R.id.btn_show_dates);
//        final SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-mm-dd");

        final int mHour = 8;
        final int mMinute = 0;

        from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BlockCalendar.this, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (!(hourOfDay < 8 || hourOfDay > 21)) {
//                                    from_text.setErrorEnabled(false);
                                    String time = hourOfDay + ":" + minute + ":00";
                                    from_time.setText(time);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BlockCalendar.this);
                                    builder.setCancelable(true);

                                    builder.setMessage("Select Time");

                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    final AlertDialog alert = builder.create();
                                    alert.show();
                                }

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        till_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BlockCalendar.this, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (!(hourOfDay < 8 || hourOfDay > 21)) {
//                                    till_text.setErrorEnabled(false);
                                    String time = hourOfDay + ":" + minute + ":00";
                                    till_time.setText(time);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BlockCalendar.this);
                                    builder.setCancelable(true);

                                    builder.setMessage("Select Time");

                                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    final AlertDialog alert = builder.create();
                                    alert.show();
                                }

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });


    }

//    private void block_user_dates() {
//
////        String CAL_URL = "http://images.mynewcar.in/MNC-TESTDRIVE/api_2/confirmed_data/format/json";
////
////        StringRequest calRequest = new StringRequest(Request.Method.POST, CAL_URL,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////
////                        try {
////                            JSONObject jsonObject = new JSONObject(response);
////
////                            if (jsonObject != null) {
////
////                                JSONArray block_jsonArray = jsonObject.getJSONArray("block");
////                                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////                                if (block_jsonArray != null) {
////                                    for (int i = 0; i < block_jsonArray.length(); i++) {
////                                        JSONObject block_jsonObject = (JSONObject) block_jsonArray.get(i);
////                                        final String dates = block_jsonObject.getString("date");
//////                                        dates[k++] = block_jsonObject.getString("date");
////                                        calendar_view.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
////
////                                            @Override
////                                            public boolean isDateSelectable(Date date) {
////                                                boolean isSelecteable = true;
////
////                                                String datte = formatter.format(date);
////                                                if (datte.equalsIgnoreCase(dates)) {
////                                                    isSelecteable = false;
////                                                }
//////                                                int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
//////
//////                                                //disable if weekend
//////                                                if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
//////                                                    isSelecteable=false;
//////                                                }
////                                                return isSelecteable;
////                                            }
////                                        });
////                                        calendar_view.setOnInvalidDateSelectedListener(null);
////
////                                    }
////
////                                }
////                            }
////
////
////                        } catch (Exception e) {
//////                            Toast.makeText(OwnerMain.this, "Error occured " + e.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    }
////
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
//////                        Toast.makeText(OwnerMain.this, "E3 on " + error.toString(), Toast.LENGTH_LONG).show();
////                    }
////
////                }) {
////            @Override
////            protected Map<String, String> getParams() {
////                Map<String, String> params = new HashMap<String, String>();
////                Calendar c = Calendar.getInstance();
////                SimpleDateFormat df = new SimpleDateFormat("MM");
////                String formatMonth = df.format(c.getTime());
////
////                SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
////                String formatYear = df1.format(c.getTime());
////
////                params.put("owner_id", sessionManagementOwner.getUserDetails().get(KEY_ID));
////                params.put("month", formatMonth);
////                params.put("year", formatYear);
////
////                return params;
////            }
////
////        };
////
////        calRequest.setRetryPolicy(new DefaultRetryPolicy(
////                300000,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////
////        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
////        requestQueue1.add(calRequest);
//    }







}
