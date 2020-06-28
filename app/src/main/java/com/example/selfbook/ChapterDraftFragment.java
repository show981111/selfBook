package com.example.selfbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selfbook.Data.userInfo;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchChapterList;
import com.example.selfbook.getData.fetchTemplateContent;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChapterDraftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterDraftFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private userInfo userPurchaseInfo;
    private RecyclerView rv_chapterList;

    public ChapterDraftFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChapterDraftFragment newInstance(userInfo userPurchaseInfo) {
        ChapterDraftFragment fragment = new ChapterDraftFragment();

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
        View v = inflater.inflate(R.layout.fragment_chapter_draft, container, false);
        rv_chapterList = v.findViewById(R.id.rv_chapter);
        Log.d("chapAdaterONCREATEV", "CALLED");
//        fetchTemplateContent fetchTemplateContent = new fetchTemplateContent(userPurchaseInfo.getUserID() ,
//                userPurchaseInfo.getUserTemplateCode(), getActivity() , rv_chapterList );
//        fetchTemplateContent.execute(Api.GET_getTemplateContent);
        fetchChapterList fetchChapterList = new fetchChapterList(userPurchaseInfo.getUserID() ,
               userPurchaseInfo.getUserTemplateCode(), getActivity() , rv_chapterList );
        fetchChapterList.execute(Api.GET_CHAPTERLIST);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("chapAdap", "resume");
        if(rv_chapterList != null) {
            if(rv_chapterList.getAdapter() != null) {
                rv_chapterList.getAdapter().notifyDataSetChanged();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        }

    }
}
