import dayjs from 'dayjs';
import { getMonthCompleted, proportionalDifference } from './reportCalculations';

it('Proportional Difference between n-1 and n',() => {
    const month_completed = getMonthCompleted();
    const n_minus_1 = 1328;
    const n = 798;
    const expected_result = (n_minus_1 * month_completed) < n
    console.log(`\tn-1: ${n_minus_1}\n\tn: ${n}\n\tMonth Completed: ${month_completed}\n\t${(n_minus_1*month_completed)} < ${n}\n\tExpect: ${expected_result}`)
    const result = proportionalDifference(n_minus_1, n, month_completed);
    expect(result).toEqual(expected_result);
})

it('Proportion of Month Completed', () => {
    const d = new Date();
    d.setDate(d.getDate() - 1)
    const expected_result = d.getDate() / dayjs(d).daysInMonth();
    console.log(`\tDate Today: ${d.getDate()}\n\tMonth ${d.getMonth() + 1} has ${dayjs(d).daysInMonth()} days\n\tProportion on Month Completed: ${expected_result}`)
    const result = getMonthCompleted()
    expect(result).toEqual(expected_result);
})
