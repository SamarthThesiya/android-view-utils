package customView.dependantHolders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class VuButton extends AppCompatButton implements View.OnClickListener {

    public DependantHandler dependantHandler;
    private OnClickListener onClickListener;
    public VuButton(@NonNull Context context) {
        super(context);
        init();
    }

    public VuButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VuButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dependantHandler = new DependantHandler();
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (dependantHandler.validateValidatables() && onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
