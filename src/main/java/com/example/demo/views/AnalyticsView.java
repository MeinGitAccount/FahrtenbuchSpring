package com.example.demo.views;

import com.example.demo.model.Fahrt;
import com.example.demo.service.FahrtService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import lombok.Getter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@View
public class AnalyticsView {

    @Autowired
    FahrtService fahrtService;
    List<Fahrt> fahrten;
    @Getter
    LineChartModel lineModel;
    LocalDate end = LocalDate.now().plusYears(1);
    LocalDate beginning = LocalDate.now().minusYears(1);
    @PostConstruct
    public void createLineModel() {
        fahrten = fahrtService.findAll();

        lineModel = new LineChartModel();

        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        List<String> labels = new ArrayList<>();

        //dataSet.setData(fahrten.stream().filter(f -> (f.getDate().isBefore(end) || f.getDate().isEqual(end)) && (f.getDate().isAfter(beginning) || f.getDate().isEqual(beginning))).map(Fahrt::getRiddenKM).collect(Collectors.toList()));
        LocalDate start = beginning;


        while(start.isBefore(end)) {
            labels.add(start.getYear() + start.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN));
            start = start.plusMonths(1);
        }
/*
        fahrten.forEach(f -> {


        });
*/
        dataSet.setFill(false);
        dataSet.setLabel("KM pro Monat");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(100);

        data.addChartDataSet(dataSet);
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);

        Title subtitle = new Title();
        subtitle.setDisplay(true);
        subtitle.setText("Line Chart Subtitle");
        options.setSubtitle(subtitle);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }
}
