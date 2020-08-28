const nameValue = document.getElementById('name');
const ageValue = document.getElementById('age');
const passwordValue = document.getElementById('password');
const rolesValue = document.getElementById('roles');
const postsList = document.getElementById('output');

fetch('/rest/users')
    .then(res => res.json())
    .then(data => renderPosts(data))

//Вывод на страницу
    let output = '';

    const renderPosts = (posts) => {
        posts.forEach(post => {
            output += `
    <tr>
      <th id="id${post.id}" scope="row">${post.id}</th>
      <td id="name${post.id}">${post.name}</td>
      <td id="age${post.id}">${post.age}</td>
      <td id="password${post.id}">${post.password}</td>
      <td><button onclick="openEditModal(${post.id})" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Edit</button></td>
      <td><button onclick="openDeleteModal(${post.id})" class="btn btn-danger" data-toggle="modal" data-target="#deleteExampleModal">Delete</button></td> 
    </tr>   
`;
        });
        postsList.innerHTML = output;
    }

//открывает модалку удаления
function openDeleteModal(id) {
console.log(id);
    let name = document.getElementById('name'+id).textContent;
    let age = document.getElementById('age'+id).textContent;
    let password = document.getElementById('password'+id).textContent;

    $('#idDelete').val(id);
    $('#nameDelete').val(name);
    $('#ageDelete').val(age);
    $('#passwordDelete').val(password);

//кнопка для удаления в нутри модалки
    $('.buttonDelete').on('click', function (event) {
        event.preventDefault();
        fetch('/rest/users/delete/'+id, {
            method: 'DELETE',
        })
            .then(res => res.text())
            .then(() => location.reload())
    })

}


//открывает модалку редактирования
function openEditModal(id) {
    let name = document.getElementById('name'+id).textContent;
    let age = document.getElementById('age'+id).textContent;
    let password = document.getElementById('password'+id).textContent;

    $('#idEdit').val(id);
    $('#nameEdit').val(name);
    $('#ageEdit').val(age);
    $('#passwordEdit').val(password);
}

//кнопка в модалке для редактирования
$('.buttonEdit').on('click', function (event) {
    event.preventDefault();
    let users = {
        id: $('#idEdit').val(),
        name: $('#nameEdit').val(),
        age: $('#ageEdit').val(),
        password: $('#passwordEdit').val()
    };
    fetch('/rest/users/update', {
        method:'PUT',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-type':'application/json'
        },
        body:JSON.stringify(users)
    }).then(res => res.text())
        .then(() => location.reload())
})


$(document).ready(function() {
//Post - добавление данных
document.getElementById('addData').addEventListener('submit', addData);

//post
    function addData(e){
        e.preventDefault();

        console.log(rolesValue.value);

        fetch('/rest/users/add', {
            method:'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-type':'application/json'
            },
            body:JSON.stringify({
                name:nameValue.value,
                age:ageValue.value,
                password:passwordValue.value,
                role_id:rolesValue.value
            })
        })
            .then(res => res.json())
            .then(data => {
                const dataArr = [];
                dataArr.push(data);
                renderPosts(dataArr);
            })
        }
});