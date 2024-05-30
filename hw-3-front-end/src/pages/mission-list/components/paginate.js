export const paginate = (array, pageSize) => {
  return array.reduce((result, item, index) => {
    const pageIndex = Math.floor(index / pageSize);
    if (!result[pageIndex]) {
      result[pageIndex] = [];
    }
    result[pageIndex].push(item);
    return result;
  }, []);
};
