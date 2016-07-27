package com.github.javierugarte;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class ContributionsProvider {

    public final static String FILL_STRING = "fill=\"";
    public final static String DATA_STRING = "data-count=\"";
    public final static String DATE_STRING = "data-date=\"";

    public List<ContributionsDay> getContributions(String string, int lastWeeks) {
        ArrayList<ContributionsDay> contributions = new ArrayList<>();
        int fillPos = -1;
        int dataPos = -1;
        int datePos = -1;
        while (true) {
            fillPos = string.indexOf(FILL_STRING, fillPos + 1);
            dataPos = string.indexOf(DATA_STRING, dataPos + 1);
            datePos = string.indexOf(DATE_STRING, datePos + 1);

            if (fillPos == -1) break;

            int level = 0;
            String levelString
                    = string.substring(fillPos + FILL_STRING.length(),
                    fillPos + FILL_STRING.length() + 7);
            switch (levelString) {
                case "#eeeeee": level = 0; break;
                case "#d6e685": level = 1; break;
                case "#8cc665": level = 2; break;
                case "#44a340": level = 3; break;
                case "#1e6823": level = 4; break;
            }

            int dataEndPos = string.indexOf("\"", dataPos + DATA_STRING.length());
            String dataString = string.substring(dataPos + DATA_STRING.length(), dataEndPos);
            int data = Integer.valueOf(dataString);

            String dateString
                    = string.substring(datePos + DATE_STRING.length(),
                    datePos + DATE_STRING.length() + 11);

            contributions.add(new ContributionsDay(
                    Integer.valueOf(dateString.substring(0, 4)),
                    Integer.valueOf(dateString.substring(5, 7)),
                    Integer.valueOf(dateString.substring(8, 10)),
                    level,
                    data
            ));
        }

        return getLastContributions(contributions, lastWeeks);
    }

    private List<ContributionsDay> getLastContributions(
            ArrayList<ContributionsDay> contributions,
            int lastWeeks) {

        int lastWeekDays = contributions.size() % 7;
        int lastDays = (lastWeekDays > 0) ? lastWeekDays + (lastWeeks-1) * 7 : lastWeeks * 7;

        return contributions.subList(contributions.size()-lastDays, contributions.size());
    }
}
