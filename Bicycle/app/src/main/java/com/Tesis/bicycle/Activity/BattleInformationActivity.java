package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Tesis.bicycle.R;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

public class BattleInformationActivity extends AppCompatActivity {

    private TableView tableView;
    private TableColumnWeightModel columnModel;

    private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_information);
        init();

    }

    private void init(){
        tableView = (TableView) findViewById(R.id.tableView);
        tableView.setColumnCount(4);
        columnModel = new TableColumnWeightModel(4);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);

        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
    }
}