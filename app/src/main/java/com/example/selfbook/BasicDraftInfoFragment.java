package com.example.selfbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfbook.Data.userInfo;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.postUserAnswer;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicDraftInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicDraftInfoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
            //Log.d("sibal",this.userPurchaseInfo.getUserBookName());
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
        if(getView() == null) return;
        final EditText et_selfBookTitle = getView().findViewById(R.id.et_selfBookTitle);
        TextView tv_publishDate = getView().findViewById(R.id.tv_publishDate);
        TextView tv_pageNumber = getView().findViewById(R.id.tv_pageNumber);
        TextView tv_selfBookUserName = getView().findViewById(R.id.tv_selfBookUserName);
        ImageButton bt_uploadTitle = getView().findViewById(R.id.bt_uploadTitle);

        if(userPurchaseInfo != null) {
            et_selfBookTitle.setText(userPurchaseInfo.getUserBookName(), TextView.BufferType.EDITABLE);
            tv_publishDate.setText(userPurchaseInfo.getUserBookPublishDate());
            tv_selfBookUserName.setText(userPurchaseInfo.getUserName());
        }

        bt_uploadTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(getActivity() != null ) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if(imm != null) {
                            if(getActivity().getCurrentFocus() != null) {
                                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                userPurchaseInfo.setUserBookName(et_selfBookTitle.getText().toString());
                postUserAnswer postUserAnswer = new postUserAnswer(getContext(),userPurchaseInfo.getUserTemplateCode(),et_selfBookTitle.getText().toString()
                        ,userPurchaseInfo.getUserID(),et_selfBookTitle,"setBookTitle");
                postUserAnswer.execute(Api.POST_SETUSERANSWER);
            }
        });



    }

    @Override
    public void onClick(View v) {

    }
}
