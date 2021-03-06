package com.example.cultureevents.ui.calendar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultureevents.R;

import java.util.List;

/**
 * Event adapter used in [CalendarFragment].
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<String> name;
    private List<String> date;
    private List<String> time;
    private List<String> description;

    public EventsAdapter(List<String> name, List<String> date, List<String> time, List<String> description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.eventcard, null);
        return new EventsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.MyViewHolder holder, int position) {
        holder.title.setText(String.valueOf(name.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        holder.description.setText(String.valueOf(description.get(position)));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView time;
        private TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.eventDate);
            time = itemView.findViewById(R.id.eventTime);
            description = itemView.findViewById(R.id.eventDescription);
        }
    }
}