import dayjs from "dayjs";

// Return true if this month is greater than last month
export function proportionalDifference(n_1: number, n: number, month_completed: number) {
    return (n_1 * month_completed) < n;
}

// Return yesterdays date because the report is generated over night
// from the perpective of the day that was
export function getMonthCompleted() {
    const d = new Date();
    d.setDate(d.getDate() - 1)
    return d.getDate() / dayjs(d).daysInMonth();
}