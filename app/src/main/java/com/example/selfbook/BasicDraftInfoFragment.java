package com.example.selfbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.selfbook.Data.userInfo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicDraftInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicDraftInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private userInfo userPurchaseInfo;

//    public BasicDraftInfoFragment(userInfo userPurchaseInfo) {
//        this.userPurchaseInfo = userPurchaseInfo;
//        // Required empty public constructor
//    }

    public BasicDraftInfoFragment() {
    }

    public static BasicDraftInfoFragment newInstance(userInfo userPurchaseInfo) {
        BasicDraftInfoFragment fragment = new BasicDraftInfoFragment();

        Bundle args = new Bundle();
        args.putParcelable("userPurchaseInfo", userPurchaseInfo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.userPurchaseInfo = getArguments().getParcelable("userPurchaseInfo");
            Log.d("sibal",this.userPurchaseInfo.getUserBookName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_draft_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //et_selfBookTitle
        //tv_publishDate
        //tv_pageNumber
        //tv_selfBookUserName
        EditText et_selfBookTitle = getView().findViewById(R.id.et_selfBookTitle);
        TextView tv_publishDate = getView().findViewById(R.id.tv_publishDate);
        TextView tv_pageNumber = getView().findViewById(R.id.tv_pageNumber);
        TextView tv_selfBookUserName = getView().findViewById(R.id.tv_selfBookUserName);

        if(userPurchaseInfo != null) {
            et_selfBookTitle.setText(userPurchaseInfo.getUserBookName(), TextView.BufferType.EDITABLE);
            tv_publishDate.setText(userPurchaseInfo.getUserBookPublishDate());
            tv_selfBookUserName.setText(userPurchaseInfo.getUserName());
        }
    }
}
