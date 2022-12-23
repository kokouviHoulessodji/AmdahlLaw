package com.amdahl.amdahllaw;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.Scanner;

public class Amdahl extends Application {
    @FXML
    private LineChart<?, ?> LineChart;
    @Override
    public void start(Stage stage) {

        draw();

        GridPane grid = new GridPane();
        grid.addColumn(0, LineChart);
        Group root = new Group(grid);
        root.setAutoSizeChildren(true);
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Speed up by number of process");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    /**
     * Draw Chart
     */
    public void draw()
    {
        int threads = 0;//nombre de threads
        double parallel = 0.0;//pourcentage de travail en parallèle
        System.out.println("################################################################");
        System.out.println("#########################LOI D'AMDAHL###########################");
        System.out.println("################################################################");
        System.out.println();

        Scanner in = new Scanner(System.in);

        do
        {
            System.out.println("Bonjour, veuillez rentrer les paramètres de calcul");
            System.out.print("Pourcentage de travail parallèle (chiffre rond > 0 && <= 100): ");
            parallel = in.nextDouble();

        }
        while (parallel <= 0 || parallel > 100);
        parallel /= 100.0;

        System.out.print("Nombre de fils de threads(processus) utilisés : ");
        threads = in.nextInt();

        //Defining the x-axis
        NumberAxis xAxis = new NumberAxis(0, threads+1, 1);
        xAxis.setLabel("Threads");

        //Defining the y-axis
        NumberAxis yAxis = new NumberAxis   (0, threads+1, 0.5);
        yAxis.setLabel("Speed up");

        //Creating the line chart
        LineChart = new LineChart(xAxis, yAxis);
        LineChart.setPrefSize(800, 700);

        //Prepare XYChart.Series objects by setting data

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(0, 0));
        for(int s = 1; s<=threads; s++)
        {
            //Speed up
            double speedup = 1/(1-parallel+(parallel/s));
            System.out.println("Thread " + s + " : " + " speed up : " + speedup);

            series.getData().add(new XYChart.Data(s, speedup));
        }

        series.setName("Speed up");

        //Setting the data to Line chart
        LineChart.getData().add(series);

    }
}