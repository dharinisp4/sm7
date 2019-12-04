package in.games.sm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.games.sm.Model.Starline_Objects;
import in.games.sm.R;

public class PGAdapter extends BaseAdapter {


    Context context;
    private ArrayList<Starline_Objects> list;
    private String m_id;
    public static int flg=0;

    public PGAdapter(Context context, ArrayList<Starline_Objects> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view= LayoutInflater.from(context).inflate(R.layout.play_games_items_layout,null);
        TextView txtNumber=(TextView)view.findViewById(R.id.pg_Number);
        TextView  txtTime=(TextView)view.findViewById(R.id.pg_Time);
        TextView  txtId=(TextView)view.findViewById(R.id.pg_title);
        ImageView img=(ImageView)view.findViewById(R.id.pg_image);

        Starline_Objects postion=list.get(position);


        //viewHolder.txtId.setText(""+postion.getId());


        txtTime.setText(""+postion.getS_game_time());
        //boolean sTime=getTimeStatus(String.valueOf(postion.getS_game_time()));
        int sTime=getTimeFormatStatus(String.valueOf(postion.getS_game_time())); Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
        String ddt=simpleDateFormat.format(date);
        int c_tm=Integer.parseInt(ddt);

        if(sTime>c_tm)
        {
            txtId.setText("Betting Is Running For Today");
            txtNumber.setText("***-**");

            txtId.setTextColor(Color.parseColor("#316D35"));
            //txtStatus.setText("o");

        }
        else
        {
            txtId.setText("Betting Closed For Today");

            txtId.setTextColor(Color.parseColor("#FFA44546"));
            txtNumber.setText(""+postion.getS_game_number());
            img.setVisibility(View.INVISIBLE);
            //txtStatus.setText("c");
        }


        img.setImageResource(R.drawable.pll);
        return view;
    }

    public int getTimeFormatStatus(String time)
    {
        //02:00 PM;
        String t[]=time.split(" ");
        String time_type=t[1].toString();
        String t1[]=t[0].split(":");
        int tm=Integer.parseInt(t1[0].toString());

        if(time_type.equals("AM"))
        {

        }
        else
        {
            if(tm==12)
            {

            }
            else
            {
                tm=12+tm;
            }
        }
        return tm;

    }


}
