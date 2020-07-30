package com.even.sample.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.even.sample.R;

/**
 * Edit Table Fragment
 *
 * @author even.wu
 * @date 10/8/17
 */

public class EditTableFragment extends Fragment {
    @BindView(R.id.et_rows) EditText etRows;
    @BindView(R.id.et_cols) EditText etCols;

    private OnTableListener mOnTableListener;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_table, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.iv_back) void onClickBack() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    @OnClick(R.id.btn_ok) void onClickOK() {
        if (mOnTableListener != null) {
            mOnTableListener.onTableOK(Integer.parseInt(etRows.getText().toString()),
                Integer.parseInt(etCols.getText().toString()));
            onClickBack();
        }
    }

    public void setOnTableListener(OnTableListener mOnTableListener) {
        this.mOnTableListener = mOnTableListener;
    }

    public interface OnTableListener {
        void onTableOK(int rows, int cols);
    }
}
