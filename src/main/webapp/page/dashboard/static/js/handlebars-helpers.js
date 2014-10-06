var DateFormats = {
	short : "DD MMMM - YYYY",
	long : "dddd DD.MM.YYYY HH:mm"
};

// Deprecated since version 0.8.0
Handlebars.registerHelper("formatDate", function(datetime, format) {
	if (moment) {
		f = DateFormats[format];
		return moment(datetime).format(f);
	} else {
		return datetime;
	}
});
