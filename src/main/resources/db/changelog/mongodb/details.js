db.createCollection("report_details");

db.report_details.insertMany([
    {
        "reportId": "223e4567-e89b-12d3-a456-426614174001",
        "financialData": "Sample financial data 1",
        "comments": "Sample comments 1"
    },
    {
        "reportId": "323e4567-e89b-12d3-a456-426614174002",
        "financialData": "Sample financial data 2",
        "comments": "Sample comments 2"
    },
    {
        "reportId": "423e4567-e89b-12d3-a456-426614174003",
        "financialData": "Sample financial data 3",
        "comments": "Sample comments 3"
    }
]);
