package com.volodia.estafetatest.model;

import android.app.Service;

import java.io.Serializable;

/**
 * Created by Volodia on 27.11.2016.
 */

public class Task implements Serializable{
    long Id;                   //  "Id": 124906,
    String Number;             //  "Number": "2012/619199",
    String PlannedStartDate;   //  "PlannedStartDate": null,
    String PlannedEndDate;     //  "PlannedEndDate": null,
    String ActualStartDate;    //  "ActualStartDate": "2012-03-23T07:30:00Z",
    String ActualEndDate;      //  "ActualEndDate": "2012-03-23T07:30:00Z",
    String Vin;                //  "Vin": "VF1CRJN0646757203",
    String Model;              //  "Model": "Clio",
    String ModelCode;          //  "ModelCode": "CRJN06",
    String Brand;              //  "Brand": "Renault",
    String SurveyPoint;        //  "SurveyPoint": "Avtoleader-M",
    String Carrier;            //  "Carrier": "KATP 13061 PJSC",
    String Driver;             //  "Driver": "Чорноостровський Юрій Петрович"

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        return "Task{" + lineSeparator +
                "Id=" + Id + lineSeparator +
                " Number='" + Number + '\'' + lineSeparator +
                " PlannedStartDate='" + PlannedStartDate + '\'' + lineSeparator +
                " PlannedEndDate='" + PlannedEndDate + '\'' + lineSeparator +
                " ActualStartDate='" + ActualStartDate + '\'' + lineSeparator +
                " ActualEndDate='" + ActualEndDate + '\'' + lineSeparator +
                " Vin='" + Vin + '\'' + lineSeparator +
                " Model='" + Model + '\'' + lineSeparator +
                " ModelCode='" + ModelCode + '\'' + lineSeparator +
                " Brand='" + Brand + '\'' + lineSeparator +
                " SurveyPoint='" + SurveyPoint + '\'' + lineSeparator +
                " Carrier='" + Carrier + '\'' + lineSeparator +
                " Driver='" + Driver + '\'' + lineSeparator +
                '}';
    }

    public Task(String model, String brand, String carrier, String driver) {
        Model = model;
        Brand = brand;
        Carrier = carrier;
        Driver = driver;
    }

    public String getModel() {
        return Model;
    }

    public String getBrand() {
        return Brand;
    }

    public String getCarrier() {
        return Carrier;
    }

    public String getDriver() {
        return Driver;
    }
}
