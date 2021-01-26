
function showTable(data) {
    let tablesrc = '';
    for (let i = 0; i < data.content.length; i++) {
        tablesrc += '<tr><td id = ' + data.content[i].id + 'title>' + data.content[i].title + '</td><td id = ' + data.content[i].id + 'content>' + data.content[i].content
            + '</td><td id = ' + data.content[i].id + 'this_date>' + data.content[i].this_date + '</td><td><a href=javascript:deleteContent(' + data.content[i].id + ')>삭제</a></td>\n' +
            '                <td id = ' + data.content[i].id + 'update><a href=javascript:updateContent(' + data.content[i].id + ')>수정</a></td>'
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

    const action = "/doRegister/" + "?page=" + page;

    $.post(//get방식으로
        action,//action으로
        {//편지 내용을 보낸다
            title: num1,
            content: num2
        },//네트워크로 서버로 갔다가 돌아올때 실행 되는 것이 아래 function
        showTable
    );
    document.getElementById("Panel").innerHTML = "등록되었습니다.";
}

function showRegisterForm() {
    document.getElementById("Panel").innerHTML = "";

    var txt = `<input type = "text" name = "input_title" placeholder="제목을 입력하세요"  style="width: 30%"/>
            <input type = "text" name = "input_content" placeholder="내용을 입력하세요" style="width: 50%;"/>`;
    document.getElementById("Panel").innerHTML += txt;
}