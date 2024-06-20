package com.project_files;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Business_App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Charts.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Analytics");
        stage.setScene(scene);
        stage.show();

        Business_Controller controller = fxmlLoader.getController();
        BarChart<String, Number> ordersChart = controller.ordersBarChart;
        BarChart<String, Number> revenueChart = controller.revenueBarChart;
        // SQL
        ArrayList<Integer> orders = controller.getOrdersByDay();
        ArrayList<Integer> revenue = controller.getRevenueByDay();
        ArrayList<Float> percentages = controller.getItemsOrdered();
        // Orders
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Orders");
        // Orders Data
        series.getData().add(new XYChart.Data<>("Monday", orders.get(1)));
        series.getData().add(new XYChart.Data<>("Tuesday", orders.get(2)));
        series.getData().add(new XYChart.Data<>("Wednesday", orders.get(3)));
        series.getData().add(new XYChart.Data<>("Thursday", orders.get(4)));
        series.getData().add(new XYChart.Data<>("Friday", orders.get(5)));
        series.getData().add(new XYChart.Data<>("Saturday", orders.get(6)));
        series.getData().add(new XYChart.Data<>("Sunday", orders.get(0)));
        ordersChart.getData().add(series);
        for (int i = 0; i < series.getData().size(); i++) {
            XYChart.Data<String, Number> data = series.getData().get(i);
            Node bar = data.getNode();
            // Color
            if (i % 2 == 0) {
                bar.setStyle("-fx-bar-fill: green;");
            } else {
                bar.setStyle("-fx-bar-fill: gold;");
            }
        }

        // Add to ordersChart
        CategoryAxis xAxis = (CategoryAxis) ordersChart.getXAxis();
        xAxis.setTickMarkVisible(true);
        xAxis.setAnimated(false);
        xAxis.setCategories(FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        ));
        ordersChart.setLegendVisible(false);
        xAxis.setAutoRanging(true);


        // Revenue
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Revenue");
        // Revenue Data
        series2.getData().add(new XYChart.Data<>("Monday", revenue.get(1)));
        series2.getData().add(new XYChart.Data<>("Tuesday", revenue.get(2)));
        series2.getData().add(new XYChart.Data<>("Wednesday", revenue.get(3)));
        series2.getData().add(new XYChart.Data<>("Thursday", revenue.get(4)));
        series2.getData().add(new XYChart.Data<>("Friday", revenue.get(5)));
        series2.getData().add(new XYChart.Data<>("Saturday", revenue.get(6)));
        series2.getData().add(new XYChart.Data<>("Sunday", revenue.get(0)));
        revenueChart.getData().add(series2);
        for (int i = 0; i < series2.getData().size(); i++) {
            XYChart.Data<String, Number> data = series2.getData().get(i);
            Node bar = data.getNode();
            if (i % 2 == 0) {
                bar.setStyle("-fx-bar-fill: purple;");
            } else {
                bar.setStyle("-fx-bar-fill: blue;");
            }
        }
        // Add info to revenueChart
        CategoryAxis xAxis2 = (CategoryAxis) revenueChart.getXAxis();
        xAxis2.setTickMarkVisible(true);
        xAxis2.setAnimated(false);

        xAxis2.setCategories(FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        ));
        revenueChart.setLegendVisible(false);
        xAxis2.setAutoRanging(true);

        //Pie Chart
        PieChart foodChart = controller.pieChart;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Espresso", percentages.get(0) * 100),
                new PieChart.Data("Americano", percentages.get(1) * 100),
                new PieChart.Data("Macchiato", percentages.get(2) * 100),
                new PieChart.Data("Latte", percentages.get(3) * 100),
                new PieChart.Data("Iced Coffee", percentages.get(4) * 100),
                new PieChart.Data("Sandwich", percentages.get(5) * 100),
                new PieChart.Data("Muffin", percentages.get(6) * 100),
                new PieChart.Data("Donut", percentages.get(7) * 100),
                new PieChart.Data("Bagel", percentages.get(8) * 100),
                new PieChart.Data("Toast", percentages.get(9) * 100)
        );
        foodChart.setData(pieChartData);
    }

    public static void main(String[] args) {
        launch();
    }
}
