$(function () {

    $('form').on('submit', function (e) {

        e.preventDefault();
        console.log($('form').serialize());
        $.ajax({
            type: 'post',
            url: 'authors',
            data: $('form').serialize(),
            complete: function () {
                $("#edit").hide();
                $("#authors").show();
                reloadTable();
            }
        });

    });

});

function reloadTable() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/authors",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            $("#tbody").empty();
            for (var author of data) {
                $("#tbody").append(
                    "        <tr>\n" +
                    "            <td>" + author.id + "</td>\n" +
                    "            <td>" + author.name + "</td>" +
                    "            <td>\n" +
                    "                <a href='javascript: deleteAuthor(" + author.id + ")'>delete</a>\n" +
                    "            </td>\n" +
                    "            <td>\n" +
                    "                <a href='javascript: updateAuthor(" + author.id + ",\"" + author.name + "\")'>update</a>\n" +
                    "            </td>\n" +
                    "        </tr>\n"
                );
            }
            console.log(data)
        },
        error: function (e) {
            console.log(e)

        }
    })
}

function deleteAuthor(id) {
    $.ajax({
        type: "DELETE",
        url: "/authors" + '?' + $.param({"id": id}),
        dataType: 'JSON',
        cache: false,
        timeout: 600000,
        complete: function () {
            reloadTable();
        }
    });
}

function updateAuthor(id, name) {
    $("#edit").attr('hidden', false);
    $("#edit").show();
    $("#authors").hide();

    $("#idAuthor").val( id);
    $("#nameAuthor").val(name);

}

function createAuthor() {
    updateAuthor(null, '');
}
