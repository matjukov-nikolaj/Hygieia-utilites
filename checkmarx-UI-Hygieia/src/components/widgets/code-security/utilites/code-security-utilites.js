function getDateFromTimestamp(timestamp) {
    let date = new Date(timestamp);
    return [date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()].join('-');
}