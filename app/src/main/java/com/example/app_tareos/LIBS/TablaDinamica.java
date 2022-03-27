package com.example.app_tareos.LIBS;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class TablaDinamica {
    private TableLayout tableLayout;
    private Context context;
    private String [] header;
    private List<String[]> data;
    private boolean multicolor = false;
    int firColor, secondColor;


    private TableRow tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;

    public TablaDinamica(TableLayout tableLayout, Context context) {
        this.tableLayout=tableLayout;
        this.context = context;
    }

    //recibir los datos que van a ir dentro de la tabla
    public void addHeader(String [] header){

        this.header=header;
        createHeader();
    }

    public void addData(List<String[]>data){
        //data.clear();
        this.data=data;
        createDataTable();
    }

    private void newRow(){

        tableRow = new TableRow(context);
    }

    private void newCell(){
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(15);
    }

    private void createHeader(){
        indexC = 0;
        newRow();
        while (indexC<header.length){
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable(){
        //tableLayout.removeView(tableRow);
        String info;
        for(indexR=1;indexR<=data.size();indexR++){
            newRow();
            for(indexC=0;indexC<header.length;indexC++){
                newCell();
                String [] row = data.get(indexR-1);
                info = (indexC<row.length)?row[indexC]:"";
                txtCell.setText(info);
                txtCell.setPadding(10,5,10,5);
                tableRow.addView(txtCell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }

    public void addItems (String[]item){
        String info;
        data.add(item);
        indexC = 0;
        newRow();
        while (indexC<header.length){
            newCell();
            info=(indexC<item.length)?item[indexC++]:"";
            txtCell.setText(info);
            txtCell.setPadding(10,5,10,5);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow,data.size()-1);//
        redColoring();
    }

    public void backgroundHeader(int color){
        indexC = 0;
        while (indexC<header.length){
            txtCell = getCell(0,indexC++);
            txtCell.setBackgroundColor(color);
        }
        //tableLayout.addView((tableRow));
    }

    public void backgroundData(int firColor, int secondColor){
        for(indexR=1;indexR<=data.size();indexR++){
            multicolor=!multicolor;
            for(indexC=0;indexC<header.length;indexC++){
                txtCell = getCell(indexR,indexC);
                txtCell.setBackgroundColor((multicolor)?firColor:secondColor);
            }
        }
        this.firColor= firColor;
        this.secondColor=secondColor;
    }

    public void lineColor(int color){
        indexR = 0;
        while (indexR<=data.size()){
            getRow(indexR++).setBackgroundColor(color);
        }
    }

    public void textColorData(int color){
        for(indexR=1;indexR<=data.size();indexR++){
            for(indexC=0;indexC<header.length;indexC++)
                getCell(indexR,indexC).setTextColor(color);
        }
    }

    public void textColorHeader(int color){
        indexC=0;
        while (indexC<header.length){
            getCell(0,indexC++).setTextColor(color);
        }
    }

    public void redColoring(){
        indexC = 0;
        multicolor=!multicolor;
        while (indexC<header.length){
            txtCell = getCell(data.size()-1,indexC++);
            txtCell.setBackgroundColor((multicolor)?firColor:secondColor);
        }
    }

    private TableRow getRow(int index){
        return (TableRow)tableLayout.getChildAt(index);
    }

    private TextView getCell(int rowIndex, int columIndex){
        tableRow = getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columIndex);
    }

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(4,4,4,4);

        params.weight=4;
        return params;
    }
}