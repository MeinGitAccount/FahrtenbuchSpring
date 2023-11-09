package com.example.demo.views;

import com.example.demo.model.Fahrt;
import com.example.demo.service.FahrtService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
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

    @Getter
    @Setter
    LocalDate end;

    @Getter
    @Setter
    LocalDate beginning;

    @Getter
    @Setter
    String choice;
    @PostConstruct
    public void createLineModel() {
        initSelections();
        initFahrten();
        initChart();
    }

    private void initSelections() {
        beginning = LocalDate.now().minusMonths(6);
        end = LocalDate.now().plusMonths(6);
        choice = "Monat";
    }

    public void reload() {
        initFahrten();
        initChart();
    }

    private void initFahrten() {
        fahrten = fahrtService.findAll();
    }
    public void initChart() {
        lineModel = new LineChartModel();
        lineModel.setOptions(initOptions());
        lineModel.setData(initData());
    }

    private LineChartOptions initOptions() {

        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);

        //Title
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Kilometer pro " + choice);
        options.setTitle(title);

        //Subtitle
        Title subtitle = new Title();
        subtitle.setDisplay(true);
        if(choice.equals("Monat")) {
            subtitle.setText(
                    beginning.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)
                            + " " + beginning.getYear() + " bis " +
                            end.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN)
                            + " " + end.getYear()
            );
        }
        else if(choice.equals("Jahr")) {
            subtitle.setText(beginning.getYear() + " bis " + end.getYear());
        }
        else throw new IllegalStateException("Choice not known - neitehr Monat nor Jahr");

        options.setSubtitle(subtitle);

        return options;
    }

    private ChartData initData() {
        //Data
        LineChartDataSet dataSet = new LineChartDataSet();

        List<String> labels = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        LocalDate start = beginning;
        if(choice.equals("Monat")) {
            while (!start.isAfter(end)) {
                Month month = start.getMonth();
                labels.add(start.getYear() + " " + month.getDisplayName(TextStyle.FULL, Locale.GERMAN));
                values.add(
                        fahrten.stream()
                                .filter(f -> f.getDate().getMonth().compareTo(month) == 0)
                                .mapToInt(Fahrt::getRiddenKM).sum()
                );
                start = start.plusMonths(1);
            }
        }

        else if(choice.equals("Jahr")) {
            while (start.getYear() <= end.getYear()) {
                int year = start.getYear();
                labels.add(String.valueOf(year));
                values.add(
                        fahrten.stream()
                                .filter(f -> f.getDate().getYear() == year)
                                .mapToInt(Fahrt::getRiddenKM).sum()
                );
                start = start.plusYears(1);
            }
        }

        else throw new IllegalStateException("Choice not known - neitehr Monat nor Jahr");

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Kilometer");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setTension(0.1);

        ChartData data = new ChartData();
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        return data;
    }
}
