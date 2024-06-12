/**
 * Возвращает текст согласующийся с числом элементов
 * @param count число элементов
 * @param pluralTexts массив текстовок для 1 2 и 5 элементов
 * @example
 * ```
 * getPluralForm(42, ['элемент', 'элемента', 'элементов']) // 'элемента'
 * ```
 */
export const getPluralForm = (count: number, pluralTexts: string[]) => {
  if (count % 10 === 1 && count % 100 !== 11) {
    return pluralTexts[0];
  }

  if (count % 10 >= 2 && count % 10 <= 4 && (count % 100 < 10 || count % 100 >= 20)) {
    return pluralTexts[1];
  }

  return pluralTexts[2];
};
