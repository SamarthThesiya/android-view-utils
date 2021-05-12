package interfaces;

import androidx.annotation.NonNull;

import customView.VuRecyclerView;


public interface RecyclerViewListener {
    void onBindViewHolder(@NonNull VuRecyclerView.VuRecyclerViewAdaptor.VuViewHolder holder, int position);
}
