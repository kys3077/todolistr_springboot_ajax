function showTable(data) {
    let tablesrc = '';
    for (key in data) {
        tablesrc += '<tr><td id = ' + data[key].id + 'title>' + data[key].title + '</td><td id = ' + data[key].id + 'content>' + data[key].content
            + '</td><td id = ' + data[key].id + 'this_date>' + data[key].this_date + '</td><td><a href=javascript:deleteContent(' + data[key].id + ')>삭제</a></td>\n' +
            '                <td id = ' + data[key].id + 'update><a href=javascript:updateContent(' + data[key].id + ')>수정</a></td>'
            + '</tr>';
    }
    document.getElementById("tableBody").innerHTML = tablesrc;
    showTotalElements()
}

function insertContent() {
    totalel = totalel + 1;
    const form = document.form1;
    const num1 = form.input_title.value;
    const num2 = form.input_content.value;
    const action = "/" + "?page=" + page;

    $.ajax({
        url: action,
        type: "POST",
        data: {
            title: num1,
            content: num2
        },
        success: function (data) {
            $.ajax({
                url: "/list",
                type: "GET",
                success: function (data) {
                    showTable(data);
                }
            });
        }
    });
    document.getElementById("Panel").innerHTML = "등록되었습니다.";
}

function showRegisterForm() {
    document.getElementById("Panel").innerHTML = "";

    var txt = `<input type = "text" name = "input_title" placeholder="제목을 입력하세요"  style="width: 30%"/>
            <input type = "text" name = "input_content" placeholder="내용을 입력하세요" style="width: 50%;"/>`;
    document.getElementById("Panel").innerHTML += txt;
}