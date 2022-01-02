export function toTitleCase(str: string) {
  if (typeof str === 'undefined') {
    return str;
  }
  return str.replace(
    /\w\S*/g,
    function (txt) {
      // let titleCase = txt.replace('_',' ')
      return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    }
  );
}

export function camelCaseToTitleCase(str: string) {
  if (typeof str === 'undefined') {
    return str;
  }
  let result = str.split("_").join(" ")
  return toTitleCase(result);
}