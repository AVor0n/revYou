import { getPluralForm } from './getPluralForm';

const timesLocale = {
  minute: ['минуту', 'минуты', 'минут'],
  hour: ['час', 'часа', 'часов'],
  day: ['день', 'дня', 'дней'],
  week: ['неделю', 'недели', 'недель'],
  month: ['месяц', 'месяца', 'месяцев'],
  year: ['год', 'года', 'лет'],
};

/**
 * Возвращает строку со временем, которое прошло до текущего момента.
 * Период от минуты до года
 * @param eventDate Дата события
 * @example
 * ```
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // 2 года назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // год назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // 3 месяца назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // месяц назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // 4 недели назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // 5 часов назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // 12 минут назад
 * dateRelativeNow(new Date('2024-03-04T10:29:28.265Z')) // менее минуты назад
 * ```
 */
export const dateRelativeNow = (eventDate: Date) => {
  const currentDate = new Date();
  const timeDifference = currentDate.getTime() - eventDate.getTime();

  const seconds = Math.floor(timeDifference / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);
  const weeks = Math.floor(hours / 7);
  const months = Math.floor(days / 30);
  const years = Math.floor(months / 12);

  const dict: [number, string[]][] = [
    [years, timesLocale.year],
    [months, timesLocale.month],
    [weeks, timesLocale.week],
    [days, timesLocale.day],
    [hours, timesLocale.hour],
    [minutes, timesLocale.minute],
  ];

  for (const [period, pluralTexts] of dict) {
    if (period > 0) {
      const pluralFormText = `${getPluralForm(period, pluralTexts)} назад`;
      return period === 1 ? pluralFormText : `${period} ${pluralFormText}`;
    }
  }

  return 'только что';
};
