package com.example.myhotel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotels;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Hotel hotel);
    }

    public HotelAdapter(List<Hotel> hotels, OnItemClickListener listener) {
        this.hotels = hotels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.bind(hotel, listener);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public void updateData(List<Hotel> hotels) {
        this.hotels = hotels;
        notifyDataSetChanged();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView locationTextView;
        private TextView ratingTextView;
        private TextView capacityTextView;
        private TextView priceTextView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            capacityTextView = itemView.findViewById(R.id.capacityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }

        public void bind(final Hotel hotel, final OnItemClickListener listener) {
            nameTextView.setText(hotel.getName());
            locationTextView.setText(hotel.getLocation());
            ratingTextView.setText(String.valueOf(hotel.getRating()));
            capacityTextView.setText(String.valueOf(hotel.getCapacity()));
            priceTextView.setText(String.valueOf(hotel.getPrice()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(hotel);
                }
            });
        }
    }
}