package com.roshane.tickets.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshane.tickets.R;
import com.roshane.tickets.model.Ticket;

import java.util.List;

/**
 * Created by roshanedesilva on 7/7/17.
 */

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {
    private Activity activity;
    private List<Ticket> ticketList;

    public TicketListAdapter(Activity activity, List<Ticket> ticketList) {
        this.activity = activity;
        this.ticketList = ticketList;
    }


    @Override
    public TicketListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TicketListAdapter.ViewHolder holder, int position) {
        final Ticket item = ticketList.get(position);

        holder.subject.setText(item.getSubject());
        holder.type.setText(item.getType());
        holder.priority.setText(item.getPriority());
        holder.status.setText(item.getStatus());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView subject;
        public final TextView type;
        public final TextView priority;
        public final TextView status;

        public ViewHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.txt_subject);
            type = (TextView) itemView.findViewById(R.id.txt_type);
            priority = (TextView) itemView.findViewById(R.id.txt_priority);
            status = (TextView) itemView.findViewById(R.id.txt_status);

        }
    }


    @Override
    public int getItemCount() {
        return ticketList.size();
    }


}
