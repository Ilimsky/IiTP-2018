package com.example.iitp_2018;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    private List<Answer> mList = new ArrayList<Answer>();
    String[] answersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //layoutManager = new LinearLayoutManager(this);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setLayoutManager(layoutManager);

        setData();
    }

    public void setData() {
        answersArray = getResources().getStringArray(R.array.answers);
        for (int i = 0; i < answersArray.length; i++) {
            Answer data = new Answer();
            data.setAnswersText(answersArray[i]);
            mList.add(data);
        }

        recyclerAdapter.setArray(mList);
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setQueryHint("Search Country ...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        if (mList == null) {
                        } else {
                            recyclerAdapter.setFilter(mList);
                        }
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mList == null) {
            return false;
        } else {
            int textlength = newText.length();
            final List<Answer> filteredModelList = filter(mList, newText);
            recyclerAdapter.setFilter(filteredModelList);

            if (textlength == 0) {
                recyclerAdapter.setDataSearch(null);
            } else {
                String searchData = newText.toString().toLowerCase();
                recyclerAdapter.setDataSearch(searchData);
            }
            return true;
        }
    }

    private List<Answer> filter(List<Answer> models, String query) {
        query = query.toLowerCase();
        final List<Answer> filteredModelList = new ArrayList<>();
        for (Answer model : models) {
            final String text = model.getAnswersText().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_pdf:
                Intent intent = new Intent(MainActivity.this, PDFActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
