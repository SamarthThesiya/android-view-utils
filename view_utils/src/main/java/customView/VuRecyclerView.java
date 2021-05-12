package customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import interfaces.RecyclerViewListener;
import utils.ViewUtils;

public class VuRecyclerView extends RecyclerView {

    Integer              layout;
    Integer              listSize;
    RecyclerViewListener recyclerViewListener;

    public VuRecyclerView(@NonNull Context context) {
        super(context);
    }

    public VuRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VuRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(int layout, Integer listSize, Context context, RecyclerViewListener recyclerViewListener) {
        this.layout               = layout;
        this.listSize             = listSize;
        this.recyclerViewListener = recyclerViewListener;

        VuRecyclerViewAdaptor vuRecyclerViewAdaptor = new VuRecyclerViewAdaptor();
        setAdapter(vuRecyclerViewAdaptor);
        setLayoutManager(new LinearLayoutManager(context));
        vuRecyclerViewAdaptor.notifyDataSetChanged();
    }

    public class VuRecyclerViewAdaptor extends Adapter<VuRecyclerViewAdaptor.VuViewHolder> {

        @NonNull
        @Override
        public VuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewDataBinding binding = ViewUtils.getDataBinding(layout, parent);
            if (binding != null) return new VuViewHolder(binding.getRoot(), binding);

            View listItem = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new VuViewHolder(listItem, null);
        }

        @Override
        public void onBindViewHolder(@NonNull VuViewHolder holder, int position) {
            recyclerViewListener.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return listSize;
        }

        public class VuViewHolder extends ViewHolder {

            public ViewDataBinding binding;
            public VuViewHolder(@NonNull View itemView, ViewDataBinding binding) {
                super(itemView);
                this.binding = binding;
            }
        }
    }
}
