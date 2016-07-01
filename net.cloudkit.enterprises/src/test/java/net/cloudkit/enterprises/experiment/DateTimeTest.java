package net.cloudkit.enterprises.experiment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTest {

    public static void main(String[] args) {
        System.out.println(SimpleDateFormat.getDateTimeInstance().format(new Date(1444817760000L)));
    }
}
