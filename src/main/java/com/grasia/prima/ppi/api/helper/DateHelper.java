package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.time.LocalDate;
import java.time.Period;

public final class DateHelper {

    private DateHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static String getElapsedTime(LocalDate startDate) {
        LocalDate today = LocalDate.now();
        Period period = calculatePeriod(startDate, today);
        String suffix = determineSuffix(startDate, today);
        return formatDuration(period, suffix);
    }

    private static Period calculatePeriod(LocalDate startDate, LocalDate today) {
        if (startDate.isBefore(today)) {
            return Period.between(startDate, today);
        }
        return Period.between(today, startDate);
    }

    private static String determineSuffix(LocalDate startDate, LocalDate today) {
        if (startDate.isBefore(today)) {
            return "ago";
        }
        return "from now";
    }

    private static String formatDuration(Period period, String suffix) {
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        if (years > 0) {
            return years + " years " + suffix;
        }
        if (months > 0) {
            return months + " months " + suffix;
        }
        if (days > 0) {
            return days + " days " + suffix;
        }
        return "today";
    }
}
