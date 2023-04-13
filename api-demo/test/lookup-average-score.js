// noinspection JSUnresolvedReference

client.test("average option exists", function() {
    client.assert(response.body.hasOwnProperty("average"), "Cannot find 'average' option in response");
});
