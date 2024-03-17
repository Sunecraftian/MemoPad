package edu.jsu.mcis.cs408.memopad;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.jsu.mcis.cs408.memopad.databinding.MemoItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Memo> data;
    public Memo selectedMemo = null;
    public int selectedPos = RecyclerView.NO_POSITION;

    public RecyclerViewAdapter(List<Memo> data) { this.data = data; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MemoItemBinding binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();

        holder.itemView.setBackgroundColor(selectedPos == position ? Color.LTGRAY : Color.TRANSPARENT);

        holder.itemView.setOnClickListener(v -> {
            selectedMemo = holder.memo;

            notifyItemChanged(selectedPos);
            selectedPos = holder.getAdapterPosition();
            notifyItemChanged(selectedPos);
        });

    }



    @Override
    public int getItemCount() { return data.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Memo memo;

        public ViewHolder(View itemView) { super(itemView); }

        public void setMemo(Memo memo) { this.memo = memo; }

        public void bindData() {
            TextView nameLabel = (TextView) itemView.findViewById(R.id.memoNameLabel);

            nameLabel.setText((memo.getName()));
        }
    }
}
