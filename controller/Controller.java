package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.Model;

import java.util.ArrayList;

public class Controller {

    private Model model;
    private Model model2;
    private Model model3;
    private Model model4;


    @FXML
    LineChart<Integer,Double> chart1;

    @FXML
    LineChart<Integer,Double> chart2;

    @FXML
    LineChart<Integer,Double> chart3;

    @FXML
    LineChart<Integer,Double> chart4;

    @FXML
    LineChart<Integer,Double> chart5;

    @FXML
    LineChart<Integer,Double> chart6;

    @FXML
    LineChart<Integer,Double> chart7;

    @FXML
    LineChart<Integer,Double> chart8;


    @FXML
    public void initialize()
    {
        int steps=50;
        this.model=new Model();
        model.setCoolingSpeed(0);
        //model.setSize(20);
        chart1.getXAxis().setLabel("Steps");
        chart1.getYAxis().setLabel("Amount of carbon [%]");
        chart1.setTitle("No cooling temp 800");
        chart2.setTitle("Ferrite amount no cooling temp 800");
        chart2.getXAxis().setLabel("Steps");
        chart2.getYAxis().setLabel("Amount of ferrite [%]");
        XYChart.Series<Integer,Double> series2 = new XYChart.Series<Integer,Double>();
        for(int i=0;i<steps;i++) {
            this.model.step();
            XYChart.Series<Integer,Double> series = new XYChart.Series<Integer,Double>();
            for(int j=0;j<model.getSize();j++)
            {
                series.getData().add(new XYChart.Data<>(j,model.getTable().get(i).get(j)*100));
            }
            series2.getData().add(new XYChart.Data<>(i,100*countFerrite(model.getTable().get(i))));
            series.setName("step "+i);
            series2.setName("No cooling");
            if(i%5==0) {
                chart1.getData().add(series);
            }
        }
        chart2.getData().add(series2);


        this.model2=new Model();
        model2.setCoolingSpeed(0.5);
        chart3.getXAxis().setLabel("Steps");
        chart3.getYAxis().setLabel("Amount of carbon [%]");
        chart3.setTitle("Cooling 0.5 temp 800");
        chart4.setTitle("Ferrite amount temp 800");
        chart4.getXAxis().setLabel("Steps");
        chart4.getYAxis().setLabel("Amount of ferrite [%]");
        XYChart.Series<Integer,Double> series4 = new XYChart.Series<Integer,Double>();
        for(int i=0;i<steps;i++) {
            this.model2.step();
            XYChart.Series<Integer,Double> series = new XYChart.Series<Integer,Double>();
            for(int j=0;j<model2.getSize();j++)
            {
                series.getData().add(new XYChart.Data<>(j,model2.getTable().get(i).get(j)*100));
            }
            series4.getData().add(new XYChart.Data<>(i,100*countFerrite(model2.getTable().get(i))));
            series.setName("step "+i);
            series4.setName("Cooling 0.5");
            if(i%5==0) {
                chart3.getData().add(series);
            }
        }
        chart4.getData().add(series4);

        this.model3=new Model();
        model3.setCoolingSpeed(2.0);
        chart5.getXAxis().setLabel("Steps");
        chart5.getYAxis().setLabel("Amount of carbon [%]");
        chart5.setTitle("Cooling 2.0 temp 800");
        chart6.setTitle("Ferrite amount temp 800");
        chart6.getXAxis().setLabel("Steps");
        chart6.getYAxis().setLabel("Amount of ferrite [%]");
        XYChart.Series<Integer,Double> series6 = new XYChart.Series<Integer,Double>();
        for(int i=0;i<steps;i++) {
            this.model3.step();
            XYChart.Series<Integer,Double> series = new XYChart.Series<Integer,Double>();
            for(int j=0;j<model3.getSize();j++)
            {
                series.getData().add(new XYChart.Data<>(j,model3.getTable().get(i).get(j)*100));
            }
            series6.getData().add(new XYChart.Data<>(i,100*countFerrite(model3.getTable().get(i))));
            series.setName("step "+i);
            series6.setName("Cooling 2.0");
            if(i%5==0) {
                chart5.getData().add(series);
            }
        }
        chart6.getData().add(series6);

        this.model4=new Model();
        model4.setCoolingSpeed(3.0);
        chart7.getXAxis().setLabel("Steps");
        chart7.getYAxis().setLabel("Amount of carbon [%]");
        chart7.setTitle("Cooling 3.0 temp 800");
        chart8.setTitle("Ferrite amount temp 800");
        chart8.getXAxis().setLabel("Steps");
        chart8.getYAxis().setLabel("Amount of ferrite [%]");
        XYChart.Series<Integer,Double> series8 = new XYChart.Series<Integer,Double>();
        for(int i=0;i<steps;i++) {
            this.model4.step();
            XYChart.Series<Integer,Double> series = new XYChart.Series<Integer,Double>();
            for(int j=0;j<model4.getSize();j++)
            {
                series.getData().add(new XYChart.Data<>(j,model4.getTable().get(i).get(j)*100));
            }
            series8.getData().add(new XYChart.Data<>(i,100*countFerrite(model4.getTable().get(i))));
            series.setName("step "+i);
            series8.setName("Cooling 3.0");
            if(i%5==0) {
                chart7.getData().add(series);
            }
        }
        chart8.getData().add(series8);



    }


    private double countFerrite(ArrayList<Double> list)
    {
        double percentage=0.0;
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i)==0.02)
            {
                percentage++;
            }
        }
        return percentage/list.size();
    }
}
