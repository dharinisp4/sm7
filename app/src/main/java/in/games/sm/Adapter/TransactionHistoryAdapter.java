package in.games.sm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.sm.Model.TransactionHistoryObjects;
import in.games.sm.R;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private Context context;
    private Dialog dialog;
    private ArrayList<TransactionHistoryObjects> list;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionHistoryObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view= LayoutInflater.from(context).inflate(R.layout.transaction_history_layoout,null);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.ViewHolder viewHolder, int i) {

        TransactionHistoryObjects objects=list.get(i);

        viewHolder.txtId.setText(objects.getId());
        viewHolder.txtAmount.setText(objects.getAmt());

        viewHolder.txtStatus.setText("success");
        viewHolder.txtDate.setText(objects.getDate());
        String st=objects.getType().toString();
        if(st.equals("d"))
        {
            viewHolder.txtDescription.setText("Amount Debited For Bidding.\n Bid Id :#"+objects.getBid_id());
        }
        else
        {
            viewHolder.txtDescription.setText("Amount Credited For Bidding.\n Bid Id :#"+objects.getBid_id());
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId,txtAmount,txtDescription,txtStatus,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId=(TextView)itemView.findViewById(R.id.trans_id);
            txtAmount=(TextView)itemView.findViewById(R.id.trans_amount);
            txtDescription=(TextView)itemView.findViewById(R.id.description);
            txtStatus=(TextView)itemView.findViewById(R.id.status);
            txtDate=(TextView)itemView.findViewById(R.id.trans_date);
        }
    }
}
