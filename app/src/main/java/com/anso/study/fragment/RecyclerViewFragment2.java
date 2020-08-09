package com.anso.study.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anso.study.R;
import com.anso.study.view.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chidehang on 2020-01-12
 */
public class RecyclerViewFragment2 extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("viewpager",this.getClass().getName()+"--onCreateView");
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RecyclerAdapter adapter = new RecyclerAdapter(getData());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int THRESHOLD_LOAD_MORE = 3;
            private boolean hasLoadMore;

            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hasLoadMore = false;
                }

                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !hasLoadMore) {
                    int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int offset = recyclerView.getAdapter().getItemCount() - lastPosition - 1;
                    if (offset <= THRESHOLD_LOAD_MORE) {
                        hasLoadMore = true;
                        adapter.data.addAll(getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }

    private int i = 0;

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for(int tempI = i; i < tempI + 10; i ++) {
            data.add("ChildView item " + i);
        }
        return data;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("viewpager",this.getClass().getName()+"--onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("viewpager",this.getClass().getName()+"--onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("viewpager",this.getClass().getName()+"--onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("viewpager",this.getClass().getName()+"--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("viewpager",this.getClass().getName()+"--onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("viewpager",this.getClass().getName()+"--onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("viewpager",this.getClass().getName()+"--onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("viewpager",this.getClass().getName()+"--onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("viewpager",this.getClass().getName()+"--onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("viewpager",this.getClass().getName()+"--onDetach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("viewpager",this.getClass().getName()+"--onActivityCreated");
    }
}
